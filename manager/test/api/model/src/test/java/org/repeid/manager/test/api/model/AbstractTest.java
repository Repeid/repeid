package org.repeid.manager.test.api.model;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import org.junit.runner.RunWith;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.DefaultJpaConnectionProvider;
import org.repeid.manager.api.jpa.entities.security.UserEntity;
import org.repeid.manager.api.jpa.models.JpaAccionistaProvider;
import org.repeid.manager.api.jpa.models.JpaPersonaJuridicaProvider;
import org.repeid.manager.api.jpa.models.JpaPersonaNaturalProvider;
import org.repeid.manager.api.jpa.models.JpaTipoDocumentoProvider;
import org.repeid.manager.api.jpa.models.security.JpaRoleProvider;
import org.repeid.manager.api.jpa.models.security.JpaUserProvider;
import org.repeid.manager.api.model.Model;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.system.RepeidTransaction;
import org.repeid.manager.api.mongo.entities.TipoDocumentoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public abstract class AbstractTest {

    protected Logger log = LoggerFactory.getLogger(TipoDocumentoProviderTest.class);

    @Deployment
    public static WebArchive createDeployment() {
        File[] dependencies = Maven.resolver().resolve("org.slf4j:slf4j-simple:1.7.10").withoutTransitivity()
                .asFile();

        BeansDescriptor beansXml = Descriptors.create(BeansDescriptor.class);
        
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                //.addPackage(WarApiManagerConfig.class.getPackage())
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
                .addAsWebInfResource("test-ds.xml")
                
                .addAsManifestResource(
                        new StringAsset(beansXml.getOrCreateAlternatives()
                                .clazz(DefaultJpaConnectionProvider.class.getName())
                                
                                .clazz(JpaTipoDocumentoProvider.class.getName())
                                .clazz(JpaPersonaNaturalProvider.class.getName())
                                .clazz(JpaPersonaJuridicaProvider.class.getName())
                                .clazz(JpaAccionistaProvider.class.getName())
                                .clazz(JpaUserProvider.class.getName())
                                .clazz(JpaRoleProvider.class.getName())
                                .up().exportAsString()),
                        beansXml.getDescriptorName());

        war.addAsLibraries(dependencies);

        return war;
    }    
    
}
