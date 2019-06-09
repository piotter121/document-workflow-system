package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * Created by p.pysk on 16.01.2017.
 */
@NoArgsConstructor
public class VersionNotFoundException extends ResourceNotFoundException {
    private static final long serialVersionUID = -4940133288144804200L;

    private String versionId = "";

    public VersionNotFoundException(String versionId) {
        super(String.format("Version with id = %s could not be found", versionId));
        this.versionId = versionId;
    }

    @Override
    public Map<String, String> getMessageParams() {
        return Collections.singletonMap("versionId", versionId);
    }
}
