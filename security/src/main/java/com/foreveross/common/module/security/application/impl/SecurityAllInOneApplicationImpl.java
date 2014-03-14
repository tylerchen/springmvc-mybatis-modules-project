/*******************************************************************************
 * Copyright (c) 2014-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.application.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.foreveross.common.module.security.application.SecurityAllInOneApplication;
import com.foreveross.common.module.security.application.SecurityAuthorizationDataApplication;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-2-28
 */
public class SecurityAllInOneApplicationImpl implements
		SecurityAllInOneApplication {

	SecurityAuthorizationDataApplication securityAuthorizationDataApplication;
	Map<String, Object> resourceCache = new HashMap<String, Object>(1024);
	Map<String, Object> userCache = new HashMap<String, Object>(1024);

	public void setResourceCache(Map<String, Object> resourceCache) {
		this.resourceCache = resourceCache;
	}

	public void setUserCache(Map<String, Object> userCache) {
		this.userCache = userCache;
	}

	public void setSecurityAuthorizationDataApplication(
			SecurityAuthorizationDataApplication securityAuthorizationDataApplication) {
		this.securityAuthorizationDataApplication = securityAuthorizationDataApplication;
	}

	public Map<String, Object> findAccountByUsernameInMap(String username) {
		return securityAuthorizationDataApplication
				.findAccountByUsernameInMap(username);
	}

	public List<String> findRoleNameByUsername(String username) {
		return securityAuthorizationDataApplication
				.findRoleNameByUsername(username);
	}

	public Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap() {
		return securityAuthorizationDataApplication
				.findAllResourceNameAndRoleNameInResourceRolesMap();
	}

	//===================================
	@SuppressWarnings("unchecked")
	public Authentication authenticate_AuthenticationProvider(
			Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		Map<String, Object> userDetails = new HashMap<String, Object>();
		{
			Map<String, Object> account = findAccountByUsernameInMap(username);
			if (account == null || account.isEmpty()) {
				throw new UsernameNotFoundException("Username not found.");
			}
			if (!password.equals(encode_PasswordEncoder((String) account
					.get("PASSWORD")))) {
				throw new BadCredentialsException("Password is not correct.");
			}
			userDetails.putAll(account);
		}
		{
			List<GrantedAuthority> gAuthoritys = new ArrayList<GrantedAuthority>();
			for (String role : findRoleNameByUsername(username)) {
				SimpleGrantedAuthority gai = new SimpleGrantedAuthority(role);
				gAuthoritys.add(gai);
			}
			userDetails.put("grantedAuthority", gAuthoritys);
			userCache.put(username, userDetails);
		}
		{
			UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
					userDetails, authentication.getCredentials(),
					(List<GrantedAuthority>) userDetails
							.get("grantedAuthority"));
			result.setDetails(authentication.getDetails());
			return result;
		}
	}

	public boolean supports_AuthenticationProvider(Class<?> paramClass) {
		return true;
	}

	//===================================
	public void afterPropertiesSet_InitializingBean() throws Exception {

	}

	//===================================
	public String encode_PasswordEncoder(String password) {
		return password;
	}

	//===================================org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource
	public Collection<ConfigAttribute> getAllConfigAttributes_FilterInvocationSecurityMetadataSource() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfigAttribute> getAttributes_FilterInvocationSecurityMetadataSource(
			Object object) throws IllegalArgumentException {
		if (resourceCache.isEmpty()) {
			Map<String, List<String>> map = findAllResourceNameAndRoleNameInResourceRolesMap();
			resourceCache.putAll(map);
		}
		String url = ((FilterInvocation) object).getRequestUrl();
		StringBuilder tempUrl = new StringBuilder(url);
		{
			int position = tempUrl.indexOf("?");
			if (position != -1) {
				url = url.substring(0, position);
				tempUrl.delete(position - 1, tempUrl.length());
			}
		}
		Collection<ConfigAttribute> attris = new ArrayList<ConfigAttribute>();
		while (true) {
			List<String> roles = (List<String>) resourceCache.get(tempUrl
					.toString());
			if (roles != null) {
				for (String role : roles) {
					attris.add(new SecurityConfig(role));
				}
				return attris;
			} else {
				if (tempUrl.charAt(tempUrl.length() - 1) == '*') {// process "/*" and "/**" situation
					if (tempUrl.charAt(tempUrl.length() - 2) == '/') {// process "/??/*" -> "/??/**"
						tempUrl.append('*');
						continue;
					} else {// process "/??/**" -> "/**"
						int lastSpash = tempUrl.lastIndexOf("/");
						if (lastSpash > -1
								&& (lastSpash = tempUrl.lastIndexOf("/",
										lastSpash - 1)) > -1) {
							tempUrl.replace(lastSpash + 1, tempUrl.length(),
									"**");
							continue;
						}
					}
				} else {// process "/??/url" -> "/??/*"
					int lastSpash = tempUrl.lastIndexOf("/");
					if (lastSpash > -1) {
						tempUrl.replace(lastSpash + 1, tempUrl.length(), "*");
						continue;
					}
				}
			}
			break;
		}
		if (attris.isEmpty()) {
			attris.add(new SecurityConfig("ROLE_NOBODY"));
		}
		return attris;
	}

	public boolean supports_FilterInvocationSecurityMetadataSource(
			Class<?> clazz) {
		return true;
	}

	//=======================================org.springframework.security.access.intercept.AbstractSecurityInterceptor
	FilterInvocationSecurityMetadataSource securityMetadataSource;

	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	public Class<? extends Object> getSecureObjectClass_AbstractSecurityInterceptor() {
		return FilterInvocation.class;
	}

	public SecurityMetadataSource obtainSecurityMetadataSource_AbstractSecurityInterceptor() {
		return this.securityMetadataSource;
	}

	//=======================================javax.servlet.Filter
	public void destroy_Filter() {
	}

	public void doFilter_Filter(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		//InterceptorStatusToken token = null;
		try {
			//token = super.beforeInvocation(fi);
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			//super.afterInvocation(token, null);
		}
	}

	public void init_Filter(FilterConfig config) throws ServletException {
	}

	//=======================================org.springframework.security.access.AccessDecisionManager
	@SuppressWarnings("unchecked")
	public void decide_AccessDecisionManager(Authentication authentication,
			Object url, Collection<ConfigAttribute> configAttributes) {
		if (configAttributes == null || configAttributes.isEmpty()) {
			return;
		}

		Map<String, Object> userDetails = (Map<String, Object>) authentication
				.getPrincipal();

		if (userDetails == null || userDetails.isEmpty()) {
			throw new AccessDeniedException(MessageFormat.format(
					"Denied to access [{0}][{1}]", url, userDetails
							.get("USERNAME")));
		}
		if ("1".equals(userDetails.get("SUPER"))) {
			return;
		}
		for (ConfigAttribute configAttribute : configAttributes) {
			for (GrantedAuthority gAuthority : (List<GrantedAuthority>) userDetails
					.get("grantedAuthority")) {
				if (configAttribute.getAttribute().trim().equals(
						gAuthority.getAuthority().trim())) {
					return;
				}
			}
		}
		for (ConfigAttribute configAttribute : configAttributes) {
			for (GrantedAuthority gAuthority : authentication.getAuthorities()) {
				if (configAttribute.getAttribute().trim().equals(
						gAuthority.getAuthority().trim())) {
					return;
				}
			}
		}
		throw new AccessDeniedException(MessageFormat.format(
				"Denied to access [{0}]", url));
	}

	public boolean supports_AccessDecisionManager(
			ConfigAttribute configAttribute) {
		return true;
	}

	public boolean supports_AccessDecisionManager(Class<?> clazz) {
		return true;
	}

	public static void main(String[] args) {
		String url = "/a/b/c/d/e?a=1";
		System.out.println(Arrays.toString(url.split("/")));
		System.out.println(url.substring(0, url.lastIndexOf('/', url
				.lastIndexOf('/') - 1)));
		StringBuilder tempUrl = new StringBuilder(url);
		while (true) {
			System.out.println(tempUrl);
			{
				if (tempUrl.charAt(tempUrl.length() - 1) == '*') {// process "/*" and "/**" situation
					if (tempUrl.charAt(tempUrl.length() - 2) == '/') {// process "/*"
						tempUrl.append('*');
						continue;
					} else {// process "/**" situation
						int lastSpash = tempUrl.lastIndexOf("/");
						if (lastSpash > -1
								&& (lastSpash = tempUrl.lastIndexOf("/",
										lastSpash - 1)) > -1) {
							tempUrl.replace(lastSpash + 1, tempUrl.length(),
									"**");
							continue;
						}
					}
				} else {// process "/url" situation
					int lastSpash = tempUrl.lastIndexOf("/");
					if (lastSpash > -1) {
						tempUrl.replace(lastSpash + 1, tempUrl.length(), "*");
						continue;
					}
				}
			}
			break;
		}
	}
}
