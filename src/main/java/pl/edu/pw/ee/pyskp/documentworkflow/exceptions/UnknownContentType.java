package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

/**
 * Created by piotr on 06.01.17.
 */
public class UnknownContentType extends RuntimeException {
    public UnknownContentType(String name) {
        super(String.format("Unknown content type found: %s", name));
    }
}
