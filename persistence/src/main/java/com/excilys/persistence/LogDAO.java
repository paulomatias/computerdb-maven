package com.excilys.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.domain.Log;

public interface LogDAO extends JpaRepository<Log, Long> {

}
