package com.excilys.persistence;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
	public static String selectOrder(String orderBy) {
		if (orderBy == null) {
			orderBy = "computer.id ASC";
		} else
			switch (orderBy) {
			case "nameASC":
				orderBy = "computer.name ASC";
				break;
			case "nameDESC":
				orderBy = "computer.name DESC";
				break;
			case "introducedASC":
				orderBy = "computer.introduced ASC";
				break;
			case "introducedDESC":
				orderBy = "computer.introduced DESC";
				break;
			case "discontinuedASC":
				orderBy = "computer.discontinued ASC";
				break;
			case "discontinuedDESC":
				orderBy = "computer.discontinued DESC";
				break;
			case "companyASC":
				orderBy = "company.name ASC";
				break;
			case "companyDESC":
				orderBy = "company.name DESC";
				break;
			}
		return orderBy;
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

		StringBuilder select = new StringBuilder(
				"SELECT computer FROM Computer AS computer LEFT OUTER JOIN computer.company AS company ");
		String orderBy = selectOrder(pageWrapper.getOrderBy());
		select.append(" ORDER BY ").append(orderBy);
		Query query = session
				.getCurrentSession()
				.createQuery(select.toString())
				.setMaxResults(pageWrapper.getRecordsPerPage())
				.setFirstResult(
						(pageWrapper.getCurrentPage() - 1)
								* pageWrapper.getRecordsPerPage());
		return query.list();
	}

	/*
	 * Return the number of computers in the database
	 */
	public Long countAll() {
		String count = "SELECT COUNT(computer) FROM Computer computer";
		return (Long) session.getCurrentSession().createQuery(count).iterate()
				.next();
	}

	/*
	 * Return the list of computers with a specific name, ordered and limited
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> retrieveByName(PageWrapper pageWrapper) {

		StringBuilder select = new StringBuilder(
				"SELECT computer FROM Computer AS computer LEFT OUTER JOIN computer.company AS company WHERE computer.name=:name");
		String orderBy = selectOrder(pageWrapper.getOrderBy());
		select.append(" ORDER BY ").append(orderBy);

		Query query = session
				.getCurrentSession()
				.createQuery(select.toString())
				.setParameter("name", pageWrapper.getSearchComputer())
				.setMaxResults(pageWrapper.getRecordsPerPage())
				.setFirstResult(
						(pageWrapper.getCurrentPage() - 1)
								* pageWrapper.getRecordsPerPage());
		return query.list();
	}

	/*
	 * Return the number of computers with a specific name in the database
	 */
	public Long countByName(PageWrapper pageWrapper) {

		String count = "SELECT COUNT(computer) FROM Computer AS computer LEFT OUTER JOIN computer.company AS company WHERE computer.name=:name";
		return (Long) session.getCurrentSession().createQuery(count)
				.setParameter("name", pageWrapper.getSearchComputer())
				.iterate().next();
	}

	/*
	 * Return the list of computers with a specific name and a specific company,
	 * ordered and limited
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> retrieveByNameAndCompanyName(PageWrapper pageWrapper) {

		StringBuilder select = new StringBuilder(
				"SELECT computer FROM Computer AS computer LEFT OUTER JOIN computer.company AS company WHERE computer.name=:name AND company.name=:companyName");
		String orderBy = selectOrder(pageWrapper.getOrderBy());
		select.append(" ORDER BY ").append(orderBy);

		Query query = session
				.getCurrentSession()
				.createQuery(select.toString())
				.setParameter("name", pageWrapper.getSearchComputer())
				.setParameter("companyName", pageWrapper.getSearchCompany())
				.setMaxResults(pageWrapper.getRecordsPerPage())
				.setFirstResult(
						(pageWrapper.getCurrentPage() - 1)
								* pageWrapper.getRecordsPerPage());
		return query.list();
	}

	/*
	 * Return the number of computers with a specific name and a specific
	 * company in the database
	 */
	public Long countByNameAndCompanyName(PageWrapper pageWrapper) {

		String count = "SELECT COUNT(computer) FROM Computer AS computer LEFT OUTER JOIN computer.company AS company WHERE computer.name=:name AND company.name=:companyName";
		return (Long) session.getCurrentSession().createQuery(count)
				.setParameter("companyName", pageWrapper.getSearchCompany())
				.setParameter("name", pageWrapper.getSearchComputer())
				.iterate().next();
	}

	/*
	 * Return the list of computers with a specific company, ordered and limited
	 */
	@SuppressWarnings("unchecked")
	public List<Computer> retrieveByCompanyName(PageWrapper pageWrapper) {

		StringBuilder select = new StringBuilder(
				"SELECT computer FROM Computer AS computer LEFT OUTER JOIN computer.company AS company WHERE company.name=:companyName");
		String orderBy = selectOrder(pageWrapper.getOrderBy());
		select.append(" ORDER BY ").append(orderBy);

		Query query = session
				.getCurrentSession()
				.createQuery(select.toString())
				.setParameter("companyName", pageWrapper.getSearchCompany())
				.setMaxResults(pageWrapper.getRecordsPerPage())
				.setFirstResult(
						(pageWrapper.getCurrentPage() - 1)
								* pageWrapper.getRecordsPerPage());
		return query.list();
	}

	/*
	 * Return the number of computers with a specific company in the database
	 */
	public Long countByCompanyName(PageWrapper pageWrapper) {

		String count = "SELECT COUNT(computer) FROM Computer AS computer LEFT OUTER JOIN computer.company AS company WHERE company.name=:companyName ";
		return (Long) session.getCurrentSession().createQuery(count)
				.setParameter("companyName", pageWrapper.getSearchCompany())
				.iterate().next();
	}

}
