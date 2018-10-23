package pl.edu.pw.ee.pyskp.documentworkflow.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.search.SearchResultEntry;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileSearchService {
    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final SecurityService securityService;

    @Transactional(readOnly = true)
    public List<SearchResultEntry> searchInProject(ObjectId projectId, String searchPhrase) {
        List<Task> tasks = taskRepository.findByProject_Id(projectId)
                .filter(securityService::hasCurrentUserAccessToTask)
                .collect(Collectors.toList());

        List<FileMetadata> files = fileMetadataRepository.findByTaskIn(tasks);

        return doSearch(searchPhrase, files);
    }

    @Transactional(readOnly = true)
    public List<SearchResultEntry> searchInTask(ObjectId taskId, String searchPhrase) {
        List<FileMetadata> files = fileMetadataRepository.findByTask_Id(taskId);

        return doSearch(searchPhrase, files);
    }

    private List<SearchResultEntry> doSearch(String searchPhrase, List<FileMetadata> files) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(searchPhrase);

        return versionRepository.findByFileInOrderByScoreDesc(files, criteria)
                .map(version -> {
                    FileMetadata fileMetadata = version.getFile();
                    Task task = fileMetadata.getTask();
                    return new SearchResultEntry(
                            task.getId().toString(),
                            task.getName(),
                            fileMetadata.getId().toString(),
                            fileMetadata.getName(),
                            version.getVersionString()
                    );
                }).collect(Collectors.toList());
    }
}
