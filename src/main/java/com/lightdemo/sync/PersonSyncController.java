package com.lightdemo.sync;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lightdemo.util.Utils;
/**
 * 组织人员同步controller
 * @author cjqbrave@163.com
 *
 */
@Controller
@RequestMapping(value="sync/")
public class PersonSyncController {
	
	@Value(value="${APPID}")
	private String APPID = null;
	@Value(value="${SECRET}")
	private String SECRET = null;
	@Value(value="${XT_SERVERNAME}")
	private String XT_SERVERNAME = null;
	
		
	@RequestMapping(value = { "getper" })
	public @ResponseBody String getper(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String ticket) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isEmpty(ticket)) {
            sb.append( "ticket");
        } else if (StringUtils.isEmpty(APPID)) {
        	sb.append( "appid");
        } else if (StringUtils.isEmpty(SECRET)) {
        	sb.append( "appSecret");
        } else {
        	return this.getPersonName(ticket);
        }
		return null;
	}
	
	public String getPersonName(String ticket){
		boolean success = true;
		String url = XT_SERVERNAME + "/openauth2/api/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;
		try {
			String rs = Utils.sendPost(url, null);
			if(null != rs && rs.trim().length() > 0){
				JSONObject tokenJson = JSONObject.fromObject(rs);

					if(tokenJson.containsKey("access_token")) {
						String token = tokenJson.getString("access_token");
						String personUrl = XT_SERVERNAME+"/openauth2/api/getcontext?ticket="+ticket+"&access_token="+token;
						String perStr = Utils.sendPost(personUrl,null);
						if(null != perStr && perStr.trim().length() > 0) {
							JSONObject perJson = JSONObject.fromObject(perStr);	
							return perJson.toString();
						}
						 
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
