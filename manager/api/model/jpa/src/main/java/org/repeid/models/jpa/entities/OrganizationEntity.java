package org.repeid.models.jpa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Table(name = "ORGANIZATION")
@Entity
public class OrganizationEntity {

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY) // we do this because relationships often fetch
                                 // id, but not entity. This avoids an extra SQL
    protected String id;

    @Column(name = "NAME", unique = true)
    protected String name;

    @Column(name = "ENABLED")
    protected boolean enabled;

    @OneToMany(cascade ={CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "organization")
    protected Collection<DocumentEntity> documents = new ArrayList<>();

    @OneToMany(cascade ={CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "organization")
    protected Collection<PersonaNaturalEntity> naturalPersons = new ArrayList<>();

    @OneToMany(cascade ={CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "organization")
    protected Collection<PersonaNaturalEntity> legalPersons = new ArrayList<>();

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

    public Collection<PersonaNaturalEntity> getNaturalPersons() {
        return naturalPersons;
    }

    public void setNaturalPersons(Collection<PersonaNaturalEntity> naturalPersons) {
        this.naturalPersons = naturalPersons;
    }

    public Collection<PersonaNaturalEntity> getLegalPersons() {
        return legalPersons;
    }

    public void setLegalPersons(Collection<PersonaNaturalEntity> legalPersons) {
        this.legalPersons = legalPersons;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof OrganizationEntity))
            return false;

        OrganizationEntity that = (OrganizationEntity) o;

        if (!id.equals(that.getId()))
            return false;

        return true;
    }

}
