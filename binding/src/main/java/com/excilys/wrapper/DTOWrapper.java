package com.excilys.wrapper;

import java.util.List;

import com.excilys.transfert.CompanyDTO;
import com.excilys.transfert.ComputerDTO;

public class DTOWrapper {
	public static final Integer RECORDS_PER_PAGE = 25;
	private Integer recordsPerPage;
	private Integer nbrOfPages;
	private Integer currentPage;
	private String message;
	private String searchCompany;
	private String searchComputer;
	private String orderBy;
	private Long nbrComputers;
	private ComputerDTO computerDTO;
	private List<CompanyDTO> listCompaniesDTO;
	private List<ComputerDTO> listComputersDTO;

	public static class Builder {
		DTOWrapper dtoWrapper;

		private Builder() {
			dtoWrapper = new DTOWrapper();
		}

		public Builder recordsPerPage(Integer recordsPerPage) {
			if (recordsPerPage != null)
				this.dtoWrapper.recordsPerPage = recordsPerPage;
			return this;
		}

		public Builder nbrOfPages(Integer nbrOfPages) {
			if (nbrOfPages != null)
				this.dtoWrapper.nbrOfPages = nbrOfPages;
			return this;
		}

		public Builder currentPage(Integer currentPage) {
			if (currentPage != null)
				this.dtoWrapper.currentPage = currentPage;
			return this;
		}

		public Builder message(String message) {
			if (message != null)
				this.dtoWrapper.message = message;
			return this;
		}

		public Builder searchCompany(String searchCompany) {
			if (searchCompany != null)
				this.dtoWrapper.searchCompany = searchCompany;
			return this;
		}

		public Builder searchComputer(String searchComputer) {
			if (searchComputer != null)
				this.dtoWrapper.searchComputer = searchComputer;
			return this;
		}

		public Builder orderBy(String orderBy) {
			if (orderBy != null)
				this.dtoWrapper.orderBy = orderBy;
			return this;
		}

		public Builder nbrComputers(Long nbrComputers) {
			if (nbrComputers != null)
				this.dtoWrapper.nbrComputers = nbrComputers;
			return this;
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

	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public Integer getNbrOfPages() {
		return nbrOfPages;
	}

	public void setNbrOfPages(Integer nbrOfPages) {
		this.nbrOfPages = nbrOfPages;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSearchCompany() {
		return searchCompany;
	}

	public void setSearchCompany(String searchCompany) {
		this.searchCompany = searchCompany;
	}

	public String getSearchComputer() {
		return searchComputer;
	}

	public void setSearchComputer(String searchComputer) {
		this.searchComputer = searchComputer;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Long getNbrComputers() {
		return nbrComputers;
	}

	public void setNbrComputers(Long nbrComputers) {
		this.nbrComputers = nbrComputers;
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
		return "DTOWrapper [recordsPerPage=" + recordsPerPage + ", nbrOfPages="
				+ nbrOfPages + ", currentPage=" + currentPage + ", message="
				+ message + ", searchCompany=" + searchCompany
				+ ", searchComputer=" + searchComputer + ", orderBy=" + orderBy
				+ ", nbrComputers=" + nbrComputers + ", computerDTO="
				+ computerDTO + ", listCompaniesDTO=" + listCompaniesDTO
				+ ", listComputersDTO=" + listComputersDTO + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerDTO == null) ? 0 : computerDTO.hashCode());
		result = prime * result
				+ ((currentPage == null) ? 0 : currentPage.hashCode());
		result = prime
				* result
				+ ((listCompaniesDTO == null) ? 0 : listCompaniesDTO.hashCode());
		result = prime
				* result
				+ ((listComputersDTO == null) ? 0 : listComputersDTO.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((nbrComputers == null) ? 0 : nbrComputers.hashCode());
		result = prime * result
				+ ((nbrOfPages == null) ? 0 : nbrOfPages.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result
				+ ((recordsPerPage == null) ? 0 : recordsPerPage.hashCode());
		result = prime * result
				+ ((searchCompany == null) ? 0 : searchCompany.hashCode());
		result = prime * result
				+ ((searchComputer == null) ? 0 : searchComputer.hashCode());
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
		if (currentPage == null) {
			if (other.currentPage != null)
				return false;
		} else if (!currentPage.equals(other.currentPage))
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
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (nbrComputers == null) {
			if (other.nbrComputers != null)
				return false;
		} else if (!nbrComputers.equals(other.nbrComputers))
			return false;
		if (nbrOfPages == null) {
			if (other.nbrOfPages != null)
				return false;
		} else if (!nbrOfPages.equals(other.nbrOfPages))
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (recordsPerPage == null) {
			if (other.recordsPerPage != null)
				return false;
		} else if (!recordsPerPage.equals(other.recordsPerPage))
			return false;
		if (searchCompany == null) {
			if (other.searchCompany != null)
				return false;
		} else if (!searchCompany.equals(other.searchCompany))
			return false;
		if (searchComputer == null) {
			if (other.searchComputer != null)
				return false;
		} else if (!searchComputer.equals(other.searchComputer))
			return false;
		return true;
	}
}