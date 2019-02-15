package com.yishenxiao.commons.service.impl.easemob;

import com.yishenxiao.commons.service.easemob.EasemobAPI;
import com.yishenxiao.commons.service.easemob.SendMessageAPI;
import com.yishenxiao.commons.utils.easemob.OrgInfo;
import com.yishenxiao.commons.utils.easemob.ResponseHandler;
import com.yishenxiao.commons.utils.easemob.TokenUtil;

import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
