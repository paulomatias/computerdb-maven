package com.excilys.persistence;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.domain.Computer;
import com.excilys.wrapper.PageWrapper;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAO {
	@Autowired
	private BoneCPDataSource datasource;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	public static final DateTimeFormatter FORMAT = DateTimeFormat
			.forPattern("yyyy-MM-dd");
	@Autowired
	private JdbcTemplate jt;
	@Autowired
	private NamedParameterJdbcTemplate npjt;

	@Autowired
	SessionFactory session;

	/*
	 * Switch correctly the orderBy
	 */
	public static void selectOrder(String orderBy, Criteria cr) {
		if (orderBy == null) {
			cr.addOrder(Order.asc("id"));
		} else
			switch (orderBy) {
			case "nameASC":
				cr.addOrder(Order.asc("name"));
				break;
			case "nameDESC":
				cr.addOrder(Order.desc("name"));
				break;
			case "introducedASC":
				cr.addOrder(Order.asc("introduced"));
				break;
			case "introducedDESC":
				cr.addOrder(Order.desc("introduced"));
				break;
			case "discontinuedASC":
				cr.addOrder(Order.asc("discontinued"));
				break;
			case "discontinuedDESC":
				cr.addOrder(Order.desc("discontinued"));
				break;
			case "companyASC":
				cr.createAlias("company", "company");
				cr.addOrder(Order.asc("company.name"));
				break;
			case "companyDESC":
				cr.createAlias("company", "company");
				cr.addOrder(Order.desc("company.name"));
				break;
			}
	}

	/*
	 * functions SQL
	 */

	/*
	 * Add a computer to the database, and return the id auto incremented of the
	 * computer added
	 */
	public Long create(Computer computer) {
		session.getCurrentSession().persist(computer);
		return computer.getId();
	}

	/*
	 * Delete a computer from the database
	 */
	public void delete(Computer computer) {
		session.getCurrentSession().delete(computer);
	}

	/*
	 * Edit a computer from the database
	 */
	public void update(Computer computer) {
		session.getCurrentSession().merge(computer);
	}

	/*
	 * Return a computer using its id
	 */
	public Computer get(Long computerId) throws ParseException {
		return (Computer) session.getCurrentSession().get(Computer.class,
				computerId);
	}

	/*
	 * Return the list of computers, ordered and limited
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> retrieveAll(PageWrapper pageWrapper) {
		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		cr.setFirstResult((pageWrapper.getCurrentPage() - 1)
				* pageWrapper.getRecordsPerPage());
		cr.setMaxResults(pageWrapper.getRecordsPerPage());
		selectOrder(pageWrapper.getOrderBy(), cr);
		return cr.list();
	}

	/*
	 * Return the number of computers in the database
	 */
	public Long countAll() {
		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		return (Long) cr.setProjection(Projections.rowCount()).list().get(0);
	}

	/*
	 * Return the list of computers with a specific name, ordered and limited
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> retrieveByName(PageWrapper pageWrapper) {
		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		cr.add(Restrictions.like("name", pageWrapper.getSearchComputer()));
		cr.setFirstResult((pageWrapper.getCurrentPage() - 1)
				* pageWrapper.getRecordsPerPage());
		cr.setMaxResults(pageWrapper.getRecordsPerPage());
		selectOrder(pageWrapper.getOrderBy(), cr);
		return cr.list();
	}

	/*
	 * Return the number of computers with a specific name in the database
	 */
	public Long countByName(PageWrapper pageWrapper) {
		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		cr.add(Restrictions.like("name", pageWrapper.getSearchComputer()));
		return (Long) cr.setProjection(Projections.rowCount()).list().get(0);
	}

	/*
	 * Return the list of computers with a specific name and a specific company,
	 * ordered and limited
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> retrieveByNameAndCompanyName(PageWrapper pageWrapper) {

		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		cr.createAlias("company", "company");
		cr.add(Restrictions.like("name", pageWrapper.getSearchComputer()));
		cr.add(Restrictions.like("company.name", pageWrapper.getSearchCompany()));
		cr.setFirstResult((pageWrapper.getCurrentPage() - 1)
				* pageWrapper.getRecordsPerPage());
		cr.setMaxResults(pageWrapper.getRecordsPerPage());
		selectOrder(pageWrapper.getOrderBy(), cr);
		return cr.list();
	}

	/*
	 * Return the number of computers with a specific name and a specific
	 * company in the database
	 */
	public Long countByNameAndCompanyName(PageWrapper pageWrapper) {

		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		cr.createAlias("company", "company");
		cr.add(Restrictions.like("name", pageWrapper.getSearchComputer()));
		cr.add(Restrictions.like("company.name", pageWrapper.getSearchCompany()));
		cr.setFirstResult((pageWrapper.getCurrentPage() - 1)
				* pageWrapper.getRecordsPerPage());
		cr.setMaxResults(pageWrapper.getRecordsPerPage());
		selectOrder(pageWrapper.getOrderBy(), cr);
		return (Long) cr.setProjection(Projections.rowCount()).list().get(0);
	}

	/*
	 * Return the list of computers with a specific company, ordered and limited
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> retrieveByCompanyName(PageWrapper pageWrapper) {

		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		cr.createAlias("company", "company");
		cr.add(Restrictions.like("company.name", pageWrapper.getSearchCompany()));
		cr.setFirstResult((pageWrapper.getCurrentPage() - 1)
				* pageWrapper.getRecordsPerPage());
		cr.setMaxResults(pageWrapper.getRecordsPerPage());
		selectOrder(pageWrapper.getOrderBy(), cr);
		return cr.list();
	}

	/*
	 * Return the number of computers with a specific company in the database
	 */
	public Long countByCompanyName(PageWrapper pageWrapper) {

		Criteria cr = session.getCurrentSession()
				.createCriteria(Computer.class);
		cr.createAlias("company", "company");
		cr.add(Restrictions.like("company.name", pageWrapper.getSearchCompany()));
		cr.setFirstResult((pageWrapper.getCurrentPage() - 1)
				* pageWrapper.getRecordsPerPage());
		cr.setMaxResults(pageWrapper.getRecordsPerPage());
		selectOrder(pageWrapper.getOrderBy(), cr);
		return (Long) cr.setProjection(Projections.rowCount()).list().get(0);
	}

}
