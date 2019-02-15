

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.service.DataTestService;
import com.yishenxiao.commons.service.GroupCopyService;
import com.yishenxiao.commons.service.ProtertiesDataService;
import com.yishenxiao.commons.service.ShieldAccountService;
import com.yishenxiao.commons.service.TypeDataService;
import com.yishenxiao.commons.service.WechatFriendService;
import com.yishenxiao.commons.service.WechatGroupService;
import com.yishenxiao.commons.service.WechatMemberService;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.SpringContextUtils;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.GroupCategoryService;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserRelationGroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

public class UserTest2 {

	private static Logger logger = LoggerFactory.getLogger(UserTest2.class);
	
	@Autowired(required=false)@Qualifier("dataTestService")
	private DataTestService dataTestService;
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("groupCategoryService")
	private GroupCategoryService groupCategoryService;
	
	@Autowired(required=false)@Qualifier("wechatGroupService")
	private WechatGroupService wechatGroupService;
	
	@Autowired(required=false)@Qualifier("wechatFriendService")
	private WechatFriendService wechatFriendService;
	
	@Autowired(required=false)@Qualifier("wechatMemberService")
	private WechatMemberService wechatMemberService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("userRelationGroupService")
	private UserRelationGroupService userRelationGroupService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisService;
	
	@Autowired(required=false)@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Autowired(required=false)@Qualifier("groupCopyService")
	private GroupCopyService groupCopyService;
	
	@Autowired(required=false)@Qualifier("typeDataService")
	private TypeDataService typeDataService;
	
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
    private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required = false)
	@Qualifier("protertiesDataService")
	private ProtertiesDataService protertiesDataService;
	
	@Autowired(required=false)@Qualifier("shieldAccountService")
	private ShieldAccountService shieldAccountService;
	/**
	 * @Info 测试
	 * @return
	 */
	@Test
    public  void groupData(){
		int eventCounts=10000;
		//群成员
		int memberCounts=userScheduleService.queryByCount();
		int bCount= memberCounts/eventCounts+1;		
		for(int m=0;m<bCount;m++){
			List<UserSchedule> userScheduleList = userScheduleService.queryByTempUserHead(m*eventCounts,eventCounts);
			int poolSize=Integer.parseInt(PropertiesUtils.getInfoConfigProperties().getProperty("myCallSize"));
			//为线程分配数据
			int listSize = (int) Math.ceil((double)userScheduleList.size()/poolSize);
			List<List<UserSchedule>> listFP = new ArrayList<List<UserSchedule>>(poolSize);
			for(int j=0;j<poolSize;j++){
				List<UserSchedule> tempList = new ArrayList<UserSchedule>();
				for(int i=j*listSize;i<(listSize+listSize*j);i++){
					if(i < userScheduleList.size()){
					   tempList.add(userScheduleList.get(i));
					}
				}
				listFP.add(tempList);
			}
			ExecutorService executorService= Executors.newFixedThreadPool(poolSize);  
			List<Future<Integer>> future = null;
			List<MyCallabletet> myCallableExec = new ArrayList<MyCallabletet>();		
			for(int i=0;i<poolSize;i++){
				Map<String, Object> myCallMap = new HashMap<String, Object>();
				myCallMap.put("userScheduleList", listFP.get(i));
				myCallableExec.add(new MyCallabletet(myCallMap));
			}
			try {
				future=executorService.invokeAll(myCallableExec);
			} catch (InterruptedException e) {
				logger.error("user: 修改头像线程运行失败！");
			}
			for(Future<Integer> futuretemp:future){
				Integer temp=0;
				try {
					temp = futuretemp.get();
				} catch (Exception e) {
					logger.error("获取线程结果出现异常！"+e);
				} 
				if(temp==0){
					logger.error("fail");
				}
			} 		
		}
	}
}


class MyCallabletet implements Callable<Integer>{
	private static Logger logger = LoggerFactory.getLogger(MyCallabletet.class);
	private UserScheduleService userScheduleService;
	private Map<String,Object> map = new HashMap<String,Object>();
	public MyCallabletet(Map<String,Object> map){
		this.map=map;
	}
	
	private void getBean(){
		userScheduleService=(UserScheduleService) SpringContextUtils.getBean("userScheduleService");
	}
	
	@Override
	public Integer call() throws Exception {
		getBean();
		List<UserSchedule> userScheduleList = (List<UserSchedule>) map.get("userScheduleList");
		for(int i=0;i<userScheduleList.size();i++){
			Random rad = new Random();
			int temp =rad.nextInt(15);
			if(temp==0){
				temp =15;
			}
			String head="http://"+PropertiesUtils.getInfoConfigProperties().getProperty("info.ip")+":"+PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort")+"/currencyLogo/"+temp+".png";	
		    int c=userScheduleService.updateHeadPriture(head, userScheduleList.get(i).getUserid());
		    if(c!=1){
		    	return 0;
		    }
		}
		return 1;
	}
	
}
