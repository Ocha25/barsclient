package com.accenture.bars.rest.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.accenture.bars.rest.domain.Request;
public class RequestDAOImpl implements IRequestDAO {

	private Connection conn = null;
	private static Logger logger = Logger.getLogger(RequestDAOImpl.class);

	public RequestDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insertRequest(Request request){
		String sql = "INSERT request (billing_cycle,start_date,end_date) values(?,?,?)";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, request.getBillingCycle());
			prepStmt.setDate(2, new Date(request.getStartDate().getTime()));
			prepStmt.setDate(3, new Date(request.getEndDate().getTime()));
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void deleteRequests() {
		String sql = "TRUNCATE request";
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			logger.error(e);
		}
	}
}
