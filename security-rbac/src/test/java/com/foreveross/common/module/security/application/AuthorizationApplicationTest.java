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

import com.dayatang.domain.InstanceFactory;
import com.foreveross.infra.test.AbstractIntegratedTestCase;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2012-12-7
 */
public class AuthorizationApplicationTest extends AbstractIntegratedTestCase {

	@Override
	protected String[] getDataSetFilePaths() {
		return new String[] { "dataset/Demo.xml" };
	}

	private AuthorizationApplication as = null;

	protected void action4SetUp() {
		if (as == null) {
			as = InstanceFactory.getInstance(AuthorizationApplication.class);
		}
	}

	@Test
	public void test_addAccountRoles() {
		Assert.assertEquals(as.findUserRoles("test1").size(), 1);
		as.addAccountRoles("test1", new String[] { "test2", "test3" });
		Assert.assertEquals(as.findUserRoles("test1").size(), 3);
	}

	@Test
	public void test_addRoleResources() {
		Assert.assertEquals(as.findRoleResources("test1").size(), 1);
		as.addRoleResources("test1", new String[] { "test2", "test3" });
		Assert.assertEquals(as.findRoleResources("test1").size(), 3);
	}

	@Test
	public void test_findUserResources() {
		Assert.assertEquals(as.findUserResources("test1").size(), 1);
	}

	@Test
	public void test_login() {
		Assert.assertNotNull(as.login("test1", "1", null));
		Assert.assertNull(as.login("test1", "2", null));
	}

	@Test
	public void test_updateAccountRoles() {
		as.updateAccountRoles("test1", new String[] { "test2", "test3" });
		Assert.assertEquals(as.findUserRoles("test1").size(), 2);
	}

	@Test
	public void test_updateRoleResources() {
		as.updateRoleResources("test1", new String[] { "test2", "test3" });
		Assert.assertEquals(as.findRoleResources("test1").size(), 2);
	}

	@Test
	public void test_findAllRoleNameAndResourceNameInMap() {
		Map<String, List<String>> map = as
				.findAllResourceNameAndRoleNameInResourceRolesMap();
		System.out.println(map);
	}

	@Test
	public void test_findRoleNameByUsername() {
		List<String> list = as.findRoleNameByUsername("test1");
		System.out.println(list);
	}
}
