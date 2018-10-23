package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = "id")
@Document
public class Version {
    @Id
    private ObjectId id;

    @DBRef(lazy = true)
    private FileMetadata file;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date saveDate;

    private String versionString;

    @TextIndexed
    private String message;

    @DBRef
    private User author;

    private byte[] fileContent;

    @TextIndexed(weight = 2)
    private List<String> parsedFileContent;

    private String checkSum;

    private List<Difference> differences;

    @TextScore
    private Float score;
}
