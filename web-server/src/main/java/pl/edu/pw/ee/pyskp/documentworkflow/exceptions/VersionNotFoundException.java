package pl.edu.pw.ee.pyskp.documentworkflow.exceptions;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * Created by p.pysk on 16.01.2017.
 */
@NoArgsConstructor
public class VersionNotFoundException extends ResourceNotFoundException {
    private String versionId = "";

    public VersionNotFoundException(long versionId) {
        super(String.format("Version with id = %d could not be found", versionId));
        this.versionId = String.valueOf(versionId);
    }

    @Override
    public Map<String, String> getMessageParams() {
        return Collections.singletonMap("versionId", versionId);
    }
}
