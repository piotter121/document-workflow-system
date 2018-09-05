package pl.edu.pw.ee.pyskp.documentworkflow.services.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

@Getter
public class TaskCreatedEvent extends ApplicationEvent {
    private final Task createdTask;

    /**
     * Create a new TaskCreatedEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param createdTask created and already persisted task
     */
    public TaskCreatedEvent(Object source, Task createdTask) {
        super(source);
        this.createdTask = createdTask;
    }
}
