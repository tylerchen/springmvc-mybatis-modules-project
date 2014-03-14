/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.dayatang.querychannel.service.QueryService;
import com.foreveross.common.module.security.domain.*;
import com.foreveross.common.module.security.service.AuthorizationService;
import com.foreveross.infra.util.Assert;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
@Named("authorizationService")
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

	@Inject
	QueryService queryService;

	public boolean containsRole(String roleId, String roleName) {
		return Role.containsRole(roleId, roleName);
	}

	public boolean containsResource(String resourceId, String resourceName) {
		return Resource.containsResource(resourceId, resourceName);
	}

	public boolean containsAccountRole(String username, String roleName) {
		return AccountRole.containsAccountRole(username, roleName);
	}

	public boolean containsRoleResource(String roleName, String resourceName) {
		return RoleResource.containsRoleResource(roleName, resourceName);
	}

	public List<AccountRole> findUserRoles(String username) {
		return AccountRole.findByUsername(username);
	}

	public List<RoleResource> findRoleResources(String roleName) {
		return RoleResource.findByRoleName(roleName);
	}

	public List<Resource> findUserResources(String username) {
		return Resource.findByUsername(username);
	}

	public Account login(String username, String password, String encrypt) {
		return Account.login(username, password, encrypt);
	}

	public void addAccountRoles(String username, String[] roleNames) {
		Assert.notEmpty(username, "Parameter username is required!");
		if (roleNames == null || roleNames.length == 0) {
			return;
		}
		Account account = Account.getByUsername(username);
		Assert.notNull(account, String.format("Account %s is not exists!",
				username));
		for (String roleName : roleNames) {
			Role role = Role.getByName(roleName);
			Assert.notNull(role, String.format("Role %s is not exists!",
					roleName));
			if (AccountRole.containsAccountRole(account.getId(), role.getId())) {
				continue;
			}
			AccountRole accountRole = new AccountRole();
			{
				accountRole.setRoleId(role.getId());
				accountRole.setAccountId(account.getId());
			}
			accountRole.save();
		}
	}

	public void updateAccountRoles(String username, String[] roleNames) {
		Assert.notEmpty(username, "Parameter username is required!");
		AccountRole.deleteAccountRoleByUsername(username);
		if (roleNames == null || roleNames.length == 0) {
			return;
		}
		Account account = Account.getByUsername(username);
		Assert.notNull(account, String.format("Account %s is not exists!",
				username));
		for (String roleName : roleNames) {
			Role role = Role.getByName(roleName);
			Assert.notNull(role, String.format("Role %s is not exists!",
					roleName));
			if (AccountRole.containsAccountRole(account.getId(), role.getId())) {
				continue;
			}
			AccountRole accountRole = new AccountRole();
			{
				accountRole.setRoleId(role.getId());
				accountRole.setAccountId(account.getId());
			}
			accountRole.save();
		}
	}

	public void addRoleResources(String roleName, String[] resourceNames) {
		Assert.notEmpty(roleName, "Parameter roleName is required!");
		if (resourceNames == null || resourceNames.length == 0) {
			return;
		}
		Role role = Role.getByName(roleName);
		Assert.notNull(role, String.format("Role %s is not exists!", roleName));
		for (String resourceName : resourceNames) {
			Resource resource = Resource.getByName(resourceName);
			Assert.notNull(resource, String.format(
					"Resource %s is not exists!", resourceName));
			if (RoleResource.containsRoleResource(role.getId(), resource
					.getId())) {
				continue;
			}
			RoleResource roleResource = new RoleResource();
			{
				roleResource.setRoleId(role.getId());
				roleResource.setResourceId(resource.getId());
			}
			roleResource.save();
		}
	}

	public void updateRoleResources(String roleName, String[] resourceNames) {
		Assert.notEmpty(roleName, "Parameter roleName is required!");
		RoleResource.deleteRoleResourceByRoleName(roleName);
		if (resourceNames == null || resourceNames.length == 0) {
			return;
		}
		Role role = Role.getByName(roleName);
		Assert.notNull(role, String.format("Role %s is not exists!", roleName));
		for (String resourceName : resourceNames) {
			Resource resource = Resource.getByName(resourceName);
			Assert.notNull(resource, String.format(
					"Resource %s is not exists!", resourceName));
			if (RoleResource.containsRoleResource(role.getId(), resource
					.getId())) {
				continue;
			}
			RoleResource roleResource = new RoleResource();
			{
				roleResource.setRoleId(role.getId());
				roleResource.setResourceId(resource.getId());
			}
			roleResource.save();
		}
	}

	public Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap() {
		return RoleResource.findAllResourceNameAndRoleNameInResourceRolesMap();
	}

	public Account findAccountByUsername(String username) {
		return Account.getByUsername(username);
	}

	public Map<String, Object> findAccountByUsernameInMap(String username) {
		return Account.findAccountByUsernameInMap(username);
	}

	public List<String> findRoleNameByUsername(String username) {
		return AccountRole.findRoleNameByUsername(username);
	}
}
