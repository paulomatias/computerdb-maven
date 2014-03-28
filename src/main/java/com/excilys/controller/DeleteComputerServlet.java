package com.excilys.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.mapper.WrapperMapper;
import com.excilys.service.ComputerService;
import com.excilys.wrapper.ComputerWrapper;
import com.excilys.wrapper.DTOWrapper;

@SuppressWarnings("serial")
public class DeleteComputerServlet extends HttpServlet {
	/* JSP Parameters, request attributes, views */
	public static final String PARAM_COMPUTER_ID = "id";
	public static final String PARAM_CURRENT_PAGE = "currentPage";
	public static final String ATT_WRAPPER = "wrapper";
	public static final String VIEW = "/WEB-INF/dashboard.jsp";
	public static final Integer recordsPerPage = DTOWrapper.RECORDS_PER_PAGE;
	@Autowired
	ComputerService computerService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Get instance of services by serviceManager
		 */

		/*
		 * GetParameters
		 */
		String computerId = request.getParameter(PARAM_COMPUTER_ID);
		Integer currentPage = 1;
		if (request.getParameter(PARAM_CURRENT_PAGE) != null) {
			currentPage = Integer.parseInt(request
					.getParameter(PARAM_CURRENT_PAGE));
		}

		/*
		 * Get the wrapper to return to the JSP. All functions necessary are
		 * done in the service package.
		 */
		ComputerWrapper computerWrapper = computerService.delete(currentPage,
				computerId);
		WrapperMapper wrapperMapper = new WrapperMapper();
		DTOWrapper dtoWrapper = wrapperMapper.toDTOWrapper(computerWrapper);

		/*
		 * Set attributes and VIEW
		 */
		request.setAttribute(ATT_WRAPPER, dtoWrapper);
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
