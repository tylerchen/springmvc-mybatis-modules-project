/*******************************************************************************
 * Copyright (c) 2014-3-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.dsl.engine;

import com.foreveross.infra.util.Assert;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-21
 */
public class DSL {

	/**
	 * Execute the DSL
	 * @param dsl could be type of com.foreveross.infra.util.FormatableCharSequence or just String
	 * @param parameter 
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-21
	 */
	public static <T> T dsl(CharSequence dsl, Object parameter) {
		Assert.notBlank(dsl, "DSL String is required!");
		return (T) DSLEngineFactory.getDslEngine(dsl).execute(dsl, parameter);
	}
}
