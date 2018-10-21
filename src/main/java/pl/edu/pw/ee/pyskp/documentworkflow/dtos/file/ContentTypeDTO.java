package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.Value;

@Value
public class ContentTypeDTO {
    String extension, mimeType;
}
