/*******************************************************************************
 * Copyright (c) 2014-3-21 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.dsl.engine.support;

import javax.inject.Named;

import org.springframework.beans.factory.InitializingBean;

import com.dayatang.domain.InstanceFactory;
import com.dayatang.querychannel.service.RepositoryService;
import com.foreveross.infra.dsl.engine.AbstractDSLEngine;
import com.foreveross.infra.dsl.engine.DSLEngineFactory;
import com.foreveross.infra.util.Exceptions;
import com.foreveross.infra.util.FormatableCharSequence;
import com.foreveross.infra.util.Logger;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-3-21
 */
@Named("dataChannelDSLEngine")
public class DataChannelDSLEngine extends AbstractDSLEngine implements
		InitializingBean {

	RepositoryService repositoryService;

	public DataChannelDSLEngine() {
		super();
		Logger.info("Create DSL Engine: " + getId());
	}

	public <T> T execute(CharSequence dsl, Object parameter) {
		String url = getUrl(dsl, parameter);
		{
			url = url.substring(0, url.indexOf('?'));
		}
		String parameterString = getParameterString(dsl, parameter);
		Logger.debug(FormatableCharSequence.get("URL:{u}, Parameter:{p}", url,
				parameterString));
		if ("queryPage".equals(url)) {
			return (T) getRepositoryService().queryPage(parameterString,
					parameter);
		} else if ("queryList".equals(url)) {
			return (T) getRepositoryService().queryList(parameterString,
					parameter);
		} else if ("querySize".equals(url)) {
			return (T) (Long) getRepositoryService().querySize(parameterString,
					parameter);
		} else if ("queryOne".equals(url)) {
			return (T) getRepositoryService().queryOne(parameterString,
					parameter);
		} else if ("save".equals(url)) {
			getRepositoryService().save(parameterString, parameter);
		} else if ("update".equals(url)) {
			getRepositoryService().update(parameterString, parameter);
		} else if ("remove".equals(url)) {
			getRepositoryService().remove(parameterString, parameter);
		} else {
			Exceptions.runtime("Unkown DSL: " + dsl);
		}
		return null;
	}

	public String getProtocol() {
		return "datachannel";
	}

	public String getVersion() {
		return "v1";
	}

	public void afterPropertiesSet() throws Exception {
		DSLEngineFactory.register(this);
	}

	public RepositoryService getRepositoryService() {
		if (repositoryService == null) {
			repositoryService = InstanceFactory
					.getInstance(RepositoryService.class);
		}
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

}
