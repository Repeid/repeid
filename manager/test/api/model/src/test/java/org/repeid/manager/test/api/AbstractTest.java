package org.repeid.manager.test.api;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.jpa.entities.security.UserEntity;
import org.repeid.manager.api.jpa.models.JpaTipoDocumentoProvider;
import org.repeid.manager.api.jpa.models.security.JpaUserProvider;
import org.repeid.manager.api.model.Model;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.system.RepeidTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public abstract class AbstractTest {

    protected Logger log = LoggerFactory.getLogger(AbstractTest.class);

    @Deployment
    public static WebArchive createDeployment() {
        File[] dependencies = Maven.resolver().resolve("org.slf4j:slf4j-simple:1.7.10").withoutTransitivity()
                .asFile();
        
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                
                .addClass(AbstractTest.class)
                .addClass(CdiFactoryTest.class)
                
                .addPackage(StorageException.class.getPackage())
                
                /**beans*/
                .addPackage(PermissionType.class.getPackage())
                
                /** model-api **/
                .addPackage(Model.class.getPackage())                
                .addPackage(Provider.class.getPackage())
                .addPackage(ModelException.class.getPackage())                
                .addPackage(SearchCriteriaModel.class.getPackage())
                
                .addPackage(UserModel.class.getPackage())
                .addPackage(RepeidTransaction.class.getPackage())
                
                .addPackage(TipoPersona.class.getPackage())                

                /** model-jpa **/
                .addPackage(AbstractJpaStorage.class.getPackage())
                
                .addPackage(TipoDocumentoEntity.class.getPackage())
                .addPackage(JpaTipoDocumentoProvider.class.getPackage())
                
                .addPackage(UserEntity.class.getPackage())                
                .addPackage(JpaUserProvider.class.getPackage())
                
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");

        war.addAsLibraries(dependencies);

        return war;
    }    
    
}
