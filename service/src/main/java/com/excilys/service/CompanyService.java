package com.excilys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.domain.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.wrapper.ComputerWrapper;

@Service
@Transactional(readOnly = true)
public class CompanyService {

	@Autowired
	private CompanyDAO companyDAO;
	static Logger log = LoggerFactory.getLogger(CompanyService.class);

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	public ComputerWrapper addForm() {

		List<Company> listCompanies = null;
		listCompanies = companyDAO
				.findAll(new Sort(Sort.Direction.ASC, "name"));
		ComputerWrapper wrapper = ComputerWrapper.builder()
				.listCompanies(listCompanies).build();
		return wrapper;
	}
}
