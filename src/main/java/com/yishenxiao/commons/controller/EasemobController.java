package com.yishenxiao.commons.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

import com.yishenxiao.commons.beans.WechatFriend;
import com.yishenxiao.commons.beans.WechatMember;
import com.yishenxiao.commons.beans.easemob.EasemobSignUpBean;
import com.yishenxiao.commons.beans.easemob.SendInfo;
import com.yishenxiao.commons.beans.postbean.AppSendMessageBean;
import com.yishenxiao.commons.beans.postbean.SendRealMessageBean;
import com.yishenxiao.commons.service.WechatFriendService;
import com.yishenxiao.commons.service.WechatGroupService;
import com.yishenxiao.commons.service.WechatMemberService;
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
@RequestMapping("easemob")
public class EasemobController {
	
	private static Logger logger = LoggerFactory.getLogger(EasemobController.class);
	
	@Autowired(required=false)@Qualifier("groupService")
	private GroupService groupService;
	
	@Autowired(required=false)@Qualifier("wechatGroupService")
	private WechatGroupService wechatGroupService;
	
	@Autowired(required=false)@Qualifier("wechatFriendService")
	private WechatFriendService wechatFriendService;
	
	@Autowired(required=false)@Qualifier("wechatMemberService")
	private WechatMemberService wechatMemberService;
	
	@Autowired(required=false)@Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisService;
	
	@Autowired(required=false)@Qualifier("userService")
	private UserService userService;
	
	@Autowired(required=false)@Qualifier("userScheduleService")
	private UserScheduleService userScheduleService;
	
	
	/**
	 * @info app端推送自定义数据的接口
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="appSendMessage", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo appSendMessage(@RequestBody AppSendMessageBean appSendMessageBean){
		String easemobGroupId=appSendMessageBean.getEasemobGroupId();
		String msg=appSendMessageBean.getMsg();
		String from=appSendMessageBean.getFrom();
		if(StringUtils.judgeBlank(easemobGroupId)||StringUtils.judgeBlank(msg)||StringUtils.judgeBlank(from)){
			return ReturnInfo.error("参数错误！");
		}		
		try{
			List<String> groupIdsList = new ArrayList<String>(); 
			Group group = groupService.queryByeasemobId(easemobGroupId);
			if(group==null){
				return ReturnInfo.error("数据暂未入库！");
			}	
			User user = userService.queryByUserNameList(from).get(0);
			UserSchedule userSchedule = userScheduleService.queryByUIdList(user.getId()).get(0);			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", "txt");
			map.put("msg", "推送");
			groupIdsList.add(group.getEasemobgroupid());
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
			Map<String,Object> map01 = new HashMap<String,Object>();
			map01.put("GroupNotice", msg);
			sendInfo.setExt(map01);
			EasemobUtils.EasemobAppSendInfo(sendInfo);
		}catch(Exception e){}
		return ReturnInfo.ok();
	}
	
	/**
	 * @info 推送实时数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("sendRealMessage")
    public @ResponseBody ReturnInfo sendRealMessage(String target, String type, String msg, String from, String id){
		if(StringUtils.judgeBlank(target)||StringUtils.judgeBlank(type)||StringUtils.judgeBlank(msg)||StringUtils.judgeBlank(from)){
			return ReturnInfo.error("参数错误！");
		}
		if(StringUtils.judgeBlank(id) || redisService.opsForValue().get(id)!=null){
			return ReturnInfo.error("参数错误！");
		}else{
			   redisService.opsForValue().set(id, id, 1, TimeUnit.MINUTES);
		}
		ReturnInfo returnInfo = new ReturnInfo();
		try{
			returnInfo=sendInfo(target, type, msg, from, id);
		}catch(Exception e){
			logger.error("target:"+target+" type:"+type+" msg:"+msg+" from:"+from+" id:"+id);
		}
		return returnInfo;
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
			List<Group> groupList = groupService.queryByGroupNameCode(target);
			if(groupList.size()==0){
				return ReturnInfo.error(500,"群数据暂未入库！");
			}		
			List<User> userList = userService.queryByUserNameList(from);
			if(userList.size()==0){
				return ReturnInfo.error(500,"用户数据暂未入库！");
			}
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
					sendInfo.setHeadIcon(userSchedule.getHeadicon());
					sendInfo.setTarget_type("chatgroups");
					sendList.add(sendInfo);
					EasemobUtils.EasemobBatchSendInfo(sendList);
			}else if(type.equals("Picture")){
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
							sendInfo.setHeadIcon(userSchedule.getHeadicon());
						sendList.add(sendInfo);
					  EasemobUtils.EasemobBatchSendInfo(sendList);
					}
				}else{
	                logger.error(msg+"文件上传失败！");				
				}
			}else if(type.equals("Sharing")){
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
				Document doc = Jsoup.parse(msg);
				Map<String,Object> map01 = new HashMap<String,Object>();
				map01.put("title", doc.getElementsByTag("title").text());
				map01.put("message", doc.getElementsByTag("des").text());
				map01.put("url", doc.getElementsByTag("url").text());
				map01.put("thumburl", doc.getElementsByTag("thumburl").text());
				sendInfo.setExt(map01);
				if(!StringUtils.judgeBlank(userSchedule.getNickname())){
					  sendInfo.setNickName(userSchedule.getNickname());
					}
					sendInfo.setHeadIcon(userSchedule.getHeadicon());
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
						sendInfo.setHeadIcon(userSchedule.getHeadicon());
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
						map.put("url", "https://a1.easemob.com/"+PropertiesUtils.getInfoConfigProperties().getProperty("easemob.appkeymodel")+"/chatfiles/"+easemobSignUpBean.getEntities().get(0).get("uuid"));																	
						groupIdsList.add(groupList.get(0).getEasemobgroupid());
					    SendInfo sendInfo = new SendInfo();
						sendInfo.setFrom(from);
						sendInfo.setTarget(groupIdsList);
						sendInfo.setMsg(map);
						sendInfo.setTarget_type("chatgroups");
						if(!StringUtils.judgeBlank(userSchedule.getNickname())){
							  sendInfo.setNickName(userSchedule.getNickname());
							}
							sendInfo.setHeadIcon(userSchedule.getHeadicon());
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
