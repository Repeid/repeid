package org.repeid.models;

import java.util.Set;

import org.repeid.provider.ProviderEvent;

public interface OrganizationModel {

	interface OrganizationCreationEvent extends ProviderEvent {
        OrganizationModel getCreatedOrganization();
    }

    interface DocumentCreationEvent extends ProviderEvent {
    	DocumentModel getCreatedDocument();
    	OrganizationModel getOrganization();
    }
    
	String getId();

	String getName();

	void setName(String name);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	
	DocumentModel addDocument(String abbreviation);

	DocumentModel addDocument(String id, String abbreviation);
	
	DocumentModel getDocumentById(String id);
	
	boolean removeDocument(DocumentModel document);

	boolean removeDocumentById(String id);
	
	Set<DocumentModel> getDocuments();
	
}
