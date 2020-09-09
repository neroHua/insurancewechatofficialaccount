package com.yuexing.insurancewechatofficalaccount.wechat.enumeuration;

public enum WechatEventType {
    SUBSCRIBE("subscribe", "订阅事件"),
    UNSUBSCRIBE("unsubscribe", "取消订阅事件");

    private String code;
    private String description;

    WechatEventType(String code, String description) {
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
