package org.unibl.etf.ip.studentskeprakse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.studentskeprakse.dto.ApplicationResponseDTO;
import org.unibl.etf.ip.studentskeprakse.dto.StudentResponseDTO;
import org.unibl.etf.ip.studentskeprakse.entity.Application;
import org.unibl.etf.ip.studentskeprakse.entity.Company;
import org.unibl.etf.ip.studentskeprakse.entity.Internship;
import org.unibl.etf.ip.studentskeprakse.entity.Student;
import org.unibl.etf.ip.studentskeprakse.repository.ApplicationRepository;
import org.unibl.etf.ip.studentskeprakse.repository.CompanyRepository;
import org.unibl.etf.ip.studentskeprakse.repository.InternshipRepository;
import org.unibl.etf.ip.studentskeprakse.repository.StudentRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<ApplicationResponseDTO> getApplicationsForCompany(Integer userId)
    {
        Company company =  companyRepository.findByUser_Id(userId).orElseThrow(() -> new RuntimeException("Nije pronadjena kompanija za identifikator korisnika"));
        List<Application> applications = applicationRepository.findByCompanyId(company.getId());
        return applications.stream().map(app->
        {
           ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
           applicationResponseDTO.setApplicationId(app.getId());
           applicationResponseDTO.setInternshipTitle(app.getInternshipIdinternship().getTitle());
           applicationResponseDTO.setCompanyName(app.getInternshipIdinternship().getCompany().getName());
           applicationResponseDTO.setStatus(app.getStatus());
           applicationResponseDTO.setInternshipId(app.getInternshipIdinternship().getId());
           applicationResponseDTO.setStudentFullName(app.getStudentIdstudent().getFirstname() + " " + app.getStudentIdstudent().getSurname());
           applicationResponseDTO.setStudentId(app.getStudentIdstudent().getId());
           return applicationResponseDTO;
        }).collect(Collectors.toList());
    }

    public List<ApplicationResponseDTO> getApplicationsForInternship(Integer internshipId) {
        return applicationRepository.findAllByInternshipIdinternship_Id(internshipId)
                .stream()
                .map(app ->
                {
                     ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
                     applicationResponseDTO.setApplicationId(app.getId());
                     applicationResponseDTO.setInternshipTitle(app.getInternshipIdinternship().getTitle());
                     applicationResponseDTO.setCompanyName(app.getInternshipIdinternship().getCompany().getName());
                     applicationResponseDTO.setStatus(app.getStatus());
                     applicationResponseDTO.setInternshipId(app.getInternshipIdinternship().getId());
                     applicationResponseDTO.setStudentId(app.getStudentIdstudent().getId());
                     applicationResponseDTO.setStudentFullName(app.getStudentIdstudent().getFirstname() + " " + app.getStudentIdstudent().getSurname());
                     applicationResponseDTO.setGrade(app.getGrade());
                     applicationResponseDTO.setComment(app.getComment());
                    return applicationResponseDTO;
                  }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAcceptedStudents(Integer internshipId) {
        List<Application> applications = applicationRepository
                .findAllByInternshipIdinternship_IdAndStatus(internshipId, "ACCEPTED");
        return applications.stream()
                .map(app -> {
                    Student s = app.getStudentIdstudent();
                    return new StudentResponseDTO(
                            s.getId(),
                            s.getFirstname(),
                            s.getSurname(),
                            s.getIndex(),
                            s.getEmail(),
                            s.getUser().getUsername(),
                            s.getFaculty().getName());
                }).collect(Collectors.toList());
    }


    public boolean updateApplication(Integer id, String newStatus)
    {
        return applicationRepository.findById(id).map(app->
        {
            app.setStatus(newStatus);
            applicationRepository.save(app);
            return true;
        }).orElse(false);
    }

    public List<ApplicationResponseDTO> getAll()
    {

        List<Application> applications = applicationRepository.findAll();
        return applications.stream().map(app->
        {
            ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
            applicationResponseDTO.setApplicationId(app.getId());
            applicationResponseDTO.setInternshipTitle(app.getInternshipIdinternship().getTitle());
            applicationResponseDTO.setCompanyName(app.getInternshipIdinternship().getCompany().getName());
            applicationResponseDTO.setStatus(app.getStatus());
            applicationResponseDTO.setInternshipId(app.getInternshipIdinternship().getId());
            applicationResponseDTO.setStudentFullName(app.getStudentIdstudent().getFirstname() + " " + app.getStudentIdstudent().getSurname());
            applicationResponseDTO.setStudentId(app.getStudentIdstudent().getId());
            applicationResponseDTO.setComment(app.getComment());
            applicationResponseDTO.setGrade(app.getGrade());
            return applicationResponseDTO;
        }).collect(Collectors.toList());
    }


    @Transactional
    public void updateGradeAndComment(Integer id, Integer grade, String comment) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setGrade(grade);
        application.setComment(comment);
        applicationRepository.save(application);
    }

    @Transactional
    public void createApplication(Integer studentId, Integer internshipId) {
        Application application = new Application();
        Student student = studentRepository.findByUser_Id(studentId)
                .orElseThrow(() -> new RuntimeException("Student nije pronađen"));
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Praksa nije pronađena"));

        application.setStudentIdstudent(student);
        application.setInternshipIdinternship(internship);
        application.setStatus("PENDING");
        application.setApplied(Instant.now());

        applicationRepository.save(application);
    }
}
