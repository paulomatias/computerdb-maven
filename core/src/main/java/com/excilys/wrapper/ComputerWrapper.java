package com.excilys.wrapper;

import java.util.List;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;

public class ComputerWrapper {
	private Computer computer;
	private Company company;
	private List<Company> listCompanies;
	private List<Computer> listComputers;

	public static class Builder {
		ComputerWrapper computerWrapper;

		private Builder() {
			computerWrapper = new ComputerWrapper();
		}

		public Builder computer(Computer computer) {
			if (computer != null)
				this.computerWrapper.computer = computer;
			return this;
		}

		public Builder company(Company company) {
			if (company != null)
				this.computerWrapper.company = company;
			return this;
		}

		public Builder listComputers(List<Computer> listComputers) {
			if (listComputers != null)
				this.computerWrapper.listComputers = listComputers;
			return this;
		}

		public Builder listCompanies(List<Company> listCompanies) {
			if (listCompanies != null)
				this.computerWrapper.listCompanies = listCompanies;
			return this;
		}

		public ComputerWrapper build() {
			return this.computerWrapper;
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Company> getListCompanies() {
		return listCompanies;
	}

	public void setListCompanies(List<Company> listCompanies) {
		this.listCompanies = listCompanies;
	}

	public List<Computer> getListComputers() {
		return listComputers;
	}

	public void setListComputers(List<Computer> listComputers) {
		this.listComputers = listComputers;
	}

	@Override
	public String toString() {
		return "ComputerWrapper [computer=" + computer + ", company=" + company
				+ ", listCompanies=" + listCompanies + ", listComputers="
				+ listComputers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((computer == null) ? 0 : computer.hashCode());
		result = prime * result
				+ ((listCompanies == null) ? 0 : listCompanies.hashCode());
		result = prime * result
				+ ((listComputers == null) ? 0 : listComputers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComputerWrapper other = (ComputerWrapper) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (computer == null) {
			if (other.computer != null)
				return false;
		} else if (!computer.equals(other.computer))
			return false;
		if (listCompanies == null) {
			if (other.listCompanies != null)
				return false;
		} else if (!listCompanies.equals(other.listCompanies))
			return false;
		if (listComputers == null) {
			if (other.listComputers != null)
				return false;
		} else if (!listComputers.equals(other.listComputers))
			return false;
		return true;
	}

}
