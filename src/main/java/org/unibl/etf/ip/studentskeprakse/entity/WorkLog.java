package org.unibl.etf.ip.studentskeprakse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "work_log")
public class WorkLog {
    @Id
    @Column(name = "idWork_Log", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Internship_idInternship", nullable = false)
    private Internship internship;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "datesubmitted")
   // private Instant datesubmitted;
    private LocalDateTime datesubmitted;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Student_idStudent", nullable = false)
    private Student studentIdstudent;


    @Lob
    @Column(name = "mentorComment", nullable = false)
    private String mentorComment;
}