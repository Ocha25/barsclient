package com.accenture.bars.rest.dao;

import com.accenture.bars.rest.domain.Request;


public interface IRequestDAO {

	public void insertRequest(Request request);

	public void deleteRequests();
}
