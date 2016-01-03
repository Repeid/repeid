/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.manager.api.rest.contract.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.BooleanUtils;
import org.repeid.manager.api.rest.contract.ISystemResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;

import io.apiman.common.util.MediaType;
import io.apiman.manager.api.beans.download.DownloadBean;
import io.apiman.manager.api.beans.download.DownloadType;
import io.apiman.manager.api.beans.system.SystemStatusBean;
import io.apiman.manager.api.config.Version;
import io.apiman.manager.api.core.IDownloadManager;
import io.apiman.manager.api.core.IStorage;
import io.apiman.manager.api.core.exceptions.StorageException;
import io.apiman.manager.api.security.ISecurityContext;

/**
 * Implementation of the System API.
 *
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class SystemResourceImpl implements ISystemResource {

    @Inject
    private IStorage storage;
    @Inject
    private ISecurityContext securityContext;
    @Inject
    private Version version;

    @Inject
    private IDownloadManager downloadManager;

    @Context
    private HttpServletRequest request;

    /**
     * Constructor.
     */
    public SystemResourceImpl() {
    }

    /**
     * @see org.repeid.manager.api.rest.contract.ISystemResource#getStatus()
     */
    @Override
    public SystemStatusBean getStatus() {
        SystemStatusBean rval = new SystemStatusBean();
        rval.setId("apiman-manager-api"); //$NON-NLS-1$
        rval.setName("API Manager REST API"); //$NON-NLS-1$
        rval.setDescription(
                "The API Manager REST API is used by the API Manager UI to get stuff done.  You can use it to automate any apiman task you wish.  For example, create new Organizations, Plans, Applications, and Services."); //$NON-NLS-1$
        rval.setMoreInfo("http://www.apiman.io/latest/api-manager-restdocs.html"); //$NON-NLS-1$
        rval.setUp(getStorage() != null);
        if (getVersion() != null) {
            rval.setVersion(getVersion().getVersionString());
            rval.setBuiltOn(getVersion().getVersionDate());
        }
        return rval;
    }

    /**
     * @see org.repeid.manager.api.rest.contract.ISystemResource#exportData(java.lang.String)
     */
    @Override
    public Response exportData(String download) {
        if (BooleanUtils.toBoolean(download)) {
            try {
                DownloadBean dbean = downloadManager.createDownload(DownloadType.exportJson,
                        "/system/export"); //$NON-NLS-1$
                return Response.ok(dbean, MediaType.APPLICATION_JSON).build();
            } catch (StorageException e) {
                throw new SystemErrorException(e);
            }
        } else {
            if (!securityContext.isAdmin())
                throw ExceptionFactory.notAuthorizedException();
            return exportData();
        }
    }

    /**
     * @see org.repeid.manager.api.rest.contract.ISystemResource#exportData()
     */
    @Override
    public Response exportData() {
        return null;
    }

    /**
     * @see org.repeid.manager.api.rest.contract.ISystemResource#importData()
     */
    @Override
    public Response importData() {
        return null;
    }

    /**
     * @return the storage
     */
    public IStorage getStorage() {
        return storage;
    }

    /**
     * @param storage
     *            the storage to set
     */
    public void setStorage(IStorage storage) {
        this.storage = storage;
    }

    /**
     * @return the version
     */
    public Version getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * @return the securityContext
     */
    public ISecurityContext getSecurityContext() {
        return securityContext;
    }

    /**
     * @param securityContext
     *            the securityContext to set
     */
    public void setSecurityContext(ISecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
