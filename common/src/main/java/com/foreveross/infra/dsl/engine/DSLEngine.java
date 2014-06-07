/*******************************************************************************
 * Copyright (c) 2014-3-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.dsl.engine;

/**
 * protocol:version:url?parameters
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-21
 */
public interface DSLEngine {

	<T> T execute(CharSequence dsl, Object parameter);

	String getProtocol();

	String getVersion();

	String getUrl(CharSequence dsl, Object parameter);

	String getParameterString(CharSequence dsl, Object parameter);

	DSLEngine getSubEngine(CharSequence dsl, Object parameter);

	/**
	 * ID = protocol+':'+version
	 * @return getProtocol()+':'+getVersion()
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-21
	 */
	String getId();
}
