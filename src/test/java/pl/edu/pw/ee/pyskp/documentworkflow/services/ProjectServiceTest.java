package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ProjectSummaryDTO;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void getProjects() {
        List<ProjectSummaryDTO> projects = projectService.getUserParticipatedProjects("pyskp");
        assertThat(projects).isNotEmpty();
    }

    @Test
    public void createProject() {
        NewProjectForm form = new NewProjectForm();
        form.setName("Testowy projekt");
        form.setDescription("Testowy opis");
        UUID projectId = projectService.createNewProjectFromForm(form);
        assertThat(projectId).isNotNull();
        ProjectInfoDTO projectInfo = projectService.getProjectInfo(projectId);
        assertThat(projectInfo).hasNoNullFieldsOrProperties();
    }

}
