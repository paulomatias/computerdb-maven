package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Component
public class ConnectionManager {

	public final static String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	public final static String USER = "jee-cdb";
	public final static String PASSWORD = "password";
	public final static String driverClass = "com.mysql.jdbc.Driver";
	private static Logger logger = LoggerFactory
			.getLogger(ConnectionManager.class);
	private static ThreadLocal<Connection> connectionThread = new ThreadLocal<Connection>();
	private static BoneCP connectionPool;

	/*
	 * Initializing Connection Pool Manager
	 */
	public static void initialize() {
		logger.debug("Enterring initialize in ConnectionManager.");
		try {
			Class.forName(driverClass);
		} catch (Exception e) {
			logger.debug("Leaving initialize in ConnectionManager, e catched.\n");
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
			logger.debug("Leaving initialize in ConnectionManager, e1 catched.\n");
			e1.printStackTrace();
		}
		logger.debug("Leaving initialize in ConnectionManager.\n");
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
		logger.debug("Enterring openConnection in ConnectionManager.");
		if (connectionPool == null) {
			initialize();
		}
		if (connectionThread != null && connectionThread.get() != null
				&& !connectionThread.get().isClosed()) {
			connectionThread.get().close();
		}
		connectionThread.set(connectionPool.getConnection());
		logger.debug("Leaving openConnection in ConnectionManager.");
	}

	/*
	 * Close connection
	 */
	public static void closeConnection() throws SQLException {
		logger.debug("Entering closeConnection in ConnectionManager.");
		if (connectionThread != null && connectionThread.get() != null
				&& !connectionThread.get().isClosed()) {
			connectionThread.get().close();
		}
		logger.debug("Leaving closeConnection in ConnectionManager.");
	}
}
