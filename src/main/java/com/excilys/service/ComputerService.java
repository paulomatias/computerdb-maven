package com.excilys.service;

import java.sql.SQLException;
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
		try {
			log.info("Counting number of computers...");
			Long nbrComputers = computerDAO.count();
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			log.info("Getting list of computers...");
			List<Computer> listComputers = computerDAO.getList(orderBy,
					currentPage, recordsPerPage);
			String message = "welcome";
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.listComputers(listComputers).nbrComputers(nbrComputers)
					.nbrOfPages(nbrOfPages).message(message).orderBy(orderBy)
					.build();
			log.info("Transactions successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper addComputer(Integer currentPage, Computer computer) {

		ComputerWrapper wrapper = null;
		try {

			log.info("Adding computer...");
			computer.setId(computerDAO.add(computer));
			log.info("Counting number of computers...");
			Long nbrComputers = computerDAO.count();
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			log.info("Getting list of companies...");
			List<Company> listCompanies = companyDAO.getList();
			log.info("Getting list of computers...");
			List<Computer> listComputers = computerDAO.getList(null,
					currentPage, recordsPerPage);
			String message = "welcomeAdd";
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
					.listCompanies(listCompanies).listComputers(listComputers)
					.message(message).build();
			logDAO.setLog(computer, "insert");
			log.info("Transaction successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the deleteServlet, using transactions
	 */
	@Transactional(readOnly = false)
	public ComputerWrapper delete(Integer currentPage, String computerId) {

		ComputerWrapper wrapper = null;
		try {

			List<Computer> listComputers = computerDAO.getList(null);
			Computer computer = Computer.builder().id(Long.valueOf(computerId))
					.build();
			computerDAO.delete(computer);
			listComputers = computerDAO.getList(null, currentPage,
					recordsPerPage);
			Long nbrComputers;
			nbrComputers = computerDAO.count();
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			String message = "welcomeDelete";
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrComputers(nbrComputers).nbrOfPages(nbrOfPages)
					.listComputers(listComputers).message(message).build();
			logDAO.setLog(computer, "delete");
			log.info("Transaction successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the get method, using
	 * transactions
	 */
	public ComputerWrapper edit(String computerId) {

		ComputerWrapper wrapper = null;
		try {

			List<Company> listCompanies;
			listCompanies = companyDAO.getList();
			Computer computer = computerDAO.get(Long.valueOf(computerId));
			wrapper = ComputerWrapper.builder().computer(computer)
					.listCompanies(listCompanies).build();
			logDAO.setLog(computer, "update");
			log.info("Transaction successful");
		} catch (SQLException e) {
			e.printStackTrace();
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
	public ComputerWrapper editing(Integer currentPage, Computer computer) {

		ComputerWrapper wrapper = null;
		try {

			computerDAO.edit(computer);
			Long nbrComputers = computerDAO.count();
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			List<Computer> listComputers = computerDAO.getList(null,
					currentPage, recordsPerPage);
			String message = "welcomeEdit";
			wrapper = ComputerWrapper.builder().computer(computer)
					.currentPage(currentPage).nbrOfPages(nbrOfPages)
					.listComputers(listComputers).nbrComputers(nbrComputers)
					.message(message).build();
			log.info("Transaction successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wrapper;
	}

	public ComputerWrapper dashboardSearchComputer(String orderBy,
			Integer currentPage, String searchComputer) {

		ComputerWrapper wrapper = null;
		try {

			List<Computer> listComputers = computerDAO.getListByName(
					searchComputer, orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.countByName(searchComputer);
			String message = "welcomeSelect";
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).searchComputer(searchComputer)
					.orderBy(orderBy).message(message).build();
			log.info("Transaction successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompanySearchComputer(String orderBy,
			Integer currentPage, String searchCompany, String searchComputer) {

		ComputerWrapper wrapper = null;
		try {

			List<Computer> listComputers = computerDAO
					.getListByNameAndCompanyName(searchComputer, searchCompany,
							orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.countByNameAndCompanyName(
					searchComputer, searchCompany);
			String message = "welcomeSelect";
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = ComputerWrapper.builder().nbrComputers(nbrComputers)
					.currentPage(currentPage).nbrOfPages(nbrOfPages)
					.listComputers(listComputers).nbrComputers(nbrComputers)
					.searchComputer(searchComputer)
					.searchCompany(searchCompany).orderBy(orderBy)
					.message(message).build();
			log.info("Transaction successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompany(String orderBy,
			Integer currentPage, String searchCompany) {

		ComputerWrapper wrapper = null;
		try {

			List<Computer> listComputers = computerDAO.getListByCompanyName(
					searchCompany, orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.countByCompanyName(searchCompany);
			String message = "welcomeSelect";
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).searchCompany(searchCompany)
					.orderBy(orderBy).message(message).build();
			log.info("Transaction successful");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wrapper;
	}
}
