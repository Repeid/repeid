package org.repeid.manager.api.jpa.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.TipoDocumentoProviderFactory;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.model.system.RepeidSessionFactory;

import io.apiman.manager.api.core.config.Config.Scope;
import io.apiman.manager.api.jpa.AbstractStorage;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class JpaTipoDocumentoProviderFactory implements TipoDocumentoProviderFactory {

	@Override
	public TipoDocumentoProvider create(RepeidSession session) {
		EntityManager em = session.getProvider(TipoDocumentoProvider.class).getEntityManager();
		return new JpaUserProvider(session, em);
	}

	@Override
	public void init(Scope config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postInit(RepeidSessionFactory factory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		return "jpa";
	}

}
