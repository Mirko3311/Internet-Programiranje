package org.unibl.etf.ip.studentskeprakse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.studentskeprakse.dto.WorkLogRequest;
import org.unibl.etf.ip.studentskeprakse.dto.WorkLogResponse;
import org.unibl.etf.ip.studentskeprakse.security.UserDetailsImpl;
import org.unibl.etf.ip.studentskeprakse.service.InternshipService;
import org.unibl.etf.ip.studentskeprakse.service.WorkLogService;

import java.util.List;


@RestController
@RequestMapping("/api/work-log")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:4200")
public class WorkLogController
{
    @Autowired
    private WorkLogService workLogService;
    @Autowired
    private InternshipService internshipService;

    @PostMapping("/create/{internshipId}")
    public ResponseEntity<WorkLogResponse> create(@PathVariable Integer internshipId, @RequestBody WorkLogRequest request, Authentication authentication)
    {

        Integer studentId =  getLoggedInStudentId(authentication);
        WorkLogResponse response = workLogService.create(internshipId,request,studentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkLogResponse> update(@PathVariable Integer id, @RequestBody WorkLogRequest request, Authentication authentication)
    {
        Integer studentId = getLoggedInStudentId(authentication);
        WorkLogResponse response = workLogService.updateEntry(id,request,studentId);
        return ResponseEntity.ok(response);
    }

    private Integer getLoggedInStudentId(Authentication authentication)
    {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }
    @GetMapping("/{id}")
    public List<WorkLogResponse> getAll(@PathVariable Integer id)
    {
        return workLogService.getAll(id);
    }

    @GetMapping("/student/{studentId}/internship/{internshipId}")
    public ResponseEntity<List<WorkLogResponse>> getStudentLogs(
            @PathVariable Integer studentId,
            @PathVariable Integer internshipId) {
        return ResponseEntity.ok(workLogService.getLogsForStudentOnInternship(internshipId, studentId));
    }

    @PutMapping("/{logId}/comment")
    public ResponseEntity<String> addComment(@PathVariable Integer logId, @RequestBody String comment) {
        workLogService.updateMentorComment(logId, comment);
        return ResponseEntity.ok("Komentar je uspješno dodat!");
    }

 /*   @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getMyInternships(Authentication authentication)
    {
        if(authentication == null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        List<Internship> response = internshipService.getStudentInternships(user.getId());
        return ResponseEntity.ok(response);
    }*/
}
