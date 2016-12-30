package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.CreateProjectFormDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UserNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.ProjectService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by piotr on 29.12.16.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final UserService userService;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(UserService userService, ProjectRepository projectRepository) {
        this.userService = userService;
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Project> getOneByName(String name) {
        return projectRepository.findOneByName(name);
    }

    @Override
    public List<ProjectInfoDTO> findAllAdministratedProjects(String login) throws UserNotFoundException {
        Optional<User> user = userService.getUserByLogin(login);
        if (user.isPresent())
            return mapAllToProjectInfoDTO(user.get().getAdministratedProjects());
        else
            throw new UserNotFoundException(login);
    }

    @Override
    public List<ProjectInfoDTO> findAllProjectsWhereUserIsParticipant(String login) throws UserNotFoundException {
        Optional<User> user = userService.getUserByLogin(login);
        if (user.isPresent())
            return mapAllToProjectInfoDTO(user.get().getParticipatedProjects());
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

    private static List<ProjectInfoDTO> mapAllToProjectInfoDTO(Collection<Project> projects) {
        return projects != null
                ? projects.parallelStream().map(ProjectServiceImpl::mapToProjectInfoDTO).collect(Collectors.toList())
                : Collections.emptyList();
    }

    private static ProjectInfoDTO mapToProjectInfoDTO(Project project) {
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setCreationDate(project.getCreationDate());
        dto.setAdministratorName(project.getAdministrator().getPersonalData().getFullName());
        Optional<Date> lastModified = project.getLastModified();
        if (lastModified.isPresent())
            dto.setLastModified(lastModified.get());
        List<Task> tasks = project.getTasks();
        dto.setNumberOfTasks(tasks == null ? 0 : tasks.size());
        return dto;
    }
}
