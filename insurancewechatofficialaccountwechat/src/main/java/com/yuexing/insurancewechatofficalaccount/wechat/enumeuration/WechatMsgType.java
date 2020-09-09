package com.yuexing.insurancewechatofficalaccount.wechat.enumeuration;

public enum WechatMsgType {
    EVENT("event", "事件消息");

    private String code;
    private String description;

    WechatMsgType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}