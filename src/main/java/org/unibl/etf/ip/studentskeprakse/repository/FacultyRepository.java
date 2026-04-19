package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.studentskeprakse.entity.Faculty;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty,Integer>
{

   Optional<Faculty> findByUserIduser_Id(Integer userId);
}
