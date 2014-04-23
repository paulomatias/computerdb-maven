package com.excilys.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.domain.Company;

public interface CompanyDAO extends JpaRepository<Company, Long> {

}