package com.excilys.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.excilys.transfert.ComputerDTO;

public class Validator {
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public Integer getValidation(ComputerDTO computerDTO) {
		Integer error = new Integer(0);

		if (computerDTO.getName() == null
				|| computerDTO.getName().trim().isEmpty()) {
			error += 1;
		}

		if (computerDTO.getIntroduced() != null
				&& !computerDTO.getIntroduced().equals("")) {
			try {
				FORMAT.parse(computerDTO.getIntroduced());
			} catch (ParseException e) {
				error += 2;
			}
		}

		if (computerDTO.getDiscontinued() != null
				&& !computerDTO.getDiscontinued().equals("")) {
			try {
				FORMAT.parse(computerDTO.getDiscontinued());
			} catch (ParseException e) {
				error += 4;
			}
		}
		return error;
	}

}
