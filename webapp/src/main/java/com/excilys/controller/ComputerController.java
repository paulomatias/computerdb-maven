package com.excilys.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.domain.Computer;
import com.excilys.mapper.DTOMapper;
import com.excilys.mapper.WrapperMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.transfert.ComputerDTO;
import com.excilys.validator.ComputerValidator;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.DTOWrapper;
import com.excilys.wrapper.PageWrapper;

@Controller
public class ComputerController {
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_INTRODUCED = "introduced";
	public static final String PARAM_DISCONTINUED = "discontinued";
	public static final String PARAM_COMPANY = "company";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String PARAM_SEARCH_COMPUTER = "searchComputer";
	public static final String PARAM_SEARCH_COMANY = "searchCompany";
	public static final String PARAM_CURRENTPAGE = "currentPage";
	public static final String PARAM_ORDER_BY = "orderBy";
	public static final String MESSAGE = "message";
	public static final String COMPUTER_DTO_WRAPPER = "dtoWrapper";
	public static final String PAGE_WRAPPER = "pageWrapper";
	public static final String ATT_ERROR = "error";
	public static final String ATT_ERROR_NAME = "errorName";
	public static final String ATT_ERROR_INTRODUCED = "errorIntroduced";
	public static final String ATT_ERROR_DISCONTINUED = "errorDiscontinued";

	private static Logger logger = LoggerFactory
			.getLogger(ComputerController.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private WrapperMapper wrapperMapper;
	@Autowired
	private DTOMapper dtoMapper;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(
			Model model,
			@RequestParam(value = MESSAGE, required = false) String message,
			@RequestParam(value = CURRENT_PAGE, required = false) Integer currentPage,
			@RequestParam(value = PARAM_SEARCH_COMPUTER, required = false) String searchComputer,
			@RequestParam(value = PARAM_SEARCH_COMANY, required = false) String searchCompany,
			@RequestParam(value = PARAM_ORDER_BY, required = false) String orderBy) {

		PageWrapper pageWrapper = PageWrapper.builder().message(message)
				.recordsPerPage(PageWrapper.RECORDS_PER_PAGE)
				.currentPage(currentPage).searchCompany(searchCompany)
				.searchComputer(searchComputer).orderBy(orderBy).build();
		ComputerWrapper computerWrapper = new ComputerWrapper();

		if (pageWrapper.getSearchComputer().equals("")
				&& pageWrapper.getSearchCompany().equals("")) {
			computerWrapper = computerService.dashboard(pageWrapper);
		} else if (!pageWrapper.getSearchComputer().equals("")
				&& pageWrapper.getSearchCompany().equals("")) {
			computerWrapper = computerService
					.dashboardSearchComputer(pageWrapper);
		} else if ((!pageWrapper.getSearchComputer().equals("") && !pageWrapper
				.getSearchCompany().equals(""))) {
			computerWrapper = computerService
					.dashboardSearchCompanySearchComputer(pageWrapper);
		} else if (pageWrapper.getSearchComputer().equals("")
				&& !pageWrapper.getSearchCompany().equals("")) {
			computerWrapper = computerService
					.dashboardSearchCompany(pageWrapper);
		}
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);

		model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
		model.addAttribute(PAGE_WRAPPER, pageWrapper);
		return "dashboard";
	}

	@RequestMapping(value = "/addForm", method = RequestMethod.GET)
	public String addForm(Model model) {

		ComputerWrapper computerWrapper = companyService.addForm();
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
		model.addAttribute("cDTO", new ComputerDTO());
		model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
		return "addComputer";
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String add(Model model,
			@ModelAttribute("cDTO") @Valid ComputerDTO computerDTO,
			BindingResult result) {

		PageWrapper pageWrapper = new PageWrapper();
		if (!result.hasErrors()) {
			DTOMapper mapperDTO = new DTOMapper();
			Computer computer = mapperDTO.toComputer(computerDTO);
			ComputerWrapper computerWrapper = computerService.add(pageWrapper,
					computer);

			DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(CURRENT_PAGE, pageWrapper.getCurrentPage());
			model.addAttribute(MESSAGE, pageWrapper.getMessage());
			model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
			return "redirect:dashboard";
		} else {
			ComputerWrapper computerWrapper = companyService.addForm();
			DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(CURRENT_PAGE, pageWrapper.getCurrentPage());
			model.addAttribute(MESSAGE, pageWrapper.getMessage());
			model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
			return "addComputer";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(
			Model model,
			@RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerId,
			@RequestParam(value = CURRENT_PAGE, required = false) Integer currentPage) {

		PageWrapper pageWrapper = new PageWrapper();
		pageWrapper.setCurrentPage(currentPage);
		ComputerWrapper computerWrapper = computerService.delete(pageWrapper,
				computerId);
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
		model.addAttribute(CURRENT_PAGE, pageWrapper.getCurrentPage());
		model.addAttribute(MESSAGE, pageWrapper.getMessage());
		model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
		return "redirect:dashboard";
	}

	@RequestMapping(value = "/editForm", method = RequestMethod.GET)
	public String editForm(
			Model model,
			@RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerId) {

		ComputerWrapper computerWrapper = computerService.editForm(computerId);
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
		model.addAttribute("cDTO", dtoWrapper.getComputerDTO());
		model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
		return "editComputer";
	}

	@RequestMapping(value = "/edit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String edit(
			Model model,
			@RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerId,
			@ModelAttribute("cDTO") @Valid ComputerDTO computerDTO,
			BindingResult result) {

		PageWrapper pageWrapper = new PageWrapper();

		if (!result.hasErrors()) {
			Computer computer = dtoMapper.toComputer(computerDTO);
			ComputerWrapper computerWrapper = computerService.edit(pageWrapper,
					computer);
			DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(CURRENT_PAGE, pageWrapper.getCurrentPage());
			model.addAttribute(MESSAGE, pageWrapper.getMessage());
			model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
			return "redirect:dashboard";
		} else {
			ComputerWrapper computerWrapper = computerService
					.editForm(computerDTO.getId().toString());
			DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
			return "editComputer";
		}

	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String error(Model model) {
		return "errors/404";
	}

	@InitBinder("cDTO")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(new ComputerValidator());
	}
}