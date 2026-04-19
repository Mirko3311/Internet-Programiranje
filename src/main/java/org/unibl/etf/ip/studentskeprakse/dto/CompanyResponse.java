package org.unibl.etf.ip.studentskeprakse.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private Integer id;
    private String name;
    private String address;
    private String username;
    private Boolean isActive;
}