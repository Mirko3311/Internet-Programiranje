package org.unibl.etf.ip.studentskeprakse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="education")
public class Education
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;
    private String degree;
    private String fieldOfStudy;
    private String period;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="cv_id")
    private Cv cv;
}
