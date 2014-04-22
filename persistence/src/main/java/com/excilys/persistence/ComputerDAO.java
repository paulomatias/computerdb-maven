package com.excilys.persistence;

import java.text.ParseException;
import java.util.List;

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
import com.excilys.domain.QComputer;
import com.excilys.wrapper.PageWrapper;
import com.jolbox.bonecp.BoneCPDataSource;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.OrderSpecifier;

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
	@SuppressWarnings("rawtypes")
	public static OrderSpecifier selectOrder(String orderBy, QComputer computer) {
		OrderSpecifier order = null;
		if (orderBy == null) {
			order = computer.id.asc();
		} else
			switch (orderBy) {
			case "nameASC":
				order = computer.name.asc();
				break;
			case "nameDESC":
				order = computer.name.desc();
				break;
			case "introducedASC":
				order = computer.introduced.asc();
				break;
			case "introducedDESC":
				order = computer.introduced.desc();
				break;
			case "discontinuedASC":
				order = computer.discontinued.asc();
				break;
			case "discontinuedDESC":
				order = computer.discontinued.desc();
				break;
			case "companyASC":
				order = computer.company.name.asc();
				break;
			case "companyDESC":
				order = computer.company.name.desc();
				break;
			}
		return order;
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
	public List<Computer> retrieveAll(PageWrapper pageWrapper) {

		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query
				.from(computer)
				.orderBy(selectOrder(pageWrapper.getOrderBy(), computer))
				.offset((pageWrapper.getCurrentPage() - 1)
						* pageWrapper.getRecordsPerPage())
				.limit(pageWrapper.getRecordsPerPage()).list(computer);
	}

	/*
	 * Return the number of computers in the database
	 */
	public Long countAll() {
		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query.from(computer).count();
	}

	/*
	 * Return the list of computers with a specific name, ordered and limited
	 */
	public List<Computer> retrieveByName(PageWrapper pageWrapper) {

		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query
				.from(computer)
				.where(computer.name.eq(pageWrapper.getSearchComputer()))
				.orderBy(selectOrder(pageWrapper.getOrderBy(), computer))
				.offset((pageWrapper.getCurrentPage() - 1)
						* pageWrapper.getRecordsPerPage())
				.limit(pageWrapper.getRecordsPerPage()).list(computer);
	}

	/*
	 * Return the number of computers with a specific name in the database
	 */
	public Long countByName(PageWrapper pageWrapper) {
		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query.from(computer)
				.where(computer.name.eq(pageWrapper.getSearchComputer()))
				.count();
	}

	/*
	 * Return the list of computers with a specific name and a specific company,
	 * ordered and limited
	 */
	public List<Computer> retrieveByNameAndCompanyName(PageWrapper pageWrapper) {

		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query
				.from(computer)
				.where(computer.name.eq(pageWrapper.getSearchComputer())
						.and(computer.company.name.eq(pageWrapper
								.getSearchCompany())))
				.orderBy(selectOrder(pageWrapper.getOrderBy(), computer))
				.offset((pageWrapper.getCurrentPage() - 1)
						* pageWrapper.getRecordsPerPage())
				.limit(pageWrapper.getRecordsPerPage()).list(computer);
	}

	/*
	 * Return the number of computers with a specific name and a specific
	 * company in the database
	 */
	public Long countByNameAndCompanyName(PageWrapper pageWrapper) {

		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query
				.from(computer)
				.where(computer.name.eq(pageWrapper.getSearchComputer())
						.and(computer.company.name.eq(pageWrapper
								.getSearchCompany()))).count();
	}

	/*
	 * Return the list of computers with a specific company, ordered and limited
	 */
	public List<Computer> retrieveByCompanyName(PageWrapper pageWrapper) {

		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query
				.from(computer)
				.where(computer.company.name.eq(pageWrapper.getSearchCompany()))
				.orderBy(selectOrder(pageWrapper.getOrderBy(), computer))
				.offset((pageWrapper.getCurrentPage() - 1)
						* pageWrapper.getRecordsPerPage())
				.limit(pageWrapper.getRecordsPerPage()).list(computer);
	}

	/*
	 * Return the number of computers with a specific company in the database
	 */
	public Long countByCompanyName(PageWrapper pageWrapper) {

		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(session.getCurrentSession());
		return query
				.from(computer)
				.where(computer.company.name.eq(pageWrapper.getSearchCompany()))
				.count();
	}

}
