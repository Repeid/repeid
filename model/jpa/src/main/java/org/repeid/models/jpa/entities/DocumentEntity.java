package org.repeid.models.jpa.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "DOCUMENT")
@NamedQueries({
    @NamedQuery(name="getOrganizationDocuments", query="select document from DocumentEntity document where document.organization = :organization"),
    @NamedQuery(name="getOrganizationDocumentIds", query="select document.id from DocumentEntity document where document.organization.id = :organization"),
    @NamedQuery(name="getOrganizationDocumentByAbbreviation", query="select document from DocumentEntity document where document.abbreviation = :abbreviation and document.organization.id = :organization"),
    @NamedQuery(name="getOrganizationDocumentIdByAbbreviation", query="select document.id from DocumentEntity document where document.abbreviation = :abbreviation and document.organization.id = :organization")
})
public class DocumentEntity {

	@Id
	@Access(AccessType.PROPERTY)// we do this because relationships often fetch id, but not entity. This avoids an extra SQL
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@NaturalId(mutable = true)
	@Column(name = "abbreviation")
	private String abbreviation;

	@Column(name = "name")
	private String name;

	@Column(name = "size")
	private int size;

	@Column(name = "person_type")
	private String personType;

	@Type(type = "org.hibernate.type.TrueFalseType")
	@Column(name = "enabled")
	private boolean enabled;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id", foreignKey = @ForeignKey)
	private OrganizationEntity organization;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
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
		DocumentEntity other = (DocumentEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
