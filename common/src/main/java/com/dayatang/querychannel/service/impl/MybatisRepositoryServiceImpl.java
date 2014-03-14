/*******************************************************************************
 * Copyright (c) 2014-2-26 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.dayatang.querychannel.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.dayatang.querychannel.service.RepositoryService;
import com.foreveross.infra.util.mybatis.plugin.Page;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-2-26
 */
public class MybatisRepositoryServiceImpl implements RepositoryService {

	@Inject
	SqlSession sqlSession;

	public void remove(String queryDsl, Object params) {
		sqlSession.delete(queryDsl, params);
	}

	public void save(String queryDsl, Object params) {
		sqlSession.insert(queryDsl, params);
	}

	public void update(String queryDsl, Object params) {
		sqlSession.update(queryDsl, params);
	}

	public <T> List<T> queryList(String queryDsl, Object params) {
		List<T> list = null;
		if (params == null) {
			list = sqlSession.selectList(queryDsl);
		} else {
			RowBounds rowBounds = getObjectByType(RowBounds.class, params);
			if (rowBounds != null) {
				list = sqlSession.selectList(queryDsl, params, rowBounds);
			} else {
				list = sqlSession.selectList(queryDsl, params);
			}
		}
		return list == null ? new ArrayList<T>() : list;
	}

	public <T> T queryOne(String queryDsl, Object params) {
		return (T)sqlSession.selectOne(queryDsl, params);
	}

	public <T> Page queryPage(String queryDsl, Object params) {
		List<T> list = null;
		Page page = null;
		if (params == null) {
			list = sqlSession.selectList(queryDsl);
			page = Page.offsetPage(0, list.size(), list);
		} else {
			page = getObjectByType(Page.class, params);
			if (page != null) {
				list = sqlSession.selectList(queryDsl, params);
				page.setRows(list);
			} else {
				list = sqlSession.selectList(queryDsl, params);
				page = Page.offsetPage(0, list.size(), list);
			}
		}
		list = list == null ? new ArrayList<T>() : list;
		page = page == null ? Page.offsetPage(0, list.size(), list) : page;
		return page;
	}

	public long querySize(String queryDsl, Object params) {
		Number size = sqlSession.selectOne(queryDsl, params);
		return size == null ? 0 : size.longValue();
	}

	@SuppressWarnings( { "unchecked" })
	private <T> T getObjectByType(Class<T> type, Object params) {
		if (params == null) {
			return null;
		} else if (params.getClass().isArray()) {
			for (Object obj : (Object[]) params) {
				if (type.isInstance(obj)) {
					return (T) obj;
				}
			}
		} else if (params instanceof Collection<?>) {
			for (Object obj : (Collection<?>) params) {
				if (type.isInstance(obj)) {
					return (T) obj;
				}
			}
		} else if (params instanceof Map<?, ?>) {
			for (Entry<?, ?> entry : ((Map<?, ?>) params).entrySet()) {
				if (type.isInstance(entry.getValue())) {
					return (T) (RowBounds) entry.getValue();
				}
			}
		}
		return null;
	}
}
