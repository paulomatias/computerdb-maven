package com.excilys.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.domain.Computer;

@Component
public class LogDAO {
	private static Logger logger = LoggerFactory.getLogger(LogDAO.class);
	@Autowired
	private JdbcTemplate jt;
	@Autowired
	private NamedParameterJdbcTemplate npjt;

	public void setLog(Computer computer, String operation) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		String create = new String(
				"INSERT INTO `computer-database-db`.`log` (computer,name_computer,kind_of_change) VALUES (:computerId,:computerName,:operation)");
		map.addValue("computerId", computer.getId());
		map.addValue("computerName", computer.getName());
		map.addValue("operation", operation);
		npjt.update(create, map);
	}
}
