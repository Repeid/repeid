package org.repeid.manager.api.jpa.models;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.StoreConfigurationEntity;
import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoreConfigurationProvider;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ProviderFactory(ProviderType.JPA)
public class JpaStoreConfigurationProvider extends AbstractJpaStorage implements StoreConfigurationProvider {

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public StoreConfigurationModel create(String appKey, String denominacion) {
		if (findByDenominacion(denominacion) != null) {
			throw new ModelDuplicateException(
					"StoreConfigurationEntity denominacion debe ser unico, se encontro otra entidad con denominacion:"
							+ denominacion);
		}

		StoreConfigurationEntity storeConfigurationEntity = new StoreConfigurationEntity();
		storeConfigurationEntity.setAppKey(appKey);
		storeConfigurationEntity.setDenominacion(denominacion);
		storeConfigurationEntity.setCarpetaFirma("firma");
		storeConfigurationEntity.setCarpetaFoto("foto");
		storeConfigurationEntity.setCarpetaRaiz(appKey);
		storeConfigurationEntity.setCarpetaTemporal("tmp");
		storeConfigurationEntity.setDefault(false);
		storeConfigurationEntity.setToken(null);
		getEntityManager().persist(storeConfigurationEntity);
		return new StoreConfigurationAdapter(getEntityManager(), storeConfigurationEntity);
	}

	@Override
	public StoreConfigurationModel findById(String id) {
		StoreConfigurationEntity entity = this.getEntityManager().find(StoreConfigurationEntity.class, id);
		return entity != null ? new StoreConfigurationAdapter(getEntityManager(), entity) : null;
	}

	@Override
	public StoreConfigurationModel findByDenominacion(String denominacion) {
		TypedQuery<StoreConfigurationEntity> query = getEntityManager()
				.createNamedQuery("StoreConfigurationEntity.findByDenominacion", StoreConfigurationEntity.class);
		query.setParameter("denominacion", denominacion);
		List<StoreConfigurationEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un StoreConfigurationEntity con denominacion=" + denominacion + ", results=" + results);
		} else {
			return new StoreConfigurationAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public StoreConfigurationModel getDefaultStoreConfiguration() {
		TypedQuery<StoreConfigurationEntity> query = getEntityManager()
				.createNamedQuery("StoreConfigurationEntity.findByIsDefault", StoreConfigurationEntity.class);
		query.setParameter("isDefault", true);
		List<StoreConfigurationEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un StoreConfigurationEntity con isDefault=" + true + ", results=" + results);
		} else {
			return new StoreConfigurationAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public boolean remove(StoreConfigurationModel storeConfiguration) {
		StoreConfigurationEntity storeConfigurationEntity = getEntityManager().find(StoreConfigurationEntity.class,
				storeConfiguration.getId());
		if (storeConfigurationEntity == null) {
			return false;
		}
		getEntityManager().remove(storeConfigurationEntity);
		return true;
	}

	@Override
	public List<StoreConfigurationModel> getAll() {
		TypedQuery<StoreConfigurationEntity> query = getEntityManager()
				.createNamedQuery("StoreConfigurationEntity.findAll", StoreConfigurationEntity.class);

		List<StoreConfigurationEntity> entities = query.getResultList();
		List<StoreConfigurationModel> models = new ArrayList<StoreConfigurationModel>();
		for (StoreConfigurationEntity storeConfigurationEntity : entities) {
			models.add(new StoreConfigurationAdapter(getEntityManager(), storeConfigurationEntity));
		}
		return models;
	}

}
