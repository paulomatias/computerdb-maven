package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Component
public class ConnectionManager {

	public final static String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	public final static String USER = "jee-cdb";
	public final static String PASSWORD = "password";
	public final static String driverClass = "com.mysql.jdbc.Driver";
	private static ThreadLocal<Connection> connectionThread = new ThreadLocal<Connection>();
	private static BoneCP connectionPool;

	/*
	 * Initializing Connection Pool Manager
	 */
	public static void initialize() {
		try {
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(URL);
			config.setUsername(USER);
			config.setPassword(PASSWORD);
			config.setMaxConnectionsPerPartition(20);
			connectionPool = new BoneCP(config);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/*
	 * Get connection
	 */
	public static Connection getConnection() {
		return connectionThread.get();
	}

	/*
	 * Open connection
	 */
	public static void openConnection() throws SQLException {
		if (connectionPool == null) {
			initialize();
		}
		if (connectionThread != null && connectionThread.get() != null
				&& !connectionThread.get().isClosed()) {
			connectionThread.get().close();
		}
		connectionThread.set(connectionPool.getConnection());
	}

	/*
	 * Close connection
	 */
	public static void closeConnection() throws SQLException {
		if (connectionThread != null && connectionThread.get() != null
				&& !connectionThread.get().isClosed()) {
			connectionThread.get().close();
		}
	}
}
