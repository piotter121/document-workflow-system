package pl.edu.pw.ee.pyskp.documentworkflow.services.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;

@Getter
public class FileDeletedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 6443410189091763530L;

    private final FileMetadata deletedFile;

    public FileDeletedEvent(Object source, FileMetadata deletedFile) {
        super(source);
        this.deletedFile = deletedFile;
    }
}
