package net.etf.ip.models;

import java.io.Serializable;

public class StudentResponse implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
   
    private String email;

    public StudentResponse() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}