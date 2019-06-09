package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

/**
 * Created by piotr on 06.01.17.
 */
public class UnknownContentType extends Exception {
    private static final long serialVersionUID = 5334302625907966009L;

    public UnknownContentType(String name) {
        super(String.format("Unknown content type found: %s", name));
    }
}
