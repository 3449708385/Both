package com.yishenxiao.commons.service.impl.quzrtz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CorrespondingDataServiceImpl extends QuartzJobBean{
	
	

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		
	}
	
	/**
	 * @Info更新群分类的数据
	 * @return
	 */
    public void correspondingData(){
		
	}
}
