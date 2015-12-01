package org.repeid.models;

import org.repeid.models.enums.StoredFileProviderName;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public interface StoredFileModel extends Model {

    String getId();

    String getFileId();

    void setFileId(String fileId);

    StoredFileProviderName getProvider();

    void setProvider(StoredFileProviderName provider);

    String getUrl();

    void setUrl(String url);
}
