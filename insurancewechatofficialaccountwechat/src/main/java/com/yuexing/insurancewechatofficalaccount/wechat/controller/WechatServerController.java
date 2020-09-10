package com.yuexing.insurancewechatofficalaccount.wechat.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.yuexing.insurancewechatofficalaccount.wechat.controller.base.BaseWechatController;
import com.yuexing.insurancewechatofficalaccount.wechat.controller.vo.WechatMsgVO;
import com.yuexing.insurancewechatofficalaccount.wechat.enumeuration.WechatEventType;
import com.yuexing.insurancewechatofficalaccount.wechat.enumeuration.WechatMsgType;
import com.yuexing.insurancewechatofficalaccount.wechat.model.UserPO;
import com.yuexing.insurancewechatofficalaccount.wechat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WechatServerController extends BaseWechatController {

    protected static Logger logger = LoggerFactory.getLogger(WechatServerController.class);

    private String msgUniqueKey = "";

    private String msgEndpoint = "cgi-bin/message/custom/send?access_token=%s";
    private String userInfoEndpoint = "cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    public static String INSURANCE_MESSAGE = "沙湾县公共交通有限责任公司车辆保险采购项目成交公告"
            + "/n 采购人：沙湾县公共交通有限责任公司"
            + "/n 代理机构：新疆润标工程项目管理服务有限公司"
            + "/n 2020.09.09 19:43:21"
            + "/n 成交公告"
            + "/n 新疆"
            + "/n 服务/金融服务/保险服务/其他保险服务";

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

        return INSURANCE_MESSAGE;
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
            if (null == hasUserPO) {
                UserPO userPO = new UserPO();
                userPO.setOpenId(fromUserName.toString());
                userService.insertUser(userPO);
            }
        }

        sendWelcomeMessage(fromUserName.toString());
    }

    @RequestMapping("sendMessage")
    public void sendMessage() {
        sendWelcomeMessage("o6TsT6lNJFzWZzwtpxLH7QdpvT_Y");
    }

    private void sendWelcomeMessage(String openId) {
        Map request = new HashMap();
        request.put("touser", openId);
        request.put("msgtype", "text");
        Map text = new HashMap();

        text.put("content", INSURANCE_MESSAGE);

        request.put("text", text);

        String sendMsgUrl = String.format(apiEndpoint + msgEndpoint, getAccessToken());
        Map result = null;
        try {
            result = doExecutePostRequest(sendMsgUrl, request);
            if (null != result) {
                if (result.get("errcode").equals("0")) {
                    logger.error("Send Message Failed");
                }
            }
        } catch (Exception e) {
            logger.error("send message exception", e);
        }
    }

    private synchronized String getUniqueKey() {
        return this.msgUniqueKey;
    }

    private synchronized void setUniqueKey(String key) {
        this.msgUniqueKey = key;
    }
}
