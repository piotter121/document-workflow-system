package pl.edu.pw.ee.pyskp.documentworkflow.services.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

@Getter
public class TaskDeletedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 7645137464177707757L;

    private final Task deletedTask;

    public TaskDeletedEvent(Object source, Task deletedTask) {
        super(source);
        this.deletedTask = deletedTask;
    }
}
