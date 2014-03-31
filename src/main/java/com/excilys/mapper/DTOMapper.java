package com.excilys.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.transfert.CompanyDTO;
import com.excilys.transfert.ComputerDTO;

@Component
public class DTOMapper {
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	/*
	 * transform a computerDTO object to a computer
	 */
	public Computer toComputer(ComputerDTO computerDTO) {
		Long id = computerDTO.getId();
		String name = computerDTO.getName();
		String introduced = computerDTO.getIntroduced();
		String discontinued = computerDTO.getDiscontinued();
		Long companyId = computerDTO.getCompanyId();
		String companyName = computerDTO.getCompanyName();

		Date introducedDate = null;
		Date discontinuedDate = null;
		if (!introduced.equals("")) {
			try {
				introducedDate = FORMAT.parse(introduced);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!introduced.equals("")) {
			try {
				discontinuedDate = FORMAT.parse(discontinued);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		Company company = Company.builder().id(companyId).name(companyName)
				.build();
		Computer computer = Computer.builder().id(id).name(name)
				.introduced(introducedDate).discontinued(discontinuedDate)
				.company(company).build();
		return computer;
	}

	/*
	 * transform a computer to a computerDTO object
	 */
	public ComputerDTO toDTO(Computer computer) {
		String introduced;
		String discontinued;
		if (computer != null) {
			if (computer.getIntroduced() != null) {
				introduced = computer.getIntroduced().toString();
			} else
				introduced = null;
			if (computer.getDiscontinued() != null) {
				discontinued = computer.getDiscontinued().toString();
			} else
				discontinued = null;
			ComputerDTO computerDTO = ComputerDTO.builder()
					.id(computer.getId()).name(computer.getName())
					.introduced(introduced).discontinued(discontinued)
					.companyId(computer.getCompany().getId())
					.companyName(computer.getCompany().getName()).build();
			return computerDTO;
		} else
			return null;

	}

	/*
	 * transform a company to a companyDTO object
	 */
	public CompanyDTO toDTO(Company company) {
		CompanyDTO companyDTO = CompanyDTO.builder().id(company.getId())
				.name(company.getName()).build();
		return companyDTO;
	}

	/*
	 * transform a companyDTO object to a company
	 */
	public Company toCompany(CompanyDTO companyDTO) {
		Company company = Company.builder().id(companyDTO.getId())
				.name(companyDTO.getName()).build();
		return company;
	}

}
