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
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.TaskRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.search.SearchResultEntry;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
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
    private final TaskRepository taskRepository;

    @NonNull
    private final ElasticsearchQueryConfiguration queryConfiguration;

    @NonNull
    private final SecurityService securityService;

    @SneakyThrows(IOException.class)
    @Transactional(readOnly = true)
    public List<SearchResultEntry> searchInProject(long projectId, String searchPhrase) {
        List<Long> taskIds = taskRepository.findByProject_Id(projectId)
                .filter(securityService::hasCurrentUserAccessToTask)
                .map(Task::getId)
                .collect(Collectors.toList());

        List<Long> fileIds = fileMetadataRepository.findIdByTask_IdIn(taskIds);

        return doSearch(searchPhrase, fileIds);
    }

    @SneakyThrows(IOException.class)
    @Transactional(readOnly = true)
    public List<SearchResultEntry> searchInTask(long taskId, String searchPhrase) {
        List<Long> fileIds = fileMetadataRepository.findIdByTask_Id(taskId);

        return doSearch(searchPhrase, fileIds);
    }

    private List<SearchResultEntry> doSearch(String searchPhrase, Collection<Long> fileIds) throws IOException {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery(Version.SerializedFields.PARSED_FILE_CONTENT_FIELD, searchPhrase))
                .filter(QueryBuilders.termsQuery(Version.SerializedFields.FILE_ID_FIELD, fileIds));

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

        return jestClient.execute(search)
                .getHits(Version.class)
                .stream()
                .map(this::readSearchResultEntry)
                .collect(Collectors.toList());
    }

    private HighlightBuilder getNewHighlighter() {
        HighlightConfig highlight = queryConfiguration.getHighlight();
        return new HighlightBuilder()
                .encoder(highlight.getEncoder())
                .tagsSchema(highlight.getTagsSchema())
                .field(Version.SerializedFields.PARSED_FILE_CONTENT_FIELD);
    }

    private SearchResultEntry readSearchResultEntry(SearchResult.Hit<Version, Void> hit) {
        Version version = hit.source;
        long fileId = version.getFileId();
        FileMetadata fileMetadata = fileMetadataRepository.getOne(fileId);
        Task task = fileMetadata.getTask();
        return new SearchResultEntry(
                String.valueOf(task.getId()),
                task.getName(),
                String.valueOf(fileId),
                fileMetadata.getName().concat(".").concat(fileMetadata.getContentType().getExtension()),
                version.getVersionString(),
                hit.highlight.get(Version.SerializedFields.PARSED_FILE_CONTENT_FIELD)
        );
    }
}
