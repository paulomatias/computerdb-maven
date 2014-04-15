package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.domain.Company;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class CompanyDAO {
	@Autowired
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	@Autowired
	private BoneCPDataSource datasource;

	/*
	 * Functions
	 */

	/*
	 * Return the company list
	 */
	public List<Company> getList() throws SQLException {
		Connection connection = DataSourceUtils.getConnection(datasource);
		logger.debug("Enterring getList in CompanyDAO.");
		String GET_ALL = "SELECT id, name FROM `computer-database-db`.`company`;";
		List<Company> listCompanies = new ArrayList<Company>();
		PreparedStatement statement = connection.prepareStatement(GET_ALL);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Company company = Company.builder().id(resultSet.getLong(1))
					.name(resultSet.getString(2)).build();
			listCompanies.add(company);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leavign getList in CompanyDAO.\n");
		return listCompanies;
	}

	/*
	 * Return the name of a company using its id
	 */
	public String getName(Long companyId) throws SQLException {

		logger.debug("Enterring getName in CompanyDAO.");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String GET_NAME = "SELECT id, name FROM `computer-database-db`.`company` WHERE id=?;";
		String name = null;
		PreparedStatement statement = connection.prepareStatement(GET_NAME);
		statement.setLong(1, companyId);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			name = resultSet.getString(2);
		}
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
		logger.debug("Leaving getName in CompanyDAO.\n");
		return name;
	}

}