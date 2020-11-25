package com.siwo.service;

public interface DbyWechatExtService {

	 /** 获得授权事件的票据
     *
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @param msgSignature 消息体签名
     * @param postData     消息体
     * @return 如果获得只需要返回 SUCCESS
     */
    String parsePequest(String timestamp, String nonce, String msgSignature, String postData);
}
