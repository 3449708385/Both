package com.yishenxiao.digitalwallet.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yishenxiao.commons.utils.HttpClientUtils;
import com.yishenxiao.commons.utils.InternationalMobilePhoneNumberUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.QRCodeUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.digitalwallet.beans.CoinsList;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.MoneyData;
import com.yishenxiao.digitalwallet.beans.OverTheCounterTransferBean;
import com.yishenxiao.digitalwallet.beans.RealTransferAccountsDetails;
import com.yishenxiao.digitalwallet.beans.RedEnvelopes;
import com.yishenxiao.digitalwallet.beans.TransferAccountsDetails;
import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserRedEnvelopes;
import com.yishenxiao.digitalwallet.beans.UserTransferAccountsDetails;
import com.yishenxiao.digitalwallet.beans.UserWithdrawCashInfo;
import com.yishenxiao.digitalwallet.beans.WalletData;
import com.yishenxiao.digitalwallet.service.CoinsListService;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.MoneyDataService;
import com.yishenxiao.digitalwallet.service.RealTransferAccountsDetailsService;
import com.yishenxiao.digitalwallet.service.RedEnvelopesService;
import com.yishenxiao.digitalwallet.service.TransferAccountsDetailsService;
import com.yishenxiao.digitalwallet.service.UserDigitalAddrService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.UserRedEnvelopesService;
import com.yishenxiao.digitalwallet.service.UserTransferAccountsDetailsService;
import com.yishenxiao.digitalwallet.service.UserWithdrawCashInfoService;
import com.yishenxiao.digitalwallet.service.WalletThingService;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.service.UserService;

import net.sf.json.JSONObject;

@Service("walletThingService")
public class WalletThingServiceImpl implements WalletThingService{
	
	@Autowired(required=false)@Qualifier("userTransferAccountsDetailsService")
	private UserTransferAccountsDetailsService userTransferAccountsDetailsService;
	
	@Autowired(required=false)@Qualifier("userWithdrawCashInfoService")
    private UserWithdrawCashInfoService userWithdrawCashInfoService;
	
	@Autowired(required=false)@Qualifier("realTransferAccountsDetailsService")
    private RealTransferAccountsDetailsService realTransferAccountsDetailsService;
	
	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
	private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("redEnvelopesService")
	private RedEnvelopesService redEnvelopesService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisService;
	
	@Autowired(required=false)@Qualifier("userRedEnvelopesService")
    private UserRedEnvelopesService userRedEnvelopesService;
	
	@Autowired(required=false)@Qualifier("userDigitalAddrService")
    private UserDigitalAddrService userDigitalAddrService;
	
	@Autowired(required=false)@Qualifier("transferAccountsDetailsService")
    private TransferAccountsDetailsService transferAccountsDetailsService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("moneyDataService")
    private MoneyDataService moneyDataService;
	
	@Autowired(required=false)@Qualifier("coinsListService")
    private CoinsListService coinsListService;
	
	private static Logger logger = LoggerFactory.getLogger(WalletThingServiceImpl.class);
	
	private static Lock lock = new ReentrantLock();// 锁对象 
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
    public Map<String,Object> usersSendRedPackets(Long userId, String currency, Double amount, Integer count, String rEDesc,  Long id){  
		Map<String,Object> map = new HashMap<String,Object>();
		//插入红包数据
		RedEnvelopes redEnvelopes = new RedEnvelopes();
		redEnvelopes.setAmount(new BigDecimal(amount.toString()).setScale(8, BigDecimal.ROUND_DOWN));
		redEnvelopes.setCount(count);
		redEnvelopes.setCurrency(currency);
		redEnvelopes.setUserid(userId);
		redEnvelopes.setRedesc(rEDesc);
		redEnvelopes.setCreatetime(new Date());
		redEnvelopes.setState(0);
		redEnvelopes.setUpdatetime(new Date());
		String userWallet=userId+new Date().getTime()+UUID.randomUUID().toString();
		redEnvelopes.setRediskey(userWallet);
		redEnvelopes.setUsecount(0);
		redEnvelopesService.insertRedEnvelopesData(redEnvelopes);//插入后会返回数据id
		redisService.opsForValue().set(userWallet, redEnvelopes, 24, TimeUnit.HOURS);
		//redisService.opsForValue().set(userWallet, redEnvelopes, 5, TimeUnit.MINUTES);
		map.put("dataKey", userWallet);
		map.put("redEnvelopesId", redEnvelopes.getId());
		//足减
		userDigitalCurrencyInfoService.updateDigitalcurrencybalance(userId, id, amount);
		return map;
   }
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
    public ReturnInfo transferOutAccounts(Long userId, String currencyName, Double monetary, String transferOutAddr, String transferDesc){
		ReturnInfo info = null;
		//查询地址
		List<UserDigitalAddr> userDigitalAddrList = userDigitalAddrService.queryAddr(transferOutAddr);
        if(userDigitalAddrList.size()>0){
        	//场内转
        	//更新转出账户数据，更新转入账户数据
        	DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currencyName);
        	UserDigitalCurrencyInfo userDigitalCurrencyInfo = userDigitalCurrencyInfoService.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), userDigitalAddrList.get(0).getUserid());
        	if(userDigitalCurrencyInfo!=null){
	        	userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userId, monetary, 1);
	        	userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userDigitalAddrList.get(0).getUserid(), monetary, 0);
        	}else{
         	   userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userId, monetary, 1);
        	   UserDigitalCurrencyInfo userDigitalCurrencyInfoTemp = new UserDigitalCurrencyInfo();
        	   userDigitalCurrencyInfoTemp.setUserid(userId);
        	   userDigitalCurrencyInfoTemp.setDigitalcurrencyid(digitalCurrencyInfo.getId());
        	   userDigitalCurrencyInfoTemp.setDigitalcurrencybalance(new BigDecimal(monetary.toString()).setScale(8, BigDecimal.ROUND_DOWN));
        	   userDigitalCurrencyInfoTemp.setBorrowedbalance(new BigDecimal(0));
        	   userDigitalCurrencyInfoTemp.setForeignaccountbalance(new BigDecimal(0));
        	   userDigitalCurrencyInfoService.insertUserDigitalCurrencyData(userDigitalCurrencyInfoTemp);
        	}
        	//插入转账记录2条
        	//转出方
        	TransferAccountsDetails transferAccountsDetails = new TransferAccountsDetails();
        	transferAccountsDetails.setCreatetime(new Date());
        	transferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
        	transferAccountsDetails.setMonetary(new BigDecimal(monetary.toString()).setScale(8, BigDecimal.ROUND_DOWN));
        	transferAccountsDetails.setState(2);
        	transferAccountsDetails.setType(1);
        	transferAccountsDetails.setForm(0);
        	transferAccountsDetails.setTransferdesc(transferDesc);
        	transferAccountsDetails.setFromuserid(userDigitalAddrList.get(0).getUserid());
        	transferAccountsDetails.setUpdatetime(new Date());
        	transferAccountsDetails.setUserid(userId);
        	transferAccountsDetailsService.insertData(transferAccountsDetails);
        	//转入方
        	TransferAccountsDetails transferAccountsDetails2 = new TransferAccountsDetails();
        	transferAccountsDetails2.setCreatetime(new Date());
        	transferAccountsDetails2.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
        	transferAccountsDetails2.setMonetary(new BigDecimal(monetary.toString()).setScale(8, BigDecimal.ROUND_DOWN));
        	transferAccountsDetails2.setState(2);
        	transferAccountsDetails2.setType(0);
        	transferAccountsDetails2.setForm(0);
        	transferAccountsDetails2.setTransferdesc(transferDesc);
        	transferAccountsDetails2.setUpdatetime(new Date());
        	transferAccountsDetails2.setFromuserid(userId);
        	transferAccountsDetails2.setUserid(userDigitalAddrList.get(0).getUserid());
        	transferAccountsDetailsService.insertData(transferAccountsDetails2);
        }else{
        	//场外转
        	DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currencyName);
        	//判断是大币种还是小币种
        	String result = "";
        	if(StringUtils.judgeBlank(digitalCurrencyInfo.getDigitalcurrencyaddr())){
        		//大币种 ETH
        		if(digitalCurrencyInfo.getDigitalcurrencyname().equals("ETH")){
	        		UserDigitalAddr userDigitalAddr = userDigitalAddrService.queryByData(userId, digitalCurrencyInfo.getId());
	        		OverTheCounterTransferBean overTheCounterTransferBean = new OverTheCounterTransferBean();
	            	overTheCounterTransferBean.setCoin("eth");
	            	overTheCounterTransferBean.setFrom(userDigitalAddr.getCurrencyaddr());
	            	overTheCounterTransferBean.setTo(transferOutAddr);
	            	overTheCounterTransferBean.setNetwork("0");
	            	overTheCounterTransferBean.setValue(monetary.toString());
	            	overTheCounterTransferBean.setIndex(userDigitalAddr.getUserid().toString());
	            	String jsonStr = JsonUtils.toJson(overTheCounterTransferBean);
	            	try {
						result = HttpClientUtils.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")+"eth/sendEthTransactionByHD", jsonStr);
						if(result.contains("error")){
							JSONObject JObj = JSONObject.fromObject(result);
							info = ReturnInfo.error(500, JObj.get("error"));
						}else{
							//扣款
							userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userId, monetary, 1);
							//转出方
				        	TransferAccountsDetails transferAccountsDetails = new TransferAccountsDetails();
				        	transferAccountsDetails.setCreatetime(new Date());
				        	transferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
				        	transferAccountsDetails.setMonetary(new BigDecimal(monetary.toString()).setScale(8, BigDecimal.ROUND_DOWN));
				        	transferAccountsDetails.setState(2);
				        	transferAccountsDetails.setType(1);
				        	transferAccountsDetails.setTransferdesc(transferDesc);
				        	transferAccountsDetails.setForm(1);
				        	transferAccountsDetails.setOtcstatetransferstatuscode(JSONObject.fromObject(result).getString("result"));
				        	transferAccountsDetails.setUpdatetime(new Date());
				        	transferAccountsDetails.setUserid(userId);
				        	transferAccountsDetailsService.insertData(transferAccountsDetails);
				        	return ReturnInfo.ok();
						}
					} catch (Exception e) {
						logger.error("场外转账失败！"+e);
					}
            	}else{
            		//btc等大币种
            	}
        	}else{
        		//小币种
        		DigitalCurrencyInfo digitalCurrencyInfo2 = digitalCurrencyInfoService.queryByDigitalCurrencyId(digitalCurrencyInfo.getDigitalcurrencyinfoid());
        		if(digitalCurrencyInfo2.getDigitalcurrencyname().equals("ETH")){
        			//查询用户地址
    				DigitalCurrencyInfo digitalCurrencyInfo3 = digitalCurrencyInfoService.queryByDigitalcurrencyname("ETH");
    				UserDigitalAddr userDigitalAddr2 = userDigitalAddrService.queryByData(userId, digitalCurrencyInfo3.getId());
        			OverTheCounterTransferBean overTheCounterTransferBean = new OverTheCounterTransferBean();
        			overTheCounterTransferBean.setCoin("eth");
        			overTheCounterTransferBean.setFrom(userDigitalAddr2.getCurrencyaddr());
        			overTheCounterTransferBean.setTo(transferOutAddr);
	            	overTheCounterTransferBean.setNetwork("0");
	            	overTheCounterTransferBean.setValue(new BigDecimal(String.valueOf(monetary*Math.pow(10, digitalCurrencyInfo.getTokendecimal()))).toString());
	            	overTheCounterTransferBean.setIndex(userId.toString());
	            	overTheCounterTransferBean.setToken_address(digitalCurrencyInfo.getDigitalcurrencyaddr());
	            	overTheCounterTransferBean.setToken_identifier(digitalCurrencyInfo.getDigitalcurrencyname());
	            	overTheCounterTransferBean.setDecimals(digitalCurrencyInfo.getTokendecimal().toString());
	            	String jsonStr = JsonUtils.toJson(overTheCounterTransferBean);
	            	try {
						result = HttpClientUtils.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")+"eth/sendEthTransactionByHD", jsonStr);
						if(result.contains("error")){
							JSONObject JObj = JSONObject.fromObject(result);
							info = ReturnInfo.error(500, JObj.get("error"));
						}else{
							//扣款
							userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userId, monetary, 1);
							//转出方
				        	TransferAccountsDetails transferAccountsDetails = new TransferAccountsDetails();
				        	transferAccountsDetails.setCreatetime(new Date());
				        	transferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
				        	transferAccountsDetails.setMonetary(new BigDecimal(monetary.toString()).setScale(8, BigDecimal.ROUND_DOWN));
				        	transferAccountsDetails.setState(2);
				        	transferAccountsDetails.setType(1);
				        	transferAccountsDetails.setTransferdesc(transferDesc);
				        	transferAccountsDetails.setForm(1);
				        	transferAccountsDetails.setOtcstatetransferstatuscode(JSONObject.fromObject(result).getString("result"));
				        	transferAccountsDetails.setUpdatetime(new Date());
				        	transferAccountsDetails.setUserid(userId);
				        	transferAccountsDetailsService.insertData(transferAccountsDetails);
				        	return ReturnInfo.ok();
						}
					} catch (Exception e) {
						logger.error("场外转账失败！"+e);
					}
        		}
        	}
        }		
		return info;
   }

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public UserRedEnvelopes usersRobbingRedPackets(Long userId, Long redEnvelopesId, final String dataKey) {
		UserRedEnvelopes userRedEnvelopes = new UserRedEnvelopes();
		//查询红包金额
		final RedEnvelopes redEnvelopes = (RedEnvelopes)redisService.opsForValue().get(dataKey);
		if(redEnvelopes.getCount()>0){
			if(redEnvelopes.getCount()>1){
				//随机红包金额
				Random random=new Random();
				Double monery = random.nextDouble()*redEnvelopes.getAmount().doubleValue()*0.55;
				BigDecimal b = new BigDecimal(monery.toString());  
				monery = b.setScale(8, BigDecimal.ROUND_DOWN).doubleValue();
				userRedEnvelopes.setCreatetime(new Date());
				userRedEnvelopes.setMonery(new BigDecimal(monery.toString()));
				userRedEnvelopes.setRedenvelopesid(redEnvelopes.getId());
				userRedEnvelopes.setUpdatetime(new Date());
				userRedEnvelopes.setUserid(userId);
				//红包金额与红包个数减少,
				lock.lock();
				RedEnvelopes redEnvelopestemp = (RedEnvelopes)redisService.opsForValue().get(dataKey);
				if(redEnvelopestemp.getCount()==0){
					return null;
				}
				final Double tempMonery=monery;
				SessionCallback sessionCallback = new SessionCallback() {
				    @Override
				    public Object execute(RedisOperations redisOperations) throws DataAccessException {
				        redisOperations.multi();
				        redEnvelopes.setCount(redEnvelopes.getCount()-1);
						redEnvelopes.setAmount(new BigDecimal(redEnvelopes.getAmount().doubleValue()-tempMonery));
						redisService.opsForValue().set(dataKey, redEnvelopes);
				        return redisOperations.exec();
				    }
				};
				redisService.execute(sessionCallback);
				lock.unlock();
				//如果用户已经拥有这个币
				DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(redEnvelopes.getCurrency());
				List<UserDigitalCurrencyInfo> userDigitalCurrencyInfoList= userDigitalCurrencyInfoService.queryByDigitalCurrencyIdList(digitalCurrencyInfo.getId(), userId);
				if(userDigitalCurrencyInfoList.size()>0){
				  //更新用户账户
				  userDigitalCurrencyInfoService.updatUserDigitalCurrencyData(userId, userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN), userDigitalCurrencyInfoList.get(0).getDigitalcurrencyid());
				  //更新红包数据
				  redEnvelopesService.updateData(redEnvelopesId,userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN),redEnvelopes.getCount());
				}else{
					//添加新币数据
					UserDigitalCurrencyInfo userDigitalCurrencyInfo = new UserDigitalCurrencyInfo();
					userDigitalCurrencyInfo.setDigitalcurrencybalance(userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN));
					userDigitalCurrencyInfo.setDigitalcurrencyid(digitalCurrencyInfo.getId());
					userDigitalCurrencyInfo.setUserid(userId);
					userDigitalCurrencyInfo.setBorrowedbalance(new BigDecimal(0));
					userDigitalCurrencyInfo.setForeignaccountbalance(new BigDecimal(0));
					userDigitalCurrencyInfoService.insertUserDigitalCurrencyData(userDigitalCurrencyInfo);
					//更新红包数据
					redEnvelopesService.updateData(redEnvelopesId,userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN),redEnvelopes.getCount());
					
				}
			}else{
				userRedEnvelopes.setCreatetime(new Date());
				userRedEnvelopes.setMonery(redEnvelopes.getAmount().setScale(8, BigDecimal.ROUND_DOWN));
				userRedEnvelopes.setRedenvelopesid(redEnvelopes.getId());
				userRedEnvelopes.setUpdatetime(new Date());
				userRedEnvelopes.setUserid(userId);
				//更新用户账户
				DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(redEnvelopes.getCurrency());
				List<UserDigitalCurrencyInfo> userDigitalCurrencyInfoList= userDigitalCurrencyInfoService.queryByDigitalCurrencyIdList(digitalCurrencyInfo.getId(), userId);
				if(userDigitalCurrencyInfoList.size()>0){
				  //更新用户账户
				  userDigitalCurrencyInfoService.updatUserDigitalCurrencyData(userId, userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN), userDigitalCurrencyInfoList.get(0).getDigitalcurrencyid());
				  //更新红包数据
				  redEnvelopesService.updateData(redEnvelopesId,userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN),redEnvelopes.getCount());
				}else{
					//添加新币数据
					UserDigitalCurrencyInfo userDigitalCurrencyInfo = new UserDigitalCurrencyInfo();
					userDigitalCurrencyInfo.setDigitalcurrencybalance(userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN));
					userDigitalCurrencyInfo.setDigitalcurrencyid(digitalCurrencyInfo.getId());
					userDigitalCurrencyInfo.setBorrowedbalance(new BigDecimal(0));
					userDigitalCurrencyInfo.setForeignaccountbalance(new BigDecimal(0));
					userDigitalCurrencyInfo.setUserid(userId);
					userDigitalCurrencyInfoService.insertUserDigitalCurrencyData(userDigitalCurrencyInfo);
					//更新红包数据
					redEnvelopesService.updateData(redEnvelopesId,userRedEnvelopes.getMonery().setScale(8, BigDecimal.ROUND_DOWN),redEnvelopes.getCount());
				}
				lock.lock();
				RedEnvelopes redEnvelopestemp = (RedEnvelopes)redisService.opsForValue().get(dataKey);
				if(redEnvelopestemp.getCount()==0){
					return null;
				}
				//红包金额与红包个数减少,
				SessionCallback sessionCallback = new SessionCallback() {
				    @Override
				    public Object execute(RedisOperations redisOperations) throws DataAccessException {
				        redisOperations.multi();
				        redEnvelopes.setCount(redEnvelopes.getCount()-1);
						redEnvelopes.setAmount(new BigDecimal(0));
						redisService.opsForValue().set(dataKey, redEnvelopes);
				        return redisOperations.exec();
				    }
				};
				redisService.execute(sessionCallback);
				lock.unlock();
			}
		}else{
			return null;
		}
		return userRedEnvelopes;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public int transferIntoAccounts(String userAddr, String currency, Double monetary) {
		List<UserDigitalAddr> userDigitalAddrList = userDigitalAddrService.queryAddr(userAddr);
		List<DigitalCurrencyInfo> digitalCurrencyInfoList= digitalCurrencyInfoService.queryByDigitalcurrencynameForList(currency);
		userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfoList.get(0).getId(), userDigitalAddrList.get(0).getUserid(), monetary, 0);
		return 1;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public int changeOutPhoneAccounts(Long userId, String currencyName, Double monetary, String phone, String transferDesc) {
		List<User> userList = userService.queryUser(phone);
		DigitalCurrencyInfo digitalCurrencyInfo = digitalCurrencyInfoService.queryByDigitalcurrencyname(currencyName);
		//更新转出账户数据，更新转入账户数据
		UserDigitalCurrencyInfo userDigitalCurrencyInfo = userDigitalCurrencyInfoService.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), userList.get(0).getId());
		if(userDigitalCurrencyInfo!=null){
    	   userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userId, monetary, 1);
    	   userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userList.get(0).getId(), monetary, 0);
    	}else{
    	   userDigitalCurrencyInfoService.updateByDigitalCurrency(digitalCurrencyInfo.getId(), userId, monetary, 1);
    	   UserDigitalCurrencyInfo userDigitalCurrencyInfoTemp = new UserDigitalCurrencyInfo();
    	   userDigitalCurrencyInfoTemp.setUserid(userList.get(0).getId());
    	   userDigitalCurrencyInfoTemp.setDigitalcurrencyid(digitalCurrencyInfo.getId());
    	   userDigitalCurrencyInfoTemp.setDigitalcurrencybalance(new BigDecimal(String.valueOf(monetary)).setScale(8, BigDecimal.ROUND_DOWN));
    	   userDigitalCurrencyInfoTemp.setBorrowedbalance(new BigDecimal(0));
    	   userDigitalCurrencyInfoTemp.setForeignaccountbalance(new BigDecimal(0));
    	   userDigitalCurrencyInfoService.insertUserDigitalCurrencyData(userDigitalCurrencyInfoTemp);
    	}
		//插入转账记录2条
    	//转出方
    	TransferAccountsDetails transferAccountsDetails = new TransferAccountsDetails();
    	transferAccountsDetails.setCreatetime(new Date());
    	transferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
    	transferAccountsDetails.setMonetary(new BigDecimal(String.valueOf(monetary)).setScale(8, BigDecimal.ROUND_DOWN));
    	transferAccountsDetails.setFromuserid(userList.get(0).getId());
    	transferAccountsDetails.setTransferdesc(transferDesc);
    	transferAccountsDetails.setState(2);
    	transferAccountsDetails.setType(1);
    	transferAccountsDetails.setForm(0);
    	transferAccountsDetails.setUpdatetime(new Date());
    	transferAccountsDetails.setUserid(userId);
    	transferAccountsDetailsService.insertData(transferAccountsDetails);
    	//转入方
    	TransferAccountsDetails transferAccountsDetails2 = new TransferAccountsDetails();
    	transferAccountsDetails2.setCreatetime(new Date());
    	transferAccountsDetails2.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
    	transferAccountsDetails2.setMonetary(new BigDecimal(String.valueOf(monetary)).setScale(8, BigDecimal.ROUND_DOWN));
    	transferAccountsDetails2.setTransferdesc(transferDesc);
    	transferAccountsDetails2.setState(2);
    	transferAccountsDetails2.setType(0);
    	transferAccountsDetails2.setForm(0);
    	transferAccountsDetails2.setFromuserid(userId);
    	transferAccountsDetails2.setUpdatetime(new Date());
    	transferAccountsDetails2.setUserid(userList.get(0).getId());
    	transferAccountsDetailsService.insertData(transferAccountsDetails2);
		return 0;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public void accountOperation(List<UserDigitalAddr> userDigitalAddr, Double foreignAccount, Double borrowFrom) {	
		userDigitalCurrencyInfoService.updateUserData(userDigitalAddr.get(0).getUserid(), userDigitalAddr.get(0).getDigitalcurrencyinfoid(), foreignAccount, borrowFrom);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = {
			Exception.class })
	public ReturnInfo balanceInquiry(Long userId) {
		User user = userService.queryByUserId(userId);
		if (user == null) {
			return ReturnInfo.error("参数错误！");
		}
		// 查询该用户未处理的提现记录
		List<UserWithdrawCashInfo> userWithdrawCashInfoList = userWithdrawCashInfoService
				.queryUndisposedInfoByUserId(userId);
		if (!userWithdrawCashInfoList.isEmpty()) {
			// 真实转账记录
			RealTransferAccountsDetails realTransferAccountsDetails = null;
			String result = "";
			String receiptResult = "";
			JSONObject jsonResult = null;
			JSONObject jsonReceiptResult = null;
			// 真实转账记录状态1
			String realTransferStatus = "";
			// 真实转账记录状态2
			String blockHash = "";
			// 退还金额
			BigDecimal returnAmount = new BigDecimal("0");
			// 真实手续费
			BigDecimal realTransferFee = null;
			BigDecimal userDigitalCurrencyBalance = null;
			UserDigitalCurrencyInfo userDigitalCurrencyInfo = null;
			for (UserWithdrawCashInfo userWithdrawCashInfo : userWithdrawCashInfoList) {
				if (!userWithdrawCashInfo.getHandlestatus()) {
					// 查询真实转账记录
					realTransferAccountsDetails = realTransferAccountsDetailsService
							.selectByPrimaryKey(userWithdrawCashInfo.getRealtransferaccountsid());
					if (!realTransferAccountsDetails.getHandlestatus()) {
						try {
							// 查询真实转账记录信息
							receiptResult = queryReceiptRealTransferInfoETH(
									realTransferAccountsDetails.getTransfertxhash());
						} catch (Exception e) {
							logger.error("查询真实转账记录失败！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
						// receiptResult为空表示真实转账记录未确认
						if (!StringUtils.judgeBlank(receiptResult)) {
							if (!"invalid".equals(receiptResult)) {
								if (!"null".equals(receiptResult)) {
									try {
										// 真实转账返回成功，查询记录
										result = queryRealTransferInfoETH(realTransferAccountsDetails.getTransfertxhash());
									} catch (Exception e) {
										logger.error("查询真实转账记录失败！");
										return ReturnInfo.error("请求超时，查询余额失败！");
									}
									if (!StringUtils.judgeBlank(result)) {
										if (!"invalid".equals(result)) {
											if (!"null".equals(result)) {
												jsonResult = JSONObject.fromObject(result);
												jsonReceiptResult = JSONObject.fromObject(receiptResult);
												realTransferStatus = jsonReceiptResult.getString("status");
												blockHash = jsonReceiptResult.getString("blockHash");
												// 只有当真实转账交易确认并且外账余额更新成功才会执行下面的操作
												if (!StringUtils.judgeBlank(realTransferStatus)
														&& !"null".equals(blockHash)) {
													// 判断该笔转账交易是否成功
													if ("0x0".equals(realTransferStatus)) {
														// 失败的转账交易需要退回提现金额
														returnAmount = realTransferAccountsDetails.getTransfervalue();
														// 修改真实转账记录为失败
														realTransferAccountsDetails.setTransferprogress((short) 3);
														// 修改用户提现记录为失败
														userWithdrawCashInfo.setWithdrawprogress((short) 3);
													} else if ("0x1".equals(realTransferStatus)) {
														// 修改真实转账记录为成功
														realTransferAccountsDetails.setTransferprogress((short) 2);
														// 修改用户提现记录为成功
														userWithdrawCashInfo.setWithdrawprogress((short) 2);
													} else {
														logger.error("查询真实转账记录出现未知status！");
														return ReturnInfo.error("请求超时，查询余额失败！");
													}
													// 修改真实转账记录为已处理
													realTransferAccountsDetails
													.setGasupperlimit(Long.parseLong(jsonResult.getString("gas")));
													realTransferAccountsDetails
													.setGasprice(jsonResult.getString("gasPrice"));
													realTransferAccountsDetails.setGasused(
															Long.parseLong(jsonReceiptResult.getString("gasUsed")));
													realTransferFee = getRealTransferFeeETH(
															jsonResult.getString("gasPrice"),
															jsonReceiptResult.getString("gasUsed"));
													realTransferAccountsDetails.setRealtransferfee(realTransferFee);
													realTransferAccountsDetails.setHandlestatus(true);
													realTransferAccountsDetails.setUpdatetime(new Date());
													realTransferAccountsDetailsService
													.updateByPrimaryKey(realTransferAccountsDetails);
													// 修改用户提现记录为已处理
													userWithdrawCashInfo.setHandlestatus(true);
													userWithdrawCashInfo.setUpdatetime(new Date());
													userWithdrawCashInfoService.updateByPrimaryKey(userWithdrawCashInfo);
												}
											} else {
												// 失败的转账交易需要退回提现金额
												returnAmount = realTransferAccountsDetails.getTransfervalue();
												// 修改真实转账记录为失败
												realTransferAccountsDetails.setTransferprogress((short) 3);
												// 修改用户提现记录为失败
												userWithdrawCashInfo.setWithdrawprogress((short) 3);
												realTransferAccountsDetails.setHandlestatus(true);
												realTransferAccountsDetails.setUpdatetime(new Date());
												realTransferAccountsDetailsService
												.updateByPrimaryKey(realTransferAccountsDetails);
												// 修改用户提现记录为已处理
												userWithdrawCashInfo.setHandlestatus(true);
												userWithdrawCashInfo.setUpdatetime(new Date());
												userWithdrawCashInfoService.updateByPrimaryKey(userWithdrawCashInfo);
											}
											// 是否需要退还金额
											if (returnAmount.compareTo(BigDecimal.ZERO) == 1) {
												// 更新用户余额
												userDigitalCurrencyInfo = userDigitalCurrencyInfoService
														.queryByDigitalCurrencyId(
																userWithdrawCashInfo.getDigitalcurrencyinfoid(), userId);
												userDigitalCurrencyBalance = userDigitalCurrencyInfo
														.getDigitalcurrencybalance().add(returnAmount);
												userDigitalCurrencyInfo
												.setDigitalcurrencybalance(userDigitalCurrencyBalance);
												userDigitalCurrencyInfoService.updateByPrimaryKey(userDigitalCurrencyInfo);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		// 生成二维码的路径
		String headicon = PropertiesUtils.getInfoConfigProperties().getProperty("user.qrcode.path");
		File file = new File(headicon);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 获取所有的数字货币信息
		List<DigitalCurrencyInfo> digitalCurrencyInfoList = digitalCurrencyInfoService.queryByData();
		// 用户数字货币余额信息
		UserDigitalCurrencyInfo userDigitalCurrencyInfo = null;
		// 上级币种信息
		DigitalCurrencyInfo superDigitalCurrencyInfo = null;
		// 一级币种信息
		DigitalCurrencyInfo proxyDigitalCurrencyInfo = null;
		// 用户数字货币账户地址和当前真实余额结果集
		Map<String, Map<String, Object>> currencyDataMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> currencyMap = null;
		// 用户数字货币账户地址
		UserDigitalAddr userDigitalAddr = null;
		// 当前真实余额结果集
		String balanceResult = "";
		// 二维码图片路径
		String filePath = "";
		// 查询从公账转出到用户的未处理的手续费真实转账记录
		List<RealTransferAccountsDetails> poundageRealTransferAccountsDetailsList = null;
		// 真实公账转手续费转账记录
		RealTransferAccountsDetails poundageRealTransferAccountsDetails = null;
		// 真实转账查询结果1
		String poundageReceiptResult = "";
		// 真实转账查询结果2
		String poundageResult = "";
		JSONObject poundageJsonResult = null;
		JSONObject poundageJsonReceiptResult = null;
		// 真实转账记录状态1
		String poundageRealTransferStatus = "";
		// 真实转账记录状态2
		String poundageBlockHash = "";
		// 真实转账记录状态为null是否删除
		boolean poundageDeleted = false;
		// 查询抽取到公司未处理的真实转账记录
		List<RealTransferAccountsDetails> realTransferAccountsDetailsList = null;
		// 真实抽取转账记录
		RealTransferAccountsDetails realTransferAccountsDetails = null;
		// 真实转账查询结果1
		String receiptResult = "";
		// 真实转账查询结果2
		String result = "";
		JSONObject jsonResult = null;
		JSONObject jsonReceiptResult = null;
		// 真实转账记录状态1
		String realTransferStatus = "";
		// 真实转账记录状态2
		String blockHash = "";
		// 真实转账记录状态为null是否删除
		boolean deleted = false;
		// 第二次查询当前真实余额结果集
		String balanceResultAgain = "";
		// 用户当前真实余额
		BigDecimal realBalance = null;
		// 真实转账手续费
		BigDecimal realTransferFee = null;
		// 从公账转出到用户的手续费
		BigDecimal poundageTransferAccountsValue = new BigDecimal("0");
		// 未处理的并且成功或者失败的真实转账金额
		BigDecimal countTransferAccountsValue = new BigDecimal("0");
		// 未处理的并且成功或者失败的真实转账手续费(代币不需要计算这个手续费)
		BigDecimal countTransferAccountsFee = new BigDecimal("0");
		// 用户上一次记录的真实余额
		BigDecimal oldBalance = null;
		// 用户充值金额
		BigDecimal rechargeAmount = null;
		// 用户充值记录
		UserTransferAccountsDetails userTransferAccountsDetails = null;
		// 用户实时余额
		BigDecimal userDigitalCurrencyBalance = null;
		// 用于返回到前端的用户余额数据
		Map<String, Map<String, String>> userBalanceData = new HashMap<String, Map<String, String>>();
		Map<String, String> dataMap = null;
		// 获取公司账户地址
		String companyCurrencyAddr = "";
		// 用户手续费币种余额
		BigDecimal poundageRealBalance = null;
		// ETH手续费的最大值(此处需要修改成数据库保存，每种一级币种)
		BigDecimal maxFeeETH = new BigDecimal("0.00765");
		// 抽取转入到公账的金额
		BigDecimal extractAmount = null;
		// 真实转账txHash
		String txHash = "";
		for (DigitalCurrencyInfo digitalCurrencyInfo : digitalCurrencyInfoList) {
			// 不返回MVC和MDT的余额
			if ("MVC".equals(digitalCurrencyInfo.getDigitalcurrencyname()) || "MDT".equals(digitalCurrencyInfo.getDigitalcurrencyname())) {
				continue;
			}
			// 查询用户该数字货币的余额信息
			userDigitalCurrencyInfo = userDigitalCurrencyInfoService
					.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), userId);
			if (userDigitalCurrencyInfo != null) {
				if (0 != digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
					// 如果不是一级币种则查询出它的上级币种
					superDigitalCurrencyInfo = digitalCurrencyInfoService
							.queryById(digitalCurrencyInfo.getDigitalcurrencyinfoid());
					proxyDigitalCurrencyInfo = superDigitalCurrencyInfo;
				} else {
					proxyDigitalCurrencyInfo = digitalCurrencyInfo;
				}
				if (!currencyDataMap.containsKey(proxyDigitalCurrencyInfo.getDigitalcurrencyname())) {
					// 如果是一级币种，并且还没有查询，则查询用户该币的钱包地址和该地址下的余额，以及账户地址二维码
					userDigitalAddr = userDigitalAddrService.queryByData(userId, proxyDigitalCurrencyInfo.getId());
					if (userDigitalAddr != null) {
						// 查询用户真实账户地址余额
						try {
							balanceResult = queryUserRealBalance(userDigitalAddr.getCurrencyaddr(),
									proxyDigitalCurrencyInfo.getDigitalcurrencyname());
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("查询用户数字货币账户地址余额失败！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
						if (!StringUtils.judgeBlank(balanceResult)) {
							if (!"invalid".equals(balanceResult)) {
								if (!"null".equals(balanceResult)) {
									// 保存用户一级币种的地址和真实余额
									currencyMap = new HashMap<String, Object>();
									currencyMap.put("userDigitalAddr", userDigitalAddr);
									currencyMap.put("balanceResult", balanceResult);
									// 用户账户地址二维码图片路径
									filePath = headicon + userId + proxyDigitalCurrencyInfo.getId() + ".png";
									if (!new File(filePath).exists()) {
										try {
											QRCodeUtils.generateQRCode(userDigitalAddr.getCurrencyaddr(), 300, 300, "png",
													userId.toString() + proxyDigitalCurrencyInfo.getId().toString());
										} catch (Exception e) {
											logger.error("创建用户账户地址二维码失败！");
											return ReturnInfo.error("请求超时，查询余额失败！");
										}
									}
									filePath = filePath.substring(filePath.lastIndexOf("/"));
									filePath = PropertiesUtils.getInfoConfigProperties().getProperty("info.qrcodeAddr")
											+ "/qrcode" + filePath;
									currencyMap.put("filePath", filePath);
									currencyDataMap.put(proxyDigitalCurrencyInfo.getDigitalcurrencyname(), currencyMap);
								} else {
									logger.error("查询用户数字货币账户地址余额失败！");
									return ReturnInfo.error("请求超时，查询余额失败！");
								}
							} else {
								logger.error("查询用户数字货币账户地址余额失败！");
								return ReturnInfo.error("请求超时，查询余额失败！");
							}
						} else {
							logger.error("查询用户数字货币账户地址余额失败！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
					} else {
						logger.error("查询用户数字货币账户地址失败！");
						return ReturnInfo.error("请求超时，查询余额失败！");
					}
				}
				// 只有一级币种才有从公账转出到用户的手续费
				if (0 == digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
					// 查询是否有从公账转账到用户的手续费
					poundageRealTransferAccountsDetailsList = realTransferAccountsDetailsService
							.queryUndisposedPoundageInfoByUserIdAndDigitalCurrencyId(userId,
									proxyDigitalCurrencyInfo.getId());
					if (!poundageRealTransferAccountsDetailsList.isEmpty()) {
						// 控制每个用户只存在一条手续费转账记录
						poundageRealTransferAccountsDetails = poundageRealTransferAccountsDetailsList.get(0);
						try {
							// 查询真实转账记录信息
							poundageReceiptResult = queryReceiptRealTransferInfoETH(
									poundageRealTransferAccountsDetails.getTransfertxhash());
						} catch (Exception e) {
							logger.error("查询真实转账记录失败！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
						// poundageReceiptResult为空表示真实转账记录未确认
						if (!StringUtils.judgeBlank(poundageReceiptResult)) {
							if (!"invalid".equals(poundageReceiptResult)) {
								if (!"null".equals(poundageReceiptResult)) {
									try {
										// 真实转账返回成功，查询记录
										poundageResult = queryRealTransferInfoETH(
												poundageRealTransferAccountsDetails.getTransfertxhash());
									} catch (Exception e) {
										logger.error("查询真实转账记录失败！");
										return ReturnInfo.error("请求超时，查询余额失败！");
									}
									if (!StringUtils.judgeBlank(poundageResult)) {
										if (!"invalid".equals(poundageResult)) {
											if (!"null".equals(poundageResult)) {
												poundageJsonResult = JSONObject.fromObject(poundageResult);
												poundageJsonReceiptResult = JSONObject.fromObject(poundageReceiptResult);
												poundageRealTransferStatus = poundageJsonReceiptResult.getString("status");
												poundageBlockHash = poundageJsonReceiptResult.getString("blockHash");
											} else {
												poundageDeleted = true;
											}
										}
									}
								}
							}
						}
					}
				}
				// 查询抽取到公司未处理的真实转账记录
				realTransferAccountsDetailsList = realTransferAccountsDetailsService
						.queryUndisposedInfoByUserIdAndDigitalCurrencyId(userId, digitalCurrencyInfo.getId());
				if (!realTransferAccountsDetailsList.isEmpty()) {
					// 为了方便计算，控制每个用户只存在一条抽取记录
					realTransferAccountsDetails = realTransferAccountsDetailsList.get(0);
					try {
						// 查询真实转账记录信息
						receiptResult = queryReceiptRealTransferInfoETH(
								realTransferAccountsDetails.getTransfertxhash());
					} catch (Exception e) {
						logger.error("查询真实转账记录失败！");
						return ReturnInfo.error("请求超时，查询余额失败！");
					}
					// receiptResult为空表示真实转账记录未确认
					if (!StringUtils.judgeBlank(receiptResult)) {
						if (!"invalid".equals(receiptResult)) {
							if (!"null".equals(receiptResult)) {
								try {
									// 真实转账返回成功，查询记录
									result = queryRealTransferInfoETH(realTransferAccountsDetails.getTransfertxhash());
								} catch (Exception e) {
									logger.error("查询真实转账记录失败！");
									return ReturnInfo.error("请求超时，查询余额失败！");
								}
								if (!StringUtils.judgeBlank(result)) {
									if (!"invalid".equals(result)) {
										if (!"null".equals(result)) {
											jsonResult = JSONObject.fromObject(result);
											jsonReceiptResult = JSONObject.fromObject(receiptResult);
											realTransferStatus = jsonReceiptResult.getString("status");
											blockHash = jsonReceiptResult.getString("blockHash");
										} else {
											deleted = true;
										}
									}
								}
							}
						}
					}
				}
				// 从map中取出用户数字货币账户地址
				userDigitalAddr = (UserDigitalAddr) currencyDataMap
						.get(proxyDigitalCurrencyInfo.getDigitalcurrencyname()).get("userDigitalAddr");
				// 只要有状态为确认的转账交易记录，就重新查询真实余额
				if (!StringUtils.judgeBlank(poundageBlockHash) || !StringUtils.judgeBlank(blockHash)) {
					if (!"null".equals(poundageBlockHash) || !"null".equals(blockHash)) {
						// 第二次查询用户真实账户地址余额
						try {
							balanceResultAgain = queryUserRealBalance(userDigitalAddr.getCurrencyaddr(),
									proxyDigitalCurrencyInfo.getDigitalcurrencyname());
						} catch (Exception e) {
							logger.error("查询用户数字货币账户地址余额失败！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
						if (!StringUtils.judgeBlank(balanceResultAgain)) {
							if (!"invalid".equals(balanceResultAgain)) {
								if (!"null".equals(balanceResultAgain)) {
									// 更新currencyDataMap中的真实余额
									currencyDataMap.get(proxyDigitalCurrencyInfo.getDigitalcurrencyname())
											.put("balanceResult", balanceResultAgain);
								} else {
									logger.error("查询用户数字货币账户地址余额失败！");
									return ReturnInfo.error("请求超时，查询余额失败！");
								}
							} else {
								logger.error("查询用户数字货币账户地址余额失败！");
								return ReturnInfo.error("请求超时，查询余额失败！");
							}
						} else {
							logger.error("查询用户数字货币账户地址余额失败！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
					}
				}
				// 从map中取出用户数字货币账户余额结果集
				balanceResult = (String) currencyDataMap.get(proxyDigitalCurrencyInfo.getDigitalcurrencyname())
						.get("balanceResult");
				// 获取用户数字货币账户余额
				realBalance = new BigDecimal(
						JSONObject.fromObject(balanceResult).getString(digitalCurrencyInfo.getDigitalcurrencyname()));
				// 截取真实余额为8位数
				realBalance = realBalance.divide(new BigDecimal("1"), 8, BigDecimal.ROUND_DOWN);
				// 判断抽取记录是否有效
				if (poundageDeleted) {
					// 无效的抽取记录，直接处理掉
					poundageRealTransferAccountsDetails.setTransferprogress((short) 3);
					poundageRealTransferAccountsDetails.setHandlestatus(true);
					poundageRealTransferAccountsDetails.setUpdatetime(new Date());
					realTransferAccountsDetailsService.updateByPrimaryKey(poundageRealTransferAccountsDetails);
				} else {
					if (!StringUtils.judgeBlank(poundageRealTransferStatus) && !"null".equals(poundageBlockHash)) {
						// 判断该笔转账交易是否成功
						if ("0x0".equals(poundageRealTransferStatus)) {
							// 修改真实转账记录为失败
							poundageRealTransferAccountsDetails.setTransferprogress((short) 3);
						} else if ("0x1".equals(poundageRealTransferStatus)) {
							// 修改真实转账记录为成功
							poundageRealTransferAccountsDetails.setTransferprogress((short) 2);
							// 转账成功的需要加入充值金额计算公式
							poundageTransferAccountsValue = poundageRealTransferAccountsDetails.getTransfervalue();
						} else {
							logger.error("查询真实转账记录出现未知status！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
						// 修改真实转账记录为已处理
						poundageRealTransferAccountsDetails
								.setGasupperlimit(Long.parseLong(poundageJsonResult.getString("gas")));
						poundageRealTransferAccountsDetails.setGasprice(poundageJsonResult.getString("gasPrice"));
						poundageRealTransferAccountsDetails
								.setGasused(Long.parseLong(poundageJsonReceiptResult.getString("gasUsed")));
						poundageRealTransferAccountsDetails
								.setRealtransferfee(getRealTransferFeeETH(poundageJsonResult.getString("gasPrice"),
										poundageJsonReceiptResult.getString("gasUsed")));
						poundageRealTransferAccountsDetails.setHandlestatus(true);
						poundageRealTransferAccountsDetails.setUpdatetime(new Date());
						realTransferAccountsDetailsService.updateByPrimaryKey(poundageRealTransferAccountsDetails);
					}
				}
				if (deleted) {
					// 无效的抽取记录，直接处理掉
					realTransferAccountsDetails.setTransferprogress((short) 3);
					realTransferAccountsDetails.setHandlestatus(true);
					realTransferAccountsDetails.setUpdatetime(new Date());
					realTransferAccountsDetailsService.updateByPrimaryKey(realTransferAccountsDetails);
				} else {
					if (!StringUtils.judgeBlank(realTransferStatus) && !"null".equals(blockHash)) {
						// 真实转账手续费
						realTransferFee = getRealTransferFeeETH(jsonResult.getString("gasPrice"),
								jsonReceiptResult.getString("gasUsed"));
						// 判断该笔转账交易是否成功
						if ("0x0".equals(realTransferStatus)) {
							// 失败的抽取转账交易不退还手续费
							// 修改真实转账记录为失败
							realTransferAccountsDetails.setTransferprogress((short) 3);
						} else if ("0x1".equals(realTransferStatus)) {
							// 修改真实转账记录为成功
							realTransferAccountsDetails.setTransferprogress((short) 2);
							// 转账成功的需要加入充值金额计算公式
							countTransferAccountsValue = realTransferAccountsDetails.getTransfervalue();
							// 代币不需要计算手续费
							if (0 == digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
								countTransferAccountsFee = realTransferFee;
							}
						} else {
							logger.error("查询真实转账记录出现未知status！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
						// 修改真实转账记录为已处理
						realTransferAccountsDetails.setGasupperlimit(Long.parseLong(jsonResult.getString("gas")));
						realTransferAccountsDetails.setGasprice(jsonResult.getString("gasPrice"));
						realTransferAccountsDetails.setGasused(Long.parseLong(jsonReceiptResult.getString("gasUsed")));
						realTransferAccountsDetails.setRealtransferfee(realTransferFee);
						realTransferAccountsDetails.setHandlestatus(true);
						realTransferAccountsDetails.setUpdatetime(new Date());
						realTransferAccountsDetailsService.updateByPrimaryKey(realTransferAccountsDetails);
					}
				}
				// 查询完毕之后重置为空
				poundageDeleted = false;
				poundageRealTransferStatus = "";
				poundageBlockHash = "";
				deleted = false;
				realTransferStatus = "";
				blockHash = "";
				// 上一次记录的用户数字货币账户地址余额
				oldBalance = userDigitalCurrencyInfo.getForeignaccountbalance();
				// 计算充值金额
				rechargeAmount = getRechargeAmount(realBalance, oldBalance, poundageTransferAccountsValue,
						countTransferAccountsValue, countTransferAccountsFee);
				// 计算完毕之后重置为空
				poundageTransferAccountsValue = new BigDecimal("0");
				countTransferAccountsValue = new BigDecimal("0");
				countTransferAccountsFee = new BigDecimal("0");
				// 充值金额比0大则创建充值记录
				if (rechargeAmount.compareTo(BigDecimal.ZERO) == 1) {
					// 更新用户余额
					userDigitalCurrencyBalance = userDigitalCurrencyInfo.getDigitalcurrencybalance()
							.add(rechargeAmount);
					userDigitalCurrencyInfo.setDigitalcurrencybalance(userDigitalCurrencyBalance);
					// 创建充值记录
					userTransferAccountsDetails = new UserTransferAccountsDetails();
					userTransferAccountsDetails.setUserid(userId);
					userTransferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
					userTransferAccountsDetails.setTouserid(userId);
					userTransferAccountsDetails.setTransfertype((short) 3);
					userTransferAccountsDetails.setTransfervalue(rechargeAmount);
					userTransferAccountsDetails.setTransferstatus((short) 1);
					userTransferAccountsDetails.setRemark("充值");
					userTransferAccountsDetails.setCreatetime(new Date());
					userTransferAccountsDetails.setUpdatetime(new Date());
					userTransferAccountsDetails.setEnabled(true);
					userTransferAccountsDetails.setDeleted(false);
					userTransferAccountsDetailsService.insert(userTransferAccountsDetails);
				}
				// 记录用户此次真实余额(以供下一次计算使用)
				userDigitalCurrencyInfo.setForeignaccountbalance(realBalance);
				userDigitalCurrencyInfoService.updateByPrimaryKey(userDigitalCurrencyInfo);
				// 封装返回数据
				dataMap = new HashMap<String, String>();
				dataMap.put("balance", userDigitalCurrencyInfo.getDigitalcurrencybalance().toPlainString());
				dataMap.put("fee", digitalCurrencyInfo.getFee().toPlainString());
				dataMap.put("filePath", (String) currencyDataMap.get(proxyDigitalCurrencyInfo.getDigitalcurrencyname())
						.get("filePath"));
				dataMap.put("moneyAddr", userDigitalAddr.getCurrencyaddr());
				userBalanceData.put(digitalCurrencyInfo.getDigitalcurrencyname(), dataMap);
				// 进行抽取操作(抽取规则：没有抽取记录，用户真实余额达到设置的上限，如果是代币，还需要查询代币所使用的手续费币种余额是否够支付手续费)
				// 查询抽取到公司未处理的真实转账记录(只能存在一条抽取记录)
				realTransferAccountsDetailsList = realTransferAccountsDetailsService
						.queryUndisposedInfoByUserId(userId);
				if (realTransferAccountsDetailsList.isEmpty()) {
					// 用户真实余额如果大于或等于设置的上限，则发起转入到公账操作
					if (realBalance.compareTo(digitalCurrencyInfo.getRealbalancelimit()) != -1) {
						try {
							companyCurrencyAddr = getCompanyCurrencyAddr(
									proxyDigitalCurrencyInfo.getDigitalcurrencyname());
						} catch (Exception e) {
							logger.error("查询公司指定数字货币地址失败！");
							return ReturnInfo.error("请求超时，查询余额失败！");
						}
						// 代币需要查询所使用的手续费币种余额是否够支付手续费
						if (0 != digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
							// 用户手续费币种余额
							poundageRealBalance = new BigDecimal(JSONObject.fromObject(balanceResult)
									.getString(proxyDigitalCurrencyInfo.getDigitalcurrencyname()));
							if (poundageRealBalance.compareTo(new BigDecimal("0.0032013")) != -1) {
								// 抽取代币，抽取整数，留下小数点后面的
								extractAmount = new BigDecimal(realBalance.toBigInteger().toString());
							} else {
								// 判断是否存在未处理的公账转出手续费记录
								poundageRealTransferAccountsDetailsList = realTransferAccountsDetailsService
										.queryUndisposedPoundageInfoByUserIdAndDigitalCurrencyId(userId,
												proxyDigitalCurrencyInfo.getId());
								if (poundageRealTransferAccountsDetailsList.isEmpty()) {
									// 发起从公账转出手续费给用户
									try {
										txHash = transferFeeETH(userDigitalAddr.getCurrencyaddr(), maxFeeETH,
												proxyDigitalCurrencyInfo.getDigitalcurrencyname());
									} catch (Exception e) {
										logger.error("发起从公账转出手续费给用户失败！");
										return ReturnInfo.error("请求超时，查询余额失败！");
									}
									if (!StringUtils.judgeBlank(txHash)) {
										if (!"invalid".equals(txHash)) {
											if (!"null".equals(txHash)) {
												// 创建真实转账记录
												RealTransferAccountsDetails extractFeeRealTransferAccountsDetails = new RealTransferAccountsDetails();
												extractFeeRealTransferAccountsDetails.setUserid(userId);
												extractFeeRealTransferAccountsDetails
												.setDigitalcurrencyinfoid(proxyDigitalCurrencyInfo.getId());
												extractFeeRealTransferAccountsDetails.setTransfertype((short) 3);
												extractFeeRealTransferAccountsDetails.setTransferaddr(companyCurrencyAddr);
												extractFeeRealTransferAccountsDetails
												.setReceiptaddr(userDigitalAddr.getCurrencyaddr());
												extractFeeRealTransferAccountsDetails.setTransfertxhash(txHash);
												extractFeeRealTransferAccountsDetails.setGasupperlimit(0L);
												extractFeeRealTransferAccountsDetails.setGasprice("");
												extractFeeRealTransferAccountsDetails.setGasused(0L);
												extractFeeRealTransferAccountsDetails.setTransfervalue(maxFeeETH);
												extractFeeRealTransferAccountsDetails.setRealtransferfee(null);
												extractFeeRealTransferAccountsDetails.setTransferprogress((short) 1);
												extractFeeRealTransferAccountsDetails.setHandlestatus(false);
												extractFeeRealTransferAccountsDetails.setCreatetime(new Date());
												extractFeeRealTransferAccountsDetails.setUpdatetime(new Date());
												extractFeeRealTransferAccountsDetails.setEnabled(true);
												extractFeeRealTransferAccountsDetails.setDeleted(false);
												realTransferAccountsDetailsService
												.insert(extractFeeRealTransferAccountsDetails);
											} else {
												logger.error("txHash为null，发起从公账转出手续费给用户失败！");
											}
										} else {
											logger.error("txHash为空，发起从公账转出手续费给用户失败！");
										}
									} else {
										logger.error("txHash为空，发起从公账转出手续费给用户失败！");
									}
								}
							}
						} else {
							// 抽取一级币，留下可以支付手续费的最低金额
							extractAmount = realBalance.subtract(maxFeeETH);
						}
						if (extractAmount != null) {
							// 抽取金额大于0
							if (extractAmount.compareTo(BigDecimal.ZERO) == 1) {
								try {
									txHash = extractRealBalanceETH(userId.toString(), userDigitalAddr.getCurrencyaddr(),
											extractAmount, digitalCurrencyInfo,
											proxyDigitalCurrencyInfo.getDigitalcurrencyname());
								} catch (Exception e) {
									logger.error("从用户抽取到公账失败！");
									return ReturnInfo.error("请求超时，查询余额失败！");
								}
								if (!StringUtils.judgeBlank(txHash)) {
									if (!"invalid".equals(txHash)) {
										if (!"null".equals(txHash)) {
											// 创建真实转账记录
											RealTransferAccountsDetails extractRealTransferAccountsDetails = new RealTransferAccountsDetails();
											extractRealTransferAccountsDetails.setUserid(userId);
											extractRealTransferAccountsDetails
											.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
											extractRealTransferAccountsDetails.setTransfertype((short) 1);
											extractRealTransferAccountsDetails
											.setTransferaddr(userDigitalAddr.getCurrencyaddr());
											extractRealTransferAccountsDetails.setReceiptaddr(companyCurrencyAddr);
											extractRealTransferAccountsDetails.setTransfertxhash(txHash);
											extractRealTransferAccountsDetails.setGasupperlimit(0L);
											extractRealTransferAccountsDetails.setGasprice("");
											extractRealTransferAccountsDetails.setGasused(0L);
											extractRealTransferAccountsDetails.setTransfervalue(extractAmount);
											extractRealTransferAccountsDetails.setRealtransferfee(null);
											extractRealTransferAccountsDetails.setTransferprogress((short) 1);
											extractRealTransferAccountsDetails.setHandlestatus(false);
											extractRealTransferAccountsDetails.setCreatetime(new Date());
											extractRealTransferAccountsDetails.setUpdatetime(new Date());
											extractRealTransferAccountsDetails.setEnabled(true);
											extractRealTransferAccountsDetails.setDeleted(false);
											realTransferAccountsDetailsService.insert(extractRealTransferAccountsDetails);
										} else {
											logger.error("txHash为null，从用户抽取到公账失败！");
										}
									} else {
										logger.error("txHash为空，从用户抽取到公账失败！");
									}
								} else {
									logger.error("txHash为空，从用户抽取到公账失败！");
								}
							}
						}
					}
				}
			} else {
				logger.error("查询用户" + digitalCurrencyInfo.getDigitalcurrencyname() + "的余额信息失败！");
				return ReturnInfo.error("请求超时，查询余额失败！");
			}
		}
		if (userBalanceData.isEmpty()) {
			return ReturnInfo.error("请求超时，查询余额失败！");
		}
		// 封装返回数据
		List<WalletData> walletDataList = new ArrayList<WalletData>();
		WalletData walletData = null;
		// 图片地址ip
		String selfIp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ip");
		// 图片地址port
		String coinIconPort = PropertiesUtils.getInfoConfigProperties().getProperty("info.coinIconPort");
		// 查询数字货币市场价值
		List<MoneyData> moneyDataList = moneyDataService.queryByData();
		for (MoneyData moneyData : moneyDataList) {
			if ("MVC".equals(moneyData.getName()) || "MDT".equals(moneyData.getName())) {
				continue;
			}
			walletData = new WalletData();
			dataMap = userBalanceData.get(moneyData.getName());
			walletData.setId(moneyData.getId());
			walletData.setCoinName(moneyData.getCoinname());
			walletData.setName(moneyData.getName());
			walletData.setImageUrl("http://" + selfIp + ":" + coinIconPort + "/currencyLogo/"
					+ moneyData.getName().toLowerCase() + ".png");
			walletData.setBalance(dataMap.get("balance"));
			walletData.setFee(dataMap.get("fee"));
			walletData.setFilePath(dataMap.get("filePath"));
			walletData.setMoneyAddr(dataMap.get("moneyAddr"));
			walletData.setMarketPrice(moneyData.getMarketprice().toPlainString());
			walletDataList.add(walletData);
		}
		if (!walletDataList.isEmpty()) {
			return ReturnInfo.info(200, walletDataList);
		}
		return ReturnInfo.error("请求超时，查询余额失败！");
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = {
			Exception.class })
	public ReturnInfo userWithdrawCash(Long userId, DigitalCurrencyInfo digitalCurrencyInfo,
			DigitalCurrencyInfo superDigitalCurrencyInfo, UserDigitalCurrencyInfo userDigitalCurrencyInfo,
			String withdrawAddr, BigDecimal withdrawAmount, String remark) {
		User user = userService.queryByUserId(userId);
		if (user == null) {
			return ReturnInfo.error("参数错误！");
		}
		// 判断是否是二级币(如果之后有三级币，需要扩展此处)
		String currencyName = "";
		if (0 != digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
			currencyName = superDigitalCurrencyInfo.getDigitalcurrencyname();
		} else {
			currencyName = digitalCurrencyInfo.getDigitalcurrencyname();
		}
		// 发起提现请求
		String txHash = "";
		try {
			// 此处每种一级币都对应各自的提现方法
			if ("ETH".equals(currencyName)) {
				txHash = userWithdrawCashETH(withdrawAddr, withdrawAmount, digitalCurrencyInfo, currencyName);
			}
		} catch (Exception e) {
			logger.error("发起提现请求失败！");
			return ReturnInfo.error("请求超时，提现失败！");
		}
		// 获取公司地址
		String companyCurrencyAddr = "";
		try {
			companyCurrencyAddr = getCompanyCurrencyAddr(currencyName);
		} catch (Exception e) {
			logger.error("查询公司指定数字货币地址失败！");
			return ReturnInfo.error("请求超时，提现失败！");
		}
		if (!StringUtils.judgeBlank(txHash)) {
			if (!"invalid".equals(txHash)) {
				// 创建真实转账记录
				RealTransferAccountsDetails realTransferAccountsDetails = new RealTransferAccountsDetails();
				realTransferAccountsDetails.setUserid(userId);
				realTransferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
				realTransferAccountsDetails.setTransfertype((short) 2);
				realTransferAccountsDetails.setTransferaddr(companyCurrencyAddr);
				realTransferAccountsDetails.setReceiptaddr(withdrawAddr);
				realTransferAccountsDetails.setTransfertxhash(txHash);
				realTransferAccountsDetails.setGasupperlimit(0L);
				realTransferAccountsDetails.setGasprice("");
				realTransferAccountsDetails.setGasused(0L);
				realTransferAccountsDetails.setTransfervalue(withdrawAmount);
				realTransferAccountsDetails.setRealtransferfee(null);
				realTransferAccountsDetails.setTransferprogress((short) 1);
				realTransferAccountsDetails.setHandlestatus(false);
				realTransferAccountsDetails.setCreatetime(new Date());
				realTransferAccountsDetails.setUpdatetime(new Date());
				realTransferAccountsDetails.setEnabled(true);
				realTransferAccountsDetails.setDeleted(false);
				realTransferAccountsDetailsService.insert(realTransferAccountsDetails);
				// 创建用户提现记录
				UserWithdrawCashInfo userWithdrawCashInfo = new UserWithdrawCashInfo();
				userWithdrawCashInfo.setUserid(userId);
				userWithdrawCashInfo.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
				userWithdrawCashInfo.setRealtransferaccountsid(realTransferAccountsDetails.getId());
				userWithdrawCashInfo.setWithdrawaddr(withdrawAddr);
				userWithdrawCashInfo.setWithdrawamount(withdrawAmount);
				userWithdrawCashInfo.setWithdrawfee(digitalCurrencyInfo.getFee());
				userWithdrawCashInfo.setWithdrawprogress((short) 1);
				userWithdrawCashInfo.setHandlestatus(false);
				if (StringUtils.judgeBlank(remark)) {
					remark = "提现" + digitalCurrencyInfo.getDigitalcurrencyname();
				}
				userWithdrawCashInfo.setRemark(remark);
				userWithdrawCashInfo.setCreatetime(new Date());
				userWithdrawCashInfo.setUpdatetime(new Date());
				userWithdrawCashInfo.setEnabled(true);
				userWithdrawCashInfo.setDeleted(false);
				userWithdrawCashInfoService.insert(userWithdrawCashInfo);
				// 更新用户余额
				userDigitalCurrencyInfo.setDigitalcurrencybalance(userDigitalCurrencyInfo.getDigitalcurrencybalance()
						.subtract(withdrawAmount.add(digitalCurrencyInfo.getFee())));
				userDigitalCurrencyInfoService.updateByPrimaryKey(userDigitalCurrencyInfo);
				return ReturnInfo.info(200, "提现提交成功，处理中！");
			}
		}
		return ReturnInfo.error("请求超时，提现失败！");
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public ReturnInfo userTransfer(Long userId, DigitalCurrencyInfo digitalCurrencyInfo, DigitalCurrencyInfo superDigitalCurrencyInfo,
			UserDigitalCurrencyInfo userDigitalCurrencyInfo, String transferAddr, String userMobile, BigDecimal transferValue,
			String transferDesc) {
		BigDecimal userDigitalCurrencyBalance = null;
		Long toUserId = 0l;
		UserTransferAccountsDetails userTransferAccountsDetails = null;
		if (!StringUtils.judgeBlank(transferAddr) && StringUtils.judgeBlank(userMobile)) {
			// 查询地址,判断场内还是场外
			List<UserDigitalAddr> userDigitalAddrList = userDigitalAddrService.queryAddr(transferAddr);
			if (userDigitalAddrList.isEmpty()) {
				return ReturnInfo.error("暂不支持向其他钱包转账！");
				// 判断是否是二级币(如果之后有三级币，需要扩展此处)
				/*String currencyName = "";
				if (0 != digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
					currencyName = superDigitalCurrencyInfo.getDigitalcurrencyname();
				} else {
					currencyName = digitalCurrencyInfo.getDigitalcurrencyname();
				}
				String balanceResult = "";
				try {
					balanceResult = queryUserRealBalance(getCompanyCurrencyAddr(currencyName),
							currencyName);
				} catch (Exception e) {
					logger.error("查询公司数字货币账户地址余额失败！");
					return ReturnInfo.error("请求超时，查询余额失败！");
				}
				// 手续费 10个以下0.01ETH，10个以上千分之一的手续费
				// 转外账是提现功能
				if (userDigitalCurrencyInfo.getDigitalcurrencybalance()
						.compareTo(digitalCurrencyInfo.getFee().add(transferValue)) < 0) {
					return ReturnInfo.error("余额不足！");
				}
				// 发起提现请求
				String txHash = "";
				BigDecimal withdrawFee = digitalCurrencyInfo.getFee();
				try {
					// 此处每种一级币都对应各自的提现方法
					if ("ETH".equals(currencyName)) {
						if ("ETH".equals(digitalCurrencyInfo.getDigitalcurrencyname())) {
							// 公账ETH余额
							BigDecimal companyBalanceETH = new BigDecimal(JSONObject.fromObject(balanceResult).getString("ETH"));
							// 判断公账ETH余额是否大于1000ETH
							if (companyBalanceETH.compareTo(new BigDecimal("1000")) == 1) {
								if (transferValue.compareTo(new BigDecimal("20")) == 1) {
									if (transferValue.compareTo(new BigDecimal("10")) == 1) {
										withdrawFee = transferValue.multiply(new BigDecimal("0.001"));
										txHash = userWithdrawCashETH(transferAddr, transferValue, digitalCurrencyInfo, currencyName);
									}
								} else {
									return ReturnInfo.error("一次最多提取20ETH！");
								}
							} else {
								return ReturnInfo.error("暂不支持向其他钱包转账！");
							}
						} else if ("MOL".equals(digitalCurrencyInfo.getDigitalcurrencyname())) {
							// 公账MOL余额
							BigDecimal companyBalanceMOL = new BigDecimal(JSONObject.fromObject(balanceResult).getString("MOL"));
							// 判断公账MOL余额是否大于500000000MOL
							if (companyBalanceMOL.compareTo(new BigDecimal("500000000")) == 1) {
								if (transferValue.compareTo(new BigDecimal("10000000")) == 1) {
									if (transferValue.compareTo(new BigDecimal("5000000")) == 1) {
										withdrawFee = transferValue.multiply(new BigDecimal("0.001"));
									}
									txHash = userWithdrawCashETH(transferAddr, transferValue, digitalCurrencyInfo, currencyName);
								} else {
									return ReturnInfo.error("一次最多提取1000万MOL！");
								}
							} else {
								return ReturnInfo.error("暂不支持向其他钱包转账！");
							}
						}
					}
				} catch (Exception e) {
					logger.error("发起提现请求失败！");
					return ReturnInfo.error("请求超时，提现失败！");
				}
				// 获取公司地址
				String companyCurrencyAddr = "";
				try {
					companyCurrencyAddr = getCompanyCurrencyAddr(currencyName);
				} catch (Exception e) {
					logger.error("查询公司指定数字货币地址失败！");
					return ReturnInfo.error("请求超时，提现失败！");
				}
				if (!StringUtils.judgeBlank(txHash)) {
					if (!"invalid".equals(txHash)) {
						// 创建真实转账记录
						RealTransferAccountsDetails realTransferAccountsDetails = new RealTransferAccountsDetails();
						realTransferAccountsDetails.setUserid(userId);
						realTransferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
						realTransferAccountsDetails.setTransfertype((short) 2);
						realTransferAccountsDetails.setTransferaddr(companyCurrencyAddr);
						realTransferAccountsDetails.setReceiptaddr(transferAddr);
						realTransferAccountsDetails.setTransfertxhash(txHash);
						realTransferAccountsDetails.setGasupperlimit(0L);
						realTransferAccountsDetails.setGasprice("");
						realTransferAccountsDetails.setGasused(0L);
						realTransferAccountsDetails.setTransfervalue(transferValue);
						realTransferAccountsDetails.setRealtransferfee(null);
						realTransferAccountsDetails.setTransferprogress((short) 1);
						realTransferAccountsDetails.setHandlestatus(false);
						realTransferAccountsDetails.setCreatetime(new Date());
						realTransferAccountsDetails.setUpdatetime(new Date());
						realTransferAccountsDetails.setEnabled(true);
						realTransferAccountsDetails.setDeleted(false);
						realTransferAccountsDetailsService.insert(realTransferAccountsDetails);
						// 创建用户提现记录
						UserWithdrawCashInfo userWithdrawCashInfo = new UserWithdrawCashInfo();
						userWithdrawCashInfo.setUserid(userId);
						userWithdrawCashInfo.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
						userWithdrawCashInfo.setRealtransferaccountsid(realTransferAccountsDetails.getId());
						userWithdrawCashInfo.setWithdrawaddr(transferAddr);
						userWithdrawCashInfo.setWithdrawamount(transferValue);
						userWithdrawCashInfo.setWithdrawfee(withdrawFee);
						userWithdrawCashInfo.setWithdrawprogress((short) 1);
						userWithdrawCashInfo.setHandlestatus(false);
						if (StringUtils.judgeBlank(transferDesc)) {
							transferDesc = "提现" + digitalCurrencyInfo.getDigitalcurrencyname();
						}
						userWithdrawCashInfo.setRemark(transferDesc);
						userWithdrawCashInfo.setCreatetime(new Date());
						userWithdrawCashInfo.setUpdatetime(new Date());
						userWithdrawCashInfo.setEnabled(true);
						userWithdrawCashInfo.setDeleted(false);
						userWithdrawCashInfoService.insert(userWithdrawCashInfo);
						// 更新用户余额
						userDigitalCurrencyInfo.setDigitalcurrencybalance(userDigitalCurrencyInfo.getDigitalcurrencybalance()
								.subtract(transferValue.add(withdrawFee)));
						userDigitalCurrencyInfoService.updateByPrimaryKey(userDigitalCurrencyInfo);
						return ReturnInfo.info(200, "提现提交成功，处理中！");
					}
				}*/
			} else {
				toUserId = userDigitalAddrList.get(0).getUserid();
				// 异常用户不能转账
				List<CoinsList> coinsList = coinsListService.queryByUserId(toUserId);
				if (coinsList.size() > 0) {
					return ReturnInfo.error("对方账户已被冻结，无法转账！");
				}
				userTransferAccountsDetails = new UserTransferAccountsDetails();
				userTransferAccountsDetails.setTransfertype((short) 1);
			}
		} else if (StringUtils.judgeBlank(transferAddr) && !StringUtils.judgeBlank(userMobile)) {
			List<User> userList = userService.queryUser(InternationalMobilePhoneNumberUtils.getPhoneInfo(userMobile));
			if (userList.isEmpty()) {
				return ReturnInfo.error("参数错误！");
			}
			toUserId = userList.get(0).getId();
			// 异常用户不能转账
			List<CoinsList> coinsList = coinsListService.queryByUserId(toUserId);
			if (coinsList.size() > 0) {
				return ReturnInfo.error("对方账户已被冻结，无法转账！");
			}
			if (userId == toUserId) {
				return ReturnInfo.error("不能给自己转账！");
			}
			userTransferAccountsDetails = new UserTransferAccountsDetails();
			userTransferAccountsDetails.setTransfertype((short) 2);
		}
		if (userTransferAccountsDetails != null) {
			// 更新转账用户余额
			userDigitalCurrencyBalance = userDigitalCurrencyInfo.getDigitalcurrencybalance().subtract(transferValue);
			userDigitalCurrencyInfo.setDigitalcurrencybalance(userDigitalCurrencyBalance);
			userDigitalCurrencyInfoService.updateByPrimaryKey(userDigitalCurrencyInfo);
			// 更新被转账用户的余额
			UserDigitalCurrencyInfo passiveUserDigitalCurrencyInfo = userDigitalCurrencyInfoService
					.queryByDigitalCurrencyId(digitalCurrencyInfo.getId(), toUserId);
			userDigitalCurrencyBalance = passiveUserDigitalCurrencyInfo.getDigitalcurrencybalance().add(transferValue);
			passiveUserDigitalCurrencyInfo.setDigitalcurrencybalance(userDigitalCurrencyBalance);
			userDigitalCurrencyInfoService.updateByPrimaryKey(passiveUserDigitalCurrencyInfo);
			// 创建转账记录
			userTransferAccountsDetails.setUserid(userId);
			userTransferAccountsDetails.setDigitalcurrencyinfoid(digitalCurrencyInfo.getId());
			userTransferAccountsDetails.setTouserid(toUserId);
			userTransferAccountsDetails.setTransfervalue(transferValue);
			userTransferAccountsDetails.setTransferstatus((short) 1);
			if (!StringUtils.judgeBlank(transferDesc)) {
				userTransferAccountsDetails.setRemark(transferDesc);
			} else {
				userTransferAccountsDetails.setRemark("转账");
			}
			userTransferAccountsDetails.setCreatetime(new Date());
			userTransferAccountsDetails.setUpdatetime(new Date());
			userTransferAccountsDetails.setEnabled(true);
			userTransferAccountsDetails.setDeleted(false);
			userTransferAccountsDetailsService.insert(userTransferAccountsDetails);
		} else {
			return ReturnInfo.error("参数错误！");
		}
		return ReturnInfo.info(200, "转账成功！");
	}
	
	/**
	 * 从用户抽取到公账的接口(ETH)
	 * 
	 * @param userId
	 * @param extractAddr
	 * @param extractAmount
	 * @param digitalCurrencyInfo
	 * @param currencyName
	 * @return
	 * @throws Exception
	 */
	private String extractRealBalanceETH(String userId, String extractAddr, BigDecimal extractAmount,
			DigitalCurrencyInfo digitalCurrencyInfo, String currencyName) throws Exception {
		// 封装抽取请求参数
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("coin", currencyName.toLowerCase());
		jsonParam.put("network", "0");
		jsonParam.put("index", userId);
		jsonParam.put("from", extractAddr);
		jsonParam.put("change_address", "");
		jsonParam.put("gas", "");
		jsonParam.put("gasPrice", "");
		String certKey = "";
		String resultData = "";
		if (0 != digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
			jsonParam.put("token_identifier", digitalCurrencyInfo.getDigitalcurrencyname());
			jsonParam.put("token_address", digitalCurrencyInfo.getDigitalcurrencyaddr());
			jsonParam.put("decimals", digitalCurrencyInfo.getTokendecimal());
			BigDecimal decimals = new BigDecimal(String.valueOf(Math.pow(10, digitalCurrencyInfo.getTokendecimal())));
			// 代币抽取，需要在代币金额后面补decimals个0
			jsonParam.put("amount", extractAmount.multiply(decimals).toPlainString());
			certKey = new Sha256Hash(jsonParam.toString()).toString();
			jsonParam.put("certKey", certKey);
			resultData = HttpClientUtils
					.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
							+ "ethToken/sendEthTokenTransactionToCompanyByHD", jsonParam.toString());
		} else {
			jsonParam.put("amount", extractAmount.toPlainString());
			certKey = new Sha256Hash(jsonParam.toString()).toString();
			jsonParam.put("certKey", certKey);
			resultData = HttpClientUtils
					.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
							+ "eth/sendEthTransactionToCompanyByHD", jsonParam.toString());
		}
		String txHash = "";
		if (!StringUtils.judgeBlank(resultData)) {
			JSONObject jsonData = JSONObject.fromObject(resultData);
			if (jsonData.has("result")) {
				txHash = jsonData.getString("result");
			} else {
				txHash = "invalid";
			}
		}
		return txHash;
	}

	/**
	 * 从公账转账给用户真实手续费(ETH)
	 * 
	 * @param transferAddr
	 * @param transferAmount
	 * @param currencyName
	 * @return
	 * @throws Exception
	 */
	private String transferFeeETH(String transferAddr, BigDecimal transferAmount, String currencyName)
			throws Exception {
		// 封装提现请求参数
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("coin", currencyName.toLowerCase());
		jsonParam.put("network", "0");
		jsonParam.put("to", transferAddr);
		jsonParam.put("change_address", "");
		jsonParam.put("gas", "");
		jsonParam.put("gasPrice", "");
		jsonParam.put("amount", subZeroAndDot(transferAmount.toPlainString()));
		String certKey = new Sha256Hash(jsonParam.toString()).toString();
		jsonParam.put("certKey", certKey);
		String resultData = "";
		resultData = HttpClientUtils
				.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
						+ "eth/sendEthTransactionToOutByHD", jsonParam.toString());
		String txHash = "";
		if (!StringUtils.judgeBlank(resultData)) {
			JSONObject jsonData = JSONObject.fromObject(resultData);
			if (jsonData.has("result")) {
				txHash = jsonData.getString("result");
			} else {
				txHash = "invalid";
			}
		}
		return txHash;
	}

	/**
	 * 计算用户充值金额
	 * 
	 * @return
	 */
	private BigDecimal getRechargeAmount(BigDecimal realBalance, BigDecimal oldBalance,
			BigDecimal poundageTransferAccountsValue, BigDecimal countTransferAccountsValue,
			BigDecimal countTransferAccountsFee) {
		// 判断是否有未处理的转账金额
		if (countTransferAccountsValue.compareTo(BigDecimal.ZERO) != 0) {
			// 判断手续费是否等于0(代币不需要计算手续费)
			if (countTransferAccountsFee.compareTo(BigDecimal.ZERO) != 0) {
				return realBalance
						.subtract((oldBalance.subtract(countTransferAccountsValue).subtract(countTransferAccountsFee)))
						.subtract(poundageTransferAccountsValue);
			} else {
				return realBalance.subtract(oldBalance.subtract(countTransferAccountsValue))
						.subtract(poundageTransferAccountsValue);
			}
		} else {
			return realBalance.subtract(oldBalance).subtract(poundageTransferAccountsValue);
		}
	}

	/**
	 * 计算真实转账手续费(ETH)
	 * 
	 * @param gasPrice
	 * @param gasUsed
	 * @return
	 */
	private BigDecimal getRealTransferFeeETH(String gasPrice, String gasUsed) {
		String computeParam = "1000000000000000000";
		BigDecimal computeParamBig = new BigDecimal(computeParam);
		BigDecimal gasPriceBig = new BigDecimal(gasPrice);
		BigDecimal gasUsedBig = new BigDecimal(gasUsed);
		return new BigDecimal(subZeroAndDot(
				gasPriceBig.divide(computeParamBig, 12, BigDecimal.ROUND_HALF_UP).multiply(gasUsedBig).toString()));
	}

	private String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	/**
	 * 查询已成功的真实转账记录(ETH)
	 * 
	 * @param txHash
	 * @return
	 */
	private String queryRealTransferInfoETH(String txHash) throws Exception {
		String result = "";
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("txHash", txHash);
		String resultData = HttpClientUtils.httpPostWithJSON(
				PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr") + "eth/getTransactionPost",
				jsonParam.toString());
		if (!StringUtils.judgeBlank(resultData)) {
			JSONObject jsonData = JSONObject.fromObject(resultData);
			if (jsonData.has("result")) {
				result = jsonData.getString("result");
			} else {
				result = "invalid";
			}
		}
		return result;
	}

	/**
	 * 查询真实转账记录(ETH)，有返回值表示真实转账已成功
	 * 
	 * @param txHash
	 * @return
	 */
	private String queryReceiptRealTransferInfoETH(String txHash) throws Exception {
		String result = "";
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("txHash", txHash);
		String resultData = HttpClientUtils
				.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
						+ "eth/getTransactionReceiptPost", jsonParam.toString());
		if (!StringUtils.judgeBlank(resultData)) {
			JSONObject jsonData = JSONObject.fromObject(resultData);
			if (jsonData.has("result")) {
				result = jsonData.getString("result");
			} else {
				result = "invalid";
			}
		}
		return result;
	}

	/**
	 * 发起提现转账请求(ETH)
	 * 
	 * @param withdrawAddr
	 * @param withdrawAmount
	 * @param digitalCurrencyInfo
	 * @param currencyName
	 * @return
	 * @throws Exception
	 */
	private String userWithdrawCashETH(String withdrawAddr, BigDecimal withdrawAmount,
			DigitalCurrencyInfo digitalCurrencyInfo, String currencyName) throws Exception {
		// 封装提现请求参数
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("coin", currencyName.toLowerCase());
		jsonParam.put("network", "0");
		jsonParam.put("to", withdrawAddr);
		jsonParam.put("change_address", "");
		jsonParam.put("gas", "");
		jsonParam.put("gasPrice", "");
		String certKey = "";
		String resultData = "";
		if (0 != digitalCurrencyInfo.getDigitalcurrencyinfoid()) {
			jsonParam.put("token_identifier", digitalCurrencyInfo.getDigitalcurrencyname());
			jsonParam.put("token_address", digitalCurrencyInfo.getDigitalcurrencyaddr());
			jsonParam.put("decimals", digitalCurrencyInfo.getTokendecimal());
			BigDecimal decimals = new BigDecimal(String.valueOf(Math.pow(10, digitalCurrencyInfo.getTokendecimal())));
			// 代币抽取，需要在代币金额后面补decimals个0
			jsonParam.put("amount", withdrawAmount.multiply(decimals).toPlainString());
			certKey = new Sha256Hash(jsonParam.toString()).toString();
			jsonParam.put("certKey", certKey);
			resultData = HttpClientUtils
					.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
							+ "ethToken/sendEthTokenTransactionToOutByHD", jsonParam.toString());
		} else {
			jsonParam.put("amount", subZeroAndDot(withdrawAmount.toPlainString()));
			certKey = new Sha256Hash(jsonParam.toString()).toString();
			jsonParam.put("certKey", certKey);
			resultData = HttpClientUtils
					.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
							+ "eth/sendEthTransactionToOutByHD", jsonParam.toString());
		}
		String txHash = "";
		if (!StringUtils.judgeBlank(resultData)) {
			JSONObject jsonData = JSONObject.fromObject(resultData);
			if (jsonData.has("result")) {
				txHash = jsonData.getString("result");
			} else {
				txHash = "invalid";
			}
		}
		return txHash;
	}

	/**
	 * 查询用户真实账户地址余额(所有一级币种)
	 * 
	 * @param userRealAddr
	 * @return
	 */
	private String queryUserRealBalance(String userRealAddr, String currencyName) throws Exception {
		String result = "";
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("address", userRealAddr);
		String resultData = HttpClientUtils
				.httpPostWithJSON(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
						+ currencyName.toLowerCase() + "/getAllBalanceByAddressPost", jsonParam.toString());
		if (!StringUtils.judgeBlank(resultData)) {
			JSONObject jsonData = JSONObject.fromObject(resultData);
			if (jsonData.has("result")) {
				result = jsonData.getString("result");
			} else {
				result = "invalid";
			}
		}
		return result;
	}

	/**
	 * 获取加密参数(通用)
	 * 
	 * @param userRealAddr
	 * @return
	 * @throws Exception
	 *//*
	private String getEncryptParam(String userRealAddr, String currencyName) throws Exception {
		String topFive = userRealAddr.substring(2, 7);
		String afterFive = userRealAddr.substring(userRealAddr.length() - 5, userRealAddr.length());
		String number = getTransactionNumber(userRealAddr, currencyName);
		Long transactionNumber = Long.parseLong(number);
		return new Sha256Hash(
				afterFive + PropertiesUtils.getInfoConfigProperties().getProperty("digitalCurrency.fixed.encryptParam")
						+ topFive + transactionNumber).toString();
	}

	*//**
	 * 获取用户的交易数量(通用)
	 * 
	 * @param userRealAddr
	 * @return
	 * @throws Exception
	 *//*
	private String getTransactionNumber(String userRealAddr, String currencyName) throws Exception {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("address", userRealAddr);
		String url = PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
				+ currencyName.toLowerCase() + "/getTransactionCountPost";
		return HttpClientUtils.httpPostWithJSON(url, jsonParam.toString());
	}*/

	/**
	 * 获取公司指定币种的数字货币地址(通用)
	 * 
	 * @param currencyName
	 * @return
	 * @throws Exception
	 */
	private String getCompanyCurrencyAddr(String currencyName) throws Exception {
		String result = "";
		String resultData = HttpClientUtils
				.getSendRequest(PropertiesUtils.getInfoConfigProperties().getProperty("monery.item.addr")
						+ currencyName.toLowerCase() + "/getCompanyAddress", null);
		if (!StringUtils.judgeBlank(resultData)) {
			JSONObject jsonData = JSONObject.fromObject(resultData);
			if (jsonData.has("address")) {
				result = jsonData.getString("address");
			} else {
				result = "invalid";
			}
		}
		return result;
	}

}
