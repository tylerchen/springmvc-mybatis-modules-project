/*******************************************************************************
 * Copyright (c) 2014-3-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.step123.spring.security;

import com.foreveross.infra.step123.Step123;

/**
 * <pre>
 * A Custom Spring Security implement needs [authenticationManager, accessDecisionManager, securityMetadataSource] and [user, role, authority] data.
 * 1. UserRoleAuthorityService, to find the user, role, authority
 * 2. Spring Security init will invoke [AccessDecisionManager.supports, SecurityMetadataSource.supports]
 * 3. securityMetadataSource, call order[supports, getAllConfigAttributes, getAttributes]
 * </pre>
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-14
 */
public interface SpringSecurityStep123 extends Step123 {

	/**
	 * 
	 * @param username java.lang.String
	 * @return         java.util.Map<String, Object>
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step01_UserRoleAuthorityService_findAccountByUsernameInMap(
			Object username);

	/**
	 * 
	 * @param username java.lang.String
	 * @return         java.util.List<String>
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step02_UserRoleAuthorityService_findRoleNameByUsername(
			Object username);

	/**
	 * 
	 * @return java.util.Map<String, List<String>>
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step03_UserRoleAuthorityService_findAllResourceNameAndRoleNameInResourceRolesMap();

	/**
	 * In Security init, to determine whether the AccessDecisionManager is available.
	 * @param object could be instance of org.springframework.security.access.ConfigAttribute or java.lang.Class
	 * @return return true
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step04_AccessDecisionManager_supports(Object object);

	/**
	 * In Security init, to determine whether the SecurityMetadataSource is available.
	 * @param object java.lang.Class
	 * @return return true
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step05_SecurityMetadataSource_supports(Object object);

	/**
	 * In Security init, return all ConfigAttributes
	 * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>, return null
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step06_SecurityMetadataSource_getAllConfigAttributes();

	/**
	 * After CAS validation, this step will not execute before CAS validation.
	 * @param assertion org.jasig.cas.client.validation.Assertion
	 * @return org.springframework.security.core.userdetails.UserDetails
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step07_CasAuthenticationProvider_loadUserByAssertion(Object assertion);

	/**
	 * After CAS validation, this step will not execute before CAS validation.
	 * @param param java.lang.Object
	 * @return      org.springframework.security.core.userdetails.UserDetails
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step08_UserDetailsService_AuthenticationUserDetailsService_loadUserDetails(
			Object param);

	/**
	 * 
	 * @param object
	 * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	Object step09_SecurityMetadataSource_getAttributes(Object object);

	/**
	 * 
	 * @param authentication    org.springframework.security.core.Authentication
	 * @param url               java.lang.Object
	 * @param configAttributes  java.util.Collection<org.springframework.security.access.ConfigAttribute>
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	void step10_AccessDecisionManager_decide(Object authentication, Object url,
			Object configAttributes);

}
