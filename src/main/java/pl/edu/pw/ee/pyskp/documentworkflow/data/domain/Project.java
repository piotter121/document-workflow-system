package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public class Project {
    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;

    private String name, description;

    private Date creationDate;

    @ToString.Exclude
    @DBRef
    private FileMetadata lastModifiedFile;

    @DBRef
    @Indexed
    private User administrator;

    private long numberOfTasks, numberOfFiles, numberOfParticipants;
}