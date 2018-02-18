package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by piotr on 11.12.16.
 */
@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum ContentType {
    WORD_2003_DOCUMENT("application/msword", "doc"),
    WORD_2007_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx"),
    EXCEL_2003_SPREADSHEET("application/vnd.ms-excel", "xls"),
    EXCEL_2007_SPREADSHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
    OPEN_DOCUMENT_TEXT("application/vnd.oasis.opendocument.text", "odt"),
    OPEN_DOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet", "ods");

    @Getter
    @NonNull
    private final String name;
    @Getter
    @NonNull
    private final String extension;

    public static Optional<ContentType> fromName(String name) {
        return Stream.of(values())
                .filter(contentType -> contentType.getName().equals(name))
                .findFirst();
    }
}
