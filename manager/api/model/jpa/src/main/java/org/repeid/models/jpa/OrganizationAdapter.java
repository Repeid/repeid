/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.models.jpa;

import java.util.List;

import org.repeid.models.RealmModel;
import org.repeid.models.jpa.entities.OrganizationEntity;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class OrganizationAdapter implements RealmModel, JpaModel<OrganizationEntity> {

    @Override
    public OrganizationEntity getEntity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<String> getDefaultRoles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addDefaultRole(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateDefaultRoles(String[] defaultRoles) {
        // TODO Auto-generated method stub
        
    }
   

}