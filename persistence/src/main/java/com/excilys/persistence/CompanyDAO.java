package com.excilys.persistence;

import java.util.List;

import mapper.CompanyRowMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.domain.Company;

@Repository
public class CompanyDAO {
	@Autowired
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	@Autowired
	JdbcTemplate jt;

	/*
	 * Functions
	 */

	/*
	 * Return the company list
	 */
	public List<Company> getList() {
		String select = "SELECT id, name FROM `computer-database-db`.`company`;";
		return jt.query(select, new CompanyRowMapper());
	}
}