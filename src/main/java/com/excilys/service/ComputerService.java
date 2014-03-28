package com.excilys.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;
import com.excilys.persistence.LogDAO;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.DTOWrapper;

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
@Service
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
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
			log.info("Counting number of computers...");
			Long nbrComputers = computerDAO.count();
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			log.info("Getting list of computers...");
			List<Computer> listComputers = computerDAO.getList(orderBy,
					currentPage, recordsPerPage);
			String message = "Welcome to your computer database !";
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.listComputers(listComputers).nbrComputers(nbrComputers)
					.nbrOfPages(nbrOfPages).message(message).orderBy(orderBy)
					.build();
			ConnectionManager.getConnection().commit();
			log.info("Transactions successful\n");
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
		return wrapper;
	}

	/*
	 * Return the wrapper to the addServlet, using transactions
	 */
	public ComputerWrapper addComputer(Integer currentPage, Computer computer) {

		ComputerWrapper wrapper = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
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
			String message = "Computer added successfully !";
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).nbrComputers(nbrComputers)
					.listCompanies(listCompanies).listComputers(listComputers)
					.message(message).build();
			logDAO.setLog(computer, "insert");
			ConnectionManager.getConnection().commit();
			log.info("Transaction successful\n");
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
		return wrapper;
	}

	/*
	 * Return the wrapper to the deleteServlet, using transactions
	 */
	public ComputerWrapper delete(Integer currentPage, String computerId) {

		ComputerWrapper wrapper = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
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
			String message = "Computer deleted successfully !";
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrComputers(nbrComputers).nbrOfPages(nbrOfPages)
					.listComputers(listComputers).message(message).build();
			logDAO.setLog(computer, "delete");
			log.info("Transaction successful\n");
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
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the get method, using
	 * transactions
	 */
	public ComputerWrapper getEditComputerWrapper(String computerId) {

		ComputerWrapper wrapper = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
			List<Company> listCompanies;
			listCompanies = companyDAO.getList();
			Computer computer = computerDAO.get(Long.valueOf(computerId));
			wrapper = ComputerWrapper.builder().computer(computer)
					.listCompanies(listCompanies).build();
			logDAO.setLog(computer, "update");
			log.info("Transaction successful\n");
			ConnectionManager.getConnection().commit();
		} catch (SQLException e) {
			try {
				ConnectionManager.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	/*
	 * Return the wrapper to the editServlet for the post method, using
	 * transactions
	 */
	public ComputerWrapper getEditComputerWrapperPost(Integer currentPage,
			Computer computer) {

		ComputerWrapper wrapper = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
			computerDAO.edit(computer);
			Long nbrComputers = computerDAO.count();
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			List<Computer> listComputers = computerDAO.getList(null,
					currentPage, recordsPerPage);
			String message = "Computer edited successfully !";
			wrapper = ComputerWrapper.builder().computer(computer)
					.currentPage(currentPage).nbrOfPages(nbrOfPages)
					.listComputers(listComputers).nbrComputers(nbrComputers)
					.message(message).build();
			log.info("Transaction successful\n");
			ConnectionManager.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				ConnectionManager.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				ConnectionManager.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return wrapper;
	}

	public ComputerWrapper dashboardSearchComputer(String orderBy,
			Integer currentPage, String searchComputer) {

		ComputerWrapper wrapper = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
			List<Computer> listComputers = computerDAO.getListByName(
					searchComputer, orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.countByName(searchComputer);
			String message = "Computer(s) selected successfully !";
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).searchComputer(searchComputer)
					.orderBy(orderBy).message(message).build();
			log.info("Transaction successful\n");
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
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompanySearchComputer(String orderBy,
			Integer currentPage, String searchCompany, String searchComputer) {

		ComputerWrapper wrapper = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
			List<Computer> listComputers = computerDAO
					.getListByNameAndCompanyName(searchComputer, searchCompany,
							orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.countByNameAndCompanyName(
					searchComputer, searchCompany);
			String message = "Computer(s) selected successfully !";
			int nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = ComputerWrapper.builder().nbrComputers(nbrComputers)
					.currentPage(currentPage).nbrOfPages(nbrOfPages)
					.listComputers(listComputers).nbrComputers(nbrComputers)
					.searchComputer(searchComputer)
					.searchCompany(searchCompany).orderBy(orderBy)
					.message(message).build();
			log.info("Transaction successful\n");
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
		return wrapper;
	}

	public ComputerWrapper dashboardSearchCompany(String orderBy,
			Integer currentPage, String searchCompany) {

		ComputerWrapper wrapper = null;
		try {
			ConnectionManager.openConnection();
			ConnectionManager.getConnection().setAutoCommit(false);
			List<Computer> listComputers = computerDAO.getListByCompanyName(
					searchCompany, orderBy, currentPage, recordsPerPage);
			Long nbrComputers = computerDAO.countByCompanyName(searchCompany);
			String message = "Computer(s) selected successfully !";
			Integer nbrOfPages = (int) Math.ceil(nbrComputers * 1.0
					/ recordsPerPage);
			wrapper = ComputerWrapper.builder().currentPage(currentPage)
					.nbrOfPages(nbrOfPages).listComputers(listComputers)
					.nbrComputers(nbrComputers).searchCompany(searchCompany)
					.orderBy(orderBy).message(message).build();
			log.info("Transaction successful\n");
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
		return wrapper;
	}

}
