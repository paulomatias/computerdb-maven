package com.excilys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.domain.Computer;
import com.excilys.mapper.DTOMapper;
import com.excilys.mapper.WrapperMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.transfert.ComputerDTO;
import com.excilys.validator.Validator;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.DTOWrapper;

@Controller
public class ComputerController {
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_INTRODUCED = "introduced";
	public static final String PARAM_DISCONTINUED = "discontinued";
	public static final String PARAM_COMPANY = "company";
	public static final String PARAM_CURRENT_PAGE = "currentPage";
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String PARAM_SEARCH_COMPUTER = "searchComputer";
	public static final String PARAM_SEARCH_COMANY = "searchCompany";
	public static final String PARAM_CURRENTPAGE = "currentPage";
	public static final String PARAM_ORDER_BY = "orderBy";
	public static final Integer recordsPerPage = DTOWrapper.RECORDS_PER_PAGE;
	public static final String ATT_WRAPPER = "wrapper";
	public static final String ATT_ERROR = "error";
	public static final String ATT_ERROR_NAME = "errorName";
	public static final String ATT_ERROR_INTRODUCED = "errorIntroduced";
	public static final String ATT_ERROR_DISCONTINUED = "errorDiscontinued";
	public static final Integer recordsPercurrentPage = DTOWrapper.RECORDS_PER_PAGE;
	public static Logger logger = LoggerFactory
			.getLogger(ComputerController.class);

	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerService computerService;
	@Autowired
	WrapperMapper wrapperMapper;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addComputer(Model model) {

		logger.debug("Entering addComputer in ComputerController.");
		ComputerWrapper computerWrapper = companyService.addComputer();
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
		model.addAttribute(ATT_WRAPPER, dtoWrapper);
		logger.debug("Leaving addComputer in ComputerController.");
		return "addComputer";
	}

	@RequestMapping(value = "/adding", method = RequestMethod.POST)
	public String adding(
			Model model,
			@RequestParam(value = PARAM_NAME, required = false) String computerName,
			@RequestParam(value = PARAM_INTRODUCED, required = false) String introduced,
			@RequestParam(value = PARAM_DISCONTINUED, required = false) String discontinued,
			@RequestParam(value = PARAM_COMPANY, required = false) String company,
			@RequestParam(value = PARAM_CURRENT_PAGE, required = false) Integer currentPage) {

		logger.debug("Entering adding in ComputerController.");
		ComputerDTO computerDTO = ComputerDTO.builder().name(computerName)
				.introduced(introduced).discontinued(discontinued)
				.companyId(new Long(company)).build();
		Validator validator = new Validator();
		String VIEW = null;
		switch (validator.getValidation(computerDTO)) {
		/*
		 * normal case
		 */
		case 0:
			/*
			 * Mapping to computer
			 */
			DTOMapper mapperDTO = new DTOMapper();
			Computer computer = mapperDTO.toComputer(computerDTO);
			/*
			 * Get the wrapper to return to the JSP. All functions necessary are
			 * done in the service package.
			 */
			ComputerWrapper computerWrapper = computerService.addComputer(
					currentPage, computer);

			DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);

			/*
			 * Set attributes and VIEW
			 */
			model.addAttribute(ATT_ERROR, false);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			logger.debug("Leaving adding in ComputerController, case 0.");
			VIEW = "dashboard";
			break;
		/*
		 * error cases
		 */
		case 1:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			logger.debug("Leaving adding in ComputerController, case 1.");
			VIEW = "addComputer";
			break;
		case 2:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			logger.debug("Leaving adding in ComputerController, case 2.");
			VIEW = "addComputer";
			break;
		case 3:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			logger.debug("Leaving adding in ComputerController, case 3.");
			VIEW = "addComputer";
			break;
		case 4:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving adding in ComputerController, case 4.");
			VIEW = "addComputer";
			break;
		case 5:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving adding in ComputerController, case 5.");
			VIEW = "addComputer";
			break;
		case 6:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving adding in ComputerController, case 6.");
			VIEW = "addComputer";
			break;
		case 7:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving adding in ComputerController, case 7.");
			VIEW = "addComputer";
			break;
		}
		return VIEW;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(
			Model model,
			@RequestParam(value = PARAM_SEARCH_COMPUTER, required = false) String searchComputer,
			@RequestParam(value = PARAM_SEARCH_COMANY, required = false) String searchCompany,
			@RequestParam(value = PARAM_CURRENT_PAGE, required = false) Integer currentPage,
			@RequestParam(value = PARAM_ORDER_BY, required = false) String orderBy) {

		if (currentPage == null) {
			currentPage = 1;
		}
		logger.debug("Enterring dashboard in ComputerController.");
		ComputerWrapper computerWrapper = null;
		if (searchComputer == null) {
			searchComputer = "";
		}
		if (searchCompany == null) {
			searchCompany = "";
		}
		if (searchComputer.equals("") && searchCompany.equals("")) {
			logger.debug("no search found");
			computerWrapper = computerService.dashboard(currentPage, orderBy);
		} else if (!searchComputer.equals("") && searchCompany.equals("")) {
			logger.debug("search company");
			computerWrapper = computerService.dashboardSearchComputer(orderBy,
					currentPage, searchComputer);
		} else if ((!searchComputer.equals("") && !searchCompany.equals(""))) {
			logger.debug("search computer and company");
			computerWrapper = computerService
					.dashboardSearchCompanySearchComputer(orderBy, currentPage,
							searchCompany, searchComputer);
		} else if (searchComputer.equals("") && !searchCompany.equals("")) {
			logger.debug("search comptuer");
			computerWrapper = computerService.dashboardSearchCompany(orderBy,
					currentPage, searchCompany);
		}
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
		DTOWrapper.builder().currentPage(currentPage)
				.searchCompany(searchCompany).searchComputer(searchComputer)
				.orderBy(orderBy).build();

		model.addAttribute(ATT_WRAPPER, dtoWrapper);
		logger.debug("Leaving dashboard in ComputerController.");
		return "dashboard";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(
			Model model,
			@RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerId,
			@RequestParam(value = PARAM_CURRENT_PAGE, required = false) Integer currentPage) {

		if (currentPage == null) {
			currentPage = 1;
		}

		ComputerWrapper computerWrapper = computerService.delete(currentPage,
				computerId);
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);

		model.addAttribute(ATT_WRAPPER, dtoWrapper);
		return "dashboard";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(
			Model model,
			@RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerId) {

		ComputerWrapper computerWrapper = computerService
				.getEditComputerWrapper(computerId);
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);

		model.addAttribute(ATT_WRAPPER, dtoWrapper);
		return "editComputer";
	}

	@RequestMapping(value = "/editing", method = RequestMethod.POST)
	public String editing(
			Model model,
			@RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerId,
			@RequestParam(value = PARAM_NAME, required = false) String computerName,
			@RequestParam(value = PARAM_INTRODUCED, required = false) String introduced,
			@RequestParam(value = PARAM_DISCONTINUED, required = false) String discontinued,
			@RequestParam(value = PARAM_COMPANY, required = false) String company,
			@RequestParam(value = PARAM_CURRENT_PAGE, required = false) Integer currentPage) {
		/*
		 * GetParameters
		 */

		Long computerIdL;
		if (!computerId.equals("")) {
			computerIdL = Long.valueOf(computerId);
		} else
			computerIdL = 0L;

		ComputerDTO computerDTO = ComputerDTO.builder().id(computerIdL)
				.name(computerName).introduced(introduced)
				.discontinued(discontinued).companyId(new Long(company))
				.build();
		DTOMapper mapperDTO = new DTOMapper();
		Computer computer = mapperDTO.toComputer(computerDTO);
		Validator validator = new Validator();
		ComputerWrapper computerWrapper;
		DTOWrapper dtoWrapper;
		String VIEW = null;
		switch (validator.getValidation(computerDTO)) {
		/*
		 * normal case
		 */
		case 0:
			/*
			 * Get the wrapper to return to the JSP. All functions necessary are
			 * done in the service package.
			 */
			computerWrapper = computerService.getEditComputerWrapperPost(
					currentPage, computer);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			/*
			 * Set attributes and VIEW
			 */
			model.addAttribute(ATT_ERROR, false);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "dashboard";
			break;
		/*
		 * error cases
		 */
		case 1:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			computerWrapper = computerService
					.getEditComputerWrapper(computerId);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "editComputer";
			break;
		case 2:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			computerWrapper = computerService
					.getEditComputerWrapper(computerId);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "editComputer";
			break;
		case 3:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			computerWrapper = computerService
					.getEditComputerWrapper(computerId);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "editComputer";
			break;
		case 4:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			computerWrapper = computerService
					.getEditComputerWrapper(computerId);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "editComputer";
			break;
		case 5:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			computerWrapper = computerService
					.getEditComputerWrapper(computerId);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "editComputer";
			break;
		case 6:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			computerWrapper = computerService
					.getEditComputerWrapper(computerId);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "editComputer";
			break;
		case 7:
			model.addAttribute(ATT_ERROR, true);
			model.addAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			model.addAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			model.addAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			computerWrapper = computerService
					.getEditComputerWrapper(computerId);
			dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(ATT_WRAPPER, dtoWrapper);
			VIEW = "editComputer";
			break;
		}
		return VIEW;
	}
}
