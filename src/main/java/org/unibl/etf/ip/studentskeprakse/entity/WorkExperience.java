package org.unibl.etf.ip.studentskeprakse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="work_experience")
public class WorkExperience
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String employer;
    private String position;
    private String city;
    private String period;
    @Lob
    private String mainActivities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "cv_id")
    private Cv cv;
}
