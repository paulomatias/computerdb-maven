package com.excilys.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.domain.Computer;

public interface ComputerDAO extends JpaRepository<Computer, Long> {
	Page<Computer> findByName(String name, Pageable page);

	Page<Computer> findByCompanyName(String companyName, Pageable page);

	Page<Computer> findByNameAndCompanyName(String name, String companyName,
			Pageable page);
}
