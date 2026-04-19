package org.unibl.etf.ip.studentskeprakse.dto;

import lombok.Data;

@Data
public class InternshipExperienceResponseDTO
{
    private Long idInternshipExperience;
    private String companyName;
    private String period;
    private String description;
}
