package org.repeid.models;

import java.time.LocalDate;
import java.util.Date;

import org.repeid.models.enums.Gender;
import org.repeid.models.enums.MaritalStatus;

public interface NaturalPersonModel extends PersonModel {

	String FIRST_NAME = "firstName";
	String LAST_NAME = "lastName";
	String DOCUMENT_NUMBER = "documentNumber";

	String getId();

	String getFirstName();

	void setFirstName(String firstName);

	String getMiddleName();

	void setMiddleName(String middleName);

	String getLastName();

	void setLastName(String lastName);

	LocalDate getDateBirth();

	void setDateBirth(LocalDate date);

	Gender getGender();

	void setGender(Gender gender);

	MaritalStatus getMarriageStatus();

	void setMarriageStatus(MaritalStatus marriageStatus);

	String getJob();

	void setJob(String job);
}
