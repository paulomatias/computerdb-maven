package com.excilys.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.transfert.ComputerDTO;

public class ComputerValidator implements Validator {
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static Logger logger = LoggerFactory
			.getLogger(ComputerValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ComputerDTO computerDTO = (ComputerDTO) target;

		if (computerDTO.getName() == null
				|| computerDTO.getName().trim().isEmpty()) {
			errors.rejectValue("name", "computer.name.empty",
					"Name is a required field.");
		}

		if (computerDTO.getIntroduced() != null
				&& !computerDTO.getIntroduced().equals("")) {
			try {
				FORMAT.parse(computerDTO.getIntroduced());
			} catch (ParseException e) {
				errors.rejectValue("introduced", "computer.introduced.error",
						"You have not given a correct date");
				logger.debug("Problem parsing introduced date.");
			}
		}

		if (computerDTO.getDiscontinued() != null
				&& !computerDTO.getDiscontinued().equals("")) {
			try {
				FORMAT.parse(computerDTO.getDiscontinued());
			} catch (ParseException e) {
				errors.rejectValue("discontinued",
						"computer.discontinued.error",
						"You have not given a correct date");
				logger.debug("Problem parsing discontinued date.");
			}
		}

		if (computerDTO.getIntroduced() != null
				&& !computerDTO.getIntroduced().equals("")
				&& computerDTO.getDiscontinued() != null
				&& !computerDTO.getDiscontinued().equals("")) {

			try {
				if (FORMAT.parse(computerDTO.getIntroduced()).after(
						FORMAT.parse(computerDTO.getDiscontinued()))) {
					errors.rejectValue("introduced",
							"computer.introduced.error",
							"Introduced date must be before discontinued.");
				}
			} catch (ParseException e) {

				logger.debug("Problem parsing either introduced or discontinued date.");
				e.printStackTrace();
			}
		}

	}
}
