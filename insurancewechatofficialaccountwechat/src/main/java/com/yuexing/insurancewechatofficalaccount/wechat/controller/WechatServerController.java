package com.yuexing.insurancewechatofficalaccount.wechat.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yuexing.insurancewechatofficalaccount.wechat.controller.vo.WechatMsgVO;
import com.yuexing.insurancewechatofficalaccount.wechat.enumeuration.WechatEventType;
import com.yuexing.insurancewechatofficalaccount.wechat.enumeuration.WechatMsgType;
import com.yuexing.insurancewechatofficalaccount.wechat.model.UserPO;
import com.yuexing.insurancewechatofficalaccount.wechat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WechatServerController {

    protected static Logger logger = LoggerFactory.getLogger(WechatServerController.class);

    private String msgUniqueKey = "";

    private String msgEndpoint = "cgi-bin/message/custom/send?access_token=%s";
    private String userInfoEndpoint = "cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    @Autowired
    UserService userService;

    @GetMapping("meetWechatServer")
    public String meetWechatServer(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {

        if (verify(signature, timestamp, nonce)) {
            return echostr;
        }

        return "非法的请求";
    }

    @PostMapping("meetWechatServer")
    public String acceptWechatEventOrMessagePush(@RequestBody String body) throws Exception {
        logger.info(body);

        WechatMsgVO msg = new XmlMapper().readValue(body, WechatMsgVO.class);
        try {
            handleWechatEventOrMessagePush(msg);
        } catch (Exception e) {
            logger.error("deal wechat event or message error", e);
        }

        return "";
    }

    private boolean verify(String signature, String timestamp, String nonce) {
        return true;
    }

    private void handleWechatEventOrMessagePush(WechatMsgVO msg) throws Exception {
        Object toUserName = msg.get("ToUserName");
        Object fromUserName = msg.get("FromUserName");
        Object createTime = msg.get("CreateTime");
        Object msgType = msg.get("MsgType");
        Object event = msg.get("Event");

        if (WechatMsgType.EVENT.getCode().equals(msgType) && WechatEventType.SUBSCRIBE.getCode().equals(event)) {
            UserPO hasUserPO = userService.findUserByOpenId(fromUserName.toString());
            if (null != hasUserPO) {
                return;
            }

            UserPO userPO = new UserPO();
            userPO.setOpenId(fromUserName.toString());
            userService.insertUser(userPO);
        }
    }

    private synchronized String getUniqueKey() {
        return this.msgUniqueKey;
    }

    private synchronized void setUniqueKey(String key) {
        this.msgUniqueKey = key;
    }
}
