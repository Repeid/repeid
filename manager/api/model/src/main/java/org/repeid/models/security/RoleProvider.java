package org.repeid.models.security;

import java.util.List;

public interface RoleProvider {
    
    RoleModel create();
    
    RoleModel findById(String rolId);

    boolean delete(RoleModel rol);

    List<RoleModel> getAll();
    
}
