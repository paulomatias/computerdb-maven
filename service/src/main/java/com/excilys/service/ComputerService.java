package com.excilys.service;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.domain.Log;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.LogDAO;
import com.excilys.wrapper.ComputerWrapper;

@Service
@Transactional(readOnly = true)
public class ComputerService {

	@Autowired
	private LogDAO logDAO;
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;
	static Logger log = LoggerFactory.getLogger(ComputerService.class);

	/*
	 * Return the wrapper to the dahboardServlet, using transactions
	 */
	public Page<Computer> dashboard(Pageable page) {
		return computerDAO.findAll(page);
	}

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public void add(Computer computer) {
		computerDAO.save(computer);
		Log log = Log.builder().computerId(computer.getId())
				.nameComputer(computer.getName()).kindOfChange("INSERT")
				.time(DateTime.now()).build();
		logDAO.save(log);
	}

	/*
	 * Return the wrapper to the deleteServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public void delete(String computerId) {

		Computer computer = Computer.builder().id(Long.valueOf(computerId))
				.build();
		computerDAO.delete(computer);
		Log log = Log.builder().computerId(computer.getId())
				.nameComputer(computer.getName()).kindOfChange("DELETE")
				.time(DateTime.now()).build();
		logDAO.save(log);
	}

	/*
	 * Return the wrapper to the editServlet for the get method, using
	 * transactions
	 */
	@Transactional(readOnly = true)
	public ComputerWrapper editForm(String computerId) {

		ComputerWrapper wrapper = null;
		List<Company> listCompanies = companyDAO.findAll();
		Computer computer = computerDAO.findOne(Long.valueOf(computerId));
		wrapper = ComputerWrapper.builder().computer(computer)
				.listCompanies(listCompanies).build();
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the post method, using
	 * transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper edit(Computer computer) {

		ComputerWrapper wrapper = null;
		computerDAO.save(computer);
		wrapper = ComputerWrapper.builder().computer(computer).build();
		Log log = Log.builder().computerId(computer.getId())
				.nameComputer(computer.getName()).kindOfChange("UPDATE")
				.time(DateTime.now()).build();
		logDAO.save(log);
		return wrapper;
	}

	public Page<Computer> dashboardSearchComputer(String name, Pageable page) {

		return computerDAO.findByName(name, page);
	}

	public Page<Computer> dashboardSearchCompanySearchComputer(String name,
			String companyName, Pageable page) {

		return computerDAO.findByNameAndCompanyName(name, companyName, page);
	}

	public Page<Computer> dashboardSearchCompany(String companyName,
			Pageable page) {

		return computerDAO.findByCompanyName(companyName, page);
	}

	public List<Computer> findAll() {
		return computerDAO.findAll();
	}
}
