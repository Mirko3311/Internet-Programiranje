package org.unibl.etf.ip.studentskeprakse;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StudentskePrakseApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentskePrakseApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
