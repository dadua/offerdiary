package com.itech.offer.job;

public class JobStatus {
	private JobStatusEnum status = JobStatusEnum.RUNNING;

	public enum JobStatusEnum {
		RUNNING, PAUSED, STOPPED, NOT_RUNNING, FINISHED
	}

	public JobStatus(JobStatusEnum status) {
		this.setStatus(status);
	}

	public JobStatusEnum getStatus() {
		return status;
	}

	public void setStatus(JobStatusEnum status) {
		this.status = status;
	}

}
