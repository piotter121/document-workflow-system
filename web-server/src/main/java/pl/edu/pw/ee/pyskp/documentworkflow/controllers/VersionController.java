package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;
import pl.edu.pw.ee.pyskp.documentworkflow.validators.NewVersionFormValidator;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by p.pysk on 16.01.2017.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/files/{fileId}/versions")
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
    public VersionInfoDTO getVersionInfo(@PathVariable long versionSaveDateMillis,
                                         @PathVariable UUID fileId,
                                         @PathVariable UUID taskId,
                                         @PathVariable UUID projectId) throws VersionNotFoundException {
        return versionService.getVersionInfo(fileId, versionSaveDateMillis);
//        model.addAttribute("diffData", diffData);
//        model.addAttribute("taskId", taskId.toString());
//        model.addAttribute("projectId", projectId.toString());
//        model.addAttribute("fileId", fileId.toString());
//        model.addAttribute("fileName", filesMetadataService.getFileName(taskId, fileId));
//        return "version";
    }

    @GetMapping("/{versionSaveDateMillis}/diffData")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public DiffData getDiffData(@PathVariable UUID projectId, @PathVariable UUID taskId, @PathVariable UUID fileId,
                                @PathVariable long versionSaveDateMillis) throws VersionNotFoundException {
        return versionService.buildDiffData(fileId, versionSaveDateMillis);
    }

    @GetMapping("/{versionSaveDate}/content")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public ResponseEntity<InputStreamResource>
    getVersionContent(@PathVariable long versionSaveDate, @PathVariable UUID projectId, @PathVariable UUID taskId,
                      @PathVariable UUID fileId)
            throws ResourceNotFoundException {
        InputStream fileContent = versionService.getVersionFileContent(fileId, new Date(versionSaveDate));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment",
                filesMetadataService.getFileName(taskId, fileId));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new InputStreamResource(fileContent));
    }

//    @GetMapping("/add")
//    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
//    public String getNewVersionForm(Model model,
//                                    @ModelAttribute NewVersionForm form,
//                                    @PathVariable UUID fileId,
//                                    @PathVariable UUID taskId,
//                                    @PathVariable UUID projectId) {
//        return getAddVersion(fileId, taskId, projectId, model);
//    }

//    @PostMapping("/add")
//    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
//    public String processAddingNewVersion(@PathVariable UUID fileId,
//                                          @PathVariable UUID taskId,
//                                          @PathVariable UUID projectId,
//                                          @ModelAttribute NewVersionForm form,
//                                          BindingResult bindingResult,
//                                          Model model) throws IOException, TaskNotFoundException {
//        form.setFileId(fileId);
//        form.setTaskId(taskId);
//        form.setProjectId(projectId);
//        newVersionFormValidator.validate(form, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return getAddVersion(fileId, taskId, projectId, model);
//        }
//
//        long versionId = versionService.addNewVersionOfFile(form);
//
//        return String.format("redirect:/projects/%s/tasks/%s/files/%s/versions/%d",
//                projectId.toString(), taskId.toString(), fileId.toString(), versionId);
//    }

//    private String getAddVersion(UUID fileId, UUID taskId, UUID projectId, Model model) {
//        addCurrentUserToModel(model);
//        model.addAttribute("projectId", projectId.toString());
//        model.addAttribute("taskId", taskId.toString());
//        model.addAttribute("fileId", fileId.toString());
//        model.addAttribute("fileName", filesMetadataService.getFileName(taskId, fileId));
//        return "addVersion";
//    }
//
//    private void addCurrentUserToModel(Model model) {
//        model.addAttribute("currentUser", new UserInfoDTO(userService.getCurrentUser()));
//    }
}


