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
public class Resource implements com.dayatang.domain.Entity, Serializable {

	protected String id;
	/** 名称 **/
	protected String name;

	/** 别名 **/
	protected String alias;

	/** 状态 **/
	protected Integer enabled = 1;

	/** 类型（URL、菜单） **/
	protected String resourceType;

	/** 类别（用于显示的归类） **/
	protected String category;

	/** 备注 **/
	protected String description;

	public Resource() {
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

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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

	private static RepositoryService getRepository() {
		return InstanceFactory.getInstance(RepositoryService.class);
	}

	public static Resource get(String id) {
		return getRepository().queryOne("modules-security.Resource.findById",
				id);
	}

	public void save() {
		if (containsResource(getId(), getName())) {
			throw new RuntimeException(String.format("Role name %s is exists!",
					getName()));
		}
		if (getId() != null) {
			getRepository().update("modules-security.Resource.update", this);
		} else {
			getRepository().save("modules-security.Resource.save", this);
		}
	}

	public void remove() {
		RoleResource.deleteRoleResourceByResourceId(getId());
		getRepository().remove("modules-security.Resource.remove", getId());
	}

	public static boolean containsResource(String resourceId,
			String resourceName) {
		Assert.isEmpty(resourceName, "Parameter resourceName is required!");
		Number count = getRepository().queryOne(
				"modules-security.Resource.hasResource",
				MapHelper.toMap("resourceId", resourceId, "resourceName",
						resourceName));
		return count.longValue() > 0;
	}

	public static Resource getByName(String resourceName) {
		return getRepository().queryOne("modules-security.Resource.findByName",
				resourceName);
	}

	public static List<Resource> findByUsername(String username) {
		return getRepository().queryList(
				"modules-security.Resource.findByUsername", username);
	}

	@Override
	public String toString() {
		return "Resource [alias=" + alias + ", description=" + description
				+ ", enabled=" + enabled + ", id=" + id + ", name=" + name
				+ "]";
	}

}
