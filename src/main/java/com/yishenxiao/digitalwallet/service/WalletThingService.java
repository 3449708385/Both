package com.yishenxiao.digitalwallet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.digitalwallet.beans.DigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserDigitalAddr;
import com.yishenxiao.digitalwallet.beans.UserDigitalCurrencyInfo;
import com.yishenxiao.digitalwallet.beans.UserRedEnvelopes;

public interface WalletThingService {
   public Map<String,Object> usersSendRedPackets(Long userId, String currency, Double amount, Integer count, String rEDesc, Long id);
   public UserRedEnvelopes usersRobbingRedPackets(Long userId, Long redEnvelopesId, String dataKey);
   public ReturnInfo transferOutAccounts(Long userId, String currencyName, Double monetary, String transferOutAddr, String transferDesc);
   public int transferIntoAccounts(String userAddr, String currency, Double monetary);
   public int changeOutPhoneAccounts(Long userId, String currencyName, Double monetary, String phone, String transferDesc);
   public void accountOperation(List<UserDigitalAddr> userDigitalAddr, Double foreignAccount, Double borrowFrom);
   public ReturnInfo userWithdrawCash(Long userId, DigitalCurrencyInfo digitalCurrencyInfo, DigitalCurrencyInfo superDigitalCurrencyInfo, UserDigitalCurrencyInfo userDigitalCurrencyInfo, String withdrawAddr, BigDecimal withdrawAmount, String remark);
   public ReturnInfo balanceInquiry(Long userId);
   public ReturnInfo userTransfer(Long userId, DigitalCurrencyInfo digitalCurrencyInfo, DigitalCurrencyInfo superDigitalCurrencyInfo, UserDigitalCurrencyInfo userDigitalCurrencyInfo, String transferAddr, String userMobile, BigDecimal transferValue, String transferDesc);
}
