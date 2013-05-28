package com.itech.offer.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.itech.common.services.Initialize;
import com.itech.common.services.ServiceLocator;
import com.itech.offer.hdfc.parser.HDFCCardOfferSyncJob;
import com.itech.offer.job.JobStatus.JobStatusEnum;
import com.itech.redwine.parser.RedWineSyncJob;
import com.itech.scotchworld.parser.VendorSyncJob;

public class ItechBackGroundTasksService implements Initialize{
	private static final Logger logger = Logger.getLogger(ItechBackGroundTasksService.class);

	public enum TaskAction {
		START, STOP, RESUME, PAUSE
	}

	public enum Tasks {
		REDWINESYNC ("redWineSync" , "Red Wine Sync Job"), VENDORSYNC("vendorSync", "CD Vendor Sync Job"), HDFCOFFERSYNC("hdfcOfferSync", "HDFC CARD Offer Sync Job");

		private final String taskName;
		private final String displayName;

		Tasks(String taskName, String displayName) {
			this.taskName = taskName;
			this.displayName = displayName;
		}

		public String getTaskName() {
			return taskName;
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	private final List<String> taskActions = new ArrayList<String>();
	private final Map<String, String>  taskNames= new HashMap<String, String>();


	public Map<String, String>  getTaskNames() {
		return taskNames;
	}

	public List<String> getTaskActions() {
		return taskActions;
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

		if (Tasks.REDWINESYNC.getTaskName().equalsIgnoreCase(taskName)) {
			return ServiceLocator.getInstance().getBean(RedWineSyncJob.class);
		}

		if (Tasks.VENDORSYNC.getTaskName().equalsIgnoreCase(taskName)) {
			return ServiceLocator.getInstance().getBean(VendorSyncJob.class);
		}

		if (Tasks.HDFCOFFERSYNC.getTaskName().equalsIgnoreCase(taskName)) {
			return ServiceLocator.getInstance().getBean(HDFCCardOfferSyncJob.class);
		}
		return null;
	}

	@Override
	public void init() {
		for (TaskAction taskAction : TaskAction.values()) {
			taskActions.add(taskAction.toString());
		}

		for (Tasks task : Tasks.values()) {
			taskNames.put(task.getTaskName(), task.getDisplayName());
		}
		ItechJobManager.start();
	}



}
