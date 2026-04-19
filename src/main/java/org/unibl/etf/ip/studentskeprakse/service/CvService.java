package org.unibl.etf.ip.studentskeprakse.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.unibl.etf.ip.studentskeprakse.dto.*;
import org.unibl.etf.ip.studentskeprakse.entity.*;
import org.unibl.etf.ip.studentskeprakse.repository.CvRepository;
import org.unibl.etf.ip.studentskeprakse.repository.SkillRepository;
import org.unibl.etf.ip.studentskeprakse.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CvService {
    private StudentRepository studentRepository;
    private CvRepository cvRepository;
    private SkillRepository skillRepository;

    public CvService(StudentRepository studentRepository, CvRepository cvRepository, SkillRepository skillRepository) {
        this.studentRepository = studentRepository;
        this.cvRepository = cvRepository;
        this.skillRepository = skillRepository;
    }

    @Transactional
    public CvResponseDTO saveCv(@Valid @RequestBody CvRequestDTO requestDTO) {
        Cv cv = new Cv();
        cv.setEmail(requestDTO.getEmail());
        cv.setSummary(requestDTO.getSummary());
        cv.setPhoneNumber(requestDTO.getPhoneNumber());
        cv.setPhotoPath(requestDTO.getPhotoPath());

        cv.setInterests(requestDTO.getInterests());
        cv.setPhotoPath(" ");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUser_Username(username).orElseThrow(() -> new RuntimeException("Student sa ID-em nije pronađen!"));
        System.out.println(student.getFirstname() + " " + student.getSurname() + " "+ student.getId());
        cv.setStudent(student);

        Cv savedCv = cvRepository.save(cv);

        if (requestDTO.getInternships() != null) {
            List<InternshipExperience> internships = requestDTO.getInternships().stream().map(internDTO ->
            {
                InternshipExperience ie = new InternshipExperience();
                ie.setCompanyName(internDTO.getCompanyName());
                ie.setPosition(internDTO.getPosition());
                ie.setDescription(internDTO.getDescription());
                ie.setPeriod(internDTO.getPeriod());
                ie.setCv(cv);
                return ie;
            }).collect(Collectors.toList());
            cv.setInternships(internships);
        }
        if (requestDTO.getEducations() != null) {
            List<Education> educations = requestDTO
                    .getEducations().stream().map(eduDto ->
                    {
                        Education education = new Education();
                        education.setInstitution(eduDto.getInstitution());
                        education.setDegree(eduDto.getDegree());
                        education.setFieldOfStudy(eduDto.getFieldOfStudy());
                        education.setPeriod(eduDto.getPeriod());
                        education.setCv(cv);
                        return education;
                    }).collect(Collectors.toList());
            cv.setEducations(educations);
        }
        if (requestDTO.getWorkExperiences() != null) {
            List<WorkExperience> workExperiences = requestDTO.getWorkExperiences().stream().map(workDto -> {
                WorkExperience work = new WorkExperience();
                work.setEmployer(workDto.getEmployer());
                work.setPosition(workDto.getPosition());
                work.setCity(workDto.getCity());
                work.setPeriod(workDto.getPeriod());
                work.setMainActivities(workDto.getMainActivities());
                work.setCv(cv);
                return work;
            }).collect(Collectors.toList());
            cv.setWorkExperiences(workExperiences);
        }

        if (requestDTO.getSkillNames() != null) {
            for (String skillName : requestDTO.getSkillNames()) {
                String name = skillName.trim();
                if (name.isEmpty()) continue;

                Skill skill = skillRepository.findByNameIgnoreCase(name).orElseGet(
                        () ->
                        {
                            Skill newSkill = new Skill();
                            newSkill.setName(name);
                            return skillRepository.save(newSkill);
                        }
                );
                if (!cv.getSkills().contains(skill)) {
                    cv.getSkills().add(skill);
                }
            }
        }


        return convertToResponseDTO(savedCv);
    }

    public CvResponseDTO getCvByUsername(String username) {
        return cvRepository.findByStudent_User_Username(username).map(this::convertToResponseDTO).orElse(null);
    }

    private CvResponseDTO convertToResponseDTO(Cv entity) {
        CvResponseDTO cvResponseDTO = new CvResponseDTO();
        cvResponseDTO.setLastName(entity.getStudent().getSurname());
        cvResponseDTO.setFirstName(entity.getStudent().getFirstname());
        cvResponseDTO.setId(entity.getId());
        cvResponseDTO.setEmail(entity.getEmail());
        cvResponseDTO.setSummary(entity.getSummary());
        cvResponseDTO.setPhoneNumber(entity.getPhoneNumber());
        cvResponseDTO.setPhotoPath(entity.getPhotoPath());
        cvResponseDTO.setInterests(entity.getInterests());

        if(entity.getSkills() != null)
        {
            cvResponseDTO.setSkillNames(entity.getSkills().stream().map(skill -> skill.getName()).collect(Collectors.toList()));
        }
        if (entity.getInternships() != null) {
            cvResponseDTO.setInternships(entity.getInternships()
                    .stream().map(eduDto ->
                    {
                        InternshipExperienceResponseDTO ie = new InternshipExperienceResponseDTO();
                        ie.setIdInternshipExperience(eduDto.getId());
                        ie.setPeriod(eduDto.getPeriod());
                        ie.setDescription(eduDto.getDescription());
                        ie.setCompanyName(eduDto.getCompanyName());
                        return ie;
                    }).collect(Collectors.toList()));
        }
        if (entity.getEducations() != null) {
            cvResponseDTO.setEducations(entity.getEducations()
                    .stream().map(eduDto ->
                    {
                        EducationResponseDTO edu = new EducationResponseDTO();
                        edu.setInstitution(eduDto.getInstitution());
                        edu.setDegree(eduDto.getDegree());
                        edu.setFieldOfStudy(eduDto.getFieldOfStudy());
                        edu.setPeriod(eduDto.getPeriod());
                        return edu;
                    }).collect(Collectors.toList()));
        }
        if (entity.getWorkExperiences() != null) {
            cvResponseDTO.setWorkExperiences(entity.getWorkExperiences().stream().map(work -> {
                WorkExperienceResponseDTO wDto = new WorkExperienceResponseDTO();
                wDto.setEmployer(work.getEmployer());
                wDto.setPeriod(work.getPeriod());
                wDto.setCity(work.getCity());
                wDto.setMainActivities(work.getMainActivities());
                wDto.setPosition(work.getPosition());
                return wDto;
            }).collect(Collectors.toList()));
        }
        return cvResponseDTO;
    }


    @Transactional(readOnly = true)
    public CVResponse getCVByStudentId(Integer studentId) {
        Cv cv = cvRepository.findByStudent_User_Id(studentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student sa ID-jem " + studentId + " nema kreiran profil (CV)."));
        CVResponse dto = new CVResponse();
        dto.setId(cv.getId());
        dto.setFirstName(cv.getStudent().getFirstname());
        dto.setLastName(cv.getStudent().getSurname());
        dto.setSummary(cv.getSummary());
        dto.setPhotoPath(cv.getPhotoPath());
        dto.setEmail(cv.getEmail());
        dto.setPhoneNumber(cv.getPhoneNumber());
        dto.setInterests(cv.getInterests());


        dto.setSkillNames(cv.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toList()));

        dto.setEducations(cv.getEducations().stream().map(edu -> {
            EducationResponseDTO eDto = new EducationResponseDTO();
            eDto.setInstitution(edu.getInstitution());
            eDto.setDegree(edu.getDegree());
            eDto.setPeriod(edu.getPeriod());
            return eDto;
        }).collect(Collectors.toList()));

        dto.setWorkExperiences(cv.getWorkExperiences().stream().map(work -> {
            WorkExperienceResponseDTO wDto = new WorkExperienceResponseDTO();
            wDto.setEmployer(work.getEmployer());
            wDto.setPeriod(work.getPeriod());
            wDto.setPeriod(work.getPeriod());
            wDto.setCity(work.getCity());
            wDto.setMainActivities(work.getMainActivities());
            return wDto;
        }).collect(Collectors.toList()));

        dto.setInternships(cv.getInternships().stream().map(intern -> {
            InternshipExperienceResponseDTO iDto = new InternshipExperienceResponseDTO();
            iDto.setDescription(intern.getDescription());
            iDto.setCompanyName(intern.getCompanyName());
            iDto.setPeriod(intern.getPeriod());
            iDto.setIdInternshipExperience(intern.getId());
            return iDto;
        }).collect(Collectors.toList()));

        return dto;
    }


    public void addSkillToCV(Integer cvId, List<String> skillNames)
    {
        Cv cv = cvRepository.findById(cvId).orElseThrow();

        for(String name: skillNames)
        {
            Skill skill = skillRepository.findByNameIgnoreCase(name.trim()).orElseGet(() ->
            {
                Skill newSkill = new Skill();
                newSkill.setName(name.trim());
                return skillRepository.save(newSkill);
            });
            if(!cv.getSkills().contains(skill))
            cv.getSkills().add(skill);
        }
        cvRepository.save(cv);
    }

    @Transactional
    public void deleteCurrentUsersCV(String username)
    {
       Cv cv = cvRepository.findByStudent_User_Username(username).orElseThrow(()-> new EntityNotFoundException("CV nije pronadjen"));
       cvRepository.delete(cv);
    }


    @Transactional
    public CvResponseDTO updateCv(Integer id, CvRequestDTO requestDTO) {
        Cv currentCV = cvRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CV nije pronađen"));

        currentCV.setSummary(requestDTO.getSummary());
        currentCV.setEmail(requestDTO.getEmail());
        currentCV.setPhoneNumber(requestDTO.getPhoneNumber());
        currentCV.setInterests(requestDTO.getInterests());
        currentCV.setPhotoPath(requestDTO.getPhotoPath());

        currentCV.getSkills().clear();
        if (requestDTO.getSkillNames() != null) {
            for (String name : requestDTO.getSkillNames()) {
                String trimmedName = name.trim();
                if (trimmedName.isEmpty()) continue;
                Skill skill = skillRepository.findByNameIgnoreCase(trimmedName)
                        .orElseGet(() -> {
                            Skill newSkill = new Skill();
                            newSkill.setName(trimmedName);
                            return skillRepository.save(newSkill); // Snimamo novu vještinu ako je nema
                        });

                if (!currentCV.getSkills().contains(skill)) {
                    currentCV.getSkills().add(skill);
                }
            }
        }
        currentCV.getWorkExperiences().clear();
        if (requestDTO.getWorkExperiences() != null) {
            for (WorkExperienceRequestDTO workDto : requestDTO.getWorkExperiences()) {
                WorkExperience work = new WorkExperience();
                work.setEmployer(workDto.getEmployer());
                work.setPosition(workDto.getPosition());
                work.setCity(workDto.getCity());
                work.setPeriod(workDto.getPeriod());
                work.setMainActivities(workDto.getMainActivities());
                work.setCv(currentCV);
                currentCV.getWorkExperiences().add(work);
            }
        }
        currentCV.getEducations().clear();
        if (requestDTO.getEducations() != null) {
            for (EducationRequestDTO eduDto : requestDTO.getEducations()) {
                Education edu = new Education();
                edu.setInstitution(eduDto.getInstitution());
                edu.setDegree(eduDto.getDegree());
                edu.setFieldOfStudy(eduDto.getFieldOfStudy());
                edu.setPeriod(eduDto.getPeriod());
                edu.setCv(currentCV);
                currentCV.getEducations().add(edu);
            }
        }

        Cv savedCv = cvRepository.save(currentCV);
        return convertToResponseDTO(savedCv);
    }
/*    @Transactional
    public Cv updateCv(Integer id,Cv data)
    {
       Cv currentCV = cvRepository.findById(id).orElseThrow();

       currentCV.setSummary(data.getSummary());
       currentCV.setEmail(data.getEmail());
       currentCV.setPhoneNumber(data.getPhoneNumber());
       currentCV.setInterests(data.getInterests());
       currentCV.setPhotoPath(data.getPhotoPath());

        if (currentCV.getInternships() != null) {
            currentCV.getInternships().clear();
        }
        if (data.getInternships() != null) {
            for (InternshipExperience intern : data.getInternships()) {
                intern.setCv(currentCV);
                currentCV.getInternships().add(intern);
            }
        }
       if(currentCV.getEducations()!=null)
       {
           currentCV.getEducations().forEach(e->e.setCv(null));
           currentCV.getEducations().clear();
       }

       if(data.getEducations()!=null)
       {
           for(Education edu: data.getEducations())
           {
               edu.setCv(currentCV);
               currentCV.getEducations().add(edu);
           }
       }

       if(currentCV.getWorkExperiences()!=null)
       {
           currentCV.getWorkExperiences().forEach(e->e.setCv(null));
           currentCV.getWorkExperiences().clear();
       }
       if(data.getWorkExperiences()!=null)
       {
           for(WorkExperience work: data.getWorkExperiences())
           {
               work.setCv(currentCV);
               currentCV.getWorkExperiences().add(work);
           }
       }
       currentCV.setSkills(data.getSkills());
       return cvRepository.save(currentCV);
    }

    */
}
