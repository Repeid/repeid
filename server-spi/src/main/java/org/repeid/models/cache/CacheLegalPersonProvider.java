package org.repeid.models.cache;

import org.repeid.models.LegalPersonProvider;
import org.repeid.models.OrganizationProvider;

public interface CacheLegalPersonProvider extends LegalPersonProvider {

    void clear();

    LegalPersonProvider getDelegate();

}
