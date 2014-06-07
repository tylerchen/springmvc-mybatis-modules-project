/*******************************************************************************
 * Copyright (c) 2012-12-7 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.application;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.foreveross.infra.dsl.engine.DSL;
import com.foreveross.infra.test.AbstractIntegratedTestCase;
import com.foreveross.infra.util.Logger;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
public class AuthorizationApplicationTest extends AbstractIntegratedTestCase {

	@Override
	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/Demo.xml" };
	}

	@Test
	public void test_loggerLevel() {
		Logger.info("Disable debug=========================");
		Logger.changeLevel(Logger.getName(), "INFO");
		Logger.debug("============you can't see this debug message=============");
		Logger.info("Enable debug=========================");
		Logger.changeLevel(Logger.getName(), "DEBUG");
		Logger.debug("============you can see this debug message=============");
	}

	@Test
	public void test_addAccountRoles() {

		Assert.assertEquals(((List<?>) DSL.dsl(
				"bean:v1:authorizationApplication?findUserRoles",
				new Object[] { "test1" })).size(), 1);
		DSL.dsl("bean:v1:authorizationApplication?addAccountRoles",
				new Object[] { "test1", new String[] { "test2", "test3" } });
		Assert.assertEquals(((List<?>) DSL.dsl(
				"bean:v1:authorizationApplication?findUserRoles",
				new Object[] { "test1" })).size(), 3);
	}

	@Test
	public void test_addRoleResources() {
		Assert.assertEquals(((List<?>) DSL.dsl(
				"bean:v1:authorizationApplication?findRoleResources",
				new Object[] { "test1" })).size(), 1);
		DSL.dsl("bean:v1:authorizationApplication?addRoleResources",
				new Object[] { "test1", new String[] { "test2", "test3" } });
		Assert.assertEquals(((List<?>) DSL.dsl(
				"bean:v1:authorizationApplication?findRoleResources",
				new Object[] { "test1" })).size(), 3);
	}

	@Test
	public void test_findUserResources() {
		Assert.assertEquals(((List<?>) DSL.dsl(
				"bean:v1:authorizationApplication?findUserResources",
				new Object[] { "test1" })).size(), 1);
	}

	@Test
	public void test_login() {
		Assert.assertNotNull(DSL.dsl("bean:v1:authorizationApplication?login",
				new Object[] { "test1", "1", null }));
		Assert.assertNull(DSL.dsl("bean:v1:authorizationApplication?login",
				new Object[] { "test1", "2", null }));
	}

	@Test
	public void test_updateAccountRoles() {
		DSL.dsl("bean:v1:authorizationApplication?updateAccountRoles",
				new Object[] { "test1", new String[] { "test2", "test3" } });
		Assert.assertEquals(((List<?>) DSL.dsl(
				"bean:v1:authorizationApplication?findUserRoles",
				new Object[] { "test1" })).size(), 2);
	}

	@Test
	public void test_updateRoleResources() {
		DSL.dsl("bean:v1:authorizationApplication?updateRoleResources",
				new Object[] { "test1", new String[] { "test2", "test3" } });
		Assert.assertEquals(((List<?>) DSL.dsl(
				"bean:v1:authorizationApplication?findRoleResources",
				new Object[] { "test1" })).size(), 2);
	}

	@Test
	public void test_findAllRoleNameAndResourceNameInMap() {
		Map<String, List<String>> map = DSL
				.dsl(
						"bean:v1:authorizationApplication?findAllResourceNameAndRoleNameInResourceRolesMap",
						new Object[] {});
		System.out.println(map);
	}

	@Test
	public void test_findRoleNameByUsername() {
		List<String> list = DSL.dsl(
				"bean:v1:authorizationApplication?findRoleNameByUsername",
				new Object[] { "test1" });
		System.out.println(list);
	}
}
