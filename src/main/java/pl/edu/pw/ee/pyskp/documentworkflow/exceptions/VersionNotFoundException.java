package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

/**
 * Created by p.pysk on 16.01.2017.
 */
public class VersionNotFoundException extends RuntimeException {
    public VersionNotFoundException(long versionId) {
        super(String.format("Version with id = %d could not be found", versionId));
    }

    public VersionNotFoundException() {

    }
}
