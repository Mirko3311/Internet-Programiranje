package org.unibl.etf.ip.studentskeprakse.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternshipResponseDTO
{
    private Integer id;
    private String title;
    private String description;
    private String companyName;
    private List<String> skillNames;
    private LocalDate startDate;
    private LocalDate endDate;
}
