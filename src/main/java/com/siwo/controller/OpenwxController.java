package com.siwo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.jeewx.api.mp.aes.WXBizMsgCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.commons.WXXmlToMapUtil;
import com.siwo.model.OpenComponentParams;

@RestController
@RequestMapping("/openwx")
public class OpenwxController {
	 
	/**
     * 获得授权事件接受URL
     *
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param encryptType 加密类型 aes
     * @param msgSignature 消息体签名
     * @param postdata 消息体
     * @return 如果获得只需要返回 SUCCESS
     */
	@PostMapping("/getChat")
	public String ticket(String timestamp, String nonce,
            @RequestParam("encrypt_type") String encryptType,
            @RequestParam("msg_signature") String msgSignature, @RequestBody String postdata) {
        try {
            //这个类是微信官网提供的解密类,需要用到消息校验Token 消息加密Key和服务平台appid
            WXBizMsgCrypt pc = new WXBizMsgCrypt(OpenComponentParams.COMPONENT_TOKEN,
                    OpenComponentParams.aesKey, OpenComponentParams.appId);
            String xml = pc.decryptMsg(msgSignature, timestamp, nonce, postdata);
            
//            System.out.println("WXxml:"+xml);
            
            Map<String, String> result = WXXmlToMapUtil.xmlToMap(xml);// 将xml转为map
 
            String componentVerifyTicket = MapUtils.getString(result, "ComponentVerifyTicket");
            // 存储平台授权票据,保存ticket
            OpenComponentParams.TICKET = componentVerifyTicket;
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


	/**
	 * 消息与事件接收URL
	 * @param request
	 * @param appid
	 * @param response
	 * @return
	 */
	@GetMapping("/$APPID$/callBack")
	public String callBack(HttpServletRequest request,String appid,
			HttpServletResponse response) {
		
		request.getParameter("postdata");
		System.err.println("消息与时间接收URL"+request);
		
		return "success";
	}

}
