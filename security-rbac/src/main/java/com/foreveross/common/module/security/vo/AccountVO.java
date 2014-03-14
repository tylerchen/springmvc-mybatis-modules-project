/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
@SuppressWarnings("serial")
public class AccountVO implements Serializable {

	protected String id;

	// 账户名
	protected String username;

	// 密码
	protected String password;

	// 真实姓名
	protected String realName;

	// 电子邮件
	protected String email;

	// 注册日期
	protected Date registryDate;

	// 是否被锁定
	protected Integer enabled = 1;

	// 最后的密码修改日期
	protected Date passwordLastUpdateDate;

	// 描述
	protected String description;

	public AccountVO() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AccountVO [description=" + description + ", email=" + email
				+ ", enabled=" + enabled + ", id=" + id + ", password="
				+ password + ", passwordLastUpdateDate="
				+ passwordLastUpdateDate + ", realName=" + realName
				+ ", registryDate=" + registryDate + ", username=" + username
				+ "]";
	}

}
