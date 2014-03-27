package com.excilys.transfert;

public class CompanyDTO {
	protected Long id;
	protected String name;

	public static class Builder {

		CompanyDTO companyDTO;

		private Builder() {
			companyDTO = new CompanyDTO();
		}

		public Builder id(Long id) {
			if (id != null)
				this.companyDTO.id = id;
			return this;
		}

		public Builder name(String name) {
			this.companyDTO.name = name;
			return this;
		}

		public CompanyDTO build() {
			return this.companyDTO;
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CompanyDTO other = (CompanyDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
