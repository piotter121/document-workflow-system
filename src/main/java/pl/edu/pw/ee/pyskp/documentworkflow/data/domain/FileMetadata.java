package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public class FileMetadata {
    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;

    @ToString.Exclude
    @DBRef(lazy = true)
    private Task task;

    private String name, description;

    private ContentType contentType;

    private Boolean confirmed, markedToConfirm;

    private Date creationDate;

    @ToString.Exclude
    @DBRef(lazy = true)
    private Version latestVersion;

    private long numberOfVersions;
}
