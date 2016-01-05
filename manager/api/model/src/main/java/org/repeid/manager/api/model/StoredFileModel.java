package org.repeid.manager.api.model;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public interface StoredFileModel extends Model {

    String getId();

    String getFileId();

    void setFileId(String fileId);

    String getUrl();

    void setUrl(String url);

    StoreConfigurationModel getStoreConfiguration();

}
