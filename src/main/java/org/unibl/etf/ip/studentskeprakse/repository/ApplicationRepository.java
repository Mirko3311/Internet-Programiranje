package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.unibl.etf.ip.studentskeprakse.entity.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Integer>
{
    List<Application> findByStudentIdstudent_IdAndStatus(Integer studentId, String status);

    @Query("SELECT a FROM Application a WHERE a.internshipIdinternship.company.id = :companyId")
    List<Application> findByCompanyId(@Param("companyId") Integer companyId);

    List<Application> findAllByInternshipIdinternship_Id(Integer internshipId);

    List<Application> findAllByInternshipIdinternship_IdAndStatus(Integer internshipId, String status);
}
