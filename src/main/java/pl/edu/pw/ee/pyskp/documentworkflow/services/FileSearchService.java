package pl.edu.pw.ee.pyskp.documentworkflow.services;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.pyskp.documentworkflow.config.ElasticsearchQueryConfiguration;
import pl.edu.pw.ee.pyskp.documentworkflow.config.ElasticsearchQueryConfiguration.HighlightConfig;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.ProjectRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.search.SearchResultEntry;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ProjectNotFoundException;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileSearchService {
    @NonNull
    private final JestClient jestClient;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final FilesMetadataService filesMetadataService;

    @NonNull
    private final TaskRepository taskRepository;

    @NonNull
    private final ProjectRepository projectRepository;

    @NonNull
    private final ElasticsearchQueryConfiguration queryConfiguration;

    @NonNull
    private final SecurityService securityService;

    @SneakyThrows(IOException.class)
    @Transactional(readOnly = true)
    public List<SearchResultEntry> searchInProject(UUID projectId, String searchPhrase)
            throws ProjectNotFoundException {
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId.toString()));

        List<UUID> taskIds = taskRepository.findAllByProjectId(projectId).stream()
                .filter(task -> securityService.hasCurrentUserAccessToTask(project, task))
                .map(Task::getTaskId)
                .collect(Collectors.toList());

        Map<UUID, UUID> fileIdToTaskId = fileMetadataRepository.findByTaskIdIn(taskIds)
                .collect(Collectors.toMap(FileMetadata::getFileId, FileMetadata::getTaskId));

        return doSearch(searchPhrase, fileIdToTaskId);
    }

    @SneakyThrows(IOException.class)
    @Transactional(readOnly = true)
    public List<SearchResultEntry> searchInTask(UUID taskId, String searchPhrase) {
        List<UUID> fileIds = fileMetadataRepository.findByTaskId(taskId)
                .map(FileMetadata::getFileId)
                .collect(Collectors.toList());

        Map<UUID, UUID> fileIdToTaskId = fileIds.stream()
                .collect(Collectors.toMap(Function.identity(), (fileId) -> taskId));

        return doSearch(searchPhrase, fileIdToTaskId);
    }

    private List<SearchResultEntry> doSearch(String searchPhrase, Map<UUID, UUID> fileIdToTaskId) throws IOException {
        Set<String> fileIds = fileIdToTaskId.keySet()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery(Version.Fields.PARSED_FILE_CONTENT_FIELD, searchPhrase))
                .filter(QueryBuilders.termsQuery(Version.Fields.FILE_ID_FIELD, fileIds));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(query)
                .highlighter(getNewHighlighter());

        String queryString = searchSourceBuilder.toString();
        if (log.isDebugEnabled()) {
            log.debug("Searching with query: " + queryString);
        }

        Search search = new Search.Builder(queryString)
                .addIndex(queryConfiguration.getIndexName())
                .addType(queryConfiguration.getTypeName())
                .build();

        SearchResult searchResult = jestClient.execute(search);
        if (searchResult.isSucceeded()) {
            return searchResult
                    .getHits(Version.class)
                    .stream()
                    .map((SearchResult.Hit<Version, Void> hit) -> readSearchResultEntry(hit, fileIdToTaskId))
                    .collect(Collectors.toList());
        } else {
            log.error("Search not accomplished properly: {}", searchResult.getErrorMessage());
            return Collections.emptyList();
        }
    }

    private HighlightBuilder getNewHighlighter() {
        HighlightConfig highlightConfig = queryConfiguration.getHighlight();
        return new HighlightBuilder()
                .encoder(highlightConfig.getEncoder())
                .tagsSchema(highlightConfig.getTagsSchema())
                .field(Version.Fields.PARSED_FILE_CONTENT_FIELD);
    }

    @SneakyThrows(FileNotFoundException.class)
    private SearchResultEntry readSearchResultEntry(SearchResult.Hit<Version, Void> hit,
                                                    Map<UUID, UUID> fileIdToTaskId) {
        Version version = hit.source;
        UUID fileId = version.getFileId();
        UUID taskId = fileIdToTaskId.get(fileId);
        FileMetadata fileMetadata = filesMetadataService.getFileMetadata(taskId, fileId);
        return new SearchResultEntry(
                String.valueOf(fileMetadata.getTaskId()),
                fileMetadata.getTaskName(),
                String.valueOf(fileId),
                fileMetadata.getName().concat(".").concat(fileMetadata.getContentType().getExtension()),
                version.getVersionString(),
                hit.highlight.get(Version.Fields.PARSED_FILE_CONTENT_FIELD)
        );
    }
}
