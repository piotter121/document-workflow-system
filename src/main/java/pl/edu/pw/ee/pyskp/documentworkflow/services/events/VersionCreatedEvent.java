package pl.edu.pw.ee.pyskp.documentworkflow.services.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

@Getter
public class VersionCreatedEvent extends ApplicationEvent {
    private static final long serialVersionUID = -7750757515820936130L;

    private final Version createdVersion;
    private final FileMetadata modifiedFile;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public VersionCreatedEvent(Object source, Version createdVersion, FileMetadata modifiedFile) {
        super(source);
        this.createdVersion = createdVersion;
        this.modifiedFile = modifiedFile;
    }
}
