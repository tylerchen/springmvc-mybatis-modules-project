/*******************************************************************************
 * Copyright (c) 2014-3-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.dsl.engine;

import com.foreveross.infra.util.FormatableCharSequence;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-21
 */
public abstract class AbstractDSLEngine implements DSLEngine {

	public String getParameterString(CharSequence dsl, Object parameter) {
		String dslString = dsl.toString();
		int offset = dslString.indexOf('?');
		if (offset < 0) {
			return "";
		}
		return dslString.substring(offset + 1);
	}

	public DSLEngine getSubEngine(CharSequence dsl, Object parameter) {
		return this;
	}

	public String getUrl(CharSequence dsl, Object parameter) {
		String dslString = dsl.toString();
		int len = dslString.length(), offset = 0, i = 0;
		for (; i < len; i++) {
			if (dslString.charAt(i) == ':') {
				if (offset > 0) {// match the second ':'
					offset = i;
					break;
				} else {
					offset = i;
				}
			}
		}
		return dslString.substring(offset + 1);
	}

	public String getId() {
		return new StringBuilder().append(getProtocol()).append(':').append(
				getVersion()).toString().toLowerCase();
	}

	public String toString() {
		return FormatableCharSequence.get("{0}[{id}]",
				this.getClass().getName(), getId()).toString();
	}
}
