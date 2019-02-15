package com.yishenxiao.commons.service.impl.quzrtz;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.yishenxiao.commons.beans.IpAddr;
import com.yishenxiao.commons.service.IpAddrService;
import com.yishenxiao.commons.utils.SpringContextUtils;

@DisallowConcurrentExecution
public class UnLockIpServiceImpl  extends QuartzJobBean {
	
	private IpAddrService ipAddrService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		unLockBean();
		unLockIp();
	}

	public void unLockBean(){
		if(ipAddrService==null){
			ipAddrService = (IpAddrService)SpringContextUtils.getBean("ipAddrService");
		}
	}
	
	private void unLockIp() {
		List<IpAddr> ipAddrList = ipAddrService.queryByIpData();
		for(int i=0;i<ipAddrList.size();i++){
		  ipAddrService.unLockIp(ipAddrList.get(i));
		}
	}

}
