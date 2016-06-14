package org.repeid.models;

import java.util.List;
import java.util.Map;

import org.repeid.provider.Provider;

public interface NaturalPersonProvider extends Provider {

	NaturalPersonModel addNaturalPerson(OrganizationModel organization, DocumentModel document, String documentNumber);
	
	NaturalPersonModel addNaturalPerson(OrganizationModel organization, String id, DocumentModel document, String documentNumber);
	
	boolean removeNaturalPerson(OrganizationModel organization, NaturalPersonModel person);

	NaturalPersonModel getNaturalPersonById(String id, OrganizationModel organization);

	NaturalPersonModel getNaturalPersonByDocument(DocumentModel document, String documentNumber, OrganizationModel organization);

	// Service account is included for counts
	int getNaturalPersonCount(OrganizationModel organization);

	List<NaturalPersonModel> getNaturalPersons(OrganizationModel organization, int firstResult, int maxResults);

	List<NaturalPersonModel> searchForNaturalPerson(String search, OrganizationModel organization);

	List<NaturalPersonModel> searchForNaturalPerson(String search, OrganizationModel organization, int firstResult, int maxResults);

	List<NaturalPersonModel> searchForNaturalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization);

	List<NaturalPersonModel> searchForNaturalPersonByAttributes(Map<String, String> attributes, OrganizationModel organization, int firstResult, int maxResults);

	void preRemove(OrganizationModel organization);

	void close();

}
