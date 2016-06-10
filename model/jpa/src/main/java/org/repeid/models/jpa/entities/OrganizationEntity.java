package org.repeid.models.jpa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Table(name = "ORGANIZATION")
@Entity
public class OrganizationEntity {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY) // we do this because relationships often fetch id, but not entity. This avoids an extra SQL
    protected String id;

    @Column(name = "name", unique = true)
    protected String name;

    @Column(name = "enabled")
    protected boolean enabled;

    @OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "organization")
    protected Collection<DocumentEntity> documents = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "organization")
    protected Collection<NaturalPersonEntity> naturalPersons = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "organization")
    protected Collection<NaturalPersonEntity> legalPersons = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<DocumentEntity> documents) {
        this.documents = documents;
    }

    public Collection<NaturalPersonEntity> getNaturalPersons() {
        return naturalPersons;
    }

    public void setNaturalPersons(Collection<NaturalPersonEntity> naturalPersons) {
        this.naturalPersons = naturalPersons;
    }

    public Collection<NaturalPersonEntity> getLegalPersons() {
        return legalPersons;
    }

    public void setLegalPersons(Collection<NaturalPersonEntity> legalPersons) {
        this.legalPersons = legalPersons;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		OrganizationEntity other = (OrganizationEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
