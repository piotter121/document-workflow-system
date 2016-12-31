package pl.edu.pw.ee.pyskp.documentworkflow.exception;

/**
 * Created by piotr on 31.12.16.
 */
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String nameOfNonExistingProject) {
        super(String.format("Nie znaleziono projektu o nazwie %s", nameOfNonExistingProject));
    }
}
