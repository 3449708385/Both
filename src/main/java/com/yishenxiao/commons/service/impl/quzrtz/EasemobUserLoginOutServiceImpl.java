package com.yishenxiao.commons.service.impl.quzrtz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.yishenxiao.commons.utils.EasemobUtils;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.service.UserService;

@DisallowConcurrentExecution
public class EasemobUserLoginOutServiceImpl extends QuartzJobBean{

	private UserService userService;
	
	private static Logger logger = LoggerFactory.getLogger(EasemobUserLoginOutServiceImpl.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		getBean();
		userLoginOut();
	}
	
	private void getBean() {
		if(userService==null){
			userService = (UserService) SpringContextUtils.getBean("userService");
	    }
	}
	
	//调用环信用户下线机制
	private void userLoginOut(){
		int poolSize=1;//线程数
		//获取数据库btc_name数据		
		List<User> userList = userService.queryByType(0);
		if(userList.size()==0){
			return;
		}
		if(userList.size()>=100000){
			poolSize=10;//线程数	
		}else if(userList.size()>=50000){
			poolSize=5;//线程数	
		}else if(userList.size()>=30000){
			poolSize=3;//线程数	
		}else{
			poolSize=1;//线程数	
		}
		logger.info("user count :"+ userList.size());
		//为线程分配数据
		int listSize = (int) Math.ceil((double)userList.size()/poolSize);
		List<List<User>> listFP = new ArrayList<List<User>>(poolSize);
		for(int j=0;j<poolSize;j++){
			List<User> tempList = new ArrayList<User>();
			for(int i=j*listSize;i<(listSize+listSize*j);i++){
				if(i < userList.size()){
				   tempList.add(userList.get(i));
				}
			}
			listFP.add(tempList);
		}
		ExecutorService executorService= Executors.newFixedThreadPool(poolSize); 
		for(int i=0;i<poolSize;i++){
			executorService.submit(new EasemobUserLoginOutServiceImpl.MyRunnable(listFP.get(i)));
		}
		//关闭线程池线程
		executorService.shutdown();
	}
    

	/**
	 * 
	 * @author mgp
	 * @info  获取bittrex数据线程 
	 */
	class MyRunnable implements Runnable{
        private List<User> userList;
		public List<User> getUserList() {
			return userList;
		}
		public void setUserList(List<User> userList) {
			this.userList = userList;
		}
		@Override
		public void run() {
		  EasemobUtils.EasemobLoginout(userList);	
		}
		public MyRunnable(List<User> userList){
		   this.userList = userList;	
		}
	}
}
