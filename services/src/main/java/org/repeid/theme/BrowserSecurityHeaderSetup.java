package org.repeid.theme;

import org.repeid.models.BrowserSecurityHeaders;
import org.repeid.models.OrganizationModel;

import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

public class BrowserSecurityHeaderSetup {

	public static Response.ResponseBuilder headers(Response.ResponseBuilder builder, OrganizationModel organization) {
		return headers(builder, /* organization.getBrowserSecurityHeaders() */new HashMap<String, String>());
	}

	public static Response.ResponseBuilder headers(Response.ResponseBuilder builder, Map<String, String> headers) {
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			String headerName = BrowserSecurityHeaders.headerAttributeMap.get(entry.getKey());
			if (headerName != null && entry.getValue() != null && entry.getValue().length() > 0) {
				builder.header(headerName, entry.getValue());
			}
		}
		return builder;
	}

}
