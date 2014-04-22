package com.excilys.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.domain.Company;
import com.excilys.domain.QCompany;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDAO {
	@Autowired
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	@Autowired
	JdbcTemplate jt;
	@Autowired
	SessionFactory session;

	/*
	 * Functions
	 */

	/*
	 * Return the company list
	 */
	public List<Company> getList() {

		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		query.from(QCompany.company);
		return query.list(QCompany.company);
	}
}