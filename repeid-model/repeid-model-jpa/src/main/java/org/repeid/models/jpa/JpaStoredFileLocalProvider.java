package org.repeid.models.jpa;

import java.io.File;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.repeid.models.ModelDuplicateException;
import org.repeid.models.StoredFileModel;
import org.repeid.models.StoredFileProvider;
import org.repeid.models.jpa.entities.PersonaNaturalEntity;
import org.repeid.models.jpa.entities.StoredFileEntity;
import org.repeid.models.jpa.entities.TipoDocumentoEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Default
@Named
@Stateless
@Local(StoredFileProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaStoredFileLocalProvider implements StoredFileProvider {

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
    public StoredFileModel upload(byte[] file) {
        StoredFileEntity storedFileEntity = new StoredFileEntity();
        storedFileEntity.setFileId(null);
        storedFileEntity.setUrl(null);
        storedFileEntity.setStoreConfiguration(storeConfiguration);
        storedFileEntity.set
        em.persist(storedFileEntity);
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
