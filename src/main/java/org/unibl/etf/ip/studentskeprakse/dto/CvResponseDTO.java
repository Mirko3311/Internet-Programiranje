package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;
import java.util.List;

@Data
public class CvResponseDTO
{
    private String firstName;
    private String lastName;
    private Integer id;
    private String summary;
    private String photoPath;
    private String email;
    private String phoneNumber;
    private String interests;
    private List<String> skillNames;
    private List<InternshipExperienceResponseDTO> internships;
    private List<EducationResponseDTO> educations;
    private List<WorkExperienceResponseDTO> workExperiences;
}
