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

import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class JpaRealmProvider implements OrganizationProvider {

    @Override
    public OrganizationModel createRealm(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel createRealm(String id, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel getRealm(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel getRealmByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeRealm(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }
   
}
