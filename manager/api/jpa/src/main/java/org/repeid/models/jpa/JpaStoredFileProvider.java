package org.repeid.models.jpa;

import java.util.Locale;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.repeid.models.StoreConfigurationModel;
import org.repeid.models.StoredFileModel;
import org.repeid.models.StoredFileProvider;
import org.repeid.models.enums.StoreFileProviderName;
import org.repeid.models.jpa.entities.FileEntity;
import org.repeid.models.jpa.entities.StoreConfigurationEntity;
import org.repeid.models.jpa.entities.StoredFileEntity;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxRequestConfig;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Default
@Named
@Stateless
@Local(StoredFileProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaStoredFileProvider implements StoredFileProvider {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public StoredFileModel findById(String id) {
		StoredFileEntity storedFileEntity = this.em.find(StoredFileEntity.class, id);
		return storedFileEntity != null ? new StoredFileAdapter(em, storedFileEntity) : null;
	}

	@Override
	public StoredFileModel create(byte[] file, StoreConfigurationModel configuration) {
		StoreFileProviderName provider = configuration.getProviderName();
		switch (provider) {
		case localhost:
			return createLocalFile(file, configuration);
		case Dropbox:
			return createDropboxFile(file, configuration);
		case GoogleDrive:
			return createGoogleDriveFile(file, configuration);
		default:
			return null;
		}
	}

	private StoredFileModel createLocalFile(byte[] file, StoreConfigurationModel configuration) {
		// File storage
		FileEntity fileEntity = new FileEntity();
		fileEntity.setFile(file);
		em.persist(fileEntity);

		// Store configuration entity
		StoreConfigurationEntity storeConfigurationEntity = StoreConfigurationAdapter
				.toStoreConfigurationEntity(configuration, em);

		// Create StoreFileEntity
		StoredFileEntity storedFileEntity = new StoredFileEntity();
		storedFileEntity.setFileId(UUID.randomUUID().toString());
		storedFileEntity.setUrl(UUID.randomUUID().toString());
		storedFileEntity.setStoreConfiguration(storeConfigurationEntity);

		em.persist(storedFileEntity);
		return new StoredFileAdapter(em, storedFileEntity);
	}

	private StoredFileModel createDropboxFile(byte[] file, StoreConfigurationModel configuration) {
		// File storage
		DbxRequestConfig config = new DbxRequestConfig(configuration.getAppKey(), Locale.getDefault().toString());
		DbxClient dbxClient = new DbxClient(config, configuration.getToken());
		DropboxProvider dropboxProvider = new DropboxProvider(dbxClient);
		DbxEntry.File fileEntity = dropboxProvider.upload(file);

		// Store configuration entity
		StoreConfigurationEntity storeConfigurationEntity = StoreConfigurationAdapter
				.toStoreConfigurationEntity(configuration, em);

		// Create StoreFileEntity
		StoredFileEntity storedFileEntity = new StoredFileEntity();
		storedFileEntity.setFileId(fileEntity.name);
		storedFileEntity.setUrl(fileEntity.path);
		storedFileEntity.setStoreConfiguration(storeConfigurationEntity);

		em.persist(storedFileEntity);
		return new StoredFileAdapter(em, storedFileEntity);
	}

	private StoredFileModel createGoogleDriveFile(byte[] file, StoreConfigurationModel configuration) {
		return null;
	}

	@Override
	public byte[] download(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(StoredFileModel storedFile) {
		// TODO Auto-generated method stub
		return false;
	}

}
