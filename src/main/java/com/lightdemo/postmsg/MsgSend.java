package com.lightdemo.postmsg;
/**
 * 发送消息控制类
 * @author cjqbrave@163.com
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lightdemo.model.Common;
import com.lightdemo.util.FileUtil;
import com.lightdemo.util.PropertiesUtil;
import com.lightdemo.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping(value="msg/")
public class MsgSend {
	
	@Value("${PUBACC}")
	private String PUBACC;
	@Value("${EID}")
	private String EID;
	@Value("${PUBACC_KEY}")
	private String PUBACC_KEY;
	@Value("${XT_SERVERNAME}")
	private String XT_SERVERNAME;
	@Value("${APPID}")
	private String APPID;
	public static int DEFAULT_BUFFER_SIZE = 1024;
	
	String imgName = "timg.jpg";
	String imgFilePath= "timg.jpg";
	
	String imgName1 = "timg1.jpg";
	String imgFilePath1= "timg1.jpg";
	
	@RequestMapping(value = { "msgmain" })
	public String msgmain(){
		JSONObject reJson = null;
		
		return "msgmain";
	}
	/**
	 * 发送消息
	 * @param text 消息文本
	 * @param users 用户的openId
	 * @return
	 */
	@RequestMapping(value="/postmsg",method=RequestMethod.POST,
	produces="application/json;charset=UTF-8")
	public @ResponseBody String postmsg(String text, String users) {
		return postmsg(text, users, 5, System.currentTimeMillis()+"");
	}
	/**
	 * 发送消息
	 * @param text 文本
	 * @param users 用户openId
	 * @param type 类型
	 * @return
	 */
	public String postmsg(String text, String users, int type, String sourceId) {
		String path = MsgSend.class.getResource("/").getPath();
		JSONObject reJson = new JSONObject();
		List<String> toUserList = null;
		if(!StringUtils.isBlank(users)) {
			if(users.contains(",")) {
				String[] arr = users.split(",");
				toUserList = new ArrayList<String>(arr.length);
				for(String obj : arr){
					toUserList.add(obj);
				}
			} else{
				toUserList = new ArrayList<String>(1);
				toUserList.add(users);
			}
		} 
		
		try {
			String random = String.valueOf(Math.random() * 100);
			String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
			long time_1 = System.currentTimeMillis();
			// form
			JSONObject from = new JSONObject();
			from.put("no", EID); // 
			from.put("pub", PUBACC); // 
			from.put("time", time_1);
			from.put("nonce", random);
			String[] data = { EID, PUBACC, PUBACC_KEY, random,
					String.valueOf(time_1) };
			from.put("pubtoken", Utils.sha(data));
			// to
			JSONArray tos = new JSONArray();
			JSONObject to = new JSONObject();
			to.put("no", EID);
			if(null != toUserList) {
				to.put("user", toUserList);
				to.put("code", 2);
			} else {
				to.put("code", "all");
			}
			tos.add(to);

			JSONObject msg = new JSONObject();
			if(type == 5) {
				msg.put("url", "http://www.yunzhijia.com/home");
				msg.put("appid", APPID);
				msg.put("todo", 0);
//				msg.put("todo", 1);
//				msg.put("todoPriStatus", "undo");
				if(!StringUtils.isBlank(sourceId)) {
					msg.put("sourceid", sourceId);
				}
				msg.put("text", text);
			} else if(type == 6) {
				JSONArray list = new JSONArray();
				JSONObject msgJson = new JSONObject();
//				msgJson.put("date", nowDate);
				msgJson.put("title", "红包来袭，动起来");
				msgJson.put("text", text);
				msgJson.put("name", imgName);
				msgJson.put("pic", FileUtil.encodeBase64File(path+"/"+imgFilePath));
				msgJson.put("url", "http://www.yunzhijia.com/home");
				
				JSONObject msgJson1 = new JSONObject();
//				msgJson1.put("date", nowDate);
//				msgJson1.put("title", "鞭炮响");
				msgJson1.put("text", "鞭炮起源至今有1000多年的历史。在没有火药和纸张时，古代人便用火烧竹子，使之爆裂发声，以驱逐瘟神。");
				msgJson1.put("name", imgName1);
				msgJson1.put("pic", FileUtil.encodeBase64File(path+"/"+imgFilePath1));
				msgJson1.put("url", "http://www.yunzhijia.com/home");
				
				list.add(msgJson);
				list.add(msgJson1);
				msg.put("model", 3);
				msg.put("todo", 0);
//				msg.put("sourceid", sourceId);
				msg.put("appid", APPID);
				msg.put("list", list);
			} else {
				msg.put("text", text);
			}
			JSONObject content = new JSONObject();
			content.put("from", from);
			content.put("to", tos);
			content.put("type", type);
			content.put("msg", msg);
			String contentStr = content.toString();
			System.out.println(contentStr);
			String rs = Utils.sendPostO(XT_SERVERNAME+"/pubacc/pubsend", contentStr);
			System.out.println("结果是---"+rs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reJson.toString();
	
	}
	
	
	/**
	 * 修改待办状态的接口
	 */
	public void changeMsgTodoStatus(String openId, String msgId){
		JSONObject param = new JSONObject();
//		param.put("openId", openId);
		param.put("account", openId);
		param.put("todoMsgIds", msgId);
		param.put("todoStatus","done");
		param.put("no",EID);
		param.put("pub",PUBACC);
		param.put("pubkey",PUBACC_KEY);
		String nonce = String.valueOf(Math.random());
		String time = String.valueOf(System.currentTimeMillis());
		String [] str = new String[]{EID,PUBACC,PUBACC_KEY,nonce,time};
		param.put("pubtoken",Utils.sha(str));
		param.put("nonce",nonce);
		param.put("time",time);
		Utils.sendPostO(XT_SERVERNAME+"/pubacc/changeTodoMsgStatus", param.toString());
	}
	
	public static void main(String[] args) {
		Common cm = (Common) PropertiesUtil.loadCommonProperties("common.properties", Common.class);
		MsgSend ms = new MsgSend();
		ms.XT_SERVERNAME = cm.getXT_SERVERNAME();
		ms.EID = cm.getEID();
		ms.PUBACC = cm.getPUBACC();
		ms.PUBACC_KEY = cm.getPUBACC_KEY();
		ms.APPID = cm.getAPPID();
//		ms.postmsg("红包来了，抢红包啦...",cm.getOPENIDS(), 5, cm.getMSGID());
		ms.postmsg("红包来了，抢红包啦...",cm.getOPENIDS(), 6, cm.getMSGID());
//		ms.changeMsgTodoStatus(cm.getOPENIDS(), cm.getMSGID());
	}
	
}
