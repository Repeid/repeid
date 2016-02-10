/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.test.api;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import org.repeid.common.util.SystemEnvProperties;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.core.config.RepeidApplication;
import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.jpa.entities.security.UserEntity;
import org.repeid.manager.api.jpa.models.JpaTipoDocumentoProvider;
import org.repeid.manager.api.jpa.models.security.JpaUserProvider;
import org.repeid.manager.api.model.Model;
import org.repeid.manager.api.model.box.DropboxProvider;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.system.RepeidTransaction;
import org.repeid.manager.api.war.WarCdiModelFactory;
import org.repeid.manager.api.war.WarCdiModelSecurityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
//@UsingDataSet("empty.xml")
public abstract class AbstractTest {

    protected Logger log = LoggerFactory.getLogger(AbstractTest.class);

    @Deployment
    public static WebArchive createDeployment() {    	
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                
                .addClass(AbstractTest.class)
                .addClass(WarCdiModelFactory.class)
                .addClass(WarCdiModelSecurityFactory.class)
                
                /**common-utils*/
                .addPackage(SystemEnvProperties.class.getPackage())
                
                /**core*/
                .addPackage(RepeidApplication.class.getPackage())
                
                /**beans*/
                .addPackage(PermissionType.class.getPackage())
                .addPackage(StorageException.class.getPackage())
                
                /** model-api **/
                .addPackage(Model.class.getPackage())                
                .addPackage(Provider.class.getPackage())
                .addPackage(ModelException.class.getPackage())                
                .addPackage(SearchCriteriaModel.class.getPackage())
                
                .addPackage(UserModel.class.getPackage())
                .addPackage(RepeidTransaction.class.getPackage())
                
                .addPackage(TipoPersona.class.getPackage())
                
                .addPackage(DropboxProvider.class.getPackage())

                /** model-jpa **/
                .addPackage(AbstractJpaStorage.class.getPackage())
                
                .addPackage(TipoDocumentoEntity.class.getPackage())
                .addPackage(JpaTipoDocumentoProvider.class.getPackage())
                
                .addPackage(UserEntity.class.getPackage())                
                .addPackage(JpaUserProvider.class.getPackage())                             
                
                /**Config**/
                .addAsResource("META-INF/repeid-server.json", "META-INF/repeid-server.json")               
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                
                .addAsManifestResource("META-INF/jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                
                .addAsWebInfResource("test-ds.xml");

        return war;
    }    
    
}
