package com.excilys.persistence;

/* Singleton : enum will ensure that we really have a singleton (otherwise, a exploit can be done with the JVM to duplicate objects */
public enum DAOFactory {
	INSTANCE;

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	/*
	 * Get the instances of DAOs
	 */
	public ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}

	public CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}

	public LogDAO getLogDAO() {
		return LogDAO.getInstance();
	}
}