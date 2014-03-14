/*******************************************************************************
 * Copyright (c) 2014-2-26 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-2-26
 */
public class MapHelper {

	/**
	 * <pre>
	 * Usage : toMap("KEY1", value1, "KEY2", value2);
	 * Expect: map = new LinkedHashMap(); map.put("KEY1", value1); map.put("KEY2", value2);
	 * </pre>
	 * @param params
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	public static Map toMap(Object... params) {
		Map map = new LinkedHashMap();
		Assert.notNull(params);
		Assert.isTrue(params.length % 2 == 0);
		for (int i = 0; i < params.length; i++) {
			map.put(params[i++], params[i]);
		}
		return map;
	}

	/**
	 * <pre>
	 * Usage : fillMap(map, "KEY1", value1, "KEY2", value2);
	 * Expect: map.put("KEY1", value1); map.put("KEY2", value2);
	 * </pre>
	 * @param map
	 * @param params
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	public static Map fillMap(Map map, Object... params) {
		map = map == null ? new LinkedHashMap() : map;
		Assert.notNull(params);
		Assert.isTrue(params.length % 2 == 0);
		for (int i = 0; i < params.length; i++) {
			map.put(params[i++], params[i]);
		}
		return map;
	}
}
