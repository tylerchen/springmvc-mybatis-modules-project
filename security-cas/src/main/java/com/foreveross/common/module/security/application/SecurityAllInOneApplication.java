/*******************************************************************************
 * Copyright (c) 2014-2-28 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.application;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-2-28
 */

public interface SecurityAllInOneApplication {

	//
	Map<String, Object> findAccountByUsernameInMap(String username);

	List<String> findRoleNameByUsername(String username);

	Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap();

	/*org.springframework.security.authentication.AuthenticationProvider*/
	Authentication authenticate_AuthenticationProvider(
			Authentication paramAuthentication) throws AuthenticationException;

	boolean supports_AuthenticationProvider(Class<?> paramClass);

	/*org.springframework.security.authentication.AuthenticationProvider ==END*/

	/*org.springframework.beans.factory.InitializingBean*/
	void afterPropertiesSet_InitializingBean() throws Exception;

	/*org.springframework.beans.factory.InitializingBean ==END*/

	/*PasswordEncoder*/
	String encode_PasswordEncoder(String password);

	/*PasswordEncoder ==END*/

	/*org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource*/
	Collection<ConfigAttribute> getAllConfigAttributes_FilterInvocationSecurityMetadataSource();

	Collection<ConfigAttribute> getAttributes_FilterInvocationSecurityMetadataSource(
			Object object) throws IllegalArgumentException;

	boolean supports_FilterInvocationSecurityMetadataSource(Class<?> clazz);

	Class<? extends Object> getSecureObjectClass_AbstractSecurityInterceptor();

	SecurityMetadataSource obtainSecurityMetadataSource_AbstractSecurityInterceptor();

	void destroy_Filter();

	void doFilter_Filter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException;

	void init_Filter(FilterConfig config) throws ServletException;

	/*org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource ==END*/

	/*org.springframework.security.access.AccessDecisionManager*/
	void decide_AccessDecisionManager(Authentication authentication,
			Object url, Collection<ConfigAttribute> configAttributes);

	boolean supports_AccessDecisionManager(ConfigAttribute configAttribute);

	boolean supports_AccessDecisionManager(Class<?> clazz);

	/*org.springframework.security.access.AccessDecisionManager ==END*/

	/* CAS */
	/*org.springframework.security.core.userdetails.UserDetailsService*/
	/*org.springframework.security.core.userdetails.AuthenticationUserDetailsService<CasAssertionAuthenticationToken>*/
	UserDetails loadUserDetails_UserDetailsService_AuthenticationUserDetailsService(
			Object param);

	/*org.springframework.security.cas.authentication.CasAuthenticationProvider*/
	UserDetails loadUserByAssertion_CasAuthenticationProvider(
			Assertion assertion);
	/* CAS ==END*/
}
