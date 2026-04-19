package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.studentskeprakse.entity.Skill;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer>
{
    Optional<Skill> findByNameIgnoreCase(String name);
}
