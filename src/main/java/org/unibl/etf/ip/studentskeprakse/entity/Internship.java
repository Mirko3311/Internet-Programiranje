package org.unibl.etf.ip.studentskeprakse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "internship")
public class Internship {
    @Id
    @Column(name = "idInternship", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @NotNull
    @Column(name = "description",columnDefinition = "text")
    private String description;

    @NotNull
    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Company_idCompany", nullable = false)
    private Company company;

    @ManyToMany
    @JoinTable(name = "internship_has_skills",
    joinColumns = @JoinColumn(name="Internship_idInternship"),
    inverseJoinColumns = @JoinColumn(name = "Skills_idSkills"))
    private List<Skill> skills = new ArrayList<>();

}