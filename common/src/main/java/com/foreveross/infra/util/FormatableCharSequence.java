/*******************************************************************************
 * Copyright (c) 2014-3-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util;

/**
 * <pre>
 * Usage:
 * Example: FormatableCharSequence.get(FormatableCharSequence.get("fuck {0},{1}", "ie6"), "ie7")
 * Expect : fuck ie6,ie7
 * </pre>
 * <pre>
 * Depends: com.foreveross.infra.util.StringHelper
 * </pre>
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-14
 */
public class FormatableCharSequence implements java.io.Serializable,
		CharSequence {

	private static final long serialVersionUID = 4667459143425384729L;
	private StringBuilder builder = new StringBuilder(64);
	private Object[] parameters = new Object[0];
	private String toString = null;

	protected FormatableCharSequence(CharSequence formatString,
			Object[] parameters) {
		String string = formatString == null ? "" : formatString.toString();
		builder.append(string);
		this.parameters = parameters == null ? this.parameters : parameters;
	}

	/**
	 * return FormatableCharSequence instance
	 * @param formatString A CharSequence type can be FormatableCharSequence
	 * @param params       Use to format the formatString
	 * @return
	 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
	 * @since 2014-3-14
	 */
	public static FormatableCharSequence get(CharSequence formatString,
			Object... params) {
		FormatableCharSequence fs = new FormatableCharSequence(formatString,
				params);
		return fs;
	}

	public char charAt(int index) {
		return builder.charAt(index);
	}

	public int length() {
		return builder.length();
	}

	public CharSequence subSequence(int start, int end) {
		return builder.subSequence(start, end);
	}

	public String toString() {
		if (toString != null) {
			return toString;
		} else if (builder.length() < 1) {
			toString = "";
		} else {
			toString = StringHelper.replaceBlock(builder.toString(),
					parameters, null);
		}
		return toString;
	}

	public static void main(String[] args) {
		Assert.notNull(null, FormatableCharSequence.get(FormatableCharSequence
				.get("fuck {0},{1}", "fuck"), "you", "you"));
	}
}
