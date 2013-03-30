package com.itech.offer.job;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.itech.common.db.hibernate.HibernateSessionFactory;
import com.itech.offer.job.JobStatus.JobStatusEnum;

public abstract class BaseItechJob implements ItechJob {
	@Autowired
	private HibernateSessionFactory hibernateSessionFactory;
	protected boolean stopped;
	protected boolean paused;
	protected boolean finished;
	private static final Logger logger = Logger.getLogger(BaseItechJob.class);

	@Override
	public void doJob() {
		Transaction transaction = null;
		try {
			getHibernateSessionFactory().openNewSession();
			transaction = getHibernateSessionFactory().getCurrentSession().beginTransaction();
			doJobTask();
			transaction.commit();
		} catch (Exception e) {
			logger.error("error in job", e);
		} finally {
			if (transaction !=null && transaction.isActive()) {
				try{
					transaction.rollback();
				} catch (Exception e) {
					logger.error("Failed to rollback transaction", e);
				}
			}
			getHibernateSessionFactory().closeCurrentSession();
			finished = true;
		}
	}

	protected abstract void doJobTask();

	@Override
	public void stop() {
		synchronized (this) {
			this.stopped = true;
			this.notifyAll();
		}
	}

	@Override
	public void pause() {
		synchronized (this) {
			this.paused = true;
			this.notifyAll();
		}
	}

	@Override
	public void resume() {
		synchronized (this) {
			this.paused = false;
			this.notifyAll();
		}
	}

	@Override
	public JobStatus getJobStatus() {
		if (stopped) {
			return new JobStatus(JobStatusEnum.STOPPED);
		}

		if (paused) {
			return new JobStatus(JobStatusEnum.PAUSED);
		}

		if (finished) {
			return new JobStatus(JobStatusEnum.FINISHED);
		}
		return new JobStatus(JobStatusEnum.RUNNING);
	}

	@Override
	public void addJobEventListener(JobEventListener eventListener) {
		// TODO Auto-generated method stub

	}

	public HibernateSessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

	public void setHibernateSessionFactory(HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

}
