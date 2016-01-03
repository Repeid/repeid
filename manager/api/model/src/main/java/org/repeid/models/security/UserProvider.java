package org.repeid.models.security;

import javax.ejb.Local;

import org.repeid.provider.Provider;

@Local
public interface UserProvider extends Provider {

    UserModel findById(String id);

}
