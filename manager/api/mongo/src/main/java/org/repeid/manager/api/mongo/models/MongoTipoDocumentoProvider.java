package org.repeid.manager.api.mongo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.mongo.AbstractMongoStorage;
import org.repeid.manager.api.mongo.entities.MongoPersonaJuridicaEntity;
import org.repeid.manager.api.mongo.entities.MongoPersonaNaturalEntity;
import org.repeid.manager.api.mongo.entities.MongoTipoDocumentoEntity;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ProviderFactory(ProviderType.MONGO)
public class MongoTipoDocumentoProvider extends AbstractMongoStorage implements TipoDocumentoProvider {

	private static final String ABREVIATURA = "abreviatura";
	private static final String DENOMINACION = "denominacion";
	private static final String TIPO_PERSONA = "tipoPersona";
	private static final String ESTADO = "estado";

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public TipoDocumentoModel create(String abreviatura, String denominacion, int cantidadCaracteres,
			TipoPersona tipoPersona) throws StorageException {
		if (findByAbreviatura(abreviatura) != null) {
			throw new ModelDuplicateException(
					"MongoTipoDocumentoEntity abreviatura debe ser unico, se encontro otra entidad con abreviatura:"
							+ abreviatura);
		}

		MongoTipoDocumentoEntity tipoDocumentoEntity = new MongoTipoDocumentoEntity();
		tipoDocumentoEntity.setAbreviatura(abreviatura);
		tipoDocumentoEntity.setDenominacion(denominacion);
		tipoDocumentoEntity.setCantidadCaracteres(cantidadCaracteres);
		tipoDocumentoEntity.setTipoPersona(tipoPersona.toString());
		tipoDocumentoEntity.setEstado(true);
		super.create(tipoDocumentoEntity);
		return new TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity);
	}

	@Override
	public TipoDocumentoModel findByAbreviatura(String abreviatura) throws StorageException {
		TypedQuery<MongoTipoDocumentoEntity> query = getEntityManager()
				.createNamedQuery("MongoTipoDocumentoEntity.findByAbreviatura", MongoTipoDocumentoEntity.class);
		query.setParameter("abreviatura", abreviatura);
		List<MongoTipoDocumentoEntity> results = executeTypedQuery(query);
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un TipoDocumentoEntity con abreviatura=" + abreviatura + ", results=" + results);
		} else {
			return new TipoDocumentoAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public TipoDocumentoModel findById(String id) throws StorageException {
		MongoTipoDocumentoEntity entity = get(id, MongoTipoDocumentoEntity.class);
		return entity != null ? new TipoDocumentoAdapter(getEntityManager(), entity) : null;
	}

	@Override
	public boolean remove(TipoDocumentoModel tipoDocumentoModel) throws StorageException {
		TypedQuery<MongoPersonaNaturalEntity> query1 = getEntityManager()
				.createNamedQuery("MongoPersonaNaturalEntity.findByTipoDocumento", MongoPersonaNaturalEntity.class);
		query1.setParameter("tipoDocumento", tipoDocumentoModel.getAbreviatura());
		query1.setMaxResults(1);
		if (!executeTypedQuery(query1).isEmpty()) {
			return false;
		}

		TypedQuery<MongoPersonaJuridicaEntity> query2 = getEntityManager()
				.createNamedQuery("MongoPersonaJuridicaEntity.findByTipoDocumento", MongoPersonaJuridicaEntity.class);
		query2.setParameter("tipoDocumento", tipoDocumentoModel.getAbreviatura());
		query2.setMaxResults(1);
		if (!executeTypedQuery(query2).isEmpty()) {
			return false;
		}

		MongoTipoDocumentoEntity tipoDocumentoEntity = get(tipoDocumentoModel.getId(), MongoTipoDocumentoEntity.class);
		if (tipoDocumentoEntity == null) {
			return false;
		}
		delete(tipoDocumentoEntity);
		return true;
	}

	@Override
	public List<TipoDocumentoModel> getAll() throws StorageException {
		return getAll(-1, -1);
	}

	@Override
	public List<TipoDocumentoModel> getAll(int firstResult, int maxResults) throws StorageException {
		TypedQuery<MongoTipoDocumentoEntity> query = getEntityManager().createNamedQuery("TipoDocumentoEntity.findAll",
				MongoTipoDocumentoEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<MongoTipoDocumentoEntity> entities = query.getResultList();
		List<TipoDocumentoModel> models = new ArrayList<TipoDocumentoModel>();
		for (MongoTipoDocumentoEntity tipoDocumentoEntity : entities) {
			models.add(new TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity));
		}
		return models;
	}

	@Override
	public List<TipoDocumentoModel> search(String filterText) throws StorageException {
		return search(filterText, -1, -1);
	}

	@Override
	public List<TipoDocumentoModel> search(String filterText, int firstResult, int maxResults) throws StorageException {
		TypedQuery<MongoTipoDocumentoEntity> query = getEntityManager()
				.createNamedQuery("MongoTipoDocumentoEntity.findByFilterText", MongoTipoDocumentoEntity.class);
		query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<MongoTipoDocumentoEntity> entities = executeTypedQuery(query);
		List<TipoDocumentoModel> models = new ArrayList<TipoDocumentoModel>();
		for (MongoTipoDocumentoEntity tipoDocumentoEntity : entities) {
			models.add(new TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity));
		}

		return models;
	}

	@Override
	public List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes) throws StorageException {
		return searchByAttributes(attributes, -1, -1);
	}

	@Override
	public List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes, int firstResult, int maxResults)
			throws StorageException {
		StringBuilder builder = new StringBuilder("SELECT t FROM MongoTipoDocumentoEntity");
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attribute = null;
			String parameterName = null;
			if (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) {
				attribute = "lower(t.abreviatura)";
				parameterName = MongoTipoDocumentoProvider.ABREVIATURA;
			} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
				attribute = "lower(t.denominacion)";
				parameterName = MongoTipoDocumentoProvider.DENOMINACION;
			}

			if (attribute != null) {
				builder.append(" and ");
				builder.append(attribute).append(" like :").append(parameterName);
			} else {
				if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
					attribute = "lower(t.tipoPersona)";
					parameterName = MongoTipoDocumentoProvider.TIPO_PERSONA;
				} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
					attribute = "t.estado";
					parameterName = MongoTipoDocumentoProvider.ESTADO;
				}

				if (attribute == null) {
					continue;
				}
				builder.append(" and ");
				builder.append(attribute).append(" = :").append(parameterName);
			}
		}
		builder.append(" order by t.abreviatura");
		String q = builder.toString();
		TypedQuery<MongoTipoDocumentoEntity> query = getEntityManager().createQuery(q, MongoTipoDocumentoEntity.class);
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String parameterName = null;
			if (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) {
				parameterName = MongoTipoDocumentoProvider.ABREVIATURA;
			} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
				parameterName = MongoTipoDocumentoProvider.DENOMINACION;
			}

			if (parameterName != null) {
				query.setParameter(parameterName, "%" + String.valueOf(entry.getValue()).toLowerCase() + "%");
			} else {
				if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
					parameterName = MongoTipoDocumentoProvider.TIPO_PERSONA;
				} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
					parameterName = MongoTipoDocumentoProvider.ESTADO;
				}

				if (parameterName == null) {
					continue;
				}
				query.setParameter(parameterName, entry.getValue());
			}
		}
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<MongoTipoDocumentoEntity> results = query.getResultList();
		List<TipoDocumentoModel> tipoDocumentos = new ArrayList<TipoDocumentoModel>();
		for (MongoTipoDocumentoEntity entity : results)
			tipoDocumentos.add(new TipoDocumentoAdapter(getEntityManager(), entity));
		return tipoDocumentos;
	}

	@Override
	public SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria) throws StorageException {
		SearchResultsModel<MongoTipoDocumentoEntity> entityResult = find(criteria, MongoTipoDocumentoEntity.class);

		SearchResultsModel<TipoDocumentoModel> modelResult = new SearchResultsModel<>();
		List<TipoDocumentoModel> list = new ArrayList<>();
		for (MongoTipoDocumentoEntity entity : entityResult.getModels()) {
			list.add(new TipoDocumentoAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

	@Override
	public SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria, String filterText)
			throws StorageException {
		SearchResultsModel<MongoTipoDocumentoEntity> entityResult = findFullText(criteria, MongoTipoDocumentoEntity.class,
				filterText, ABREVIATURA, DENOMINACION);

		SearchResultsModel<TipoDocumentoModel> modelResult = new SearchResultsModel<>();
		List<TipoDocumentoModel> list = new ArrayList<>();
		for (MongoTipoDocumentoEntity entity : entityResult.getModels()) {
			list.add(new TipoDocumentoAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

}
