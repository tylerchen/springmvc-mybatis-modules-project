/*******************************************************************************
 * Copyright (c) 2014-3-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-14
 */
public class Logger {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger("FOSS");

	public static org.slf4j.Logger getLogger() {
		return log;
	}

	public static void debug(CharSequence message) {
		if (log.isDebugEnabled()) {
			log.debug(message.toString());
		}
	}

	public static void debug(CharSequence message, Throwable t) {
		if (log.isDebugEnabled()) {
			log.debug(message.toString(), t);
		}
	}

	public static void error(CharSequence message) {
		if (log.isErrorEnabled()) {
			log.error(message.toString());
		}
	}

	public static void error(CharSequence message, Throwable t) {
		if (log.isErrorEnabled()) {
			log.error(message.toString(), t);
		}
	}

	public static String getName() {
		return "FOSS";
	}

	public static void info(CharSequence message) {
		if (log.isInfoEnabled()) {
			log.info(message.toString());
		}
	}

	public static void info(CharSequence message, Throwable t) {
		if (log.isInfoEnabled()) {
			log.info(message.toString(), t);
		}
	}

	public static void trace(CharSequence message) {
		if (log.isTraceEnabled()) {
			log.trace(message.toString());
		}
	}

	public static void trace(CharSequence message, Throwable t) {
		if (log.isTraceEnabled()) {
			log.trace(message.toString(), t);
		}
	}

	public static void warn(CharSequence message) {
		if (log.isWarnEnabled()) {
			log.warn(message.toString());
		}
	}

	public static void warn(CharSequence message, Throwable t) {
		if (log.isWarnEnabled()) {
			log.warn(message.toString(), t);
		}
	}

	public static void changeLevel(String loggerName, String level) {
		if (loggerName == null || loggerName.length() < 1) {
			Level lv = Level.toLevel(level);
			LogManager.getRootLogger().setLevel(lv);
		} else {
			Level lv = Level.toLevel(level);
			org.apache.log4j.Logger logger = LogManager.getLogger(loggerName);
			logger.setLevel(lv);
		}
	}
}
