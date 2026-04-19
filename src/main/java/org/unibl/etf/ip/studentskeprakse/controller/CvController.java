package org.unibl.etf.ip.studentskeprakse.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.studentskeprakse.dto.CVResponse;
import org.unibl.etf.ip.studentskeprakse.dto.CvRequestDTO;
import org.unibl.etf.ip.studentskeprakse.dto.CvResponseDTO;
import org.unibl.etf.ip.studentskeprakse.entity.Cv;
import org.unibl.etf.ip.studentskeprakse.entity.Student;
import org.unibl.etf.ip.studentskeprakse.repository.CvRepository;
import org.unibl.etf.ip.studentskeprakse.repository.StudentRepository;
import org.unibl.etf.ip.studentskeprakse.service.CvService;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:4200")
@RequestMapping("/api/cv")
public class CvController
{
    private final CvService cvService;
    private final StudentRepository studentRepository;
    private final CvRepository cvRepository;
    public CvController(CvService cvService, StudentRepository studentRepository, CvRepository cvRepository)
    {
        this.cvService = cvService;
        this.studentRepository = studentRepository;
        this.cvRepository = cvRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<CvResponseDTO> createCv(@Valid @RequestBody CvRequestDTO cvRequestDTO)
    {

       /* Cv cv = new Cv();
        cv.setSummary(cvRequestDTO.getSummary());
        cv.setPhotoPath(cvRequestDTO.getPhotoPath());
        cv.setInternships(cvRequestDTO.getInternships());


        if(cvRequestDTO.getEducations()!=null && cvRequestDTO.getEducations().size()>0)
        {
            cvRequestDTO.getEducations().stream().map(eduDto->
            {
                Education edu = new Education();
                edu.setInstitution(eduDto.getInstitution());
                edu.setDegree(eduDto.getDegree());
                edu.setFieldOfStudy(eduDto.getFieldOfStudy());
                edu.setPeriod(eduDto.getPeriod());
                edu.setCv(cv);
                return edu;
            }).collect(Collectors.toList());
        }*/
        System.out.println(cvRequestDTO.toString());
        CvResponseDTO response = cvService.saveCv(cvRequestDTO);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addSkills(@PathVariable Integer id, @RequestBody List<String> skillNames)
    {
      cvService.addSkillToCV(id, skillNames);
      return ResponseEntity.ok().build();
    }

    @GetMapping("/moj-cv")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<CvResponseDTO> getMyCV(@AuthenticationPrincipal UserDetails user)
    {
       CvResponseDTO cv = cvService.getCvByUsername(user.getUsername());

       if(cv!=null)
       {
           return ResponseEntity.ok(cv);
       }
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> deleteCV(@AuthenticationPrincipal UserDetails user)
    {

        cvService.deleteCurrentUsersCV(user.getUsername());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-my-cv")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<CvResponseDTO> updateCv(@RequestBody CvRequestDTO data, @AuthenticationPrincipal UserDetails user)
    {

        System.out.println(data.toString());
        String name = user.getUsername();

        Student student = studentRepository.findByUser_Username(name).orElseThrow(() -> new RuntimeException("Student nije pronadjen!"));

        Cv currentCv = cvRepository.findByStudent(student).orElseThrow(()-> new RuntimeException("CV ne postoji za ovog korisnika!"));

        return ResponseEntity.ok(cvService.updateCv(currentCv.getId(),data));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CVResponse> getCvByStudent(@PathVariable Integer id)
    {
        CVResponse cvResponseDTO = cvService.getCVByStudentId(id);
        return ResponseEntity.ok(cvResponseDTO);
    }


}
