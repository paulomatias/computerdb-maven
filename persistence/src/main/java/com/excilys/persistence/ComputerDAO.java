package com.excilys.persistence;

import java.text.ParseException;
import java.util.List;

import mapper.ComputerRowMapper;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.domain.Computer;
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
				orderBy = "computer.company_id ASC";
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

		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource map = new MapSqlParameterSource();

		String add = "INSERT INTO `computer-database-db`.`computer` (name,introduced,discontinued,company_id) VALUES (:name,:introduced,:discontinued,:company);";
		String name = computer.getName();
		java.sql.Date introduced = null;
		java.sql.Date discontinued = null;
		Long id = null;
		if (computer.getIntroduced() != null) {
			introduced = new java.sql.Date(computer.getIntroduced().getMillis());
		}
		if (computer.getDiscontinued() != (null)) {
			discontinued = new java.sql.Date(computer.getDiscontinued()
					.getMillis());
		}
		if (!computer.getCompany().getId().equals((0L))) {
			id = computer.getCompany().getId();
		}

		map.addValue("name", name);
		map.addValue("introduced", introduced);
		map.addValue("discontinued", discontinued);
		map.addValue("company", id);
		npjt.update(add, map, keyHolder);
		return keyHolder.getKey().longValue();
	}

	/*
	 * Delete a computer from the database
	 */
	public void delete(Computer computer) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		String delete = "DELETE FROM `computer-database-db`.`computer` WHERE id=:id;";
		map.addValue("id", computer.getId());
		npjt.update(delete, map);
	}

	/*
	 * Edit a computer from the database
	 */
	public void update(Computer computer) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		String update = "UPDATE computer SET name =:name,introduced=:introduced,discontinued=:discontinued,company_id=:companyId WHERE id=:id;";

		String name = computer.getName();
		java.sql.Date introduced = null;
		java.sql.Date discontinued = null;
		Long companyId = null;
		if (computer.getIntroduced() != null) {
			introduced = new java.sql.Date(computer.getIntroduced().getMillis());
		}
		if (computer.getDiscontinued() != (null)) {
			discontinued = new java.sql.Date(computer.getDiscontinued()
					.getMillis());
		}
		if (!computer.getCompany().getId().equals((0L))) {
			companyId = computer.getCompany().getId();
		}
		map.addValue("name", name);
		map.addValue("introduced", introduced);
		map.addValue("discontinued", discontinued);
		map.addValue("companyId", companyId);
		map.addValue("id", computer.getId());
		npjt.update(update, map);
	}

	/*
	 * Return a computer using its id
	 */
	public Computer get(Long computerId) throws ParseException {

		MapSqlParameterSource map = new MapSqlParameterSource();
		String select = "SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.id=:id;";
		map.addValue("id", computerId);
		return npjt.queryForObject(select, map, new ComputerRowMapper());
	}

	/*
	 * Return the list of computers, ordered and limited
	 */
	public List<Computer> retrieveAll(String orderBy, Integer page,
			Integer recordsPerPage) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		StringBuilder select = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id");
		orderBy = ComputerDAO.selectOrder(orderBy);
		select.append(" ORDER BY ").append(orderBy)
				.append(" LIMIT :offset,:nbDisplay;");

		map.addValue("offset", (page - 1) * recordsPerPage);
		map.addValue("nbDisplay", recordsPerPage);

		return npjt.query(select.toString(), map, new ComputerRowMapper());
	}

	/*
	 * Return the number of computers in the database
	 */
	public Long countAll() {

		String count = "SELECT COUNT(*) FROM `computer-database-db`.computer ;";
		return jt.queryForObject(count, Long.class);
	}

	/*
	 * Return the list of computers with a specific name, ordered and limited
	 */
	public List<Computer> retrieveByName(String computerName, String orderBy,
			Integer page, Integer recordsPerPage) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		StringBuilder select = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=:name");
		orderBy = ComputerDAO.selectOrder(orderBy);
		select.append(" ORDER BY ").append(orderBy)
				.append(" LIMIT :offset,:nbDisplay;");
		map.addValue("name", computerName);
		map.addValue("offset", (page - 1) * recordsPerPage);
		map.addValue("nbDisplay", recordsPerPage);
		return npjt.query(select.toString(), map, new ComputerRowMapper());
	}

	/*
	 * Return the number of computers with a specific name in the database
	 */
	public Long countByName(String name) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		String count = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=:name;";
		map.addValue("name", name);
		return npjt.queryForObject(count, map, Long.class);
	}

	/*
	 * Return the list of computers with a specific name and a specific company,
	 * ordered and limited
	 */
	public List<Computer> retrieveByNameAndCompanyName(String computerName,
			String companyName, String orderBy, Integer page,
			Integer recordsPerPage) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		StringBuilder select = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=:name AND company.name=:companyName");
		orderBy = ComputerDAO.selectOrder(orderBy);
		select.append(" ORDER BY ").append(orderBy)
				.append(" LIMIT :offset,:nbDisplay;");
		map.addValue("name", computerName);
		map.addValue("companyName", companyName);
		map.addValue("offset", (page - 1) * recordsPerPage);
		map.addValue("nbDisplay", recordsPerPage);
		return npjt.query(select.toString(), map, new ComputerRowMapper());
	}

	/*
	 * Return the number of computers with a specific name and a specific
	 * company in the database
	 */
	public Long countByNameAndCompanyName(String name, String companyName) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		String count = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE computer.name=:name AND company.name=:companyName";
		map.addValue("name", name);
		map.addValue("companyName", companyName);
		return npjt.queryForObject(count, map, Long.class);
	}

	/*
	 * Return the number of computers with a specific company in the database
	 */
	public Long countByCompanyName(String companyName) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		String count = "SELECT COUNT(*) FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=:companyName ;";
		map.addValue("companyName", companyName);
		return npjt.queryForObject(count, map, Long.class);
	}

	/*
	 * Return the list of computers with a specific company, ordered and limited
	 */

	public List<Computer> retrieveByCompanyName(String companyName,
			String orderBy, Integer page, Integer recordsPerPage) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		StringBuilder select = new StringBuilder(
				"SELECT * FROM `computer-database-db`.`computer` AS computer LEFT OUTER JOIN `computer-database-db`.`company` AS company ON computer.company_id=company.id WHERE company.name=:companyName");
		orderBy = ComputerDAO.selectOrder(orderBy);
		select.append(" ORDER BY ").append(orderBy)
				.append(" LIMIT :offset,:nbDisplay;");
		map.addValue("companyName", companyName);
		map.addValue("offset", (page - 1) * recordsPerPage);
		map.addValue("nbDisplay", recordsPerPage);
		return npjt.query(select.toString(), map, new ComputerRowMapper());
	}

}
