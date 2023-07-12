package com.getui.push.v2.sdk.api.env;

import com.getui.push.v2.sdk.GtApiConfiguration;

public class ApiContext {

    public GtApiConfiguration configuration;
    public String cid;

    public static ApiContext build() {
        ApiContext context = new ApiContext();
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        context.configuration = apiConfiguration;

        apiConfiguration.setAppId("6SEzGpaRID7g2XH1F3Zrl5");
        apiConfiguration.setAppKey("sdYjjF3Unl9llS9Ec6ZgP2");
        apiConfiguration.setMasterSecret("UJ0cE6cIYu9Zv2yj6vipZ7");
        // 接口调用前缀，请查看文档: 接口调用规范 -> 接口前缀, 可不填写appId
        apiConfiguration.setDomain("https://restapi.getui.com/v2/");
        context.cid = "b727b434994cbafa4f12d24e461e9c46";

        return context;
    }
}
