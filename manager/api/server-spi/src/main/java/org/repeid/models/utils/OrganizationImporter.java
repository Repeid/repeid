package org.repeid.models.utils;

import org.repeid.models.OrganizationModel;
import org.repeid.representations.idm.OrganizationRepresentation;

/**
 * Helper interface used just because RealmManager is in repeid-services and not accessible for ImportUtils
 *
 */
public interface OrganizationImporter {

    OrganizationModel importOrganization(OrganizationRepresentation rep);
}
