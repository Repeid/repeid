package org.repeid.manager.api.rest.impl;

import javax.ws.rs.core.HttpHeaders;

import org.repeid.manager.api.rest.AdminRoot;
import org.repeid.manager.api.rest.ServerInfoAdminResource;
import org.repeid.manager.api.rest.admin.MaestroResource;
import org.repeid.manager.api.rest.admin.PersonasResource;
import org.repeid.manager.api.rest.admin.TiposDocumentoResource;

/**
 * Root resource for admin console and admin REST API
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */

public class AdminRootImpl implements AdminRoot {

    @Override
    public TiposDocumentoResource getTiposDocumentoResource(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersonasResource getPersonasResource(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MaestroResource getUtilsResource(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServerInfoAdminResource getServerInfo(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

}
