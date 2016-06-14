package org.repeid.models;

import java.util.List;
import java.util.Set;

import org.repeid.migration.MigrationModel;
import org.repeid.provider.Provider;

public interface OrganizationProvider extends Provider {
	// Note: The reason there are so many query methods here is for layering a
	// cache on top of an persistent RepeidSession
	MigrationModel getMigrationModel();

	OrganizationModel createOrganization(String name);

	OrganizationModel createOrganization(String id, String name);

	OrganizationModel getOrganization(String id);
	
	OrganizationModel getOrganizationByName(String name);

	

	DocumentModel addOrganizationDocument(OrganizationModel organization, String abbreviation);

	DocumentModel addOrganizationDocument(OrganizationModel organization, String id, String abbreviation);

	DocumentModel getOrganizationDocument(OrganizationModel organization, String abbreviation);

	DocumentModel getDocumentById(String id, OrganizationModel organization);
	
	Set<DocumentModel> getOrganizationDocuments(OrganizationModel organization);
		
	boolean removeDocument(OrganizationModel organization, DocumentModel document);
	
	
	
	List<OrganizationModel> getOrganizations();

	boolean removeOrganization(String id);

	void close();

}
