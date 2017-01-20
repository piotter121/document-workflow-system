package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileContent;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.TaskService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.VersionService;
import pl.edu.pw.ee.pyskp.documentworkflow.validator.NewVersionFormValidator;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by p.pysk on 16.01.2017.
 */
@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/files/{fileId}/versions")
public class VersionController {
    private final static Logger logger = Logger.getLogger(VersionController.class);

    private final VersionService versionService;
    private final UserService userService;
    private final TaskService taskService;
    private final FilesMetadataService filesMetadataService;
    private final NewVersionFormValidator newVersionFormValidator;

    public VersionController(VersionService versionService,
                             UserService userService,
                             TaskService taskService,
                             FilesMetadataService filesMetadataService,
                             NewVersionFormValidator newVersionFormValidator) {
        this.versionService = versionService;
        this.userService = userService;
        this.taskService = taskService;
        this.filesMetadataService = filesMetadataService;
        this.newVersionFormValidator = newVersionFormValidator;
    }

    @GetMapping("/{versionId}")
    @PreAuthorize("@securityService.hasAccessToFile(#fileId)")
    public String getVersionInfo(Model model,
                                 @PathVariable long versionId,
                                 @PathVariable long fileId,
                                 @PathVariable long taskId) {

        Version version = versionService.getOneById(versionId)
                .orElseThrow(() -> new VersionNotFoundException(versionId));
        model.addAttribute("version", VersionService.mapToVersionInfoDTO(version));
        Optional<Version> previousVersionOpt = version.getPreviousVersion();
        previousVersionOpt.ifPresent(previousVersion ->
                model.addAttribute("previousVersion", VersionService.mapToVersionInfoDTO(previousVersion)));
        DiffData diffData = versionService.buildDiffData(versionId);
        model.addAttribute("diffData", diffData);
        addCurrentUserToModel(model);
        addTaskToModel(model, taskId);
        addFileToModel(model, fileId);

        return "version";
    }

    private void addFileToModel(Model model, long fileId) {
        model.addAttribute("file",
                filesMetadataService.getOneById(fileId)
                        .map(FilesMetadataService::mapToFileMetadataDTO)
                        .orElseThrow(() -> new FileNotFoundException(fileId)));
    }

    @GetMapping("/{versionId}/content")
    @PreAuthorize("@securityService.hasAccessToFile(#fileId)")
    public ResponseEntity<byte[]> getVersionContent(@PathVariable long versionId, @PathVariable long fileId) {
        FileContent fileContent = versionService.getOneById(versionId)
                .orElseThrow(() -> new VersionNotFoundException(versionId))
                .getFileContent();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment",
                fileContent.getVersion().getFileMetadata().getFileName());
        return new ResponseEntity<>(fileContent.getContent(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/add")
    @PreAuthorize("@securityService.hasAccessToFile(#fileId)")
    public String getNewVersionForm(Model model,
                                    @ModelAttribute NewVersionForm form,
                                    @PathVariable long fileId,
                                    @PathVariable long taskId) {
        addCurrentUserToModel(model);
        addTaskToModel(model, taskId);
        return "addVersion";
    }

    @PostMapping("/add")
    @PreAuthorize("@securityService.hasAccessToFile(#fileId)")
    public String processAddingNewVersion(@PathVariable long fileId,
                                          @PathVariable long taskId,
                                          @PathVariable long projectId,
                                          @ModelAttribute NewVersionForm form,
                                          BindingResult bindingResult,
                                          Model model)
            throws IOException {
        form.setFileId(fileId);
        newVersionFormValidator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) {
            addCurrentUserToModel(model);
            addTaskToModel(model, taskId);
            return "addVersion";
        }

        long versionId = versionService.addNewVersionOfFile(form).getId();

        return String.format("redirect:/projects/%d/tasks/%d/files/%d/versions/%d",
                projectId, taskId, fileId, versionId);
    }

    private void addTaskToModel(Model model, @PathVariable long taskId) {
        TaskInfoDTO task = taskService.getTaskById(taskId)
                .map(TaskService::mapToTaskInfoDto)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        model.addAttribute("task", task);
    }

    private void addCurrentUserToModel(Model model) {
        model.addAttribute("currentUser", UserService.mapToUserInfoDTO(userService.getCurrentUser()));
    }
}
