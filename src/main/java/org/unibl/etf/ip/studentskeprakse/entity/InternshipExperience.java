package org.unibl.etf.ip.studentskeprakse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "internship_experience")
public class InternshipExperience
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_internship")
    public Long id;

    public String companyName;
    public String position;
    public String period;

    @Column(columnDefinition = "TEXT")
    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cv_id")
    @JsonBackReference
    public Cv cv;

}
