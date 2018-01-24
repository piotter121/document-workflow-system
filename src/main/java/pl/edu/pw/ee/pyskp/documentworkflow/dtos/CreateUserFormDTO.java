package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by piotr on 14.12.16.
 */
@Data
@NoArgsConstructor
public class CreateUserFormDTO {

    private String login = "";
    private String email = "";
    private String firstName = "";
    private String lastName = "";
    private String password = "";
    private String passwordRepeated = "";

}
