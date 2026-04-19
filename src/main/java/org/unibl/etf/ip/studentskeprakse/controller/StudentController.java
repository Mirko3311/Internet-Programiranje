package org.unibl.etf.ip.studentskeprakse.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.studentskeprakse.dto.InternshipResponseDTO;
import org.unibl.etf.ip.studentskeprakse.dto.StudentRequest;
import org.unibl.etf.ip.studentskeprakse.dto.StudentResponseDTO;
import org.unibl.etf.ip.studentskeprakse.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "http://localhost:8082", "http://localhost:8080"})
public class StudentController
{

    private final StudentService studentService;

    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequest request) {

        StudentResponseDTO newStudent = studentService.save(request);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Integer id, @RequestBody StudentRequest request) {
        StudentResponseDTO updated = studentService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Fajl je prazan!");
        }
        try {
            studentService.processCSV(file);
            return ResponseEntity.ok("Svi studenti iz fajla su uspješno dodati!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<StudentResponseDTO>> listAll()
    {
        return ResponseEntity.ok(studentService.getAll());
    }
}
