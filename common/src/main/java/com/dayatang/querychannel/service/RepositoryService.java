package com.dayatang.querychannel.service;

public interface RepositoryService extends QueryService {

	void save(String queryDsl, Object params);

	void update(String queryDsl, Object params);

	void remove(String queryDsl, Object params);

}
