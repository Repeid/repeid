package org.repeid.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.repeid.common.config.ConfigFactory;
import org.repeid.common.config.ConfigFileConfiguration;
import org.repeid.common.config.CryptLookup;
import org.repeid.common.config.EnvLookup;
import org.repeid.common.config.SystemPropertiesConfiguration;
import org.repeid.common.config.VaultLookup;
import org.repeid.manager.api.jpa.IJpaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.apiman.manager.api.core.config.ApiManagerConfig;
import io.apiman.manager.api.war.WarApiManagerConfig;

@RunWith(Arquillian.class)
public class IJpaPropertiesTest {

	@Deployment
	public static WebArchive createDeployment() {
		File[] dependencies = Maven.resolver().resolve("org.slf4j:slf4j-simple:1.7.10").withoutTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addClass(IJpaProperties.class)
				.addClass(WarApiManagerConfig.class).addClass(ApiManagerConfig.class).addClass(ConfigFactory.class)
				.addClass(SystemPropertiesConfiguration.class).addClass(ConfigFileConfiguration.class)
				.addClass(CryptLookup.class).addClass(EnvLookup.class).addClass(VaultLookup.class)

				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml").addAsWebInfResource("test-ds.xml");

		war.addAsLibraries(dependencies);

		return war;
	}

	protected Logger log = LoggerFactory.getLogger(IJpaPropertiesTest.class);

	@Inject
	private IJpaProperties iJpaProperties;

	@Test
	public void test() {
		assertThat(iJpaProperties, is(notNullValue()));
	}

}
