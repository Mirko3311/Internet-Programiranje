package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;
import org.unibl.etf.ip.studentskeprakse.entity.Role;

import java.util.List;

@Data
public class UserResponse
{
    private Integer id;
    private String username;;
    private List<Role> roles;

    public UserResponse(Integer id,String username, List<Role> roles)
    {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

}
