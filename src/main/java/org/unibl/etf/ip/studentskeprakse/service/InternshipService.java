package org.unibl.etf.ip.studentskeprakse.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.studentskeprakse.dto.InternshipRequestDTO;
import org.unibl.etf.ip.studentskeprakse.dto.InternshipResponseDTO;
import org.unibl.etf.ip.studentskeprakse.entity.*;
import org.unibl.etf.ip.studentskeprakse.repository.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@Transactional
public class InternshipService {
    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;
    private final ApplicationRepository applicationRepository;

    public InternshipService(InternshipRepository internshipRepository, ApplicationRepository applicationRepository ,UserRepository userRepository, CompanyRepository companyRepository, SkillRepository skillRepository) {
        this.internshipRepository = internshipRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
        this.applicationRepository  = applicationRepository;
    }


  /*  @Transactional
    public Internship createInternship(InternshipRequestDTO internshipDTO, String username)
    {
        Company company = companyRepository.findByUser_Username(username).orElseThrow(() -> new RuntimeException("Korisnik nema registrovanu komapniju"));


        Internship internship = new Internship();
        internship.setTitle(internshipDTO.getTitle());
        internship.setDescription(internshipDTO.getDescription());
        internship.setStartDate(internshipDTO.getStartDate());
        internship.setEndDate(internshipDTO.getEndDate());
        internship.setRequirements(internshipDTO.getRequirements());
        internship.setIsPublished(true);

        internship.setCompany(company);

        if(internshipDTO.getSkillsId() != null && !internshipDTO.getSkillsId().isEmpty())
        {
            List<Skill> skills = skillRepository.findAllById(internshipDTO.getSkillsId());
            internship.setSkills(skills);
        }
        return internshipRepository.save(internship);
    }*/

    @Transactional
    public Internship createInternship(InternshipRequestDTO internshipDTO, String username) {
        Company company = companyRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Korisnik nema registrovanu kompaniju"));

        Internship internship = new Internship();
        internship.setTitle(internshipDTO.getTitle());
        internship.setDescription(internshipDTO.getDescription());
        internship.setStartDate(internshipDTO.getStartDate());
        internship.setEndDate(internshipDTO.getEndDate());
        internship.setIsPublished(true);
        internship.setCompany(company);

        if (internshipDTO.getSkills() != null && !internshipDTO.getSkills().isEmpty()) {
            List<Skill> skills = internshipDTO.getSkills().stream()
                    .map(name -> {
                        return skillRepository.findByNameIgnoreCase(name.trim())
                                .orElseGet(() -> {
                                    Skill newSkill = new Skill();
                                    newSkill.setName(name.trim());
                                    return skillRepository.save(newSkill);
                                });
                    })
                    .collect(Collectors.toList());

            internship.setSkills(skills);
        }

        return internshipRepository.save(internship);
    }
    public List<InternshipResponseDTO> getAll()
    {
      List<Internship> internships =  internshipRepository.findAll();

      return internships.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public List<InternshipResponseDTO> getUnappliedInternships(Integer studentId)
    {
        List<Internship> internships =  internshipRepository.findUnappliedInternships(studentId);
        return internships.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public boolean deleteInternshipById(Integer internshipId)
    {
        if(internshipRepository.existsInternshipsById(internshipId))
        {
            try {
                internshipRepository.deleteById(internshipId);
                return true;
            }catch(Exception e) {
                return false;
            }
        }
        return false;
    }

    public InternshipResponseDTO getById(Integer id)
    {
        return internshipRepository.findById(id).map(this::convertDTO)
                .orElseThrow(()-> new EntityNotFoundException("Praksa sa ID: " + id + "ne postoji!"));
    }


    public List<InternshipResponseDTO> getStudentInternships(Integer studentId)
    {
        List<Application> applications = applicationRepository.findByStudentIdstudent_IdAndStatus(studentId,"ACCEPTED");

        return applications.stream().
                map(Application::getInternshipIdinternship).
        map(this::convertDTO).collect(Collectors.toList());
    }
    private InternshipResponseDTO convertDTO(Internship i)
    {
        InternshipResponseDTO dto = new InternshipResponseDTO();
        dto.setId(i.getId());
        dto.setTitle(i.getTitle());
        dto.setDescription(i.getDescription());
        dto.setStartDate(i.getStartDate());
        dto.setEndDate(i.getEndDate());
        dto.setCompanyName(i.getCompany().getName());
        dto.setSkillNames(i.getSkills().stream().map(Skill::getName).collect(Collectors.toList()));

        return dto;
    }

    @Transactional
    public InternshipResponseDTO updateInternship(Integer id, InternshipRequestDTO request) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Praksa sa ID: " + id + " ne postoji!"));
        internship.setTitle(request.getTitle());
        internship.setDescription(request.getDescription());
        internship.setStartDate(request.getStartDate());
        internship.setEndDate(request.getEndDate());
        if (request.getSkills() != null) {
            List<Skill> updatedSkills = request.getSkills().stream()
                    .filter(name -> name != null && !name.trim().isEmpty())
                    .map(name -> {
                        String trimmedName = name.trim();
                        return skillRepository.findByNameIgnoreCase(trimmedName)
                                .orElseGet(() -> {
                                    Skill newSkill = new Skill();
                                    newSkill.setName(trimmedName);
                                    return skillRepository.save(newSkill);
                                });
                    })
                    .collect(Collectors.toList());
            internship.setSkills(updatedSkills);
        }
        Internship savedInternship = internshipRepository.save(internship);
        return convertDTO(savedInternship);
    }


    public List<InternshipResponseDTO> getInternshipsByCompany(Integer userId) {

        int companyId = companyRepository.findByUser_Id(userId).get().getId();
        return internshipRepository.findAllByCompanyId(companyId)
                .stream()
                .map(entity -> {
                    InternshipResponseDTO response = new InternshipResponseDTO();
                    response.setId(entity.getId());
                    response.setTitle(entity.getTitle());
                    response.setDescription(entity.getDescription());
                    response.setCompanyName(entity.getCompany().getName());
                    response.setStartDate(entity.getStartDate());
                    response.setEndDate(entity.getEndDate());
                    // Mapiraj i ostala polja (vještine itd.)
                    return response;
                })
                .collect(Collectors.toList());
    }
   /* public InternshipResponseDTO updateInternship(Integer id, InternshipRequestDTO request) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Praksa sa ID: " + id + " ne postoji!"));

        internship.setTitle(request.getTitle());
        internship.setDescription(request.getDescription());
        internship.setStartDate(request.getStartDate());
        internship.setEndDate(request.getEndDate());
        internship.setRequirements(request.getRequirements());
        if (request.getSkillsId() != null) {
            List<Skill> updatedSkills = skillRepository.findAllById(request.getSkillsId());
            internship.setSkills(updatedSkills);
        }

        Internship savedInternship = internshipRepository.save(internship);

        return convertDTO(savedInternship);
    }*/
}
