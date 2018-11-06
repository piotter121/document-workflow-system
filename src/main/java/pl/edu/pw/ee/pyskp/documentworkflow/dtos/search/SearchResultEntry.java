package pl.edu.pw.ee.pyskp.documentworkflow.dtos.search;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SearchResultEntry {
    @NonNull
    @EqualsAndHashCode.Include
    String taskId;

    @NonNull
    String taskName;

    @NonNull
    @EqualsAndHashCode.Include
    String fileId;

    @NonNull
    String fileName;

    @NonNull
    @EqualsAndHashCode.Include
    String versionString;

    @NonNull
    @ToString.Exclude
    List<String> highlightContent;
}
