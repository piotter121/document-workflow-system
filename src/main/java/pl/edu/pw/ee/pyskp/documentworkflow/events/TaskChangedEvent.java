package pl.edu.pw.ee.pyskp.documentworkflow.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;
import java.util.UUID;

public class TaskChangedEvent extends ApplicationEvent {
    @Getter
    private final UUID projectId;

    @Getter
    private final UUID taskId;

    /**
     * Create a new TaskChangedEvent.
     *
     * @param source    the object on which the event initially occurred (never {@code null})
     * @param projectId ID of the project which task has been updated (never {@code null})
     * @param taskId    ID of the task which has been updated (never {@code null})
     */
    public TaskChangedEvent(Object source, UUID projectId, UUID taskId) {
        super(source);
        this.projectId = Objects.requireNonNull(projectId);
        this.taskId = Objects.requireNonNull(taskId);
    }
}
