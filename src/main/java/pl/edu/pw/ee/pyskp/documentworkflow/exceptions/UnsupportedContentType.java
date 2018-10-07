package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

/**
 * Created by piotr on 06.01.17.
 */
public class UnsupportedContentType extends Exception {
    public UnsupportedContentType(String name) {
        super(String.format("Unsupported content type found: %s", name));
    }
}
