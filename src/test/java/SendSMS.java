import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import com.alibaba.fastjson.JSON;
import com.yishenxiao.commons.beans.smsbean.SmsVariableRequest;
import com.yishenxiao.commons.beans.smsbean.SmsVariableResponse;
import com.yishenxiao.commons.utils.ChuangLanSmsUtil;

/**
 * 
 * @param url
 *            应用地址，类似于http://ip:port/msg/
 * @param un
 *            账号
 * @param pw
 *            密码
 * @param phone
 *            手机号码，多个号码使用","分割
 * @param msg
 *            短信内容
 * @param rd
 *            是否需要状态报告，需要1，不需要0
 * @return 返回值定义参见HTTP协议文档
 * @throws Exception
 */
public class SendSMS {
	public static void main(String[] args) {

		  String report = "true";
          
          String content="【币圈】您的验证码是：{$var}";
          SmsVariableRequest smsVariableRequest = new SmsVariableRequest("CN6433015", "4F9n8yr3Zi509e",
                  content, "15219486931,7596", report, null);

          String requestJson = JSON.toJSONString(smsVariableRequest);
          System.out.println(requestJson);
          String response = ChuangLanSmsUtil.sendSmsByPost("http://smssh1.253.com/msg/variable/json", requestJson);

          SmsVariableResponse smsVariableResponse = (SmsVariableResponse)JSON.parseObject(response, SmsVariableResponse.class);

          System.out.println("response  toString is : " + smsVariableResponse);
          System.out.println(smsVariableResponse.getCode());
          if(null!=smsVariableResponse&&!"0".equals(smsVariableResponse.getCode())){
            /*  if(log.isInfoEnabled()){
                  log.info(smsVariableResponse);
              }*/
          }
	}
}
