package com.excilys.mapper;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.transfert.CompanyDTO;
import com.excilys.transfert.ComputerDTO;

@Component
public class DTOMapper {
	public static Logger logger = LoggerFactory.getLogger(DTOMapper.class);

	/*
	 * transform a computerDTO object to a computer
	 */
	public Computer toComputer(ComputerDTO computerDTO) {

		logger.debug("Entering toComputer in DTOMapper.");
		Long id = computerDTO.getId();
		String name = computerDTO.getName();
		String introduced = computerDTO.getIntroduced();
		String discontinued = computerDTO.getDiscontinued();
		Long companyId = computerDTO.getCompanyId();
		String companyName = computerDTO.getCompanyName();
		Company company = null;
		DateTime introducedDate = null;
		DateTime discontinuedDate = null;
		if (!introduced.equals("")) {
			introducedDate = new DateTime(introduced);
		}
		if (!discontinued.equals("")) {
			discontinuedDate = new DateTime(discontinued);
		}
		if (!companyId.equals(0L)) {
			company = Company.builder().id(companyId).name(companyName).build();
		}

		Computer computer = Computer.builder().id(id).name(name)
				.introduced(introducedDate).discontinued(discontinuedDate)
				.company(company).build();
		logger.debug("Leaving toComputer in DTOMapper.");
		return computer;
	}

	/*
	 * transform a computer to a computerDTO object
	 */
	public ComputerDTO toDTO(Computer computer) {
		logger.debug("Entering for computer toDTO in DTOMapper.");
		String introduced = null, discontinued = null;
		if (computer != null) {
			if (computer.getIntroduced() != null) {
				introduced = ISODateTimeFormat.date().print(
						computer.getIntroduced());
			} else
				introduced = null;
			if (computer.getDiscontinued() != null) {
				discontinued = ISODateTimeFormat.date().print(
						computer.getDiscontinued());
			} else
				discontinued = null;

			ComputerDTO computerDTO = null;
			if (computer.getCompany() == null) {
				computerDTO = ComputerDTO.builder().id(computer.getId())
						.name(computer.getName()).introduced(introduced)
						.discontinued(discontinued).build();
			} else
				computerDTO = ComputerDTO.builder().id(computer.getId())
						.name(computer.getName()).introduced(introduced)
						.discontinued(discontinued)
						.companyId(computer.getCompany().getId())
						.companyName(computer.getCompany().getName()).build();
			return computerDTO;
		} else
			logger.debug("Leaving toDTO for computer in DTOMapper, null computer.");
		return null;

	}

	/*
	 * transform a company to a companyDTO object
	 */
	public CompanyDTO toDTO(Company company) {
		logger.debug("Enterring toDTO for company in DTOMapper.");
		CompanyDTO companyDTO = CompanyDTO.builder().id(company.getId())
				.name(company.getName()).build();
		logger.debug("Leaving toDTO for company in DTOMapper.");
		return companyDTO;
	}

	/*
	 * transform a companyDTO object to a company
	 */
	public Company toCompany(CompanyDTO companyDTO) {
		logger.debug("Enterring toCompany in DTOMapper.");
		Company company = Company.builder().id(companyDTO.getId())
				.name(companyDTO.getName()).build();
		logger.debug("Leaving toCompany in DTOMapper.");
		return company;
	}

}
