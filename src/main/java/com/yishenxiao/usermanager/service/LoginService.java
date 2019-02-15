package com.yishenxiao.usermanager.service;

import java.util.Map;

public interface LoginService {
   public Map<String, Object> userLogin(Long extensionUserId, String account,String nativecode, String phonecode);
   public int userLoginInfo(Long extensionUserId, String phone, Long userId);
}
