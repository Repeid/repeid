package org.repeid.models;

import org.repeid.provider.Provider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface NaturalPersonProvider extends Provider {

    void close();
    
}
