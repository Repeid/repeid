package org.repeid.models;

import org.repeid.models.enums.PersonType;

public interface DocumentModel {

	String getId();

	String getAbbreviature();

	void setAbbreviature(String abbreviature);

	String getName();

	void setName(String name);

	int getSize();

	void setSize(int size);

	PersonType getPersonType();

	void setPersonType(PersonType personType);

	boolean isEnabled();

}
