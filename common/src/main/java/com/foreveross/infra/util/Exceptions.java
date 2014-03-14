/*******************************************************************************
 * Copyright (c) 2014-3-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-14
 */
public class Exceptions {

	/**
	 * throw Exception with Throwable error
	 * @param message you can use com.foreveross.infra.util.FormatableCharSequence.get
	 * @param t
	 * @throws Exception
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	public static void exception(CharSequence message, Throwable t)
			throws Exception {
		throw new Exception("[FOSS-1001][" + t.getClass().getSimpleName()
				+ "][" + message + "]", t);
	}

	/**
	 * throw Exception without Throwable error
	 * @param message you can use com.foreveross.infra.util.FormatableCharSequence.get
	 * @throws Exception
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	public static void exception(CharSequence message) throws Exception {
		throw new Exception("[FOSS-1002][Exception][" + message + "]");
	}

	/**
	 * throw RuntimeException with Throwable error
	 * @param message you can use com.foreveross.infra.util.FormatableCharSequence.get
	 * @param t
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	public static void runtime(CharSequence message, Throwable t) {
		throw new RuntimeException("[FOSS-1003]["
				+ t.getClass().getSimpleName() + "][" + message + "]", t);
	}

	/**
	 * throw RuntimeException without Throwable error
	 * @param message you can use com.foreveross.infra.util.FormatableCharSequence.get
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	public static void runtime(CharSequence message) {
		throw new RuntimeException("[FOSS-1004][RuntimeException][" + message
				+ "]");
	}
}
