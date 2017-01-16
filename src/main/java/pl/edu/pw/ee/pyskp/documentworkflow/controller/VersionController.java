package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileContent;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.VersionService;

/**
 * Created by p.pysk on 16.01.2017.
 */
@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/files/{fileId}/versions")
public class VersionController {
    private final VersionService versionService;
    private final UserService userService;

    public VersionController(VersionService versionService, UserService userService) {
        this.versionService = versionService;
        this.userService = userService;
    }

    @GetMapping("/{versionId}")
    @PreAuthorize("@securityService.hasAccessToFile(#fileId)")
    public String getVersionInfo(Model model, @PathVariable long versionId, @PathVariable long fileId) {
        model.addAttribute("version",
                versionService.getOneById(versionId)
                        .map(VersionService::mapToVersionInfoDTO)
                        .orElseThrow(() -> new VersionNotFoundException(versionId)));
        addCurrentUserToModel(model);

        return "version";
    }

    @GetMapping("/{versionId}/content")
    @PreAuthorize("@securityService.hasAccessToFile(#fileId)")
    public ResponseEntity<byte[]> getVersionContent(@PathVariable long versionId, @PathVariable long fileId) {
        FileContent fileContent = versionService.getOneById(versionId)
                .orElseThrow(() -> new VersionNotFoundException(versionId))
                .getFileContent();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", fileContent.getVersion().getFileMetadata().getFileName());
        return new ResponseEntity<>(fileContent.getContent(), httpHeaders, HttpStatus.OK);
    }

    private void addCurrentUserToModel(Model model) {
        model.addAttribute("currentUser", UserService.mapToUserInfoDTO(userService.getCurrentUser()));
    }
}
