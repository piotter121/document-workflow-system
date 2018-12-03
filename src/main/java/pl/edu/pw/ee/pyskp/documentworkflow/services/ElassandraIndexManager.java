package pl.edu.pw.ee.pyskp.documentworkflow.services;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.config.ElasticsearchQueryConfiguration;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class ElassandraIndexManager {
    private static final String MAPPINGS = "{\n" +
            "    \"version\": {\n" +
            "      \"discover\": \"^(?!message|parsed_file_content|file_content|differences).*\",\n" +
            "      \"properties\": {\n" +
            "        \"message\": {\n" +
            "          \"type\": \"text\",\n" +
            "          \"cql_collection\": \"singleton\"\n" +
            "        },\n" +
            "        \"parsed_file_content\": {\n" +
            "          \"type\": \"text\",\n" +
            "          \"cql_collection\": \"singleton\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }";

    @NonNull
    private final JestClient jestClient;

    @NonNull
    private final ElasticsearchQueryConfiguration queryConfiguration;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void initializeIndex() throws IOException {
        final String indexName = queryConfiguration.getIndexName();
        IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
        JestResult indicesExistsResult = jestClient.execute(indicesExists);
        if (!indicesExistsResult.isSucceeded()) {
            log.info("Index \"{}\" doesn't exists. Attempting to create it...", indexName);
            CreateIndex createIndex = new CreateIndex.Builder(indexName)
                    .settings(Collections.singletonMap("keyspace", keyspaceName))
                    .mappings(MAPPINGS)
                    .build();
            JestResult createIndexResult = jestClient.execute(createIndex);
            if (createIndexResult.isSucceeded()) {
                log.info("Successfully created index \"{}\"", indexName);
            } else {
                log.error("Couldn't create index \"{}\"!", indexName);
            }
        } else {
            log.info("Index \"{}\" already exists", indexName);
        }
    }
}