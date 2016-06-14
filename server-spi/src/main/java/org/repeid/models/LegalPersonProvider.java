package org.repeid.models;

import java.util.List;
import java.util.Map;

import org.repeid.provider.Provider;

public interface LegalPersonProvider extends Provider {

	LegalPersonModel addLegalPerson(OrganizationModel organization, String id, String name);

	LegalPersonModel addLegalPerson(OrganizationModel organization, String name);

	boolean removeLegalPerson(OrganizationModel organization, LegalPersonModel person);

	LegalPersonModel getLegalPersonById(String id, OrganizationModel organization);

	LegalPersonModel getLegalPersonByDocument(DocumentModel document, String documentNumber, OrganizationModel organization);

	// Service account is included for counts
	int getLegalPersonCount(OrganizationModel organization);

	List<LegalPersonModel> getLegalPersons(OrganizationModel organization, int firstResult, int maxResults);

	List<LegalPersonModel> searchForLegalPerson(String search, OrganizationModel organization);

	List<LegalPersonModel> searchForLegalPerson(String search, OrganizationModel organization, int firstResult, int maxResults);

	List<LegalPersonModel> searchForLegalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization);

	List<LegalPersonModel> searchForLegalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization, int firstResult, int maxResults);

	void preRemove(OrganizationModel organization);

	void close();

}
