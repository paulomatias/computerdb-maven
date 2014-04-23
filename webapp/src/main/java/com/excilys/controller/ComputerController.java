package com.excilys.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public static Sort selectOrder(String orderBy) {
		Sort order = null;
		if (orderBy == null) {
			order = new Sort(Sort.Direction.ASC, "id");
		} else
			switch (orderBy) {
			case "nameASC":
				order = new Sort(Sort.Direction.ASC, "name");
				break;
			case "nameDESC":
				order = new Sort(Sort.Direction.DESC, "name");
				break;
			case "introducedASC":
				order = new Sort(Sort.Direction.ASC, "introduced");
				break;
			case "introducedDESC":
				order = new Sort(Sort.Direction.DESC, "introduced");
				break;
			case "discontinuedASC":
				order = new Sort(Sort.Direction.ASC, "discontinued");
				break;
			case "discontinuedDESC":
				order = new Sort(Sort.Direction.DESC, "discontinued");
				break;
			case "companyASC":
				order = new Sort(Sort.Direction.ASC, "company.name");
				break;
			case "companyDESC":
				order = new Sort(Sort.Direction.DESC, "company.name");
				break;
			}
		return order;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(
			Model model,
			@RequestParam(value = MESSAGE, required = false, defaultValue = "welcome") String message,
			@RequestParam(value = CURRENT_PAGE, required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = PARAM_SEARCH_COMPUTER, required = false, defaultValue = "") String searchComputer,
			@RequestParam(value = PARAM_SEARCH_COMANY, required = false, defaultValue = "") String searchCompany,
			@RequestParam(value = PARAM_ORDER_BY, required = false) String orderBy) {

		Sort sort = selectOrder(orderBy);
		Pageable page = new PageRequest(currentPage - 1,
				PageWrapper.RECORDS_PER_PAGE, sort);
		Page<Computer> pageComputer = null;

		ComputerWrapper computerWrapper = new ComputerWrapper();
		if (searchComputer.equals("") && searchCompany.equals("")) {
			pageComputer = computerService.dashboard(page);
		} else if (!searchComputer.equals("") && searchCompany.equals("")) {
			message = "welcomeSelect";
			pageComputer = computerService.dashboardSearchComputer(
					searchComputer, page);
		} else if ((!searchComputer.equals("") && !searchCompany.equals(""))) {
			message = "welcomeSelect";
			pageComputer = computerService
					.dashboardSearchCompanySearchComputer(searchComputer,
							searchCompany, page);
		} else if (searchComputer.equals("") && !searchCompany.equals("")) {
			message = "welcomeSelect";
			pageComputer = computerService.dashboardSearchCompany(
					searchCompany, page);
		}
		computerWrapper.setListComputers((pageComputer).getContent());
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);

		PageWrapper pageWrapper = PageWrapper.builder().message(message)
				.nbrOfPages(pageComputer.getTotalPages())
				.nbrComputers(new Long(pageComputer.getTotalElements()))
				.currentPage((pageComputer.getNumber() + 1))
				.recordsPerPage(PageWrapper.RECORDS_PER_PAGE)
				.searchCompany(searchCompany).searchComputer(searchComputer)
				.orderBy(orderBy).build();
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

		if (!result.hasErrors()) {
			DTOMapper mapperDTO = new DTOMapper();
			Computer computer = mapperDTO.toComputer(computerDTO);
			computerService.add(computer);

			model.addAttribute(MESSAGE, "welcomeAdd");
			return "redirect:dashboard";
		} else {
			ComputerWrapper computerWrapper = companyService.addForm();
			DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(COMPUTER_DTO_WRAPPER, dtoWrapper);
			return "addComputer";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(
			Model model,
			@RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerId,
			@RequestParam(value = CURRENT_PAGE, required = false) Integer currentPage) {
		computerService.delete(computerId);
		model.addAttribute(CURRENT_PAGE, currentPage);
		model.addAttribute(MESSAGE, "welcomeDelete");
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

		if (!result.hasErrors()) {
			Computer computer = dtoMapper.toComputer(computerDTO);
			ComputerWrapper computerWrapper = computerService.edit(computer);
			DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
			model.addAttribute(MESSAGE, "welcomeEdit");
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