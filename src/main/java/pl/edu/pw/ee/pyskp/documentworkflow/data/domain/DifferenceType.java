package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import difflib.Delta;

import java.util.Optional;

/**
 * Created by piotr on 11.12.16.
 */
public enum DifferenceType {
    INSERT, DELETE, MODIFICATION;

    public static Optional<DifferenceType> fromDeltaType(Delta.TYPE type) {
        switch (type) {
            case CHANGE:
                return Optional.of(MODIFICATION);
            case DELETE:
                return Optional.of(DELETE);
            case INSERT:
                return Optional.of(INSERT);
        }
        return Optional.empty();
    }
}
