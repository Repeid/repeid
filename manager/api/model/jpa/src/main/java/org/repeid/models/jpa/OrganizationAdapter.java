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

import org.jboss.logging.Logger;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.OrganizationEntity;

import javax.persistence.EntityManager;

public class OrganizationAdapter implements OrganizationModel, JpaModel<OrganizationEntity> {

    protected static final Logger logger = Logger.getLogger(OrganizationAdapter.class);
    protected OrganizationEntity organization;
    protected EntityManager em;

    protected RepeidSession session;

    public OrganizationAdapter(RepeidSession session, EntityManager em, OrganizationEntity realm) {
        this.session = session;
        this.em = em;
        this.organization = organization;
    }

    @Override
    public OrganizationEntity getEntity() {
        return organization;
    }

    @Override
    public String getId() {
       return organization.getId();
    }

    @Override
    public String getName() {
        return organization.getName();
    }

    @Override
    public void setName(String name) {
        organization.setName(name);
    }

    @Override
    public boolean isEnabled() {
        return organization.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        organization.setEnabled(enabled);
    }

}
