package org.unibl.etf.ip.studentskeprakse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.studentskeprakse.entity.Company;
import org.unibl.etf.ip.studentskeprakse.entity.User;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company,Integer> {

    Optional<Company> findByUser_Username(String username);
    Optional<Company> findByUser_Id(Integer id);
}
