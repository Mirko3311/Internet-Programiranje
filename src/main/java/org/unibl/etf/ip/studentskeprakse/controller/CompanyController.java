package org.unibl.etf.ip.studentskeprakse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.studentskeprakse.dto.CompanyRequest;
import org.unibl.etf.ip.studentskeprakse.dto.CompanyResponse;
import org.unibl.etf.ip.studentskeprakse.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(allowCredentials = "true",origins = {"http://localhost:4200","http://localhost:8081","http://localhost:8082"})
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyResponse>> listAll()
    {
        return ResponseEntity.ok(companyService.getAll());
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleStatus(@PathVariable Integer id) {
        companyService.toggleStatus(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody CompanyRequest request) {
        boolean success = companyService.createCompany(request);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Integer id,
            @RequestBody CompanyRequest dto) {
        CompanyResponse updated = companyService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(companyService.getById(id));
    }
}