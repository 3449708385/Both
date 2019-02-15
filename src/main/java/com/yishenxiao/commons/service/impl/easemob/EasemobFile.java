package com.yishenxiao.commons.service.impl.easemob;

import io.swagger.client.ApiException;
import io.swagger.client.api.UploadAndDownloadFilesApi;
import java.io.File;

import com.yishenxiao.commons.service.easemob.EasemobAPI;
import com.yishenxiao.commons.service.easemob.FileAPI;
import com.yishenxiao.commons.utils.easemob.OrgInfo;
import com.yishenxiao.commons.utils.easemob.ResponseHandler;
import com.yishenxiao.commons.utils.easemob.TokenUtil;


public class EasemobFile implements FileAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private UploadAndDownloadFilesApi api = new UploadAndDownloadFilesApi();
    @Override
    public Object uploadFile(final Object file) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatfilesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),(File)file,true);
             }
        });
    }

    @Override
    public Object downloadFile(final String fileUUID,final  String shareSecret,final Boolean isThumbnail) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
               return api.orgNameAppNameChatfilesUuidGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),fileUUID,shareSecret,isThumbnail);
            }
        });
    }
}
