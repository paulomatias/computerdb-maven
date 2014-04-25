package com.excilys.webservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.domain.Computer;
import com.excilys.mapper.DTOMapper;
import com.excilys.service.ComputerService;

@Component
@Path("/webservices")
public class ComputerWebService {

	@Autowired
	public ComputerService computerService;

	@Autowired
	public DTOMapper dtoMapper;

	@GET
	@Produces("application/xml")
	public List<Computer> findAll() {
		return computerService.findAll();
	}
}
