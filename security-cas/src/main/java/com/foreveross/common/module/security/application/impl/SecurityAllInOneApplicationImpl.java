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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.foreveross.common.module.security.application.SecurityAllInOneApplication;
import com.foreveross.common.module.security.application.SecurityAuthorizationDataApplication;
import com.foreveross.infra.util.Assert;
import com.foreveross.infra.util.MapHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-2-28
 */
@SuppressWarnings("unchecked")
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

	public UserDetails getUserDetail(Map<?, ?> userInMap,
			Collection<?> grantedAuthorities) {
		CustomUserDetails userDetails = new CustomUserDetails();
		{
			userDetails.userMap = (Map<String, Object>) userInMap;
			userDetails.grantedAuthorities = (Collection<? extends GrantedAuthority>) grantedAuthorities;
		}
		return userDetails;
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
	public Authentication authenticate_AuthenticationProvider(
			Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		UserDetails userDetails = null;
		{
			Map<String, Object> account = findAccountByUsernameInMap(username);
			if (userDetails == null || account.isEmpty()) {
				throw new UsernameNotFoundException("Username not found.");
			}
			if (!password.equals(encode_PasswordEncoder((String) account
					.get("PASSWORD")))) {
				throw new BadCredentialsException("Password is not correct.");
			}
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			for (String role : findRoleNameByUsername(username)) {
				SimpleGrantedAuthority gai = new SimpleGrantedAuthority(role);
				grantedAuthorities.add(gai);
			}
			userDetails = getUserDetail(account, grantedAuthorities);
			userCache.put(username, userDetails);
		}
		{
			UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
					userDetails, authentication.getCredentials(), userDetails
							.getAuthorities());
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
	public void decide_AccessDecisionManager(Authentication authentication,
			Object url, Collection<ConfigAttribute> configAttributes) {
		if (configAttributes == null || configAttributes.isEmpty()) {
			return;
		}

		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof UserDetails)) {
			throw new AccessDeniedException(MessageFormat.format(
					"Denied to access [{0}][{1}]", url, principal));
		}
		UserDetails userDetails = (UserDetails) principal;
		for (ConfigAttribute configAttribute : configAttributes) {
			for (GrantedAuthority gAuthority : userDetails.getAuthorities()) {
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

	//===================================cas
	//===================================org.springframework.security.core.userdetails.UserDetailsService
	//===================================org.springframework.security.core.userdetails.AuthenticationUserDetailsService<CasAssertionAuthenticationToken>
	public UserDetails loadUserDetails_UserDetailsService_AuthenticationUserDetailsService(
			Object param) {
		Assert.notNull(param);
		Assert.notBlank(param.toString());
		String username = param.toString();
		UserDetails userDetails = null;
		{
			Map<String, Object> account = findAccountByUsernameInMap(username);
			if (account == null || account.isEmpty()) {
				return userDetails;
			}
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			for (String role : findRoleNameByUsername(username)) {
				SimpleGrantedAuthority gai = new SimpleGrantedAuthority(role);
				grantedAuthorities.add(gai);
			}
			userDetails = getUserDetail(account, grantedAuthorities);
		}
		return userDetails;
	}

	//===================================org.springframework.security.cas.authentication.CasAuthenticationProvider
	public UserDetails loadUserByAssertion_CasAuthenticationProvider(
			Assertion assertion) {//after CAS has valid
		CasAssertionAuthenticationToken token = new CasAssertionAuthenticationToken(
				assertion, "");
		UserDetails userDetails = loadUserDetails_UserDetailsService_AuthenticationUserDetailsService(token
				.getName());
		// userDetails may be null
		if (userDetails == null) {
			userDetails = getUserDetail(MapHelper.toMap("USERNAME", token
					.getName()), Collections.EMPTY_LIST);
		}
		return userDetails;
	}

	@SuppressWarnings("serial")
	public class CustomUserDetails implements UserDetails {
		Map<String, Object> userMap = new HashMap<String, Object>();
		Collection<? extends GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		public boolean isEnabled() {
			return true;
		}

		public boolean isCredentialsNonExpired() {
			return true;
		}

		public boolean isAccountNonLocked() {
			return true;
		}

		public boolean isAccountNonExpired() {
			return true;
		}

		public String getUsername() {
			return (String) userMap.get("USERNAME");
		}

		public String getPassword() {
			return (String) userMap.get("PASSWORD");
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return grantedAuthorities;
		}
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
