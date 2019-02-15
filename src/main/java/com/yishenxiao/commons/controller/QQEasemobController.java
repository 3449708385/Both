package com.yishenxiao.commons.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishenxiao.commons.beans.easemob.EasemobSignUpBean;
import com.yishenxiao.commons.beans.easemob.SendInfo;
import com.yishenxiao.commons.beans.postbean.QQSendInfoBean;
import com.yishenxiao.commons.service.QQGroupService;
import com.yishenxiao.commons.service.QQMemberService;
import com.yishenxiao.commons.utils.EasemobUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.PropertiesUtils;
import com.yishenxiao.commons.utils.RecordingUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;
import com.yishenxiao.commons.utils.easemob.Thumb;
import com.yishenxiao.commons.utils.easemob.VideoTimeUtils;
import com.yishenxiao.usermanager.beans.Group;
import com.yishenxiao.usermanager.beans.User;
import com.yishenxiao.usermanager.beans.UserSchedule;
import com.yishenxiao.usermanager.service.GroupService;
import com.yishenxiao.usermanager.service.UserScheduleService;
import com.yishenxiao.usermanager.service.UserService;

@Controller
@RequestMapping("qqeasemob")
public class QQEasemobController {
	
	private static Logger logger = LoggerFactory.getLogger(QQEasemobController.class);
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("qQGroupService")
	private QQGroupService qQGroupService;
	
	@Autowired(required=false)@Qualifier("qQMemberService")
	private QQMemberService qQMemberService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	
	/**
	 * @info 推送实时数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="sendRealMessage", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo sendRealMessage(@RequestBody QQSendInfoBean qqSendInfoBean){
		/*if(StringUtils.judgeBlank(qqSendInfoBean.getGroup_id())||StringUtils.judgeBlank(qqSendInfoBean.getMessage())||StringUtils.judgeBlank(qqSendInfoBean.getUser_id())){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(qqSendInfoBean.get_id()) || redisService.opsForValue().get(qqSendInfoBean.get_id())!=null){
			return ReturnInfo.error("参数错误！");
		}else{
			   redisService.opsForValue().set(qqSendInfoBean.get_id(), qqSendInfoBean.get_id(), 1, TimeUnit.MINUTES);
		}
		ReturnInfo returnInfo = new ReturnInfo();
		String target=qqSendInfoBean.getGroup_id();
		String type="";
		String msg="";
		if(qqSendInfoBean.getMessage().contains("CQ:")){
		   type=qqSendInfoBean.getMessage().substring(qqSendInfoBean.getMessage().indexOf("CQ:")+3, qqSendInfoBean.getMessage().indexOf(","));
		   if(type.equals("image")){
			   //另行处理
			   msg=qqSendInfoBean.getMessage();
		   }else if(type.equals("share")){
			   msg=qqSendInfoBean.getMessage();
		   }else{
			   //其他的都不进行处理
			   return ReturnInfo.ok();
		   }
		}else{
			type="Text";
			msg=qqSendInfoBean.getMessage();
		}
		String from=qqSendInfoBean.getUser_id();
		String id=qqSendInfoBean.get_id();
		try{
			returnInfo=sendInfo(target, type, msg, from, id);
		}catch(Exception e){
			logger.error("target:"+target+" type:"+type+" msg:"+msg+" from:"+from+" id:"+id);
			logger.error("推送消息异常："+e);
		}*/
		return null;
	}
  
	 static class Size {
	     private long width;
	     private long height;
	
	   public long getWidth() {
			return width;
		}

		public void setWidth(long width) {
			this.width = width;
		}

		public long getHeight() {
			return height;
		}

		public void setHeight(long height) {
			this.height = height;
		}

	    public Size(long width, long height) {
	         this.width = width;
	         this.height = height;
	     }
	 }
	 
	 public ReturnInfo sendInfo(String target, String type, String msg, String from, String id){
		 List<SendInfo> sendList = new ArrayList<SendInfo>();
			List<String> groupIdsList = new ArrayList<String>(); 
			List<Group> groupList = groupService.queryByGroupName(target);
			if(groupList.size()==0){
				return ReturnInfo.error(500,"数据暂未入库！");
			}		
			List<User> userList = userService.queryByUserNameList(from);
			UserSchedule userSchedule = userScheduleService.queryByUIdList(userList.get(0).getId()).get(0);			
			if(type.equals("Text")){		
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("type", "txt");
					map.put("msg", msg);
					groupIdsList.add(groupList.get(0).getEasemobgroupid());
					SendInfo sendInfo = new SendInfo();
					sendInfo.setFrom(from);
					sendInfo.setTarget(groupIdsList);
					sendInfo.setMsg(map);
					if(!StringUtils.judgeBlank(userSchedule.getNickname())){
					  sendInfo.setNickName(userSchedule.getNickname());
					}
					String headIcon = userSchedule.getHeadicon();
					String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
					String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
					sendInfo.setHeadIcon(temp1);
					sendInfo.setTarget_type("chatgroups");
					sendList.add(sendInfo);
					EasemobUtils.EasemobBatchSendInfo(sendList);
			}else if(type.equals("image")){
				msg=PropertiesUtils.getInfoConfigProperties().getProperty("wechat.file.addr")+msg;
				String result = new EasemobUtils().EasemobUploadFile(from, msg);
				if(result.equals("ok")){	//上传失败不处理
					//上传成功
					String userkey = new Sha256Hash(from+msg).toHex();
					Object fileResult = redisService.opsForValue().get(userkey);
					if(fileResult!=null){
						String fileTempName = msg.substring(msg.lastIndexOf("/")+1);
					    EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(fileResult.toString(), EasemobSignUpBean.class);
					    Map<String,Object> map = new HashMap<String,Object>();
						map.put("type", "img");
						map.put("url", "https://a1.easemob.com/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/chatfiles/"+easemobSignUpBean.getEntities().get(0).get("uuid"));
						map.put("filename", fileTempName);
						map.put("secret", easemobSignUpBean.getEntities().get(0).get("share-secret"));
						map.put("size", new Size(480, 720));
						groupIdsList.add(groupList.get(0).getEasemobgroupid());
					    SendInfo sendInfo = new SendInfo();
						sendInfo.setFrom(from);
						sendInfo.setTarget(groupIdsList);
						sendInfo.setMsg(map);
						sendInfo.setTarget_type("chatgroups");
						if(!StringUtils.judgeBlank(userSchedule.getNickname())){
							  sendInfo.setNickName(userSchedule.getNickname());
							}
							String headIcon = userSchedule.getHeadicon();
							String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
							String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
							sendInfo.setHeadIcon(temp1);
						sendList.add(sendInfo);
					  EasemobUtils.EasemobBatchSendInfo(sendList);
					}
				}else{
	                logger.error(msg+"文件上传失败！");				
				}
			}else if(type.equals("share")){
				//文件上传
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("type", "txt");
				map.put("msg", "分享");
				groupIdsList.add(groupList.get(0).getEasemobgroupid());
				SendInfo sendInfo = new SendInfo();
				sendInfo.setFrom(from);
				sendInfo.setTarget(groupIdsList);
				sendInfo.setMsg(map);
				sendInfo.setTarget_type("chatgroups");
				//解析html
				String title=msg.substring(msg.indexOf("title=")+6, msg.indexOf(",", msg.indexOf("title=")+6));
				String des=msg.substring(msg.indexOf("content=")+8, msg.indexOf(",", msg.indexOf("content=")+8));
				String url=msg.substring(msg.indexOf("url=")+4, msg.indexOf(",", msg.indexOf("url=")+4));
				String thumburl=msg.substring(msg.indexOf("image=")+6, msg.indexOf(",", msg.indexOf("image=")+6));
				Map<String,Object> map01 = new HashMap<String,Object>();
				map01.put("title", title);
				map01.put("message", des);
				map01.put("url", url);
				map01.put("thumburl", thumburl);
				sendInfo.setExt(map01);
				if(!StringUtils.judgeBlank(userSchedule.getNickname())){
					  sendInfo.setNickName(userSchedule.getNickname());
					}
					String headIcon = userSchedule.getHeadicon();
					String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
					String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
					sendInfo.setHeadIcon(temp1);
				sendList.add(sendInfo);
				EasemobUtils.EasemobBatchSendInfo(sendList);
				
			}else if(type.equals("Recording")){
				msg=PropertiesUtils.getInfoConfigProperties().getProperty("wechat.file.addr")+msg;
				String result = new EasemobUtils().EasemobUploadFile(from, msg);
				if(result.equals("ok")){	//上传失败不处理
					//上传成功
					String userkey = new Sha256Hash(from+msg).toHex();
					Object fileResult = redisService.opsForValue().get(userkey);
					if(fileResult!=null){
						String fileTempName = msg.substring(msg.lastIndexOf("/")+1);
					    EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(fileResult.toString(), EasemobSignUpBean.class);
					    Map<String,Object> map = new HashMap<String,Object>();
						map.put("type", "audio");
						map.put("url", "https://a1.easemob.com/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/chatfiles/"+easemobSignUpBean.getEntities().get(0).get("uuid"));
						map.put("filename", fileTempName);
						map.put("secret", easemobSignUpBean.getEntities().get(0).get("share-secret"));
						map.put("length", RecordingUtils.getDuration(msg));
						groupIdsList.add(groupList.get(0).getEasemobgroupid());
					    SendInfo sendInfo = new SendInfo();
						sendInfo.setFrom(from);
						sendInfo.setTarget(groupIdsList);
						sendInfo.setMsg(map);
						sendInfo.setTarget_type("chatgroups");
						if(!StringUtils.judgeBlank(userSchedule.getNickname())){
							  sendInfo.setNickName(userSchedule.getNickname());
							}
							String headIcon = userSchedule.getHeadicon();
							String temp = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
							String temp1 = temp+headIcon.substring(headIcon.indexOf("MEDIA")+5);
							sendInfo.setHeadIcon(temp1);
						sendList.add(sendInfo);
					  EasemobUtils.EasemobBatchSendInfo(sendList);
					}
				}else{
	                logger.error(msg+"文件上传失败！");				
				}
			}else if(type.equals("Video")){
				msg=PropertiesUtils.getInfoConfigProperties().getProperty("wechat.file.addr")+msg;
				//上传视频
				String result = new EasemobUtils().EasemobUploadFile(from, msg);
				//生成缩略图
				String fileTempUrl = msg.substring(0, msg.lastIndexOf("/")+1)+"Thumb";
				File filedir = new File(fileTempUrl);
				if(!filedir.exists()){
					filedir.mkdir();
				}
				String temp = msg.substring(msg.lastIndexOf("/")+1,msg.lastIndexOf("."));
				String filetemp = msg.substring(0, msg.lastIndexOf("/")+1)+"Thumb/"+temp+".jpg";
				try {
					Thumb.fetchFrame(msg, filetemp);
				} catch (Exception e) {
					return ReturnInfo.error(msg + "视频缩略图生成失败！");
				}
				//上传缩略图
				String thumbStatus = new EasemobUtils().EasemobUploadFile(from, filetemp);
				if(result.equals("ok") && thumbStatus.equals("ok")){
					//上传成功
					String userkey = new Sha256Hash(from+msg).toHex();
					Object fileResult = redisService.opsForValue().get(userkey);
					String userSuffixkey = new Sha256Hash(from+filetemp).toHex();
					Object fileSuffixResult = redisService.opsForValue().get(userSuffixkey);
					if(fileResult!=null && fileSuffixResult!=null){
						String fileTempName = msg.substring(msg.lastIndexOf("/")+1);
					    EasemobSignUpBean easemobSignUpBean = JsonUtils.jsonToObj(fileResult.toString(), EasemobSignUpBean.class);
					    EasemobSignUpBean easemobSignUpBean2 = JsonUtils.jsonToObj(fileSuffixResult.toString(), EasemobSignUpBean.class);
					    Map<String,Object> map = new HashMap<String,Object>();
						map.put("type", "video");
						map.put("filename", fileTempName);
						map.put("thumb", "https://a1.easemob.com/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/chatfiles/"+easemobSignUpBean2.getEntities().get(0).get("uuid"));
						map.put("length", VideoTimeUtils.getTime(msg));
						map.put("secret", easemobSignUpBean.getEntities().get(0).get("share-secret"));
						map.put("file_length", new File(msg).length());
						map.put("thumb_secret", easemobSignUpBean2.getEntities().get(0).get("share-secret"));
						map.put("url", "https://a1.easemob.com/easemob-demo/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/"+easemobSignUpBean.getEntities().get(0).get("uuid"));																	
						groupIdsList.add(groupList.get(0).getEasemobgroupid());
					    SendInfo sendInfo = new SendInfo();
						sendInfo.setFrom(from);
						sendInfo.setTarget(groupIdsList);
						sendInfo.setMsg(map);
						sendInfo.setTarget_type("chatgroups");
						if(!StringUtils.judgeBlank(userSchedule.getNickname())){
							  sendInfo.setNickName(userSchedule.getNickname());
							}
							String headIcon = userSchedule.getHeadicon();
							String temp2 = PropertiesUtils.getInfoConfigProperties().getProperty("info.ipAddr");
							String temp1 = temp2+headIcon.substring(headIcon.indexOf("MEDIA")+5);
							sendInfo.setHeadIcon(temp1);
						sendList.add(sendInfo);
					  EasemobUtils.EasemobBatchSendInfo(sendList);
					}else{
						return ReturnInfo.ok();
					}
				}else{
					//上传失败
					logger.error(msg+"文件上传失败！");
				}
			}
			return ReturnInfo.ok();
	 }

}
