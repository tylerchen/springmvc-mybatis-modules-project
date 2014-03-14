/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.application.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dayatang.querychannel.service.QueryService;
import com.foreveross.common.module.security.application.AuthorizationApplication;
import com.foreveross.common.module.security.domain.*;
import com.foreveross.common.module.security.vo.*;
import com.foreveross.infra.util.Assert;
import com.foreveross.infra.util.BeanHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
@Named("authorizationApplication")
public class AuthorizationApplicationImpl implements AuthorizationApplication {

	@Inject
	QueryService queryService;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean containsRole(String roleId, String roleName) {
		return Role.containsRole(roleId, roleName);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean containsResource(String resourceId, String resourceName) {
		return Resource.containsResource(resourceId, resourceName);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean containsAccountRole(String username, String roleName) {
		return AccountRole.containsAccountRole(username, roleName);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean containsRoleResource(String roleName, String resourceName) {
		return RoleResource.containsRoleResource(roleName, resourceName);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<AccountRoleVO> findUserRoles(String username) {
		List<AccountRole> list = AccountRole.findByUsername(username);
		List<AccountRoleVO> vos = new ArrayList<AccountRoleVO>();
		for (AccountRole ar : list) {
			vos.add(BeanHelper.copyProperties(AccountRoleVO.class, ar));
		}
		return vos;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<RoleResourceVO> findRoleResources(String roleName) {
		List<RoleResource> list = RoleResource.findByRoleName(roleName);
		List<RoleResourceVO> vos = new ArrayList<RoleResourceVO>();
		for (RoleResource rr : list) {
			vos.add(BeanHelper.copyProperties(RoleResourceVO.class, rr));
		}
		return vos;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ResourceVO> findUserResources(String username) {
		List<Resource> list = Resource.findByUsername(username);
		List<ResourceVO> vos = new ArrayList<ResourceVO>();
		for (Resource r : list) {
			vos.add(BeanHelper.copyProperties(ResourceVO.class, r));
		}
		return vos;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AccountVO login(String username, String password, String encrypt) {
		Account account = Account.login(username, password, encrypt);
		return BeanHelper.copyProperties(AccountVO.class, account);
	}

	@Transactional(propagation = Propagation.REQUIRED)
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

	@Transactional(propagation = Propagation.REQUIRED)
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

	@Transactional(propagation = Propagation.REQUIRED)
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

	@Transactional(propagation = Propagation.REQUIRED)
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

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap() {
		return RoleResource.findAllResourceNameAndRoleNameInResourceRolesMap();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AccountVO findAccountByUsername(String username) {
		Account account = Account.getByUsername(username);
		return BeanHelper.copyProperties(AccountVO.class, account);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> findAccountByUsernameInMap(String username) {
		return Account.findAccountByUsernameInMap(username);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> findRoleNameByUsername(String username) {
		return AccountRole.findRoleNameByUsername(username);
	}
}
