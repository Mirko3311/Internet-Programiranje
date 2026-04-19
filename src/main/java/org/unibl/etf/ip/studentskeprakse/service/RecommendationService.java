package org.unibl.etf.ip.studentskeprakse.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.studentskeprakse.entity.Cv;
import org.unibl.etf.ip.studentskeprakse.entity.Internship;
import org.unibl.etf.ip.studentskeprakse.entity.Skill;
import org.unibl.etf.ip.studentskeprakse.gemini.RecommendationDTO;
import org.unibl.etf.ip.studentskeprakse.repository.CvRepository;
import org.unibl.etf.ip.studentskeprakse.repository.InternshipRepository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private GeminiService geminiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

   /* public List<RecommendationDTO> getTopRecommendations(Integer studentId) {

        Cv cv = cvRepository.findByStudent_User_Id(studentId)
                .orElseThrow(() -> new RuntimeException("CV nije pronađen"));
        List<Internship> allInternships = internshipRepository.findAll();

        String cvText = formatCvForAi(cv);
        String internshipsText = formatInternshipsForAi(allInternships);

        String aiRawResponse = geminiService.getAiRecommendations(cvText, internshipsText);

        return parseAndRank(aiRawResponse);
    }*/

    public List<RecommendationDTO> getTopRecommendations(Integer userId) {
        Cv cv = cvRepository.findByStudent_User_Id(userId)
                .orElseThrow(() -> new RuntimeException("CV nije pronađen"));
        List<Internship> allInternships = internshipRepository.findAll();

        String cvText = formatCvForAi(cv);
        String internshipsText = formatInternshipsForAi(allInternships);

        String rawResponse = geminiService.getAiRecommendations(cvText, internshipsText);

        // Provjera: Ako odgovor nije JSON (npr. počinje sa "Greška"), prekini odmah
        if (rawResponse == null || !rawResponse.trim().startsWith("{")) {
            System.err.println("Gemini nije vratio validan JSON: " + rawResponse);
            return new ArrayList<>();
        }

        try {
            // Tek ovdje radimo parsiranje
            // Napomena: Prvo moraš izvući tekst iz Google-ovog omotača!
            JsonNode root = objectMapper.readTree(rawResponse);
            String jsonString = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();

            // Čišćenje Markdown oznaka ako ih ima
            jsonString = jsonString.replace("```json", "").replace("```", "").trim();
            return parseAndRank(jsonString);
           // return objectMapper.readValue(jsonString, new TypeReference<List<RecommendationDTO>>(){});
        } catch (Exception e) {
            System.err.println("Greška kod parsiranja: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    private String formatCvForAi(Cv cv) {
        StringBuilder sb = new StringBuilder();
        sb.append("CV STUDENTA:\n");

        // 1. Osnovni podaci i interesi
        sb.append("Interesovanja: ").append(cv.getInterests()).append("\n");
        sb.append("Sažetak: ").append(cv.getSummary()).append("\n");

        // 2. Izvlačenje vještina (Skills su objekti, pa uzimamo njihova imena)
        if (cv.getSkills() != null) {
            String vjestine = cv.getSkills().stream()
                    .map(Skill::getName) // Pretpostavka da Skill ima polje 'name'
                    .collect(Collectors.joining(", "));
            sb.append("Vještine: ").append(vjestine).append("\n");
        }

        // 3. Radno iskustvo (da AI vidi šta je student već radio)
        if (cv.getWorkExperiences() != null) {
            sb.append("Radno iskustvo: ");
            cv.getWorkExperiences().forEach(work ->
                    sb.append(work.getPosition()).append(" u ").append(work.getPeriod()).append("; ")
            );
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatInternshipsForAi(List<Internship> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("LISTA DOSTUPNIH PRAKSI ZA ANALIZU:\n");

        for (Internship i : list) {
            sb.append("---")
                    .append("\nID PRAKSE: ").append(i.getId())
                    .append("\nNASLOV: ").append(i.getTitle())
                    .append("\nOPIS: ").append(i.getDescription());
            if (i.getSkills() != null && !i.getSkills().isEmpty()) {
                String vjestine = i.getSkills().stream()
                        .map(Skill::getName)
                        .collect(Collectors.joining(", "));
                sb.append("\nPOTREBNE VJEŠTINE: ").append(vjestine);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

 /*   private List<RecommendationDTO> parseAndRank(String json) {
        try {
            // 1. Čišćenje od Markdown-a (AI često doda ```json ... ```)
            String cleanJson = json.trim();
            if (cleanJson.contains("```json")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7, cleanJson.lastIndexOf("```"));
            } else if (cleanJson.contains("```")) {
                cleanJson = cleanJson.substring(cleanJson.indexOf("```") + 3, cleanJson.lastIndexOf("```"));
            }
            cleanJson = cleanJson.trim();

            // 2. Pretvaranje Stringa u listu Java objekata
            List<RecommendationDTO> list = objectMapper.readValue(cleanJson,
                    new TypeReference<List<RecommendationDTO>>(){});

            // 3. Spajanje sa bazom da izvučemo Naslove (AI vraća samo ID) i rangiranje
            // Ovo je bitno jer studentu želiš prikazati naslov prakse, a ne samo ID
            return list.stream()
                    .peek(dto -> {
                        // Opciono: ovdje možeš dohvatiti naslov iz baze ako ga AI ne vrati
                        internshipRepository.findById(dto.getInternshipId().intValue())
                                .ifPresent(p -> dto.setInternshipTitle(p.getTitle()));
                    })
                    .sorted(Comparator.comparing(RecommendationDTO::getScore).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Greška pri obradi AI odgovora: " + e.getMessage());
            return Collections.emptyList(); // Bolje vratiti praznu listu nego srušiti aplikaciju
        }
    }
*/
 private List<RecommendationDTO> parseAndRank(String json) {
     try {
         String cleanJson = json.replaceAll("```json", "").replaceAll("```", "").trim();
         List<RecommendationDTO> list = objectMapper.readValue(cleanJson, new TypeReference<List<RecommendationDTO>>(){});

         System.out.println("AI je predložio ID-ove: " + list.stream().map(RecommendationDTO::getInternshipId).toList());

         for (RecommendationDTO dto : list) {
             // Koristi findById direktno sa Long-om ako je to tip u bazi
             internshipRepository.findById(dto.getInternshipId().intValue()).ifPresentOrElse(
                     p -> {
                         dto.setInternshipTitle(p.getTitle());
                         System.out.println("Pronađena praksa u bazi: " + p.getTitle());
                     },
                     () -> System.err.println("UPOZORENJE: Praksa sa ID " + dto.getInternshipId() + " ne postoji u bazi!")
             );
         }

         return list.stream()
                 .filter(dto -> dto.getInternshipTitle() != null) // Zadrži samo one koje smo našli u bazi
                 .sorted(Comparator.comparing(RecommendationDTO::getScore).reversed())
                 .collect(Collectors.toList());

     } catch (Exception e) {
         System.err.println("Greška u parseAndRank: " + e.getMessage());
         return new ArrayList<>();
     }
 }

}