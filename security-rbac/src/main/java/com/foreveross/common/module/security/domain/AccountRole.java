/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.domain;

import java.io.Serializable;
import java.util.List;

import com.dayatang.domain.InstanceFactory;
import com.dayatang.querychannel.service.RepositoryService;
import com.foreveross.infra.util.Assert;
import com.foreveross.infra.util.MapHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
@SuppressWarnings("serial")
public class AccountRole implements com.dayatang.domain.Entity, Serializable {

	protected String accountId;
	protected String roleId;

	public AccountRole() {
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	private static RepositoryService getRepository() {
		return InstanceFactory.getInstance(RepositoryService.class);
	}

	public static AccountRole get(String accountId, String roleId) {
		return getRepository().queryOne(
				"modules-security.AccountRole.findById",
				MapHelper.toMap("accountId", accountId, "roleId", roleId));
	}

	public void save() {
		Assert.isTrue(!containsAccountRole(getAccountId(), getRoleId()), String
				.format("AccountId:%s, RoleId:%s is exists!", getAccountId(),
						getRoleId()));
		getRepository().save(
				"modules-security.AccountRole.save",
				MapHelper.toMap("accountId", getAccountId(), "roleId",
						getRoleId()));
	}

	public static boolean containsAccountRole(String accountId, String roleId) {
		Assert.notEmpty(accountId, "Parameter accountId is required!");
		Assert.notEmpty(roleId, "Parameter roleId is required!");
		return get(accountId, roleId) != null;
	}

	public static void deleteAccountRoleByRoleId(String roleId) {
		Assert.notEmpty(roleId, "Parameter roleId is required!");
		getRepository().remove(
				"modules-security.AccountRole.deleteAccountRoleByRoleId",
				roleId);
	}

	public static void deleteAccountRoleByAccountId(String accountId) {
		Assert.notEmpty(accountId, "Parameter accountId is required!");
		getRepository().remove(
				"modules-security.AccountRole.deleteAccountRoleByAccountId",
				accountId);
	}

	public static void deleteAccountRoleByUsername(String username) {
		Assert.notEmpty(username, "Parameter username is required!");
		getRepository().remove(
				"modules-security.AccountRole.deleteAccountRoleByUsername",
				username);
	}

	public static List<AccountRole> findByAccountId(String accountId) {
		return getRepository().queryList(
				"modules-security.AccountRole.findByAccountId", accountId);
	}

	public static List<AccountRole> findByUsername(String username) {
		return getRepository().queryList(
				"modules-security.AccountRole.findByUsername", username);
	}

	public static List<String> findRoleNameByUsername(String username) {
		return getRepository()
				.queryList(
						"modules-security.AccountRole.findRoleNameByUsername",
						username);
	}

	@Override
	public String toString() {
		return "AccountRole [accountId=" + accountId + ", roleId=" + roleId
				+ "]";
	}
}
