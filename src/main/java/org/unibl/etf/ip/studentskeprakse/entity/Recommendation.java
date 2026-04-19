package org.unibl.etf.ip.studentskeprakse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recommendation")
public class Recommendation {
    @Id
    @Column(name = "idRecommndation", nullable = false)
    private Integer id;

    @Column(name = "compatibility_score")
    private Integer compatibilityScore;

    @Lob
    @Column(name = "explanation")
    private String explanation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Student_idStudent", nullable = false)
    private Student studentIdstudent;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Internship_idInternship", nullable = false)
    private Internship internshipIdinternship;

}