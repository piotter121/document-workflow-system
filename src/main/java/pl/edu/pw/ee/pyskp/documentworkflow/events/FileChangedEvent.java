package pl.edu.pw.ee.pyskp.documentworkflow.events;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

/**
 * ApplicationEvent class indicating changes made in specified file and also in associated task
 */
public class FileChangedEvent extends TaskChangedEvent {
    @Getter
    private final UUID fileId;

    /**
     * Create a new FileChangedEvent.
     *
     * @param source    the object on which the event initially occurred (never {@code null})
     * @param projectId ID of the project which task has been updated (never {@code null})
     * @param taskId    ID of the task which has been updated (never {@code null})
     * @param fileId    ID of the file which has been changed (never {@code null})
     */
    public FileChangedEvent(Object source, UUID projectId, UUID taskId, UUID fileId) {
        super(source, projectId, taskId);
        this.fileId = Objects.requireNonNull(fileId);
    }
}
