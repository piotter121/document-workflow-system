package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.ProjectNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import java.util.Date;
import java.util.List;
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
    public ProjectInfoDTO getOneByName(String name) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findOneByName(name);
        if (!project.isPresent())
            throw new ProjectNotFoundException(name);
        return ProjectService.mapToProjectInfoDTO(project.get());
    }

    @Override
    public List<ProjectInfoDTO> findAllAdministratedProjects(String login) throws UserNotFoundException {
        Optional<User> user = userService.getUserByLogin(login);
        if (user.isPresent())
            return ProjectService.mapAllToProjectInfoDTO(user.get().getAdministratedProjects());
        else
            throw new UserNotFoundException(login);
    }

    @Override
    public List<ProjectInfoDTO> findAllProjectsWhereUserIsParticipant(String login) throws UserNotFoundException {
        Optional<User> user = userService.getUserByLogin(login);
        if (user.isPresent())
            return ProjectService.mapAllToProjectInfoDTO(user.get().getParticipatedProjects());
        else
            throw new UserNotFoundException(login);
    }

    @Override
    public Project createNewProjectFromForm(CreateProjectFormDTO formDTO) {
        Project project = new Project();
        project.setName(formDTO.getName());
        project.setDescription(formDTO.getDescription());
        project.setAdministrator(userService.getUserByLogin(formDTO.getAdministratorLogin()).get());
        project.setCreationDate(new Date());
        return projectRepository.save(project);
    }

    @Override
    public boolean existsProjectWithName(String name) {
        return projectRepository.findOneByName(name).isPresent();
    }
}
