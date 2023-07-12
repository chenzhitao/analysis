package com.elai.common.mode;

import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;

//https://www.jiguang.cn/accounts/register
//极光推送
public class JGUtil {
//    static Integer smsTempId =0 ;//短信模板id
//    static String appKey = "";//appkey
//    static String masterSecret = "";//秘钥
//    private static SMSClient smsClient = new SMSClient(masterSecret, appKey);
    /**
     * @Author XIAOYU
     * @Description //发送短信方法0发送成功 1手机号不对
     **/
    public static void createDeivceSms(String phoneNumber, String username, String context,Integer smsTempId,String appKey,String masterSecret) throws Exception {
        if (isPhone(phoneNumber)) {
            SMSPayload payload = SMSPayload.newBuilder()
                    .setMobileNumber(phoneNumber) // 手机号码
                    .setTempId(smsTempId)            // 短信模板ID,需要自己去申请
                    .addTempPara("username", username)  // 模板里面的联系人参数
                    .addTempPara("context", context)  // 模板里面的内容参数
                    .build();
            SMSClient smsClient = new SMSClient(masterSecret, appKey);
            SendSMSResult res = smsClient.sendTemplateSMS(payload);
        }
    }

    /**
     * @Author XIAOYU
     * @Description //验证手机号是否正确
     **/
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        return phone.matches(regex);
    }
}
