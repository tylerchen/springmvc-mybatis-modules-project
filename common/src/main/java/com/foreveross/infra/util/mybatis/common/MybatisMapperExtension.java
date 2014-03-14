/*******************************************************************************
 * Copyright (c) 2014-2-26 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util.mybatis.common;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-2-26
 */
public class MybatisMapperExtension {

	private Configuration configuration;

	public MybatisMapperExtension(Configuration configuration) {
		this.configuration = configuration;
	}

	public void extension() {
		Collection<ResultMap> resultMaps = configuration.getResultMaps();
		for (ResultMap rm : resultMaps) {
			if(rm.getType().getClass().getName().startsWith("java.")){
				continue;
			}
			System.out.println(rm.getId()+"--"+rm.getType());
			List<ResultMapping> rms = null;
			{
				rms = rm.getIdResultMappings();
				for (ResultMapping rmp : rms) {
					System.out.println(rmp.getProperty() + "-"
							+ rmp.getColumn());
				}
			}
			{
				rms = rm.getPropertyResultMappings();
				for (ResultMapping rmp : rms) {
					System.out.println(rmp.getProperty() + "-"
							+ rmp.getColumn());
				}
			}
		}
	}
}
