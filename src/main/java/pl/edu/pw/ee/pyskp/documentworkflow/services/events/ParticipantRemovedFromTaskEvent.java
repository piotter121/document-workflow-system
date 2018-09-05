package pl.edu.pw.ee.pyskp.documentworkflow.services.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

import java.util.Objects;

@Getter
public class ParticipantRemovedFromTaskEvent extends ApplicationEvent {
    private final User removedUser;
    private final Task modifiedTask;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ParticipantRemovedFromTaskEvent(Object source, User removedUser, Task modifiedTask) {
        super(source);
        this.removedUser = Objects.requireNonNull(removedUser);
        this.modifiedTask = Objects.requireNonNull(modifiedTask);
    }
}
