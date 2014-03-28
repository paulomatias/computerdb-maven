package com.excilys.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceManager {

	public ComputerService getComputerService() {
		return new ComputerService();
	}

	public CompanyService getCompanyService() {
		return new CompanyService();
	}

}
