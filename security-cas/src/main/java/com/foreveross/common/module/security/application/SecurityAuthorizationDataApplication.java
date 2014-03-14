/*******************************************************************************
 * Copyright (c) 2014-3-2 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.common.module.security.application;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-2
 */
public interface SecurityAuthorizationDataApplication {

	/**
	 * <pre>
	 * select user data and return map result, 
	 * Map Key:{
	 * PASSWORD: required
	 * USERNAME: required
	 * REAL_NAME: optional
	 * EMAIL: optional
	 * REGISTRY_DATE: optional
	 * ENABLED: optional
	 * PASSWORD_LAST_UPDATE_DATE: optional
	 * DESCRIPTION: optional
	 * SUPER: optional
	 * }
	 * </pre>
	 * @param username
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-2
	 */
	Map<String, Object> findAccountByUsernameInMap(String username);

	/**
	 * return the user's role name
	 * @param username
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-2
	 */
	List<String> findRoleNameByUsername(String username);

	/**
	 * <pre>
	 * return all the role and resource data in map,
	 * MAP Content:{
	 * resource name: role names list
	 * }
	 * </pre>
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-2
	 */
	Map<String, List<String>> findAllResourceNameAndRoleNameInResourceRolesMap();
}
