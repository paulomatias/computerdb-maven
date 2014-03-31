package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.domain.Computer;
import com.excilys.mapper.DTOMapper;
import com.excilys.mapper.WrapperMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.transfert.ComputerDTO;
import com.excilys.validator.Validator;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.DTOWrapper;

@SuppressWarnings("serial")
public class AddComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_NAME = "computerName";
	public static final String PARAM_INTRODUCED = "introduced";
	public static final String PARAM_DISCONTINUED = "discontinued";
	public static final String PARAM_COMPANY = "company";
	public static final String PARAM_CURRENT_PAGE = "currentPage";
	public static final String ATT_WRAPPER = "wrapper";
	public static final String ATT_ERROR = "error";
	public static final String ATT_ERROR_NAME = "errorName";
	public static final String ATT_ERROR_INTRODUCED = "errorIntroduced";
	public static final String ATT_ERROR_DISCONTINUED = "errorDiscontinued";
	public static final String VIEW_POST = "/WEB-INF/dashboard.jsp";
	public static final String VIEW_GET = "/WEB-INF/addComputer.jsp";
	public static final Integer recordsPerPage = DTOWrapper.RECORDS_PER_PAGE;
	public static Logger logger = LoggerFactory
			.getLogger(AddComputerServlet.class);
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerService computerService;
	@Autowired
	WrapperMapper wrapperMapper;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("Entering AddComptuerServlet doGet.");

		/*
		 * Get instance of services by serviceManager
		 */

		/*
		 * Get the wrapper to return to the JSP. All functions necessary are
		 * done in the service package.
		 */
		ComputerWrapper computerWrapper = companyService.addComputer();
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);
		/*
		 * Set attributes and VIEW
		 */
		request.setAttribute(ATT_WRAPPER, dtoWrapper);
		request.getRequestDispatcher(VIEW_GET).forward(request, response);
		logger.debug("Leaving AddComptuerServlet doGet.");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("Entering AddComptuerServlet doPost.");

		/*
		 * Get instance of services by serviceManager
		 */

		/*
		 * GetParameters
		 */
		String name = request.getParameter(PARAM_NAME);
		String introduced = request.getParameter(PARAM_INTRODUCED);
		String discontinued = request.getParameter(PARAM_DISCONTINUED);
		String companyId = request.getParameter(PARAM_COMPANY);
		Integer currentPage = 1;
		if (request.getParameter(PARAM_CURRENT_PAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENT_PAGE));
		}

		/*
		 * Setting computerDTO
		 */
		ComputerDTO computerDTO = ComputerDTO.builder().name(name)
				.introduced(introduced).discontinued(discontinued)
				.companyId(new Long(companyId)).build();

		/*
		 * Call Validator Back function on the DTO
		 */
		Validator validator = new Validator();
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
			request.setAttribute(ATT_ERROR, false);
			request.setAttribute(ATT_WRAPPER, dtoWrapper);
			logger.debug("Leaving AddComptuerServlet doPost. case 0:");
			request.getRequestDispatcher(VIEW_POST).forward(request, response);
			break;
		/*
		 * error cases
		 */
		case 1:
			request.setAttribute(ATT_ERROR, true);
			request.setAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			logger.debug("Leaving AddComptuerServlet doPost. case 1:");
			request.getRequestDispatcher(VIEW_GET).forward(request, response);
			break;
		case 2:
			request.setAttribute(ATT_ERROR, true);
			request.setAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			logger.debug("Leaving AddComptuerServlet doPost. case 2:");
			request.getRequestDispatcher(VIEW_GET).forward(request, response);
			break;
		case 3:
			request.setAttribute(ATT_ERROR, true);
			request.setAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			request.setAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			logger.debug("Leaving AddComptuerServlet doPost. case 3:");
			request.getRequestDispatcher(VIEW_GET).forward(request, response);
			break;
		case 4:
			request.setAttribute(ATT_ERROR, true);
			request.setAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving AddComptuerServlet doPost. case 4:");
			request.getRequestDispatcher(VIEW_GET).forward(request, response);

		case 5:
			request.setAttribute(ATT_ERROR, true);
			request.setAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			request.setAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving AddComptuerServlet doPost. case 5:");
			request.getRequestDispatcher(VIEW_GET).forward(request, response);
			break;
		case 6:
			request.setAttribute(ATT_ERROR, true);
			request.setAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			request.setAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving AddComptuerServlet doPost. case 6:");
			request.getRequestDispatcher(VIEW_GET).forward(request, response);
			break;
		case 7:
			request.setAttribute(ATT_ERROR, true);
			request.setAttribute(ATT_ERROR_NAME,
					"The name of the computer is a required field.");
			request.setAttribute(ATT_ERROR_INTRODUCED,
					"Your introduced date is not correct.");
			request.setAttribute(ATT_ERROR_DISCONTINUED,
					"Your discontinued date is not correct.");
			logger.debug("Leaving AddComptuerServlet doPost. case 7:");
			request.getRequestDispatcher(VIEW_GET).forward(request, response);
			break;
		}
	}
}
