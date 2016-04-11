package org.repeid.services.resources.admin.impl;

import javax.ws.rs.core.HttpHeaders;

import org.repeid.services.resources.admin.AdminRoot;
import org.repeid.services.resources.admin.MaestroResource;
import org.repeid.services.resources.admin.PersonasResource;
import org.repeid.services.resources.admin.ServerInfoAdminResource;
import org.repeid.services.resources.admin.TiposDocumentoResource;

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
