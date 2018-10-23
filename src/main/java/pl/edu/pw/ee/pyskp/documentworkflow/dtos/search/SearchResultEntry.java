package pl.edu.pw.ee.pyskp.documentworkflow.dtos.search;

import lombok.NonNull;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
public class SearchResultEntry {
    @NonNull
    String taskId, taskName;

    @NonNull
    String fileId, fileName;

    @NonNull
    String versionString;

    List<String> highlightContent = Collections.emptyList();
}
