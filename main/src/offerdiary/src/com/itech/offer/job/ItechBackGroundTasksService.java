package com.itech.offer.job;

import org.apache.log4j.Logger;

import com.itech.common.services.Initialize;
import com.itech.common.services.ServiceLocator;
import com.itech.offer.job.JobStatus.JobStatusEnum;
import com.itech.redwine.parser.RedWineSyncJob;
import com.itech.scotchworld.parser.VendorSyncJob;

public class ItechBackGroundTasksService implements Initialize{
	private static final Logger logger = Logger.getLogger(ItechBackGroundTasksService.class);

	public enum TaskAction {
		START, STOP, RESUME, PAUSE
	}


	public void manageBackgroundTask(String taskName, String taskAction) {
		ItechJob job = ItechJobManager.getJob(taskName);
		if (job == null) {
			job = createJob(taskName);
			if (job == null ) {
				logger.warn("No task type exists with name :" +  taskName);
				return;
			}
		}

		if (TaskAction.START.toString().equalsIgnoreCase(taskAction)) {
			ItechJobManager.addJob(job, taskName);
			return;
		}

		if (TaskAction.STOP.toString().equalsIgnoreCase(taskAction)) {
			ItechJobManager.stop(taskName);
		}

		if (TaskAction.PAUSE.toString().equalsIgnoreCase(taskAction)) {
			ItechJobManager.pause(taskName);
		}

		if (TaskAction.RESUME.toString().equalsIgnoreCase(taskAction)) {
			ItechJobManager.resume(taskName);
		}
	}

	public String getTaskStatus(String taskName) {
		ItechJob job = ItechJobManager.getJob(taskName);
		if (job == null) {
			return JobStatusEnum.NOT_RUNNING.toString();
		}
		return job.getJobStatus().getStatus().toString();
	}



	private ItechJob createJob(String taskName) {

		if ("redWIneSync".equalsIgnoreCase(taskName)) {
			return ServiceLocator.getInstance().getBean(RedWineSyncJob.class);
		}

		if ("vendorSync".equalsIgnoreCase(taskName)) {
			return ServiceLocator.getInstance().getBean(VendorSyncJob.class);
		}
		return null;
	}

	@Override
	public void init() {
		ItechJobManager.start();
	}



}
