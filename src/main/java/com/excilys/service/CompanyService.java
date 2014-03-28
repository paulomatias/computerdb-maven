package com.excilys.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.domain.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ConnectionManager;
import com.excilys.wrapper.ComputerWrapper;

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
@Service
public class CompanyService {

	@Autowired
	private CompanyDAO companyDAO;
	static Logger log = LoggerFactory.getLogger(CompanyService.class);

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	public ComputerWrapper addComputer() {

		List<Company> listCompanies = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
			log.info("Counting number of companies...");
			listCompanies = companyDAO.getList();
			ConnectionManager.getConnection().commit();
		} catch (SQLException e) {
			try {
				ConnectionManager.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ComputerWrapper wrapper = ComputerWrapper.builder()
				.listCompanies(listCompanies).build();
		return wrapper;
	}

}
