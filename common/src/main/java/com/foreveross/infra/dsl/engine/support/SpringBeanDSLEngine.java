/*******************************************************************************
 * Copyright (c) 2014-3-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.dsl.engine.support;

import java.lang.reflect.Method;

import javax.inject.Named;

import org.springframework.beans.factory.InitializingBean;

import com.dayatang.domain.InstanceFactory;
import com.foreveross.infra.dsl.engine.AbstractDSLEngine;
import com.foreveross.infra.dsl.engine.DSLEngineFactory;
import com.foreveross.infra.util.Assert;
import com.foreveross.infra.util.Exceptions;
import com.foreveross.infra.util.FormatableCharSequence;
import com.foreveross.infra.util.Logger;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-21
 */
@Named("springBeanDSLEngine")
public class SpringBeanDSLEngine extends AbstractDSLEngine implements
		InitializingBean {

	public SpringBeanDSLEngine() {
		super();
		Logger.info("Create DSL Engine: " + getId());
	}

	public <T> T execute(CharSequence dsl, Object parameter) {
		Logger.debug(FormatableCharSequence.get("[DSL] {0}", dsl));
		String url = getUrl(dsl, parameter);
		if (url.indexOf('?') > -1) {
			url = url.substring(0, url.indexOf('?'));
		}
		String methodName = getParameterString(dsl, parameter);
		Logger.debug(FormatableCharSequence.get("[URL]:{u}, Parameter:{p}",
				url, methodName));
		Object bean = InstanceFactory.getInstance(Object.class, url);
		String className = bean.getClass().getName();
		if (className.startsWith("$Proxy")) {//Spring aop proxy for groovy bean
			try {
				Object o = com.foreveross.infra.util.mybatis.plugin.ReflectHelper
						.getValueByFieldName(bean, "h");
				o = com.foreveross.infra.util.mybatis.plugin.ReflectHelper
						.getValueByFieldName(o, "advised");
				o = com.foreveross.infra.util.mybatis.plugin.ReflectHelper
						.getValueByFieldName(o, "targetSource");
				org.springframework.aop.TargetSource ts = (org.springframework.aop.TargetSource) o;
				bean = ts.getTarget();
			} catch (Exception e) {
				Exceptions.runtime(FormatableCharSequence.get(
						"get [{0}.{1}.{2}.{3}] Error!", className, "h",
						"advised", "targetSource"), e);
			}
		}
		Assert.notNull(bean, FormatableCharSequence.get(
				"Can' find the bean[{bean}]!", url));
		Logger.debug(FormatableCharSequence.get(
				"DSL[{dsl}], method is not specify.", dsl));
		if (methodName == null || methodName.length() < 1) {
			return (T) bean;
		}
		Method[] methods = bean.getClass().getDeclaredMethods();
		for (Method m : methods) {
			if (methodName.equals(m.getName())) {
				try {
					Logger.debug(FormatableCharSequence.get(
							"[METHOD] Invoke spring bean method[{0}]", m));
					if (parameter.getClass().isArray()) {
						return (T) m.invoke(bean, (Object[]) parameter);
					} else {
						return (T) m.invoke(bean, parameter);
					}
				} catch (Exception e) {
					Exceptions.runtime(FormatableCharSequence.get(
							"Method[{0}] invoke error!", m), e);
				}
			}
		}
		return null;
	}

	public String getProtocol() {
		return "bean";
	}

	public String getVersion() {
		return "v1";
	}

	public void afterPropertiesSet() throws Exception {
		DSLEngineFactory.register(this);
		Logger.info("DSL Engine Registed: " + getId());
	}

}
