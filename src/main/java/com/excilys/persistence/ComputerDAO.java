package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAO {
	@Autowired
	private BoneCPDataSource datasource;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	public static final DateTimeFormatter FORMAT = DateTimeFormat
			.forPattern("yyyy-MM-dd");

	/*
	 * functions
	 */

	/*
	 * Return the list of computers, ordered
	 */
	public List<Computer> getList(String orderBy) throws SQLException {

		logger.debug("Enterring getList(String orderBy) in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		StringBuilder query = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else
			switch (orderBy) {
			case "nameASC":
				orderBy = "computer.name ASC";
				break;
			case "nameDESC":
				orderBy = "computer.name DESC";
				break;
			case "introducedASC":
				orderBy = "computer.introduced ASC";
				break;
			case "introducedDESC":
				orderBy = "computer.introduced DESC";
				break;
			case "discontinuedASC":
				orderBy = "computer.discontinued ASC";
				break;
			case "discontinuedDESC":
				orderBy = "computer.discontinued DESC";
				break;
			case "companyASC":
				orderBy = "computer.company_id ASC";
				break;
			case "companyDESC":
				orderBy = "company.name DESC";
				break;
			}

		query.append(" ORDER BY ").append(orderBy);
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		ResultSet resultSet = statement.executeQuery();
		List<Computer> listComputers = new ArrayList<Computer>();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).company(company).build();
			if (resultSet.getDate(3) != null) {
				computer.setIntroduced(new DateTime(resultSet.getDate(3)));
			}
			if (resultSet.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(resultSet.getDate(4)));
			}
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving getList(String orderBy) in ComputerDAO.");
		return listComputers;
	}

	/*
	 * Return the list of computers, ordered and limited
	 */
	public List<Computer> getList(String orderBy, Integer page,
			Integer recordsPerPage) throws SQLException {

		logger.debug("Enterring getList(String orderBy, Integer page, Integer recordsPerPage) in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		StringBuilder query = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else
			switch (orderBy) {
			case "nameASC":
				orderBy = "computer.name ASC";
				break;
			case "nameDESC":
				orderBy = "computer.name DESC";
				break;
			case "introducedASC":
				orderBy = "computer.introduced ASC";
				break;
			case "introducedDESC":
				orderBy = "computer.introduced DESC";
				break;
			case "discontinuedASC":
				orderBy = "computer.discontinued ASC";
				break;
			case "discontinuedDESC":
				orderBy = "computer.discontinued DESC";
				break;
			case "companyASC":
				orderBy = "computer.company_id ASC";
				break;
			case "companyDESC":
				orderBy = "company.name DESC";
				break;
			}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		statement.setInt(1, (page - 1) * recordsPerPage);
		statement.setInt(2, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();
		List<Computer> listComputers = new ArrayList<Computer>();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).company(company).build();
			if (resultSet.getDate(3) != null) {
				computer.setIntroduced(new DateTime(resultSet.getDate(3)));
			}
			if (resultSet.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(resultSet.getDate(4)));
			}
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving getList(String orderBy, Integer page, Integer recordsPerPage) in ComputerDAO.");
		return listComputers;
	}

	/*
	 * Add a computer to the database, and return the id auto incremented of the
	 * computer added
	 */
	public Long add(Computer computer) throws SQLException {

		logger.debug("Enterring add in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		Long id = null;
		String query = "INSERT INTO `computer-database-db`.`computer` (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, computer.getName());
		if (computer.getIntroduced() == null) {
			statement.setDate(2, null);
		} else
			statement.setDate(2, new java.sql.Date(computer.getIntroduced()
					.getMillis()));
		if (computer.getDiscontinued() == (null)) {
			statement.setDate(3, null);
		} else
			statement.setDate(3, new java.sql.Date(computer.getDiscontinued()
					.getMillis()));
		if (computer.getCompany().getId().equals((0L))) {
			statement.setString(4, null);
		} else
			statement.setLong(4, computer.getCompany().getId());
		statement.executeUpdate();
		ResultSet resultSet = null;
		resultSet = statement.getGeneratedKeys();
		if (resultSet != null) {
			resultSet.next();
			id = Long.parseLong(resultSet.getString(1));
		}
		if (statement != null)
			statement.close();
		if (resultSet != null)
			resultSet.close();
		logger.debug("Leaving add in ComputerDAO.");
		return id;
	}

	/*
	 * Return the list of computers with a specific name, ordered and limited
	 */
	public List<Computer> getListByName(String computerName, String orderBy,
			Integer page, Integer recordsPerPage) throws SQLException {

		logger.debug("Enterring getListByName(String computerName, String orderBy, Integer page, Integer recordsPerPage) in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		StringBuilder query = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=?");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else
			switch (orderBy) {
			case "nameASC":
				orderBy = "computer.name ASC";
				break;
			case "nameDESC":
				orderBy = "computer.name DESC";
				break;
			case "introducedASC":
				orderBy = "computer.introduced ASC";
				break;
			case "introducedDESC":
				orderBy = "computer.introduced DESC";
				break;
			case "discontinuedASC":
				orderBy = "computer.discontinued ASC";
				break;
			case "discontinuedDESC":
				orderBy = "computer.discontinued DESC";
				break;
			case "companyASC":
				orderBy = "computer.company_id ASC";
				break;
			case "companyDESC":
				orderBy = "company.name DESC";
				break;
			}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		statement.setString(1, computerName);
		statement.setInt(2, (page - 1) * recordsPerPage);
		statement.setInt(3, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();
		List<Computer> listComputers = new ArrayList<Computer>();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).company(company).build();
			if (resultSet.getDate(3) != null) {
				computer.setIntroduced(new DateTime(resultSet.getDate(3)));
			}
			if (resultSet.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(resultSet.getDate(4)));
			}
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving getListByName(String computerName, String orderBy, Integer page, Integer recordsPerPage) in ComputerDAO.");
		return listComputers;
	}

	/*
	 * Delete a computer from the database
	 */
	public void delete(Computer computer) throws SQLException {

		logger.debug("Enterring delete in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String query = "DELETE FROM `computer-database-db`.`computer` WHERE id=?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setLong(1, computer.getId());
		statement.executeUpdate();
		if (statement != null)
			statement.close();
		logger.debug("Leaving delete in ComputerDAO.");
	}

	/*
	 * Edit a computer from the database
	 */
	public void edit(Computer computer) throws SQLException {

		logger.debug("Enterring edit in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String query = "UPDATE computer SET name =?,introduced=?,discontinued=?,company_id=?  WHERE id=?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, computer.getName());
		if (computer.getIntroduced() == null) {
			statement.setDate(2, null);
		} else
			statement.setDate(2, new java.sql.Date(computer.getIntroduced()
					.getMillis()));
		if (computer.getDiscontinued() == (null)) {
			statement.setDate(3, null);
		} else
			statement.setDate(3, new java.sql.Date(computer.getDiscontinued()
					.getMillis()));
		if (computer.getCompany().getId().equals((0L))) {
			statement.setString(4, null);
		} else
			statement.setLong(4, computer.getCompany().getId());
		statement.setLong(5, computer.getId());
		statement.executeUpdate();
		if (statement != null)
			statement.close();
		logger.debug("Leaving edit in ComputerDAO.");
	}

	/*
	 * Return the number of computers in the database
	 */
	public Long count() throws SQLException {

		logger.debug("Enterring count in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		System.out.println(connection);
		String query = "SELECT COUNT(*) FROM `computer-database-db`.computer ;";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		Long nbrComputers = null;
		resultSet.next();
		nbrComputers = resultSet.getLong(1);

		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving count in ComputerDAO.");
		return nbrComputers;
	}

	/*
	 * Return a computer using its id
	 */
	public Computer get(Long computerId) throws SQLException, ParseException {

		logger.debug("Enterring get(Long computerId) in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String query = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.id=?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setLong(1, computerId);
		ResultSet resultSet = statement.executeQuery();
		Computer computer = null;
		while (resultSet.next()) {

			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).company(company).build();
			if (resultSet.getDate(3) != null) {
				computer.setIntroduced(new DateTime(resultSet.getDate(3)));
			}
			if (resultSet.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(resultSet.getDate(4)));
			}
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving get(Long computerId) in ComputerDAO.");
		return computer;
	}

	/*
	 * Return the list of computers with a specific name and a specific company,
	 * ordered and limited
	 */
	public List<Computer> getListByNameAndCompanyName(String computerName,
			String computerCompanyName, String orderBy, Integer page,
			Integer recordsPerPage) throws SQLException {

		logger.debug("Enterring getListByNameAndCompanyName in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		StringBuilder query = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? AND company.name=?");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else
			switch (orderBy) {
			case "nameASC":
				orderBy = "computer.name ASC";
				break;
			case "nameDESC":
				orderBy = "computer.name DESC";
				break;
			case "introducedASC":
				orderBy = "computer.introduced ASC";
				break;
			case "introducedDESC":
				orderBy = "computer.introduced DESC";
				break;
			case "discontinuedASC":
				orderBy = "computer.discontinued ASC";
				break;
			case "discontinuedDESC":
				orderBy = "computer.discontinued DESC";
				break;
			case "companyASC":
				orderBy = "computer.company_id ASC";
				break;
			case "companyDESC":
				orderBy = "company.name DESC";
				break;
			}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		statement.setString(1, computerName);
		statement.setString(2, computerCompanyName);
		statement.setInt(3, (page - 1) * recordsPerPage);
		statement.setInt(4, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();
		List<Computer> listComputers = new ArrayList<Computer>();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).company(company).build();
			if (resultSet.getDate(3) != null) {
				computer.setIntroduced(new DateTime(resultSet.getDate(3)));
			}
			if (resultSet.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(resultSet.getDate(4)));
			}
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving getListByNameAndCompanyName in ComputerDAO.");
		return listComputers;
	}

	/*
	 * Return the list of computers with a specific company, ordered and limited
	 */
	public List<Computer> getListByCompanyName(String computerCompanyName,
			String orderBy, Integer page, Integer recordsPerPage)
			throws SQLException {

		logger.debug("Entering getListByCompanyName in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		StringBuilder query = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=?");
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else
			switch (orderBy) {
			case "nameASC":
				orderBy = "computer.name ASC";
				break;
			case "nameDESC":
				orderBy = "computer.name DESC";
				break;
			case "introducedASC":
				orderBy = "computer.introduced ASC";
				break;
			case "introducedDESC":
				orderBy = "computer.introduced DESC";
				break;
			case "discontinuedASC":
				orderBy = "computer.discontinued ASC";
				break;
			case "discontinuedDESC":
				orderBy = "computer.discontinued DESC";
				break;
			case "companyASC":
				orderBy = "computer.company_id ASC";
				break;
			case "companyDESC":
				orderBy = "company.name DESC";
				break;
			}
		query.append(" ORDER BY ").append(orderBy).append(" LIMIT ?,?;");
		List<Computer> listComputers = new ArrayList<Computer>();
		PreparedStatement statement = connection.prepareStatement(query
				.toString());
		statement.setString(1, computerCompanyName);
		statement.setInt(2, (page - 1) * recordsPerPage);
		statement.setInt(3, recordsPerPage);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(5))
					.name(resultSet.getString(7)).build();
			Computer computer = Computer.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).company(company).build();
			if (resultSet.getDate(3) != null) {
				computer.setIntroduced(new DateTime(resultSet.getDate(3)));
			}
			if (resultSet.getDate(4) != null) {
				computer.setDiscontinued(new DateTime(resultSet.getDate(4)));
			}
			listComputers.add(computer);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving getListByCompanyName in ComputerDAO.");
		return listComputers;
	}

	/*
	 * Return the number of computers with a specific name in the database
	 */
	public Long countByName(String name) throws SQLException {

		logger.debug("Entering countByName in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String query = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=?;";
		Long nbrComputers = null;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		nbrComputers = resultSet.getLong(1);
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving countByName in ComputerDAO.");
		return nbrComputers;
	}

	/*
	 * Return the number of computers with a specific name and a specific
	 * company in the database
	 */
	public Long countByNameAndCompanyName(String name, String companyName)
			throws SQLException {

		logger.debug("Entering countByNameAndCompanyName in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String query = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=? AND company.name=?";
		Long nbrComputers = null;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, companyName);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		nbrComputers = resultSet.getLong(1);
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving countByNameAndCompanyName in ComputerDAO.");
		return nbrComputers;
	}

	/*
	 * Return the number of computers with a specific company in the database
	 */
	public Long countByCompanyName(String name) throws SQLException {

		logger.debug("Entering countByCompanyName in ComputerDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String query = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=? ;";
		Long nbrComputers = null;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		nbrComputers = resultSet.getLong(1);
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving countByCompanyName in ComputerDAO.");
		return nbrComputers;
	}
}
