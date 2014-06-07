/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.foreveross.infra.dsl.engine.DSL;
import com.foreveross.infra.util.MapHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
@SuppressWarnings("serial")
public class Account implements com.dayatang.domain.Entity, Serializable {

	protected String id;

	/** 账户名 **/
	protected String username;

	/** 密码 **/
	protected String password;

	/** 真实姓名 **/
	protected String realName;

	/** 电子邮件 **/
	protected String email;

	/** 注册日期 **/
	protected Date registryDate;

	/** 是否被锁定 **/
	protected Integer enabled = 1;

	/** 类型（管理员、审核员） **/
	protected String accountType;

	/** 类别（用于显示的归类） **/
	protected String category;

	/** 最后的密码修改日期 **/
	protected Date passwordLastUpdateDate;

	/** 描述 **/
	protected String description;

	public Account() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Date getPasswordLastUpdateDate() {
		return passwordLastUpdateDate;
	}

	public void setPasswordLastUpdateDate(Date passwordLastUpdateDate) {
		this.passwordLastUpdateDate = passwordLastUpdateDate;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Account get(String id) {
		return DSL.dsl("bean:v1:repositoryService?queryOne", new Object[] {
				"modules-security.Account.findById", id });
	}

	public Account save() {
		if (getId() != null) {
			DSL.dsl("bean:v1:repositoryService?update", new Object[] {
					"modules-security.Account.update", this });
		} else {
			DSL.dsl("bean:v1:repositoryService?save", new Object[] {
					"modules-security.Account.save", this });
		}
		return this;
	}

	public void remove() {
		DSL.dsl("bean:v1:repositoryService?remove", new Object[] {
				"modules-security.Account.remove", this });
	}

	public static Account getByUsername(String username) {
		return DSL.dsl("bean:v1:repositoryService?queryOne", new Object[] {
				"modules-security.Account.findByUsername", username });
	}

	public static Map<String, Object> findAccountByUsernameInMap(String username) {
		return DSL.dsl("bean:v1:repositoryService?queryOne",
				new Object[] {
						"modules-security.Account.findAccountByUsernameInMap",
						username });
	}

	public static Account login(String username, String password, String encrypt) {
		return DSL.dsl("bean:v1:repositoryService?queryOne", new Object[] {
				"modules-security.Account.findByUsernameAndPasswordAndEnabled",
				MapHelper.toMap("username", username, "password", password,
						"enabled", 1) });
	}

	@Override
	public String toString() {
		return "Account [description=" + description + ", email=" + email
				+ ", enabled=" + enabled + ", id=" + id + ", password="
				+ password + ", passwordLastUpdateDate="
				+ passwordLastUpdateDate + ", realName=" + realName
				+ ", registryDate=" + registryDate + ", username=" + username
				+ "]";
	}

}
