package org.unibl.etf.ip.studentskeprakse.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.studentskeprakse.dto.InternshipRequestDTO;
import org.unibl.etf.ip.studentskeprakse.dto.InternshipResponseDTO;
import org.unibl.etf.ip.studentskeprakse.dto.StudentResponseDTO;
import org.unibl.etf.ip.studentskeprakse.entity.Internship;
import org.unibl.etf.ip.studentskeprakse.security.UserDetailsImpl;
import org.unibl.etf.ip.studentskeprakse.service.ApplicationService;
import org.unibl.etf.ip.studentskeprakse.service.InternshipService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/internships")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:4200")
public class InternshipController
{
    private final InternshipService internshipService;
    private final ApplicationService applicationService;

    public InternshipController(InternshipService internshipService, ApplicationService applicationService, ApplicationService applicationService1) {
        this.internshipService = internshipService;
        this.applicationService = applicationService1;
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipResponseDTO> update(
            @PathVariable Integer id,
            @RequestBody InternshipRequestDTO request) {

        InternshipResponseDTO updated = internshipService.updateInternship(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/company-internships")
    public ResponseEntity<List<InternshipResponseDTO>> getInternshipsCompany(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer loggedId = userDetails.getId();
        List<InternshipResponseDTO> list = internshipService.getInternshipsByCompany(loggedId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/my-internships")
    public ResponseEntity<List<InternshipResponseDTO>> getMyInternships(Authentication authentication)
    {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer loggedInStudentId = userDetails.getId();
        return ResponseEntity.ok(internshipService.getStudentInternships(loggedInStudentId));
    }

    @GetMapping("/my-unapplied")
    public ResponseEntity<List<InternshipResponseDTO>> getUnappliedInternships(Authentication authentication)
    {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer loggedInStudentId = userDetails.getId();
        return ResponseEntity.ok(internshipService.getUnappliedInternships(loggedInStudentId));
    }
    @GetMapping("/{id}")
    public InternshipResponseDTO getById(@PathVariable Integer id)
    {
       return internshipService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody InternshipRequestDTO internshipDTO, Principal principal)
    {
        try {
            Internship  saved = internshipService.createInternship(internshipDTO,principal.getName());
            return ResponseEntity.ok().body(saved);
        }catch(Exception e)
        {
            return ResponseEntity.badRequest().body(" Greska pri kreiranju: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<InternshipResponseDTO>> listAll()
    {
      return ResponseEntity.ok(internshipService.getAll());
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudents(@PathVariable Integer id)
    {
       return ResponseEntity.ok(applicationService.getAcceptedStudents(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id)
    {
        try {
            boolean deleted = internshipService.deleteInternshipById(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();

            }
        }catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
