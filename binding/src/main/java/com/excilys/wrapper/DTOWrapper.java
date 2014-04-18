package com.excilys.wrapper;

import java.util.List;

import com.excilys.transfert.CompanyDTO;
import com.excilys.transfert.ComputerDTO;

public class DTOWrapper {
	private ComputerDTO computerDTO;
	private List<CompanyDTO> listCompaniesDTO;
	private List<ComputerDTO> listComputersDTO;

	public static class Builder {
		DTOWrapper dtoWrapper;

		private Builder() {
			dtoWrapper = new DTOWrapper();
		}

		public Builder computerDTO(ComputerDTO computerDTO) {
			if (computerDTO != null)
				this.dtoWrapper.computerDTO = computerDTO;
			return this;
		}

		public Builder listCompaniesDTO(List<CompanyDTO> listCompaniesDTO) {
			if (listCompaniesDTO != null)
				this.dtoWrapper.listCompaniesDTO = listCompaniesDTO;
			return this;
		}

		public Builder listComputersDTO(List<ComputerDTO> listComputersDTO) {
			if (listComputersDTO != null)
				this.dtoWrapper.listComputersDTO = listComputersDTO;
			return this;
		}

		public DTOWrapper build() {
			return this.dtoWrapper;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public ComputerDTO getComputerDTO() {
		return computerDTO;
	}

	public void setComputerDTO(ComputerDTO computerDTO) {
		this.computerDTO = computerDTO;
	}

	public List<CompanyDTO> getListCompaniesDTO() {
		return listCompaniesDTO;
	}

	public void setListCompaniesDTO(List<CompanyDTO> listCompaniesDTO) {
		this.listCompaniesDTO = listCompaniesDTO;
	}

	public List<ComputerDTO> getListComputersDTO() {
		return listComputersDTO;
	}

	public void setListComputersDTO(List<ComputerDTO> listComputersDTO) {
		this.listComputersDTO = listComputersDTO;
	}

	@Override
	public String toString() {
		return "DTOWrapper [computerDTO=" + computerDTO + ", listCompaniesDTO="
				+ listCompaniesDTO + ", listComputersDTO=" + listComputersDTO
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerDTO == null) ? 0 : computerDTO.hashCode());
		result = prime
				* result
				+ ((listCompaniesDTO == null) ? 0 : listCompaniesDTO.hashCode());
		result = prime
				* result
				+ ((listComputersDTO == null) ? 0 : listComputersDTO.hashCode());
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
		DTOWrapper other = (DTOWrapper) obj;
		if (computerDTO == null) {
			if (other.computerDTO != null)
				return false;
		} else if (!computerDTO.equals(other.computerDTO))
			return false;
		if (listCompaniesDTO == null) {
			if (other.listCompaniesDTO != null)
				return false;
		} else if (!listCompaniesDTO.equals(other.listCompaniesDTO))
			return false;
		if (listComputersDTO == null) {
			if (other.listComputersDTO != null)
				return false;
		} else if (!listComputersDTO.equals(other.listComputersDTO))
			return false;
		return true;
	}

}