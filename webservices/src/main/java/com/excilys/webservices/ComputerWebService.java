package com.excilys.webservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.domain.Computer;
import com.excilys.service.ComputerService;

@Component
@Path("/webservices")
public class ComputerWebService {

	@Autowired
	public ComputerService computerService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Computer> findAll() {
		return computerService.findAll();
	}
}
