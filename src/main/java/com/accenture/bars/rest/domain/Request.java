package com.accenture.bars.rest.domain;

import java.util.Date;

public class Request {

	private int billingCycle;
	private Date startDate;
	private Date endDate;

	public int getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
	}
	public Date getStartDate() {
		return (Date) startDate.clone();
	}
	public void setStartDate(Date startDate) {
		this.startDate = (Date) startDate.clone();
	}
	public Date getEndDate() {
		return (Date) endDate.clone();
	}
	public void setEndDate(Date endDate) {
		this.endDate = (Date) endDate.clone();
	}
}
