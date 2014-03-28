package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.excilys.domain.Company;

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
@Repository
public class CompanyDAO {

	/*
	 * Functions
	 */

	/*
	 * Return the company list
	 */
	public List<Company> getList() throws SQLException {

		Connection connection = ConnectionManager.getConnection();
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
		return listCompanies;
	}

	/*
	 * Return the name of a company using its id
	 */
	public String getName(Long companyId) throws SQLException {

		Connection connection = ConnectionManager.getConnection();
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
		return name;
	}

}
