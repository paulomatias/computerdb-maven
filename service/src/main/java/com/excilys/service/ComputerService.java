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
import com.excilys.wrapper.DTOWrapper;

@Service
@Transactional(readOnly = true)
public class ComputerService {

	public static final Integer recordsPerPage = DTOWrapper.RECORDS_PER_PAGE;
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
	public ComputerWrapper dashboard(Integer currentPage, String orderBy) {

		ComputerWrapper wrapper = null;
		Long nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ recordsPerPage);
		List<Computer> listComputers = computerDAO.retrieveAll(orderBy,
				currentPage, recordsPerPage);
		String message = "welcome";
		wrapper = ComputerWrapper.builder().currentPage(currentPage)
				.listComputers(listComputers).nbrComputers(nbrComputers)
				.nbrOfPages(nbrOfPages).message(message).orderBy(orderBy)
				.build();
		return wrapper;
	}

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper add(Integer currentPage, Computer computer) {

		ComputerWrapper wrapper = null;
		computer.setId(computerDAO.create(computer));
		Long nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ recordsPerPage);
		List<Company> listCompanies = companyDAO.getList();
		List<Computer> listComputers = computerDAO.retrieveAll(null,
				currentPage, recordsPerPage);
		String message = "welcomeAdd";
		wrapper = ComputerWrapper.builder().currentPage(currentPage)
				.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
				.listCompanies(listCompanies).listComputers(listComputers)
				.message(message).build();
		logDAO.setLog(computer, "insert");
		return wrapper;
	}

	/*
	 * Return the wrapper to the deleteServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper delete(Integer currentPage, String computerId) {

		ComputerWrapper wrapper = null;
		Computer computer = Computer.builder().id(Long.valueOf(computerId))
				.build();
		computerDAO.delete(computer);
		List<Computer> listComputers = computerDAO.retrieveAll(null,
				currentPage, recordsPerPage);
		Long nbrComputers;
		nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ recordsPerPage);
		String message = "welcomeDelete";
		wrapper = ComputerWrapper.builder().currentPage(currentPage)
				.nbrComputers(nbrComputers).nbrOfPages(nbrOfPages)
				.listComputers(listComputers).message(message).build();
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
	public ComputerWrapper edit(Integer currentPage, Computer computer) {

		ComputerWrapper wrapper = null;

		computerDAO.update(computer);
		Long nbrComputers = computerDAO.countAll();
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ recordsPerPage);
		List<Computer> listComputers = computerDAO.retrieveAll(null,
				currentPage, recordsPerPage);
		String message = "welcomeEdit";
		wrapper = ComputerWrapper.builder().computer(computer)
				.currentPage(currentPage).nbrOfPages(nbrOfPages)
				.listComputers(listComputers).nbrComputers(nbrComputers)
				.message(message).build();
		logDAO.setLog(computer, "update");
		return wrapper;
	}

	public ComputerWrapper dashboardSearchComputer(String orderBy,
			Integer currentPage, String searchComputer) {

		ComputerWrapper wrapper = null;
		List<Computer> listComputers = computerDAO.retrieveByName(
				searchComputer, orderBy, currentPage, recordsPerPage);
		Long nbrComputers = computerDAO.countByName(searchComputer);
		String message = "welcomeSelect";
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);
		wrapper = ComputerWrapper.builder().currentPage(currentPage)
				.nbrOfPages(nbrOfPages).listComputers(listComputers)
				.nbrComputers(nbrComputers).searchComputer(searchComputer)
				.orderBy(orderBy).message(message).build();
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompanySearchComputer(String orderBy,
			Integer currentPage, String searchCompany, String searchComputer) {

		ComputerWrapper wrapper = null;

		List<Computer> listComputers = computerDAO
				.retrieveByNameAndCompanyName(searchComputer, searchCompany,
						orderBy, currentPage, recordsPerPage);
		Long nbrComputers = computerDAO.countByNameAndCompanyName(
				searchComputer, searchCompany);
		String message = "welcomeSelect";
		int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0 / recordsPerPage);
		wrapper = ComputerWrapper.builder().nbrComputers(nbrComputers)
				.currentPage(currentPage).nbrOfPages(nbrOfPages)
				.listComputers(listComputers).nbrComputers(nbrComputers)
				.searchComputer(searchComputer).searchCompany(searchCompany)
				.orderBy(orderBy).message(message).build();
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompany(String orderBy,
			Integer currentPage, String searchCompany) {

		ComputerWrapper wrapper = null;

		List<Computer> listComputers = computerDAO.retrieveByCompanyName(
				searchCompany, orderBy, currentPage, recordsPerPage);
		Long nbrComputers = computerDAO.countByCompanyName(searchCompany);
		String message = "welcomeSelect";
		Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
				/ recordsPerPage);
		wrapper = ComputerWrapper.builder().currentPage(currentPage)
				.nbrOfPages(nbrOfPages).listComputers(listComputers)
				.nbrComputers(nbrComputers).searchCompany(searchCompany)
				.orderBy(orderBy).message(message).build();
		return wrapper;
	}
}
