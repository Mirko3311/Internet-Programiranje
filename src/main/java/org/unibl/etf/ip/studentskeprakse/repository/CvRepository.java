package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.ip.studentskeprakse.entity.Cv;
import org.unibl.etf.ip.studentskeprakse.entity.Student;

import java.util.Optional;

@Repository
public interface CvRepository extends JpaRepository<Cv, Integer>
{

    Optional<Cv> findByStudent_User_Username(String studentUserUsername);

    Optional<Cv> findByStudent_User_Id(Integer studentUserId);
    Optional<Cv> findByStudent(Student student);

}
