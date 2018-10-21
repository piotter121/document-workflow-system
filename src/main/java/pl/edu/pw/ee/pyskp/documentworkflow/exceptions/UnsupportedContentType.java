package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import lombok.Getter;

/**
 * Created by piotr on 06.01.17.
 */
public class UnsupportedContentType extends Exception {
    @Getter
    private final String unsupportedContentTypeName;

    public UnsupportedContentType(String name) {
        super(String.format("Unsupported content type found: %s", name));
        this.unsupportedContentTypeName = name;
    }
}
