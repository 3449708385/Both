package com.yishenxiao.digitalwallet.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.RedEnvelopes;
import com.yishenxiao.digitalwallet.beans.RedEnvelopesReturn;
import com.yishenxiao.digitalwallet.service.DigitalCurrencyInfoService;
import com.yishenxiao.digitalwallet.service.IMessageDelegate;
import com.yishenxiao.digitalwallet.service.RedEnvelopesReturnService;
import com.yishenxiao.digitalwallet.service.RedEnvelopesService;
import com.yishenxiao.digitalwallet.service.UserDigitalCurrencyInfoService;

public class DefaultMessageDelegate implements IMessageDelegate {

	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisService;
	
	@Autowired(required=false)@Qualifier("redEnvelopesService")
	private RedEnvelopesService redEnvelopesService;
	
	@Autowired(required=false)@Qualifier("userDigitalCurrencyInfoService")
	private UserDigitalCurrencyInfoService userDigitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("digitalCurrencyInfoService")
	private DigitalCurrencyInfoService digitalCurrencyInfoService;
	
	@Autowired(required=false)@Qualifier("redEnvelopesReturnService")
	private RedEnvelopesReturnService redEnvelopesReturnService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	public void handleMessage(String message) {
		List<RedEnvelopes> listKey = redEnvelopesService.queryByRedisKey(message);
		if(listKey.size()!=0){
			//钱返回，更改红包数据状态
			List<DigitalCurrencyInfo> digitalCurrencyInfoList =  digitalCurrencyInfoService.queryByDigitalcurrencynameForList(listKey.get(0).getCurrency());
			userDigitalCurrencyInfoService.updateData(listKey.get(0).getUserid(),digitalCurrencyInfoList.get(0).getId(),listKey.get(0).getAmount());
			redEnvelopesService.updateState(listKey.get(0).getId(), 1);
			//插入记录
			RedEnvelopesReturn redEnvelopesReturn = new RedEnvelopesReturn();
			redEnvelopesReturn.setCreatetime(listKey.get(0).getCreatetime());
			redEnvelopesReturn.setDigitalcurrencyinfoid(digitalCurrencyInfoList.get(0).getId());
			redEnvelopesReturn.setMonery(listKey.get(0).getAmount());
			redEnvelopesReturn.setRedcount(listKey.get(0).getUsecount());
			redEnvelopesReturn.setRedenvelopesid(listKey.get(0).getId());
			redEnvelopesReturn.setState(0);
			redEnvelopesReturn.setUpdatetime(new Date());
			redEnvelopesReturn.setUserid(listKey.get(0).getUserid());
			redEnvelopesReturnService.insert(redEnvelopesReturn);
		}
	}

	@Override
	public void handleMessage(Map<String, Object> message) {
		//System.out.println("Map: " +message);
	}

	@Override
	public void handleMessage(byte[] message) {
		//System.out.println("byte: " +message);
	}

	@Override
	public void handleMessage(Serializable message) {
		//System.out.println("Serializable: " +message);
	}

	@Override
	public void handleMessage(Serializable message, String channel) {
		//System.out.println("Serializable: " +message+"  channel:"+channel);
	}

}
