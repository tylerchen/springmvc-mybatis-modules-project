/*******************************************************************************
 * Copyright (c) 2014-2-26 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.domain;

import org.apache.ibatis.session.SqlSession;

import com.dayatang.domain.Entity;
import com.dayatang.domain.InstanceFactory;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-2-26
 */
@SuppressWarnings("serial")
public abstract class AbstractEntity implements Entity {

	private static SqlSession sqlSession = null;

	static SqlSession getSqlSession() {
		if (sqlSession == null) {
			sqlSession = InstanceFactory.getInstance(SqlSession.class);
		}
		return sqlSession;
	}

	public abstract <T> T getId();

	public void save() {
		if (getId() != null) {
			getSqlSession().update(getMapperNameSpace() + ".update", this);
		} else {
			getSqlSession().update(getMapperNameSpace() + ".save", this);
		}
	}

	public void remove() {
		getSqlSession().delete(getMapperNameSpace() + ".remove", this);
	}

	private String mapperNameSpace_ = null;

	public String getMapperNameSpace() {
		if (mapperNameSpace_ == null) {
			try {
				MapperNamespace annotation = getClass().getAnnotation(
						MapperNamespace.class);
				mapperNameSpace_ = annotation.value();
			} catch (Exception e) {
				mapperNameSpace_ = getClass().getName();
			}
		}
		return mapperNameSpace_;
	}
}
