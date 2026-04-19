package org.unibl.etf.ip.studentskeprakse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CvRequestDTO
{
    @NotBlank(message="Summary is required")
    private String summary;
    private String photoPath;
    @Email(message="Email should be valid")
    private String email;
    @NotBlank
    private String phoneNumber;

    private String interests;

    private List<InternshipExperienceRequestDTO> internships = new  ArrayList<>();
    private List<EducationRequestDTO> educations = new ArrayList<>();
    private List<WorkExperienceRequestDTO> workExperiences = new  ArrayList<>();
    private List<String> skillNames =  new ArrayList<>();
}
