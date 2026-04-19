package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {

    private String username;
    private String password;
    private boolean isActive;
    private String name;
    private String address;
    private String description;
}