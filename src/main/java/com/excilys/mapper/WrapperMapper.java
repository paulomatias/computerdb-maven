package com.excilys.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.transfert.CompanyDTO;
import com.excilys.transfert.ComputerDTO;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.DTOWrapper;

@Component
public class WrapperMapper {
	@Autowired
	DTOMapper mapperDTO;

	@SuppressWarnings("null")
	public DTOWrapper toDTOWrapper(ComputerWrapper computerWrapper) {
		DTOWrapper dtoWrapper;
		if (computerWrapper != null) {
			ComputerDTO computerDTO = mapperDTO.toDTO(computerWrapper
					.getComputer());
			List<ComputerDTO> listComputersDTO = new ArrayList<ComputerDTO>();
			List<CompanyDTO> listCompaniesDTO = new ArrayList<CompanyDTO>();
			if (computerWrapper.getListComputers() != null) {
				for (Computer c : computerWrapper.getListComputers()) {
					ComputerDTO cDTO = mapperDTO.toDTO(c);
					listComputersDTO.add(cDTO);
				}
			}
			if (computerWrapper.getListCompanies() != null) {
				for (Company company : computerWrapper.getListCompanies()) {
					CompanyDTO cDTO = mapperDTO.toDTO(company);
					listCompaniesDTO.add(cDTO);
				}
			}
			dtoWrapper = DTOWrapper.builder()
					.recordsPerPage(DTOWrapper.RECORDS_PER_PAGE)
					.nbrOfPages(computerWrapper.getNbrOfPages())
					.currentPage(computerWrapper.getCurrentPage())
					.message(computerWrapper.getMessage())
					.searchCompany(computerWrapper.getSearchCompany())
					.searchComputer(computerWrapper.getSearchComputer())
					.orderBy(computerWrapper.getOrderBy())
					.nbrComputers(computerWrapper.getNbrComputers())
					.computerDTO(computerDTO)
					.listCompaniesDTO(listCompaniesDTO)
					.listComputersDTO(listComputersDTO).build();
			return dtoWrapper;
		} else
			dtoWrapper = DTOWrapper.builder()
					.recordsPerPage(DTOWrapper.RECORDS_PER_PAGE)
					.nbrOfPages(computerWrapper.getNbrOfPages())
					.currentPage(computerWrapper.getCurrentPage())
					.message(computerWrapper.getMessage())
					.searchCompany(computerWrapper.getSearchCompany())
					.searchComputer(computerWrapper.getSearchComputer())
					.orderBy(computerWrapper.getOrderBy())
					.nbrComputers(computerWrapper.getNbrComputers()).build();
		return dtoWrapper;

	}

	public ComputerWrapper toComputerWrapper(DTOWrapper dtoWrapper) {
		Computer computer = mapperDTO.toComputer(dtoWrapper.getComputerDTO());
		List<Computer> listComputers = new ArrayList<Computer>();
		List<Company> listCompanies = new ArrayList<Company>();

		for (ComputerDTO cDTO : dtoWrapper.getListComputersDTO()) {
			Computer c = mapperDTO.toComputer(cDTO);
			listComputers.add(c);
		}

		for (CompanyDTO cDTO : dtoWrapper.getListCompaniesDTO()) {
			Company c = mapperDTO.toCompany(cDTO);
			listCompanies.add(c);
		}

		ComputerWrapper computerWrapper = ComputerWrapper.builder()
				.recordsPerPage(DTOWrapper.RECORDS_PER_PAGE)
				.nbrOfPages(dtoWrapper.getNbrOfPages())
				.currentPage(dtoWrapper.getCurrentPage())
				.message(dtoWrapper.getMessage())
				.searchCompany(dtoWrapper.getSearchCompany())
				.searchComputer(dtoWrapper.getSearchComputer())
				.orderBy(dtoWrapper.getOrderBy())
				.nbrComputers(dtoWrapper.getNbrComputers()).computer(computer)
				.listCompanies(listCompanies).listComputers(listComputers)
				.build();
		return computerWrapper;
	}

}
