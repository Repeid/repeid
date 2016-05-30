package org.repeid.models.cache;

import org.repeid.models.LegalPersonProvider;
import org.repeid.models.NaturalPersonProvider;
import org.repeid.models.OrganizationProvider;

public interface CacheNaturalPersonProvider extends NaturalPersonProvider {

    void clear();

    NaturalPersonProvider getDelegate();

}
