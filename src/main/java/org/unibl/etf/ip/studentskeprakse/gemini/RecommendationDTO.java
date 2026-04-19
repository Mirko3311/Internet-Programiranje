package org.unibl.etf.ip.studentskeprakse.gemini;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {
    public Long internshipId;
    public Double score;
    public String reason;
    public String internshipTitle;
}