package org.unibl.etf.ip.studentskeprakse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:4200")
@RequestMapping("/api/faculty")
public class FacultyController {



}
