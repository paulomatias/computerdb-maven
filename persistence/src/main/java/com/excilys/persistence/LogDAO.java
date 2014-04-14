package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import com.excilys.domain.Computer;
import com.jolbox.bonecp.BoneCPDataSource;

@Component
public class LogDAO {
	private static Logger logger = LoggerFactory.getLogger(LogDAO.class);

	@Autowired
	BoneCPDataSource datasource;

	public void setLog(Computer computer, String operation) throws SQLException {

		logger.debug("Entering setLog in LogDAO");
		Connection connection = DataSourceUtils.getConnection(datasource);
		String query = new String(
				"INSERT INTO `computer-database-db`.`log` (computer,name_computer,kind_of_change) VALUES (?,?,?)");
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setLong(1, computer.getId());
		statement.setString(2, computer.getName());
		statement.setString(3, operation);
		statement.executeUpdate();
		logger.debug("Leaving setLog in LogDAO");
	}
}
