package com.itech.offer.job;

public interface ItechJob {
	public void doJob();
	public void stop();
	public void pause();
	public void resume();
	public JobStatus getJobStatus();
	public void addJobEventListener(JobEventListener  eventListener);
}
