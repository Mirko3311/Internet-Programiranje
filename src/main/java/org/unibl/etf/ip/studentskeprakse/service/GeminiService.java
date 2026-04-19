package org.unibl.etf.ip.studentskeprakse.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.ip.studentskeprakse.gemini.GeminiRequest;
import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;
   private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    @Autowired
    private RestTemplate restTemplate;

   public String getAiRecommendations(String cvText, String internshipsText) {
       String prompt = "Kao stručnjak za ljudske resurse, analiziraj ovaj CV studenta: \n" + cvText +
               "\n\nI uporedi ga sa listom praksi: \n" + internshipsText +
               "\n\nVRATI ISKLJUČIVO JSON niz objekata sa poljima 'internshipId', 'score' (0-1) i 'reason' na srpskom jeziku.";

       var request = new GeminiRequest(List.of(
               new GeminiRequest.Content(List.of(
                       new GeminiRequest.Part(prompt)
               ))
       ));
       try {
           ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_URL + apiKey, request, String.class);
           String rawBody = response.getBody();
           System.out.println("SIROVI ODGOVOR OD GEMINIJA: " + rawBody);
           return rawBody;
       } catch (Exception e) {
           return "Greška: " + e.getMessage();
       }
   }
}