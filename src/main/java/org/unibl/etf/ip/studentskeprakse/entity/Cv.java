package org.unibl.etf.ip.studentskeprakse.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cv")
public class Cv {
    @Id
    @Column(name = "idCV", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "summary")
    private String summary;


    @NotNull
    @Lob
    @Column(name = "photo_path", columnDefinition = "LONGTEXT")
    private String photoPath;

    @OneToMany(mappedBy="cv", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "cv", cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @Size(max = 45)
    @NotNull
    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InternshipExperience> internships = new ArrayList<>();

    @NotNull
    @Lob
    @Column(name = "interests", nullable = false)
    private String interests;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false,orphanRemoval = true)
    @JoinColumn(name = "Student_idStudent", nullable = false)
    private Student student;

    @Size(max = 45)
    @NotNull
    @Column(name = "phoneNumber", nullable = false, length = 45)
    private String phoneNumber;

    @ManyToMany
    @JoinTable(name="cv_has_skills", joinColumns = @JoinColumn(name="CV_idCV"),
    inverseJoinColumns = @JoinColumn(name="Skills_idSkills"))
    private List<Skill> skills = new ArrayList<>();
}