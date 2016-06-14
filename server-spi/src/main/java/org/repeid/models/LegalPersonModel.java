package org.repeid.models;

public interface LegalPersonModel extends PersonModel {

	String NAME = "name";

	String getId();

	String getName();

	void setName(String name);

}
