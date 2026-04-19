package org.unibl.etf.ip.studentskeprakse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.studentskeprakse.entity.Student;
import org.unibl.etf.ip.studentskeprakse.gemini.RecommendationDTO;
import org.unibl.etf.ip.studentskeprakse.repository.StudentRepository;
import org.unibl.etf.ip.studentskeprakse.security.UserDetailsImpl;
import org.unibl.etf.ip.studentskeprakse.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:4200")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/my-recommendations")
    public ResponseEntity<List<RecommendationDTO>> getMyRecommendations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Integer userId = userDetails.getId();
        List<RecommendationDTO> recommendations = recommendationService.getTopRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }
}