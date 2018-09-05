package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;

import javax.validation.constraints.NotNull;
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
    private final FilesMetadataService filesMetadataService;

    @GetMapping("/{versionSaveDateMillis}")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public VersionInfoDTO getVersionInfo(@PathVariable long versionSaveDateMillis,
                                         @PathVariable ObjectId fileId,
                                         @PathVariable ObjectId taskId,
                                         @PathVariable ObjectId projectId) throws VersionNotFoundException {
        return versionService.getVersionInfo(fileId, versionSaveDateMillis);
    }

    @GetMapping("/{versionSaveDateMillis}/diffData")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public DiffData getDiffData(@PathVariable ObjectId projectId, @PathVariable ObjectId taskId,
                                @PathVariable ObjectId fileId, @PathVariable long versionSaveDateMillis)
            throws VersionNotFoundException {
        return versionService.buildDiffData(fileId, versionSaveDateMillis);
    }

    @GetMapping("/{versionSaveDate}/content")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public ResponseEntity<InputStreamResource> getVersionContent(
            @PathVariable long versionSaveDate, @PathVariable ObjectId projectId,
            @PathVariable ObjectId taskId, @PathVariable ObjectId fileId
    ) throws ResourceNotFoundException {
        InputStream fileContent = versionService.getVersionFileContent(fileId, new Date(versionSaveDate));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", filesMetadataService.getFileName(fileId));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new InputStreamResource(fileContent));
    }

    @GetMapping("/exists")
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public boolean exists(@PathVariable ObjectId projectId, @PathVariable ObjectId taskId,
                          @PathVariable ObjectId fileId, @RequestParam String versionString) {
        return versionService.existsByVersionString(fileId, versionString);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@securityService.hasAccessToTask(#projectId, #taskId)")
    public long processAddingNewVersion(@PathVariable ObjectId taskId,
                                        @PathVariable ObjectId projectId,
                                        @PathVariable ObjectId fileId,
                                        @RequestPart("file") @NotNull MultipartFile file,
                                        @RequestPart("versionString") @NotBlank String versionString,
                                        @RequestPart("message") @NotBlank String message)
            throws ResourceNotFoundException {
        NewVersionForm versionForm = new NewVersionForm();
        versionForm.setProjectId(projectId);
        versionForm.setTaskId(taskId);
        versionForm.setFileId(fileId);
        versionForm.setFile(file);
        versionForm.setVersionString(versionString);
        versionForm.setMessage(message);
        return versionService.addNewVersionOfFile(versionForm);
    }

}


