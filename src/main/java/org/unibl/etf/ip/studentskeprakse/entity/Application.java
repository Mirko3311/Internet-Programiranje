package org.unibl.etf.ip.studentskeprakse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "application")
public class Application {
    @Id
    @Column(name = "idApplication", columnDefinition = "int UNSIGNED not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "applied", nullable = false)
    private Instant applied;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "comment")
    private String comment;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Internship_idInternship", nullable = false)
    private Internship internshipIdinternship;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Student_idStudent", nullable = false)
    private Student studentIdstudent;

}