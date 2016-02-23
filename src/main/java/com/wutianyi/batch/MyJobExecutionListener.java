/**
 * hanjiewu 
 * 2015年12月15日:下午8:13:37
 */
package com.wutianyi.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author hanjiewu
 *
 */
public class MyJobExecutionListener implements JobExecutionListener {

	/* 
	 * 
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {

	}

	/* 
	 * 
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {

		}
	}

}
