package pl.edu.pw.ee.pyskp.documentworkflow.services.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

import java.util.Objects;

@Getter
public class ParticipantAddedToTaskEvent extends ApplicationEvent {
    private static final long serialVersionUID = 6634551896849816412L;

    private final User newParticipant;
    private final Task modifiedTask;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ParticipantAddedToTaskEvent(Object source, User newParticipant, Task modifiedTask) {
        super(source);
        this.newParticipant = Objects.requireNonNull(newParticipant);
        this.modifiedTask = Objects.requireNonNull(modifiedTask);
    }
}
