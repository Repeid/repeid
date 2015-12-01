package org.repeid.models.jpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.repeid.models.StoredFileModel;
import org.repeid.models.StoredFileProvider;
import org.repeid.models.jpa.entities.StoredFileEntity;
import org.repeid.models.jpa.qualifiers.Dropbox;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Dropbox
@Named
@Stateless
@Local(StoredFileProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaStoredFileDropboxProvider implements StoredFileProvider {

    private DbxClient client;

    @PersistenceContext
    private EntityManager em;

    public JpaStoredFileDropboxProvider() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public StoredFileModel findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StoredFileModel create(File file) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            DbxEntry.File uploadedFile = client.uploadFile("/magnum-opus.txt", DbxWriteMode.add(),
                    file.length(), inputStream);

            StoredFileEntity storedFileEntity = new StoredFileEntity();
            em.persist(storedFileEntity);
            return new StoredFileAdapter(em, storedFileEntity);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DbxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean remove(String id) {
        // TODO Auto-generated method stub
        return false;
    }

}
