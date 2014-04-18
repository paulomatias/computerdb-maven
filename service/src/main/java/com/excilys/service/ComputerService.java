package com.excilys.service;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.LogDAO;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.PageWrapper;

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
	public ComputerWrapper dashboard(PageWrapper pageWrapper) {

		ComputerWrapper wrapper = new ComputerWrapper();
		Long nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ pageWrapper.getRecordsPerPage());
		List<Computer> listComputers = computerDAO.retrieveAll(pageWrapper);
		pageWrapper.setNbrComputers(nbrComputers);
		pageWrapper.setNbrOfPages(nbrOfPages);
		wrapper = ComputerWrapper.builder().listComputers(listComputers)
				.build();
		return wrapper;
	}

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper add(PageWrapper pageWrapper, Computer computer) {

		ComputerWrapper wrapper = new ComputerWrapper();
		computer.setId(computerDAO.create(computer));
		Long nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ pageWrapper.getRecordsPerPage());
		List<Company> listCompanies = companyDAO.getList();
		List<Computer> listComputers = computerDAO.retrieveAll(pageWrapper);
		String message = "welcomeAdd";
		pageWrapper.setNbrComputers(nbrComputers);
		pageWrapper.setNbrOfPages(nbrOfPages);
		pageWrapper.setMessage(message);
		wrapper = ComputerWrapper.builder().listCompanies(listCompanies)
				.listComputers(listComputers).message(message).build();
		logDAO.setLog(computer, "insert");
		return wrapper;
	}

	/*
	 * Return the wrapper to the deleteServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper delete(PageWrapper pageWrapper, String computerId) {

		ComputerWrapper wrapper = null;
		Computer computer = Computer.builder().id(Long.valueOf(computerId))
				.build();
		computerDAO.delete(computer);
		List<Computer> listComputers = computerDAO.retrieveAll(pageWrapper);
		Long nbrComputers;
		nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ pageWrapper.getRecordsPerPage());
		String message = "welcomeDelete";
		pageWrapper.setNbrOfPages(nbrOfPages);
		pageWrapper.setMessage(message);
		wrapper = ComputerWrapper.builder().listComputers(listComputers)
				.build();
		logDAO.setLog(computer, "delete");
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the get method, using
	 * transactions
	 */
	public ComputerWrapper editForm(String computerId) {

		ComputerWrapper wrapper = null;
		try {
			List<Company> listCompanies;
			listCompanies = companyDAO.getList();
			Computer computer = computerDAO.get(Long.valueOf(computerId));
			wrapper = ComputerWrapper.builder().computer(computer)
					.listCompanies(listCompanies).build();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the post method, using
	 * transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper edit(PageWrapper pageWrapper, Computer computer) {

		ComputerWrapper wrapper = null;
		computerDAO.update(computer);
		Long nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ pageWrapper.getRecordsPerPage());
		List<Computer> listComputers = computerDAO.retrieveAll(pageWrapper);
		String message = "welcomeEdit";
		pageWrapper.setMessage(message);
		pageWrapper.setNbrOfPages(nbrOfPages);
		wrapper = ComputerWrapper.builder().computer(computer)
				.listComputers(listComputers).message(message).build();
		logDAO.setLog(computer, "update");
		return wrapper;
	}

	public ComputerWrapper dashboardSearchComputer(PageWrapper pageWrapper) {

		ComputerWrapper wrapper = null;
		List<Computer> listComputers = computerDAO.retrieveByName(pageWrapper);
		Long nbrComputers = computerDAO.countByName(pageWrapper);
		String message = "welcomeSelect";
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ pageWrapper.getRecordsPerPage());
		pageWrapper.setMessage(message);
		pageWrapper.setNbrComputers(nbrComputers);
		pageWrapper.setNbrOfPages(nbrOfPages);
		wrapper = ComputerWrapper.builder().listComputers(listComputers)
				.build();
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompanySearchComputer(
			PageWrapper pageWrapper) {

		ComputerWrapper wrapper = null;

		List<Computer> listComputers = computerDAO
				.retrieveByNameAndCompanyName(pageWrapper);
		Long nbrComputers = computerDAO.countByNameAndCompanyName(pageWrapper);
		String message = "welcomeSelect";
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ pageWrapper.getRecordsPerPage());
		pageWrapper.setMessage(message);
		pageWrapper.setNbrComputers(nbrComputers);
		pageWrapper.setNbrOfPages(nbrOfPages);
		wrapper = ComputerWrapper.builder().listComputers(listComputers)
				.build();
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompany(PageWrapper pageWrapper) {

		ComputerWrapper wrapper = null;

		List<Computer> listComputers = computerDAO
				.retrieveByCompanyName(pageWrapper);
		Long nbrComputers = computerDAO.countByCompanyName(pageWrapper);
		String message = "welcomeSelect";
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ pageWrapper.getRecordsPerPage());
		pageWrapper.setNbrComputers(nbrComputers);
		pageWrapper.setMessage(message);
		pageWrapper.setNbrOfPages(nbrOfPages);
		wrapper = ComputerWrapper.builder().listComputers(listComputers)
				.build();
		return wrapper;
	}
}
