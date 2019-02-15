package com.yishenxiao.commons.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.yishenxiao.commons.beans.InformationBean;
import com.yishenxiao.commons.beans.KuaixunBean;
import com.yishenxiao.commons.beans.NoticeBean;
import com.yishenxiao.commons.beans.NoticeTitle;
import com.yishenxiao.commons.beans.ToutiaoBean;
import com.yishenxiao.commons.beans.postbean.GetNoticeDataBean;
import com.yishenxiao.commons.utils.DateUtils;
import com.yishenxiao.commons.utils.JsonUtils;
import com.yishenxiao.commons.utils.ReturnInfo;
import com.yishenxiao.commons.utils.StringUtils;

@Controller
@RequestMapping("information")
public class InformationController {
	
	private static Logger logger = LoggerFactory.getLogger(InformationController.class);
	
	@Autowired(required=false)@Qualifier("mongoTemplate2")
	private MongoTemplate mongoTemplate2;
	
	/**
	 * @Info更新群分类的数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getNoticeDataTitle", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo getNoticeDataTitle(@RequestBody GetNoticeDataBean getNoticeDataBean){
		if(StringUtils.judgeBlank(getNoticeDataBean.getCreatTime()) || getNoticeDataBean.getType()==null || getNoticeDataBean.getLimit()==null){
			return ReturnInfo.error("参数错误！");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<NoticeTitle> strList = new ArrayList<NoticeTitle>();
		NoticeTitle noticeTitle = new NoticeTitle();
		noticeTitle.setId(1);
		noticeTitle.setTitle("头条");		
		strList.add(noticeTitle);
		noticeTitle = new NoticeTitle();
		noticeTitle.setId(2);
		noticeTitle.setTitle("交易所公告");
		strList.add(noticeTitle);
		noticeTitle = new NoticeTitle();
		noticeTitle.setId(3);
		noticeTitle.setTitle("快讯");
		strList.add(noticeTitle);
		map.put("title", strList);
		map.put("data", getData(getNoticeDataBean));
		return ReturnInfo.info(200, map);
  }
	

	/**
	 * @Info更新群分类的数据
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="getNoticeData", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ReturnInfo getNoticeData(@RequestBody GetNoticeDataBean getNoticeDataBean){
		if(StringUtils.judgeBlank(getNoticeDataBean.getCreatTime()) || getNoticeDataBean.getType()==null){
			return ReturnInfo.error("参数错误！");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data", getData(getNoticeDataBean));
		return ReturnInfo.info(200, map);
  }
	
  private Object getData(GetNoticeDataBean getNoticeDataBean){
	  List<InformationBean> beanList = new ArrayList<InformationBean>();
	  if(getNoticeDataBean.getType()==2){//公告
			DBCollection  dbCollection  = mongoTemplate2.getCollection("notice");
			DBObject time = new BasicDBObject();
			time.put("$lt", getNoticeDataBean.getCreatTime());
			DBObject geText = new BasicDBObject();
			geText.put("time", time);
			DBCursor dbCursor = dbCollection.find(geText).limit(getNoticeDataBean.getLimit()).sort(new BasicDBObject("time",-1));			
			while(dbCursor.hasNext()){
				DBObject dbObject3= dbCursor.next();
				NoticeBean noticeBean = JsonUtils.jsonToObj(dbObject3.toString(), NoticeBean.class);
				InformationBean informationBean = new InformationBean();
				informationBean.setAuthor(noticeBean.getT_platform());
				informationBean.setCreateTime(noticeBean.getTime());
				informationBean.setLink(noticeBean.getLink());
				informationBean.setTitle(noticeBean.getContent());
				beanList.add(informationBean);
			}
		}else if(getNoticeDataBean.getType()==1){//头条
			DBCollection  dbCollection  = mongoTemplate2.getCollection("Toutiao");
			DBObject time = new BasicDBObject();
			time.put("$lt", getNoticeDataBean.getCreatTime());
			DBObject geText = new BasicDBObject();
			geText.put("time", time);
			DBCursor dbCursor = dbCollection.find(geText).limit(getNoticeDataBean.getLimit()).sort(new BasicDBObject("time",-1));
			while(dbCursor.hasNext()){
				DBObject dbObject3= dbCursor.next();
				ToutiaoBean toutiaoBean = JsonUtils.jsonToObj(dbObject3.toString(), ToutiaoBean.class);
				List<String> imgUrl=toutiaoBean.getImg_url();
				if(imgUrl.size()>0){
					if(!imgUrl.get(0).contains(".jpg")){
						String imgUrltemp=imgUrl.get(0)+"_image6.png";
						imgUrl.remove(0);
						imgUrl.add(imgUrltemp);
					}
				}
				InformationBean informationBean = new InformationBean();
				informationBean.setAuthor(toutiaoBean.getAuthor());
				informationBean.setCreateTime(toutiaoBean.getTime());
				if(toutiaoBean.getImg_url().size()>0){
				  informationBean.setImg(toutiaoBean.getImg_url().get(0));
				}
				informationBean.setLink(toutiaoBean.getUrl());
				informationBean.setTitle(toutiaoBean.getTitle());
				beanList.add(informationBean);
			}
		}else if(getNoticeDataBean.getType()==3){//快讯
			Date nowDate = DateUtils.longformatDate(getNoticeDataBean.getCreatTime());
			if(nowDate==null){
				nowDate=new Date();
			}
			DBCollection  dbCollection  = mongoTemplate2.getCollection("Kuaixun");
			DBObject time = new BasicDBObject();
			time.put("$lt", nowDate.getTime());
			DBObject geText = new BasicDBObject();
			geText.put("issue_time", time);
			DBCursor dbCursor = dbCollection.find(geText).limit(getNoticeDataBean.getLimit()).sort(new BasicDBObject("issue_time",-1));
			while(dbCursor.hasNext()){
				DBObject dbObject3= dbCursor.next();	
				KuaixunBean kuaixunBean = JsonUtils.jsonToObj(dbObject3.toString(), KuaixunBean.class);
				InformationBean informationBean = new InformationBean();
				informationBean.setAuthor(kuaixunBean.getSource());
				informationBean.setCreateTime(kuaixunBean.getIssue_time());
				informationBean.setLink(kuaixunBean.getUrl());
				informationBean.setTitle(kuaixunBean.getContent());
				beanList.add(informationBean);
			}
	    }
	  return beanList;
  }	
}
