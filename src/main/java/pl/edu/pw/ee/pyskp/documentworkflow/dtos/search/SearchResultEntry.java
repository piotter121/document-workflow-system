package pl.edu.pw.ee.pyskp.documentworkflow.dtos.search;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SearchResultEntry {
    @NonNull
    @EqualsAndHashCode.Include
    String taskId, fileId, versionString;

    @NonNull
    String taskName, fileName;

    @ToString.Exclude
    List<String> highlightContent = Collections.emptyList();
}
