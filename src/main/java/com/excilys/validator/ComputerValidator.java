package com.excilys.validator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.transfert.ComputerDTO;

public class ComputerValidator implements Validator {
	public static final DateTimeFormatter FORMAT = DateTimeFormat
			.forPattern("yy-MM-dd");
	private static Logger logger = LoggerFactory
			.getLogger(ComputerValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.debug("Enterring validate in ComputerValidator.");
		ComputerDTO computerDTO = (ComputerDTO) target;

		if (computerDTO.getName() == null
				|| computerDTO.getName().trim().isEmpty()) {
			errors.rejectValue("name", "errorName", "errorName");
		}

		DateTime introduced = null, discontinued = null;
		if (computerDTO.getIntroduced() != null
				&& !computerDTO.getIntroduced().equals("")) {
			if (computerDTO.getIntroduced().trim().length() != 10) {
				errors.rejectValue("introduced", "errorDateIntroduced",
						"You have not given a correct date");
				logger.debug("Problem parsing introduced date.");
			} else {
				try {
					introduced = FORMAT.parseDateTime(computerDTO
							.getIntroduced());
				} catch (java.lang.IllegalArgumentException e) {
					errors.rejectValue("introduced", "errorDateIntroduced",
							"You have not given a correct date");
					logger.debug("Problem parsing introduced date.");
				}
			}
		}

		if (computerDTO.getDiscontinued() != null
				&& !computerDTO.getDiscontinued().trim().equals("")) {
			if (computerDTO.getDiscontinued().length() != 10) {
				errors.rejectValue("introduced", "errorDateIntroduced",
						"You have not given a correct date");
				logger.debug("Problem parsing introduced date.");
			} else {
				try {
					discontinued = FORMAT.parseDateTime(computerDTO
							.getDiscontinued());
				} catch (java.lang.IllegalArgumentException e) {
					errors.rejectValue("discontinued", "errorDateDiscontinued",
							"You have not given a correct date");
					logger.debug("Problem parsing discontinued date.");
				}
			}
		}

		if (computerDTO.getIntroduced() != null
				&& !computerDTO.getIntroduced().equals("")
				&& computerDTO.getDiscontinued() != null
				&& !computerDTO.getDiscontinued().equals("")) {

			if (introduced.isAfter(discontinued)) {
				errors.rejectValue("introduced", "errorTimeDate",
						"Introduced date must be before discontinued.");
			}
		}
		logger.debug("Leaving validate in ComputerValidator.\n");
	}
}
