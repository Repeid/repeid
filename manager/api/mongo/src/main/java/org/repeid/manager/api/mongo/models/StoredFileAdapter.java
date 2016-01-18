package org.repeid.manager.api.mongo.models;

import javax.persistence.EntityManager;

import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoredFileModel;
import org.repeid.manager.api.mongo.entities.MongoStoreConfigurationEntity;
import org.repeid.manager.api.mongo.entities.MongoStoredFileEntity;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class StoredFileAdapter implements StoredFileModel {

	private MongoStoredFileEntity fileStoreEntity;
	private EntityManager em;

	public StoredFileAdapter(EntityManager em, MongoStoredFileEntity storedFileEntity) {
		this.em = em;
		this.fileStoreEntity = storedFileEntity;
	}

	public static MongoStoredFileEntity toStoredFileEntity(StoredFileModel model, EntityManager em) {
		if (model instanceof StoredFileAdapter) {
			return ((StoredFileAdapter) model).getStoredFileEntity();
		}
		return em.getReference(MongoStoredFileEntity.class, model.getId());
	}

	public MongoStoredFileEntity getStoredFileEntity() {
		return fileStoreEntity;
	}

	@Override
	public void commit() {
		em.merge(fileStoreEntity);
	}

	@Override
	public String getId() {
		return fileStoreEntity.getId();
	}

	@Override
	public String getFileId() {
		return fileStoreEntity.getFileId();
	}

	@Override
	public void setFileId(String fileId) {
		fileStoreEntity.setFileId(fileId);
	}

	@Override
	public String getUrl() {
		return fileStoreEntity.getUrl();
	}

	@Override
	public void setUrl(String url) {
		fileStoreEntity.setUrl(url);
	}

	@Override
	public StoreConfigurationModel getStoreConfiguration() {
		MongoStoreConfigurationEntity storeConfigurationEntity = fileStoreEntity.getStoreConfiguration();
		return new StoreConfigurationAdapter(em, storeConfigurationEntity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StoredFileModel))
			return false;
		StoredFileModel other = (StoredFileModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
