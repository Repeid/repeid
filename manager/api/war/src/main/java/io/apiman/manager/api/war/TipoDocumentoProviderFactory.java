package io.apiman.manager.api.war;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;

import org.repeid.manager.api.jpa.models.JpaTipoDocumentoProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.mongo.models.MongoTipoDocumentoProvider;

import io.apiman.manager.api.core.config.Config;

@ApplicationScoped
public interface TipoDocumentoProviderFactory {

	@Produces
	public static TipoDocumentoProvider provideTipoDocumentoProvider(Config.Scope config,
			@New JpaTipoDocumentoProvider jpaProvider, @New MongoTipoDocumentoProvider mongoProvider) {
		if (config.get("provider").equalsIgnoreCase("jpa")) {
			return jpaProvider;
		} else if (config.get("provider").equalsIgnoreCase("mongo")) {
			return mongoProvider;
		} else {
			throw new RuntimeException("Unknown tipoDocumentoProvider type: " + config.get("provider"));
		}
	}

}
