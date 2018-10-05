package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.FilesMetadataService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by p.pysk on 16.01.2017.
 */
@SuppressWarnings("MVCPathVariableInspection")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/files/{fileId}/versions")
public class VersionController {

    @NonNull
    private final VersionService versionService;

    @NonNull
    private final FilesMetadataService filesMetadataService;

    @GetMapping("/{versionSaveDate}")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public VersionInfoDTO getVersionInfo(@PathVariable Long versionSaveDate, @PathVariable Long fileId,
                                         @PathVariable Long taskId) throws VersionNotFoundException {
        return versionService.getVersionInfo(fileId, new Date(versionSaveDate));
    }

    @GetMapping("/{versionSaveDate}/diffData")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public DiffData getDiffData(@PathVariable Long taskId, @PathVariable Long fileId,
                                @PathVariable Long versionSaveDate) throws VersionNotFoundException {
        return versionService.buildDiffData(fileId, new Date(versionSaveDate));
    }

    @GetMapping("/{versionSaveDate}/content")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public ResponseEntity<InputStreamResource> getVersionContent(@PathVariable Long versionSaveDate,
                                                                 @PathVariable Long taskId, @PathVariable Long fileId)
            throws ResourceNotFoundException {
        InputStream fileContent = versionService.getVersionFileContent(fileId, new Date(versionSaveDate));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", filesMetadataService.getFileName(fileId));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new InputStreamResource(fileContent));
    }

    @GetMapping("/exists")
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public boolean exists(@PathVariable Long taskId, @PathVariable Long fileId, @RequestParam String versionString) {
        return versionService.existsByVersionString(fileId, versionString);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@securityService.hasAccessToTask(#taskId)")
    public Long processAddingNewVersion(@PathVariable Long taskId, @PathVariable Long fileId,
                                        @RequestPart("file") @NotNull MultipartFile file,
                                        @RequestPart("versionString") @NotBlank String versionString,
                                        @RequestPart("message") @NotBlank String message)
            throws ResourceNotFoundException {
        NewVersionForm versionForm = new NewVersionForm();
        versionForm.setFileId(fileId);
        versionForm.setFile(file);
        versionForm.setVersionString(versionString);
        versionForm.setMessage(message);
        return versionService.addNewVersionOfFile(versionForm).getTime();
    }

}


