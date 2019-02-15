package com.yishenxiao.commons.utils.easemob;

import com.google.gson.Gson;
import com.yishenxiao.commons.service.easemob.EasemobAPI;
import com.yishenxiao.commons.utils.EasemobErrorInfo;
import com.yishenxiao.commons.utils.JsonUtils;

import io.swagger.client.ApiException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by easemob on 2017/3/16.
 */
public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public Object handle(EasemobAPI easemobAPI) {
        Object result = null;
        try {
            result = easemobAPI.invokeEasemobAPI();
        } catch (ApiException e) {
            if (e.getCode() == 401) {
                logger.info("The current token is invalid, re-generating token for you and calling it again");
                TokenUtil.initTokenByProp();
                try {
                    result = easemobAPI.invokeEasemobAPI();
                } catch (ApiException e1) {
                    logger.error(e1.getMessage());
                    Gson gson = new Gson();
                    Map<String, String> map = gson.fromJson(e.getResponseBody(), Map.class);
                    String retsultStr= JsonUtils.toJson(new EasemobErrorInfo(e1.getCode(),e1.getMessage(),map.get("error_description")));
                    return retsultStr;
                }
                return result;
            }
            if (e.getCode() == 429) {
                logger.warn("The api call is too frequent");
                Gson gson = new Gson();
                Map<String, String> map = gson.fromJson(e.getResponseBody(), Map.class);
                String retsultStr= JsonUtils.toJson(new EasemobErrorInfo(e.getCode(),e.getMessage(),map.get("error_description")));
                return retsultStr;
            }
            if (e.getCode() >= 500) {
                logger.info("The server connection failed and is being reconnected");
                result = retry(easemobAPI);
                if (result != null) {
                    return result;
                }
                logger.error("The server may be faulty. Please try again later");
                Gson gson = new Gson();
                Map<String, String> map = gson.fromJson(e.getResponseBody(), Map.class);
                String retsultStr= JsonUtils.toJson(new EasemobErrorInfo(e.getCode(),e.getMessage(),map.get("error_description")));
                return retsultStr;
            }
            Gson gson = new Gson();
            Map<String, String> map = gson.fromJson(e.getResponseBody(), Map.class);
            logger.error("error_code:{} error_msg:{} error_desc:{}", e.getCode(), e.getMessage(), map.get("error_description"));
            String retsultStr= JsonUtils.toJson(new EasemobErrorInfo(e.getCode(),e.getMessage(),map.get("error_description")));
            return retsultStr;
        }
        return result;
    }

    public Object retry(EasemobAPI easemobAPI) {
        Object result = null;
        long time = 5;
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.SECONDS.sleep(time);
                logger.info("Reconnection is in progress..." + i);
                result = easemobAPI.invokeEasemobAPI();
                if (result != null) {
                    return result;
                }
            } catch (ApiException e1) {
                time *= 3;
            } catch (InterruptedException e1) {
                logger.error(e1.getMessage());
            }
        }
        return result;
    }
}
