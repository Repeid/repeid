package org.repeid.models.jpa.entities;

import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "NATURAL_PERSON")
@NamedQueries({
    @NamedQuery(name="getAllNaturalPersonsByOrganization", query="select p from NaturalPersonEntity p where p.organization.id = :organizationId order by p.firstName"),    
    @NamedQuery(name="searchForNaturalPerson", query="select p from NaturalPersonEntity p where p.organization.id = :organizationId and " +
            "( lower(p.firstName) like :search or lower(concat(p.firstName, ' ', p.lastName)) like :search or p.documentNumber like :search ) order by p.firstName"),    
    @NamedQuery(name="getOrganizationNaturalPersonById", query="select p from NaturalPersonEntity p where p.organization.id = :id and p.organization.id = :organizationId"),    
    @NamedQuery(name="getOrganizationNaturalPersonByLastName", query="select p from NaturalPersonEntity p where p.lastName = :lastName and p.organization.id = :organizationId"),    
    @NamedQuery(name="getOrganizationNaturalPersonByFirstLastName", query="select p from NaturalPersonEntity p where p.firstName = :first and p.lastName = :last and p.organization.id = :organizationId"),
    @NamedQuery(name="getOrganizationNaturalPersonCount", query="select count(p) from NaturalPersonEntity p where p.organization.id = :organizationId"),    
    @NamedQuery(name="deleteNaturalPersonsByOrganization", query="delete from NaturalPersonEntity p where p.organization.id = :organizationId")
})
public class NaturalPersonEntity extends PersonEntity {

	@Id
	@Access(AccessType.PROPERTY) // we do this because relationships often fetch
									// id, but not entity. This avoids an extra
									// SQL
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	protected String id;

	@Column(name = "first_name")
	protected String firstName;

	@Column(name = "middle_name")
	protected String middleName;

	@Column(name = "last_name")
	protected String lastName;

	@Column(name = "date_birth")
	protected LocalDate dateBirth;

	@Column(name = "gender")
	protected String gender;

	@Column(name = "marriage_status")
	protected String marriageStatus;

	@Column(name = "job")
	protected String job;

	public NaturalPersonEntity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateBirth() {
		return dateBirth;
	}

	public void setDatebirth(LocalDate dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		NaturalPersonEntity other = (NaturalPersonEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
