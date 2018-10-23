package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Document
public class Project {
    @Id
    private ObjectId id;

    private String name, description;

    private Date creationDate;

    @DBRef
    private FileMetadata lastModifiedFile;

    @DBRef
    private User administrator;

    private long numberOfTasks, numberOfFiles, numberOfParticipants;
}