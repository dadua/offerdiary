package com.itech.offer.job;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.itech.common.CommonUtilities;
import com.itech.offer.job.JobStatus.JobStatusEnum;

public class ItechJobManager {
	private static Logger logger = Logger.getLogger(ItechJobManager.class);
	private static final Queue<ItechJob> jobs = new ArrayBlockingQueue<ItechJob>(1000);
	private static final Map<String, ItechJob> jobMap = new HashMap<String, ItechJob>();
	private static final Map<String, JobEventListener> jobListenerMap = new HashMap<String, JobEventListener>();
	private static boolean stopped;

	public static synchronized String addJob(ItechJob itechJob, String jobId) {
		ItechJob job = getJob(jobId);
		if (job != null) {
			return jobId;
		}
		for (Entry<String, ItechJob> entry : jobMap.entrySet()) {
			if (entry.getValue().equals(itechJob)) {
				return entry.getKey();
			}
		}
		jobs.add(itechJob);
		if (CommonUtilities.isNullOrEmpty(jobId)) {
			jobId = UUID.randomUUID().toString();
		}
		jobMap.put(jobId, itechJob);
		synchronized (jobs) {
			jobs.notifyAll();
		}
		return jobId;
	}

	public static synchronized ItechJob getJob(String id) {

		ItechJob job = jobMap.get(id);

		if (job !=null && JobStatusEnum.FINISHED.equals(job.getJobStatus().getStatus())) {
			jobMap.remove(id);
		}
		return jobMap.get(id);
	}

	public static synchronized void start() {
		Runnable jobManagerThread = new Runnable() {
			@Override
			public void run() {
				while (!stopped) {
					synchronized (jobs) {
						final ItechJob job = jobs.poll();
						if (job == null) {
							try {
								jobs.wait();
							} catch (InterruptedException e) {
								logger.error("Interrupted.", e);
							}
							continue;
						}
						Runnable jobCall = new Runnable() {
							@Override
							public void run() {
								job.doJob();

							}
						};
						Thread jobThread = new Thread(jobCall);
						jobThread.start();
					}
				}

			}
		};
		Thread thread = new Thread(jobManagerThread);
		thread.start();

	}

	public static synchronized void stop() {
		ItechJobManager.stopped = true;
		synchronized (jobs) {
			jobs.notifyAll();
		}
	}

	public static synchronized boolean stop(String jobId) {
		ItechJob itechJob = jobMap.get(jobId);
		if (itechJob == null) {
			return false;
		}
		jobMap.remove(itechJob);
		itechJob.stop();
		return true;

	}

	public static synchronized boolean pause(String jobId) {
		ItechJob itechJob = jobMap.get(jobId);
		if (itechJob == null) {
			return false;
		}
		itechJob.pause();
		return true;

	}


	public static synchronized boolean resume(String jobId) {
		ItechJob itechJob = jobMap.get(jobId);
		if (itechJob == null) {
			return false;
		}
		itechJob.resume();
		return true;

	}

	public static synchronized void addJobEventListener(String jobId,
			JobEventListener eventListener) {
		jobListenerMap.put(jobId, eventListener);
	}

	public static synchronized JobEventListener getJobEventListeners(String jobId) {
		return jobListenerMap.get(jobId);
	}



}
