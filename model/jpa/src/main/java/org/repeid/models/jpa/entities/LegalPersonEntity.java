package org.repeid.models.jpa.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LEGAL_PERSON")
@NamedQueries({
    @NamedQuery(name="getAllLegalPersonsByOrganization", query="select p from LegalPersonEntity p where p.id = :organizationId order by p.name"),    
    @NamedQuery(name="searchForLegalPerson", query="select p from LegalPersonEntity p where p.id = :organizationId and " +
            "( lower(p.name) like :search or p.documentNumber like :search ) order by p.name"),
    @NamedQuery(name="getOrganizationLegalPersonById", query="select p from LegalPersonEntity p where p.id = :id and p.id = :organizationId"),    
    @NamedQuery(name="getOrganizationLegalPersonByName", query="select p from LegalPersonEntity p where p.name = :name and p.id = :organizationId"),    
    @NamedQuery(name="getOrganizationLegalPersonCount", query="select count(p) from LegalPersonEntity p where p.id = :organizationId"),    
    @NamedQuery(name="deleteLegalPersonsByOrganization", query="delete from LegalPersonEntity p where p.id = :organizationId")
})
public class LegalPersonEntity extends PersonEntity {

	@Id
	@Access(AccessType.PROPERTY)// we do this because relationships often fetch id, but not entity. This avoids an extra SQL
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	public LegalPersonEntity() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		LegalPersonEntity other = (LegalPersonEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
