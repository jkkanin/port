package com.ofs.portal.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import com.ofs.portal.config.Constants;

public class RequestFilter implements ClientRequestFilter {

	private String ofsSrvSecurityKey;

	public RequestFilter(String ofsSrvSecurityKey) {
		this.ofsSrvSecurityKey = ofsSrvSecurityKey;
    }

	@Override
    public void filter(ClientRequestContext requestContext) throws IOException {

		List<Object> token = new ArrayList<Object>(1);
		token.add(ofsSrvSecurityKey);
		requestContext.getHeaders().put(Constants.SECURITY_HEADER.toString(), token);
	}
}
