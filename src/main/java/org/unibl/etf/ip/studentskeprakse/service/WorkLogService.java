package org.unibl.etf.ip.studentskeprakse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.studentskeprakse.dto.ApplicationResponseDTO;
import org.unibl.etf.ip.studentskeprakse.dto.WorkLogRequest;
import org.unibl.etf.ip.studentskeprakse.dto.WorkLogResponse;
import org.unibl.etf.ip.studentskeprakse.entity.Application;
import org.unibl.etf.ip.studentskeprakse.entity.Internship;
import org.unibl.etf.ip.studentskeprakse.entity.Student;
import org.unibl.etf.ip.studentskeprakse.entity.WorkLog;
import org.unibl.etf.ip.studentskeprakse.repository.ApplicationRepository;
import org.unibl.etf.ip.studentskeprakse.repository.InternshipRepository;
import org.unibl.etf.ip.studentskeprakse.repository.StudentRepository;
import org.unibl.etf.ip.studentskeprakse.repository.WorkLogRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkLogService
{
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Transactional
    public void saveWorkLog(Integer internshipId, WorkLogRequest request, Integer studentId)
    {
       Internship internship = internshipRepository.findById(internshipId).orElseThrow(()->new RuntimeException("Praksa nije pronadjena"));


    }
    public List<ApplicationResponseDTO> getActiveInternshipsForStudent(Integer studentId)
    {
        List<Application> apps = applicationRepository.findByStudentIdstudent_IdAndStatus(studentId, "ACCEPTED");

        return apps.stream().map(app ->
        {
           ApplicationResponseDTO response = new ApplicationResponseDTO();
           response.setApplicationId(app.getId());
           response.setStatus(app.getStatus());

            Internship intern = app.getInternshipIdinternship();
            response.setInternshipId(intern.getId());
            response.setInternshipTitle(intern.getTitle());
            response.setCompanyName(intern.getCompany().getName());
            return response;
        }).collect(Collectors.toList());
    }

    public WorkLogResponse create(Integer internshipId, WorkLogRequest request, Integer studentId)
    {
        WorkLog workLog = new WorkLog();
        Student student = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("Nema studenta!"));
        workLog.setInternship(internshipRepository.getReferenceById(internshipId));
        workLog.setWeekNumber(request.getWeekNumber());
        workLog.setDescription(request.getDescription());
        workLog.setDatesubmitted(LocalDateTime.now());
        workLog.setStudentIdstudent(student);
        workLog.setMentorComment("");
        workLogRepository.save(workLog);
        return mapToResponse(workLog);
    }

    public List<WorkLogResponse> getAll(Integer internshipId)
    {

        List<WorkLog> workLogList = workLogRepository.findByinternship_IdOrderByDatesubmittedDesc(internshipId);

        return workLogList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private WorkLogResponse mapToResponse(WorkLog workLog)
    {
        WorkLogResponse response = new WorkLogResponse();
        response.setId(workLog.getId());
        response.setDescription(workLog.getDescription());
        response.setDatesubmitted(workLog.getDatesubmitted());
        response.setMentorComment(workLog.getMentorComment());
        response.setWeekNumber(workLog.getWeekNumber());
        return response;
    }
    public WorkLogResponse updateEntry(Integer logId, WorkLogRequest request, Integer studentId)
    {
        WorkLog log = workLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("Praksa nije pronadjena"));

        if(log.getStudentIdstudent().equals(studentId))
        {
            throw new RuntimeException("Nema dozvolu da mijenjate tudji dnevnik!");
        }
        log.setWeekNumber(request.getWeekNumber());
        log.setDescription(request.getDescription());
        workLogRepository.save(log);
        return mapToResponse(log);
    }

    @Transactional(readOnly = true)
    public List<WorkLogResponse> getLogsForStudentOnInternship(Integer internshipId, Integer studentId) {
        List<WorkLog> logs = workLogRepository.findByInternship_IdAndStudentIdstudent_Id(internshipId, studentId);

        return logs.stream().map(log -> {
            WorkLogResponse dto = new WorkLogResponse();
            dto.setId(log.getId());
            dto.setWeekNumber(log.getWeekNumber());
            dto.setDescription(log.getDescription());
            dto.setDatesubmitted(log.getDatesubmitted());
            dto.setMentorComment(log.getMentorComment());
            dto.setStudentName(log.getStudentIdstudent().getFirstname() + " " + log.getStudentIdstudent().getSurname());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateMentorComment(Integer logId, String comment) {
        WorkLog log = workLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Dnevnik nije pronađen"));
        log.setMentorComment(comment);
        workLogRepository.save(log);
    }

}
