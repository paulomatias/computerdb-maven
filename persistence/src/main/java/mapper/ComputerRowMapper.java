package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Company company = Company.builder().id(rs.getLong(5))
				.name(rs.getString(7)).build();

		Computer computer = new Computer();
		computer.setId(new Long(rs.getLong(1)));
		computer.setName(rs.getString(2));
		if (rs.getDate(3) != null) {
			computer.setIntroduced(new DateTime(rs.getDate(3)));
		}
		if (rs.getDate(4) != null) {
			computer.setDiscontinued(new DateTime(rs.getDate(4)));
		}
		computer.setCompany(company);

		return computer;
	}

}
