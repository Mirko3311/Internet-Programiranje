package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.studentskeprakse.entity.Internship;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship,Integer>
{

    boolean existsInternshipsById(Integer id);
    List<Internship> findAllByCompanyId(Integer companyId);
    @Query("SELECT i FROM Internship i WHERE i.id NOT IN " +
            "(SELECT a.internshipIdinternship.id FROM Application a " +
            " WHERE a.studentIdstudent.id = :studentId " +
            " AND a.status = 'ACCEPTED')")
    List<Internship> findUnappliedInternships(@Param("studentId") Integer studentId);
}
