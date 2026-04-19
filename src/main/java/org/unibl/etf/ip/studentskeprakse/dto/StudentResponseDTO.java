package org.unibl.etf.ip.studentskeprakse.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private Integer id;
    private String firstname;
    private String surname;
    private String index;
    private String email;
    private String username;
    private String facultyName;
}

