package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.domain.Computer;

@Component
public class LogDAO {

	public void setLog(Computer computer, String operation) throws SQLException {

		Connection connection = ConnectionManager.getConnection();
		String query = new String(
				"INSERT INTO `computer-database-db`.`log` (computer,name_computer,kind_of_change) VALUES (?,?,?)");
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setLong(1, computer.getId());
		statement.setString(2, computer.getName());
		statement.setString(3, operation);
		statement.executeUpdate();
	}

}
