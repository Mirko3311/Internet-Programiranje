package org.unibl.etf.ip.studentskeprakse.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.studentskeprakse.dto.ApplicationResponseDTO;
import org.unibl.etf.ip.studentskeprakse.security.UserDetailsImpl;
import org.unibl.etf.ip.studentskeprakse.service.ApplicationService;

import java.util.List;


@RestController
@RequestMapping("/api/applications")
@CrossOrigin(allowCredentials = "true",origins = {"http://localhost:4200","http://localhost:8081","http://localhost:8082"})
public class ApplicationController
{

    private final ApplicationService  applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply/{internshipId}")
    public ResponseEntity<Void> createApplication(@PathVariable Integer internshipId, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer studentId = userDetails.getId();

        applicationService.createApplication(studentId, internshipId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/company-applications")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsForCompany(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer companyId = userDetails.getId();
        System.out.println("Company ID iz ApplicationController: " + companyId);
        List<ApplicationResponseDTO> apps = applicationService.getApplicationsForCompany(companyId);
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationResponseDTO>> getAllApplications()
    {
        List<ApplicationResponseDTO> apps= applicationService.getAll();
        return ResponseEntity.ok(apps);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Boolean> changeStatus(@PathVariable Integer id, @RequestParam String status)
    {
        boolean success = applicationService.updateApplication(id, status);
        return ResponseEntity.ok(success);
    }
    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByInternship(@PathVariable Integer internshipId) {
        List<ApplicationResponseDTO> apps = applicationService.getApplicationsForInternship(internshipId);
        return ResponseEntity.ok(apps);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateApplicationGrade(
            @PathVariable Integer id,
            @RequestBody ApplicationResponseDTO dto) {

        applicationService.updateGradeAndComment(id, dto.getGrade(), dto.getComment());
        return ResponseEntity.noContent().build();
    }
}
