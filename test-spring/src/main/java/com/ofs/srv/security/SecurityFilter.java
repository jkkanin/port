package com.ofs.srv.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ofs.srv.config.Constants;

@WebFilter(filterName = "securityFilter", urlPatterns = "/*", description = "Request Security Filter")
public class SecurityFilter extends OncePerRequestFilter {

	private SecurityHelper securityHelper;

	@Override
	public void initFilterBean() {

		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		this.securityHelper = ctx.getBean(SecurityHelper.class);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

		try {

			if (!securityHelper.validateSignature(request.getHeader(Constants.SECURITY_HEADER.toString()))) {

				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Service authentication failed.");
				return;
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "The Security Server experienced an internal error.");
			return;
		}

		filterChain.doFilter(request, response);
	}
}
