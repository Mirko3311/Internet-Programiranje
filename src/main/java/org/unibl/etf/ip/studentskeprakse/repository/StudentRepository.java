package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.studentskeprakse.entity.Skill;
import org.unibl.etf.ip.studentskeprakse.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
    Optional<Student> findByUser_Username(String username);
    Optional<Student> findByUser_Id(Integer userId);
}
