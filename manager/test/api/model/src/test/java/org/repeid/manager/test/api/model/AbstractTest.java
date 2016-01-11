package org.repeid.manager.test.api.model;

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
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.jpa.models.JpaTipoDocumentoProvider;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaFilterModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.apiman.manager.api.jpa.AbstractStorage;
import io.apiman.manager.api.war.WarApiManagerConfig;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public abstract class AbstractTest {

    protected Logger log = LoggerFactory.getLogger(TipoDocumentoProviderTest.class);

    @Deployment
    public static WebArchive createDeployment() {
        File[] dependencies = Maven.resolver().resolve("org.slf4j:slf4j-simple:1.7.10").withoutTransitivity()
                .asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(WarApiManagerConfig.class.getPackage())
                .addPackage(StorageException.class.getPackage())
                
                /** model-api **/
                .addPackage(Provider.class.getPackage()).addPackage(TipoDocumentoModel.class.getPackage())

                .addPackage(ModelException.class.getPackage())
                
                .addPackage(TipoPersona.class.getPackage())

                .addPackage(SearchCriteriaFilterModel.class.getPackage())

                /** model-jpa **/
                .addPackage(AbstractStorage.class.getPackage())
                .addPackage(JpaTipoDocumentoProvider.class.getPackage())
                .addPackage(TipoDocumentoEntity.class.getPackage())

                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml").addAsWebInfResource("test-ds.xml");

        war.addAsLibraries(dependencies);

        return war;
    }
}
