package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewProjectForm;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import java.util.Date;
import java.util.Optional;

/**
 * Created by piotr on 29.12.16.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final UserService userService;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(UserService userService,
                              ProjectRepository projectRepository) {
        this.userService = userService;
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Project> getOneById(Long id) {
        return Optional.ofNullable(projectRepository.findOne(id));
    }

    @Override
    public Optional<Project> getOneByName(String name) {
        return projectRepository.findOneByName(name);
    }

    @Override
    public Project createNewProjectFromForm(NewProjectForm formDTO) {
        Project project = new Project();
        project.setName(formDTO.getName());
        project.setDescription(formDTO.getDescription());
        project.setAdministrator(userService.getCurrentUser());
        project.setCreationDate(new Date());
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(long projectId) {
        projectRepository.delete(projectId);
    }
}
