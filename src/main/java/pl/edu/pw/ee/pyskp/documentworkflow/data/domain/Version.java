package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.util.Date;
import java.util.List;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document
public class Version {
    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;

    @ToString.Exclude
    @DBRef(lazy = true)
    @Indexed
    private FileMetadata file;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date saveDate;

    private String versionString;

    @TextIndexed
    private String message;

    @DBRef
    private User author;

    @ToString.Exclude
    private byte[] fileContent;

    @ToString.Exclude
    @TextIndexed(weight = 2)
    private List<String> parsedFileContent;

    private String checkSum;

    private List<Difference> differences;

    @ToString.Exclude
    @TextScore
    private Float score;
}
