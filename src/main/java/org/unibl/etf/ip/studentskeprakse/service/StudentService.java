package org.unibl.etf.ip.studentskeprakse.service;

import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.studentskeprakse.dto.InternshipResponseDTO;
import org.unibl.etf.ip.studentskeprakse.dto.StudentRequest;
import org.unibl.etf.ip.studentskeprakse.dto.StudentResponseDTO;
import org.unibl.etf.ip.studentskeprakse.entity.*;
import org.unibl.etf.ip.studentskeprakse.repository.FacultyRepository;
import org.unibl.etf.ip.studentskeprakse.repository.StudentRepository;
import org.unibl.etf.ip.studentskeprakse.repository.UserRepository;
import org.unibl.etf.ip.studentskeprakse.security.UserDetailsImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@Transactional
public class StudentService
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    public StudentService(StudentRepository studentRepository, UserRepository userRepository, FacultyRepository facultyRepository)
    {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<StudentResponseDTO> getAll()
    {
        List<Student> students =  studentRepository.findAll();
        return students.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    @Transactional
    public StudentResponseDTO save(StudentRequest studentRequest) {
        UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = currentUser.getId();
        Faculty faculty = facultyRepository.findByUserIduser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Ulogovani korisnik nije administrator fakulteta!"));

        User studentUser = new User();
        studentUser.setUsername(studentRequest.getUsername());
        studentUser.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        studentUser.setRole(Role.STUDENT);
        studentUser.setIsActive(true);
        studentUser = userRepository.save(studentUser);

        Student student = new Student();
        student.setFirstname(studentRequest.getFirstname());
        student.setSurname(studentRequest.getSurname());
        student.setEmail(studentRequest.getEmail());
        student.setIndex(studentRequest.getIndex());

        student.setUser(studentUser);
        student.setFaculty(faculty);
        student = studentRepository.save(student);

        return convertDTO(student);
    }
    private StudentResponseDTO convertDTO(Student student)
    {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setFirstname(student.getFirstname());
        dto.setSurname(student.getSurname());
        dto.setEmail(student.getEmail());
        dto.setIndex(student.getIndex());
        dto.setFacultyName(student.getFaculty().getName());
        dto.setUsername(student.getUser().getUsername());
        return dto;
    }


    @Transactional
    public void processCSV(MultipartFile file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 7) {
                    continue;
                }
                StudentRequest request = new StudentRequest();
                request.setFirstname(data[0].trim());
                request.setSurname(data[1].trim());
                request.setIndex(data[2].trim());
                request.setEmail(data[3].trim());
                request.setUsername(data[4].trim());
                request.setPassword(data[5].trim());
                this.save(request);
            }
        } catch (Exception e) {
            throw new Exception("Greška prilikom parsiranja CSV fajla: " + e.getMessage());
        }
    }

    @Transactional
    public StudentResponseDTO update(Integer id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student sa ID-em " + id + " nije pronađen."));

        student.setFirstname(request.getFirstname());
        student.setSurname(request.getSurname());
        student.setIndex(request.getIndex());
        student.setEmail(request.getEmail());
        User user = student.getUser();
        user.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        userRepository.save(user);
        Student updatedStudent = studentRepository.save(student);
        return convertDTO(updatedStudent);
    }

    public StudentResponseDTO getById(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student sa ID-em " + id + " nije pronađen."));
      return convertDTO(student);
    }

    @Transactional
    public void delete(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nemoguće obrisati: Student nije pronađen."));
        studentRepository.delete(student);
    }
}
