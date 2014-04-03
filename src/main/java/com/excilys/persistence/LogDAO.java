package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.domain.Computer;

@Component
public class LogDAO {
	private static Logger logger = LoggerFactory.getLogger(LogDAO.class);

	public void setLog(Computer computer, String operation) throws SQLException {

		logger.debug("Entering setLog in LogDAO");
		Connection connection = ConnectionManager.getConnection();
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
