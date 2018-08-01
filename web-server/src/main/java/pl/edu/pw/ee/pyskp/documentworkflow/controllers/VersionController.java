package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.NewVersionFormValidator;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by p.pysk on 16.01.2017.
 */
//@Controller
@RequiredArgsConstructor
@SuppressWarnings("SameReturnValue")
@RequestMapping("/projects/{projectId}/tasks/{taskId}/files/{fileId}/versions")
public class VersionController {

    @NonNull
    private final VersionService versionService;

    @NonNull
    private final UserService userService;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final FilesMetadataService filesMetadataService;

    @NonNull
    private final NewVersionFormValidator newVersionFormValidator;

    @GetMapping("/{versionSaveDateMillis}")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public String getVersionInfo(Model model,
                                 @PathVariable long versionSaveDateMillis,
                                 @PathVariable UUID fileId,
                                 @PathVariable UUID taskId,
                                 @PathVariable UUID projectId) {
        model.addAttribute("version", versionService.getVersionInfo(fileId, versionSaveDateMillis));
        DiffData diffData = versionService.buildDiffData(fileId, versionSaveDateMillis);
        model.addAttribute("diffData", diffData);
        addCurrentUserToModel(model);
        model.addAttribute("taskId", taskId.toString());
        model.addAttribute("projectId", projectId.toString());
        model.addAttribute("fileId", fileId.toString());
        model.addAttribute("fileName", filesMetadataService.getFileName(taskId, fileId));
        return "version";
    }

    @GetMapping("/{versionSaveDate}/content")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public ResponseEntity<byte[]> getVersionContent(@PathVariable long versionSaveDate,
                                                    @PathVariable UUID projectId,
                                                    @PathVariable UUID taskId,
                                                    @PathVariable UUID fileId) {
        Version version = versionRepository.findOneByFileIdAndSaveDate(fileId, new Date(versionSaveDate))
                .orElseThrow(() -> new VersionNotFoundException(versionSaveDate));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment",
                filesMetadataService.getFileName(taskId, fileId));
        return new ResponseEntity<>(version.getFileContent().array(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/add")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public String getNewVersionForm(Model model,
                                    @ModelAttribute NewVersionForm form,
                                    @PathVariable UUID fileId,
                                    @PathVariable UUID taskId,
                                    @PathVariable UUID projectId) {
        return getAddVersion(fileId, taskId, projectId, model);
    }

    @PostMapping("/add")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public String processAddingNewVersion(@PathVariable UUID fileId,
                                          @PathVariable UUID taskId,
                                          @PathVariable UUID projectId,
                                          @ModelAttribute NewVersionForm form,
                                          BindingResult bindingResult,
                                          Model model) throws IOException, TaskNotFoundException {
        form.setFileId(fileId);
        form.setTaskId(taskId);
        form.setProjectId(projectId);
        newVersionFormValidator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) {
            return getAddVersion(fileId, taskId, projectId, model);
        }

        long versionId = versionService.addNewVersionOfFile(form);

        return String.format("redirect:/projects/%s/tasks/%s/files/%s/versions/%d",
                projectId.toString(), taskId.toString(), fileId.toString(), versionId);
    }

    private String getAddVersion(UUID fileId, UUID taskId, UUID projectId, Model model) {
        addCurrentUserToModel(model);
        model.addAttribute("projectId", projectId.toString());
        model.addAttribute("taskId", taskId.toString());
        model.addAttribute("fileId", fileId.toString());
        model.addAttribute("fileName", filesMetadataService.getFileName(taskId, fileId));
        return "addVersion";
    }

    private void addCurrentUserToModel(Model model) {
        model.addAttribute("currentUser", new UserInfoDTO(userService.getCurrentUser()));
    }
}
