package org.unibl.etf.ip.studentskeprakse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "idStudent", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "firstname", nullable = false, length = 45)
    private String firstname;

    @Size(max = 45)
    @NotNull
    @Column(name = "surname", nullable = false, length = 45)
    private String surname;

    @Size(max = 45)
    @Column(name = "`index`", length = 45, unique = true)
    private String index;

    @Size(max = 45)
    @Column(name = "email", length = 45,  unique = true)
    private String email;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_idUser", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Faculty_idFaculty", nullable = false)
    private Faculty faculty;
}