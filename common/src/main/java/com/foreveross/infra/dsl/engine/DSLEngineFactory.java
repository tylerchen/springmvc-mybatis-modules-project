/*******************************************************************************
 * Copyright (c) 2014-3-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.dsl.engine;

import java.util.HashMap;
import java.util.Map;

import com.foreveross.infra.util.Assert;
import com.foreveross.infra.util.Logger;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-21
 */
public class DSLEngineFactory {

	private static final Map<String, DSLEngine> dslEngines = new HashMap<String, DSLEngine>();

	public static DSLEngine getDslEngine(CharSequence dsl) {
		int len = dsl.length(), offset = 0, i = 0;
		for (; i < len; i++) {
			if (dsl.charAt(i) == ':') {
				if (offset > 0) {// match the second ':'
					offset = i;
					break;
				} else {
					offset = i;
				}
			}
		}
		CharSequence subSequence = dsl.subSequence(0, offset);
		return dslEngines.get(subSequence.toString().toLowerCase());
	}

	public static void register(DSLEngine dslEngine) {
		Assert.notNull(dslEngine, "DSLEngine can not be null!");
		String dslEngineId = dslEngine.getId();
		if (dslEngines.containsKey(dslEngineId)) {
			Logger.info("Update DSL TO: " + dslEngine);
		} else {
			Logger.info("ADD DSL: " + dslEngine);
		}
		synchronized (dslEngines) {
			dslEngines.put(dslEngineId, dslEngine);
		}
		Logger.info("Current DSL Engine count: " + dslEngines.size());
	}
}
