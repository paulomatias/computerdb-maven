package com.excilys.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	@Column(name = "computer")
	protected Long computerId;
	@Column(name = "name_computer")
	protected String nameComputer;
	@Column(name = "kind_of_change")
	protected String kindOfChange;
	@Column(name = "time")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	protected DateTime time;

	public static class Builder {
		Log log;

		private Builder() {
			log = new Log();
		}

		public Builder id(Long id) {
			if (id != null)
				this.log.id = id;
			return this;
		}

		public Builder computerId(Long computerId) {
			if (computerId != null)
				this.log.computerId = computerId;
			return this;
		}

		public Builder nameComputer(String nameComputer) {
			if (nameComputer != null)
				this.log.nameComputer = nameComputer;
			return this;
		}

		public Builder kindOfChange(String kindOfChange) {
			if (kindOfChange != null)
				this.log.kindOfChange = kindOfChange;
			return this;
		}

		public Builder time(DateTime time) {
			if (time != null)
				this.log.time = time;
			return this;
		}

		public Log build() {
			return this.log;
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

	public Long getComputerId() {
		return computerId;
	}

	public void setComputerId(Long computerId) {
		this.computerId = computerId;
	}

	public String getNameComputer() {
		return nameComputer;
	}

	public void setNameComputer(String nameComputer) {
		this.nameComputer = nameComputer;
	}

	public String getKindOfChange() {
		return kindOfChange;
	}

	public void setKindOfChange(String kindOfChange) {
		this.kindOfChange = kindOfChange;
	}

	public DateTime getTime() {
		return time;
	}

	public void setTime(DateTime time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerId == null) ? 0 : computerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kindOfChange == null) ? 0 : kindOfChange.hashCode());
		result = prime * result
				+ ((nameComputer == null) ? 0 : nameComputer.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Log other = (Log) obj;
		if (computerId == null) {
			if (other.computerId != null)
				return false;
		} else if (!computerId.equals(other.computerId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kindOfChange == null) {
			if (other.kindOfChange != null)
				return false;
		} else if (!kindOfChange.equals(other.kindOfChange))
			return false;
		if (nameComputer == null) {
			if (other.nameComputer != null)
				return false;
		} else if (!nameComputer.equals(other.nameComputer))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", computerId=" + computerId
				+ ", nameComputer=" + nameComputer + ", kindOfChange="
				+ kindOfChange + ", time=" + time + "]";
	}

}
