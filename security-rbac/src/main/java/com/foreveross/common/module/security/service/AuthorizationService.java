/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.service;

import java.util.List;
import java.util.Map;

import com.foreveross.common.module.security.domain.Account;
import com.foreveross.common.module.security.domain.AccountRole;
import com.foreveross.common.module.security.domain.Resource;
import com.foreveross.common.module.security.domain.RoleResource;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
public interface AuthorizationService {

	boolean containsRole(String roleId, String roleName);

	boolean containsResource(String resourceId, String resourceName);

	boolean containsAccountRole(String username, String roleName);

	boolean containsRoleResource(String roleName, String resourceName);

	List<AccountRole> findUserRoles(String username);

	List<RoleResource> findRoleResources(String roleName);

	List<Resource> findUserResources(String username);

	Account login(String username, String password, String encrypt);

	void addAccountRoles(String username, String[] roleNames);

	void updateAccountRoles(String username, String[] roleNames);

	void addRoleResources(String roleName, String[] resourceNames);

	void updateRoleResources(String roleName, String[] resourceNames);

	Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap();

	Account findAccountByUsername(String username);

	Map<String, Object> findAccountByUsernameInMap(String username);

	List<String> findRoleNameByUsername(String username);
}
