package com.dayatang.querychannel.service;

import java.util.List;

import com.foreveross.infra.util.mybatis.plugin.Page;

public interface QueryService {

	long querySize(String queryDsl, Object params);

	<T> List<T> queryList(String queryDsl, Object params);

	<T> T queryOne(String queryDsl, Object params);

	<T> Page queryPage(String queryDsl, Object params);
}
