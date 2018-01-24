package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by piotr on 11.12.16.
 */
public enum ContentType {
    WORD_2003_DOCUMENT("application/msword", "doc"),
    WORD_2007_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx"),
    EXCEL_2003_SPREADSHEET("application/vnd.ms-excel", "xls"),
    EXCEL_2007_SPREADSHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
    OPEN_DOCUMENT_TEXT("application/vnd.oasis.opendocument.text", "odt"),
    OPEN_DOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet", "ods");

    private String name;
    private String extension;

    ContentType(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public static Optional<ContentType> fromName(String name) {
        return Stream.of(values())
                .filter(contentType -> contentType.getName().equals(name))
                .findFirst();
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }
}
