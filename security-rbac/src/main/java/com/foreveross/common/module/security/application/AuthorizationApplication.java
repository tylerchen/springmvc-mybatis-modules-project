package com.foreveross.common.module.security.application;

import java.util.*;

import com.foreveross.common.module.security.vo.*;

public interface AuthorizationApplication {

	boolean containsRole(String roleId, String roleName);

	boolean containsResource(String resourceId, String resourceName);

	boolean containsAccountRole(String username, String roleName);

	boolean containsRoleResource(String roleName, String resourceName);

	List<AccountRoleVO> findUserRoles(String username);

	List<RoleResourceVO> findRoleResources(String roleName);

	List<ResourceVO> findUserResources(String username);

	AccountVO login(String username, String password, String encrypt);

	void addAccountRoles(String username, String[] roleNames);

	void updateAccountRoles(String username, String[] roleNames);

	void addRoleResources(String roleName, String[] resourceNames);

	void updateRoleResources(String roleName, String[] resourceNames);

	Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap();

	AccountVO findAccountByUsername(String username);

	Map<String, Object> findAccountByUsernameInMap(String username);

	List<String> findRoleNameByUsername(String username);
}