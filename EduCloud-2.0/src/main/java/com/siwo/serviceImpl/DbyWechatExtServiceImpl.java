package com.siwo.serviceImpl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.jeewx.api.mp.aes.AesException;
import org.jeewx.api.mp.aes.WXBizMsgCrypt;
import org.springframework.stereotype.Service;

import com.siwo.commons.WXXmlToMapUtil;
import com.siwo.model.OpenComponentParams;
import com.siwo.service.DbyWechatExtService;

@Service
public class DbyWechatExtServiceImpl implements DbyWechatExtService{

	/**
	 * 获得授权事件的票据
	 *
	 * @param timestamp    时间戳
	 * @param nonce        随机数
	 * @param msgSignature 消息体签名
	 * @param postData     消息体
	 * @return 如果获得只需要返回 SUCCESS
	 */

	@Override
	public String parsePequest(String timestamp, String nonce, String msgSignature, String postData) {
		 try {
	            //	这个类是微信官网提供的解密类,需要用到消息校验Token 消息加密Key和服务平台appid
	            WXBizMsgCrypt pc = new WXBizMsgCrypt(OpenComponentParams.COMPONENT_TOKEN, OpenComponentParams.aesKey, OpenComponentParams.appId);
	            String xml = pc.decryptMsg(msgSignature, timestamp, nonce, postData);
	            Map<String, String> result = WXXmlToMapUtil.xmlToMap(xml);// 将xml转为map
	            String componentVerifyTicket = MapUtils.getString(result, "ComponentVerifyTicket");
	            if (StringUtils.isNotEmpty(componentVerifyTicket)) {
	                // 存储平台授权票据,保存ticket
	               OpenComponentParams.TICKET = componentVerifyTicket;
	            } else {
	                throw new RuntimeException("微信开放平台，第三方平台获取【验证票据】失败");
	            }
	        } catch (AesException e) {
	            e.printStackTrace();
	        }
	        return "success";
	}
}
