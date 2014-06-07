/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.domain;

import java.io.Serializable;

import com.foreveross.infra.dsl.engine.DSL;
import com.foreveross.infra.util.Assert;
import com.foreveross.infra.util.MapHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
@SuppressWarnings("serial")
public class Role implements com.dayatang.domain.Entity, Serializable {

	protected String id;

	/** 角色名称 **/
	protected String name;

	/** 别名 **/
	protected String alias;

	/** 状态 **/
	protected Integer enabled = 1;

	/** 类型（管理员角色、审核员角色） **/
	protected String roleType;

	/** 类别（用于显示的归类） **/
	protected String category;

	/** 描述 **/
	protected String description;

	public Role() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
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

	public Role save() {
		if (containsRole(getId(), getName())) {
			throw new RuntimeException(String.format("Role name %s is exists!",
					getName()));
		}
		if (getId() != null) {
			DSL.dsl("bean:v1:repositoryService?update", new Object[] {
					"modules-security.Role.update", this });
		} else {
			DSL.dsl("bean:v1:repositoryService?save", new Object[] {
					"modules-security.Role.save", this });
		}
		return this;
	}

	public void remove() {
		AccountRole.deleteAccountRoleByRoleId(getId());
		RoleResource.deleteRoleResourceByRoleId(getId());
		DSL.dsl("bean:v1:repositoryService?remove", new Object[] {
				"modules-security.Role.remove", getId() });
	}

	public static boolean containsRole(String roleId, String roleName) {
		Assert.notEmpty(roleName, "Parameter roleName is required!");
		Number count = DSL.dsl("bean:v1:repositoryService?queryOne",
				new Object[] { "modules-security.Role.hasRole",
						MapHelper.toMap("id", roleId, "name", roleName) });
		return count.longValue() > 0;
	}

	public static Role get(String roleId) {
		return DSL.dsl("bean:v1:repositoryService?queryOne", new Object[] {
				"modules-security.Role.findById", roleId });
	}

	public static Role getByName(String roleName) {
		return DSL.dsl("bean:v1:repositoryService?queryOne", new Object[] {
				"modules-security.Role.findByName", roleName });
	}

	@Override
	public String toString() {
		return "Role [description=" + description + ", enabled=" + enabled
				+ ", id=" + id + ", name=" + name + "]";
	}

}
