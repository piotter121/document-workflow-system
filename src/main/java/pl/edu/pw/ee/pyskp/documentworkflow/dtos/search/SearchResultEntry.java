package pl.edu.pw.ee.pyskp.documentworkflow.dtos.search;

import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class SearchResultEntry {
    @NonNull
    String taskId, taskName;

    @NonNull
    String fileId, fileName;

    @NonNull
    String versionString;

    @NonNull
    List<String> highlightContent;
}
