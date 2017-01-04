package pl.edu.pw.ee.pyskp.documentworkflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 04.01.17.
 */
@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/files")
public class FilesMetadataController {
    @GetMapping
    public String redirectToTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return String.format("/projects/%d/tasks/%d", projectId, taskId);
    }

    @GetMapping("/add")
    public String getNewFileForm() {
        return "addFile";
    }
}
