/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foreveross.infra.dsl.engine.DSL;
import com.foreveross.infra.util.Assert;
import com.foreveross.infra.util.MapHelper;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
@SuppressWarnings("serial")
public class RoleResource implements com.dayatang.domain.Entity, Serializable {

	protected String roleId;
	protected String resourceId;

	public RoleResource() {
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public static RoleResource get(String roleId, String resourceId) {
		return DSL.dsl("bean:v1:repositoryService?queryOne", new Object[] {
				"modules-security.RoleResource.findById",
				MapHelper.toMap("roleId", roleId, "resourceId", resourceId) });
	}

	public RoleResource save() {
		Assert.isTrue(!containsRoleResource(getRoleId(), getResourceId()),
				String.format("RoleId:%s, ResourceId:%s is exists!",
						getRoleId(), getResourceId()));
		DSL.dsl("bean:v1:repositoryService?save", new Object[] {
				"modules-security.RoleResource.save", this });
		return this;
	}

	public static boolean containsRoleResource(String roleId, String resourceId) {
		Assert.notEmpty(resourceId, "Parameter resourceId is required!");
		Assert.notEmpty(roleId, "Parameter roleId is required!");
		return get(roleId, resourceId) != null;
	}

	public static void deleteRoleResourceByRoleId(String roleId) {
		Assert.notEmpty(roleId, "Parameter roleId is required!");
		DSL.dsl("bean:v1:repositoryService?remove", new Object[] {
				"modules-security.RoleResource.deleteRoleResourceByRoleId",
				roleId });
	}

	public static void deleteRoleResourceByResourceId(String resourceId) {
		Assert.notEmpty(resourceId, "Parameter resourceId is required!");
		DSL.dsl("bean:v1:repositoryService?remove", new Object[] {
				"modules-security.RoleResource.deleteRoleResourceByResourceId",
				resourceId });
	}

	public static void deleteRoleResourceByRoleName(String roleName) {
		Assert.notEmpty(roleName, "Parameter roleName is required!");
		DSL.dsl("bean:v1:repositoryService?remove", new Object[] {
				"modules-security.RoleResource.deleteRoleResourceByRoleName",
				roleName });
	}

	public static List<RoleResource> findByRoleName(String roleName) {
		return DSL.dsl("bean:v1:repositoryService?queryList", new Object[] {
				"modules-security.RoleResource.findByRoleName", roleName });
	}

	public static Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap() {
		List<Map<String, Object>> list = DSL
				.dsl(
						"bean:v1:repositoryService?queryList",
						new Object[] {
								"modules-security.RoleResource.findAllResourceNameAndRoleNameInResourceRolesMap",
								null });
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (Map<String, Object> m : list) {
			String roleName = (String) m.get("ROLENAME");
			String resourceName = (String) m.get("RESOURCENAME");
			List<String> roles = map.get(resourceName);
			if (roles == null) {
				roles = new ArrayList<String>();
				map.put(resourceName, roles);
			}
			roles.add(roleName);
		}
		return map;
	}

	@Override
	public String toString() {
		return "RoleResource [resourceId=" + resourceId + ", roleId=" + roleId
				+ "]";
	}

}
