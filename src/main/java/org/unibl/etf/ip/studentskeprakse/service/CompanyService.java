package org.unibl.etf.ip.studentskeprakse.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.studentskeprakse.dto.CompanyRequest;
import org.unibl.etf.ip.studentskeprakse.dto.CompanyResponse;
import org.unibl.etf.ip.studentskeprakse.entity.Company;
import org.unibl.etf.ip.studentskeprakse.entity.Role;
import org.unibl.etf.ip.studentskeprakse.entity.User;
import org.unibl.etf.ip.studentskeprakse.repository.CompanyRepository;
import org.unibl.etf.ip.studentskeprakse.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CompanyService(CompanyRepository companyRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CompanyResponse> getAll()
    {
       return  companyRepository.findAll().stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public CompanyResponse convertDTO(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setAddress(company.getAddress());
        companyResponse.setUsername(company.getUser().getUsername());
        companyResponse.setIsActive(company.getUser().getIsActive());
        return companyResponse;
    }

    public Boolean createCompany(CompanyRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPassword);
            user.setRole(Role.COMPANY);
            user.setIsActive(true);

            User savedUser = userRepository.save(user);
            Company company = new Company();
            company.setName(request.getName());
            company.setAddress(request.getAddress());
            company.setDescription(request.getDescription());
            company.setUser(savedUser);

            companyRepository.save(company);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void toggleStatus(Integer companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Kompanija nije pronađena"));
        User user = company.getUser();
        user.setIsActive(!user.getIsActive());
    }

    @Transactional
    public CompanyResponse update(Integer id, CompanyRequest dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kompanija sa ID " + id + " nije pronađena"));
        company.setName(dto.getName());
        company.setAddress(dto.getAddress());
        company.setDescription(dto.getDescription());
        Company updatedCompany = companyRepository.save(company);
        return convertDTO(updatedCompany);
    }

    public @Nullable CompanyResponse getById(Integer id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kompanija sa ID " + id + " nije pronađena"));
        return  convertDTO(company);
    }
}