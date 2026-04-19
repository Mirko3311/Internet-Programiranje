package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.studentskeprakse.dto.WorkLogResponse;
import org.unibl.etf.ip.studentskeprakse.entity.WorkLog;

import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog,Integer>
{

    List<WorkLog> findByinternship_IdOrderByDatesubmittedDesc(Integer internshipIdinternshipId);
    List<WorkLog> findByInternship_IdAndStudentIdstudent_Id(Integer internshipId, Integer studentId);
    List<WorkLog> findAllByStudentIdstudent_IdAndInternship_IdOrderByWeekNumberAsc(Integer studentId, Integer internshipId);
}
