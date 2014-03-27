package com.excilys.service;


public class ServiceManager {

	private final static ServiceManager instance = new ServiceManager();

	private ServiceManager() {
	}

	public static ServiceManager getInstance() {
		return instance;
	}

	public ComputerService getComputerService() {
		return ComputerService.getInstance();
	}

	public CompanyService getCompanyService() {
		return CompanyService.getInstance();
	}

}
