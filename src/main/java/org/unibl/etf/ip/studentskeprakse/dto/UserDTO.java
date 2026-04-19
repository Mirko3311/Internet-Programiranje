package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;
import org.unibl.etf.ip.studentskeprakse.entity.Role;

@Data
public class UserDTO
{
    private Integer id;
    private String username;
    private Role role;
}
