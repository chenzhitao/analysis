package com.elai.third.tencent.sample;

import com.alibaba.fastjson.JSONObject;
import com.elai.third.tencent.sample.dto.*;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;

import java.util.HashMap;

//腾讯发送短信
//https://console.cloud.tencent.com/api/explorer?Product=sms&Version=2021-01-11&Action=SmsPackagesStatistics&SignVersion=
//
public class TencentYunUtils {

    public static SmsClient createClient(String secretId, String secretKey, String region) throws Exception {
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(secretId, secretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        SmsClient client = new SmsClient(cred, region, clientProfile);
        return client;
    }

    //套餐包信息统计
    public static HashMap smsPackagesStatistics(SmsPackagesStatisticsDto smsPackagesStatisticsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsPackagesStatisticsDto.getSecretId(), smsPackagesStatisticsDto.getSecretKey(), smsPackagesStatisticsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SmsPackagesStatisticsRequest req = new SmsPackagesStatisticsRequest();
            req.setSmsSdkAppId(smsPackagesStatisticsDto.getSmsSdkAppId());
            req.setLimit(smsPackagesStatisticsDto.gettLimit());
            req.setOffset(smsPackagesStatisticsDto.getOffset());
            req.setBeginTime(smsPackagesStatisticsDto.getBeginTime());
            req.setEndTime(smsPackagesStatisticsDto.getEndTime());
            // 返回的resp是一个SmsPackagesStatisticsResponse的实例，与请求对象对应
            SmsPackagesStatisticsResponse resp = client.SmsPackagesStatistics(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //发送短信数据统计
    public static HashMap sendStatusStatistics(SmsPackagesStatisticsDto smsPackagesStatisticsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsPackagesStatisticsDto.getSecretId(), smsPackagesStatisticsDto.getSecretKey(), smsPackagesStatisticsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            SendStatusStatisticsRequest req = new SendStatusStatisticsRequest();
            req.setBeginTime(smsPackagesStatisticsDto.getBeginTime());
            req.setEndTime(smsPackagesStatisticsDto.getEndTime());
            req.setSmsSdkAppId(smsPackagesStatisticsDto.getSmsSdkAppId());
            req.setLimit(smsPackagesStatisticsDto.gettLimit());
            req.setOffset(smsPackagesStatisticsDto.getOffset());
            // 返回的resp是一个SendStatusStatisticsResponse的实例，与请求对象对应
            SendStatusStatisticsResponse resp = client.SendStatusStatistics(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }


    //回执数据统计
    public static HashMap callbackStatusStatistics(SmsPackagesStatisticsDto smsPackagesStatisticsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsPackagesStatisticsDto.getSecretId(), smsPackagesStatisticsDto.getSecretKey(), smsPackagesStatisticsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            CallbackStatusStatisticsRequest req = new CallbackStatusStatisticsRequest();
            req.setBeginTime(smsPackagesStatisticsDto.getBeginTime());
            req.setEndTime(smsPackagesStatisticsDto.getEndTime());
            req.setSmsSdkAppId(smsPackagesStatisticsDto.getSmsSdkAppId());
            req.setLimit(smsPackagesStatisticsDto.gettLimit());
            req.setOffset(smsPackagesStatisticsDto.getOffset());
            // 返回的resp是一个CallbackStatusStatisticsResponse的实例，与请求对象对应
            CallbackStatusStatisticsResponse resp = client.CallbackStatusStatistics(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //发送短信
    public static HashMap sendSms(SendSmsDto sendSmsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(sendSmsDto.getSecretId(), sendSmsDto.getSecretKey(), sendSmsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(sendSmsDto.getPhoneNumberSet());
            req.setSmsSdkAppId(sendSmsDto.getSmsSdkAppId());
            req.setTemplateId(sendSmsDto.getTemplateId());
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }


    //拉取单个号码短信下发状态
    public static HashMap pullSmsSendStatusByPhoneNumber(PullSmsDto pullSmsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(pullSmsDto.getSecretId(), pullSmsDto.getSecretKey(), pullSmsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            PullSmsSendStatusByPhoneNumberRequest req = new PullSmsSendStatusByPhoneNumberRequest();
            req.setBeginTime(pullSmsDto.getBeginTime());
            req.setOffset(pullSmsDto.getOffset());
            req.setLimit(pullSmsDto.gettLimit());
            req.setPhoneNumber(pullSmsDto.getPhoneNumber());
            req.setSmsSdkAppId(pullSmsDto.getSmsSdkAppId());
            // 返回的resp是一个PullSmsSendStatusByPhoneNumberResponse的实例，与请求对象对应
            PullSmsSendStatusByPhoneNumberResponse resp = client.PullSmsSendStatusByPhoneNumber(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //拉取单个号码短信下发状态
    public static HashMap pullSmsSendStatus(PullSmsDto pullSmsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(pullSmsDto.getSecretId(), pullSmsDto.getSecretKey(), pullSmsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            PullSmsSendStatusRequest req = new PullSmsSendStatusRequest();
            req.setLimit(pullSmsDto.gettLimit());
            req.setSmsSdkAppId(pullSmsDto.getSmsSdkAppId());
            // 返回的resp是一个PullSmsSendStatusResponse的实例，与请求对象对应
            PullSmsSendStatusResponse resp = client.PullSmsSendStatus(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //拉取单个号码短信回复状态
    public static HashMap pullSmsReplyStatusByPhoneNumber(PullSmsDto pullSmsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(pullSmsDto.getSecretId(), pullSmsDto.getSecretKey(), pullSmsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            PullSmsReplyStatusByPhoneNumberRequest req = new PullSmsReplyStatusByPhoneNumberRequest();
            req.setBeginTime(pullSmsDto.getBeginTime());
            req.setOffset(pullSmsDto.getOffset());
            req.setLimit(pullSmsDto.gettLimit());
            req.setPhoneNumber(pullSmsDto.getPhoneNumber());
            req.setSmsSdkAppId(pullSmsDto.getSmsSdkAppId());
            // 返回的resp是一个PullSmsReplyStatusByPhoneNumberResponse的实例，与请求对象对应
            PullSmsReplyStatusByPhoneNumberResponse resp = client.PullSmsReplyStatusByPhoneNumber(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //拉取短信回复状态
    public static HashMap pullSmsReplyStatus(PullSmsDto pullSmsDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(pullSmsDto.getSecretId(), pullSmsDto.getSecretKey(), pullSmsDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            PullSmsReplyStatusRequest req = new PullSmsReplyStatusRequest();
            req.setLimit(pullSmsDto.gettLimit());
            req.setSmsSdkAppId(pullSmsDto.getSmsSdkAppId());
            // 返回的resp是一个PullSmsReplyStatusResponse的实例，与请求对象对应
            PullSmsReplyStatusResponse resp = client.PullSmsReplyStatus(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //修改短信模板
    public static HashMap modifySmsTemplate(SmsTemplateDto smsTemplateDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsTemplateDto.getSecretId(), smsTemplateDto.getSecretKey(), smsTemplateDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            ModifySmsTemplateRequest req = new ModifySmsTemplateRequest();
            req.setTemplateId(smsTemplateDto.getTemplateId());
            req.setTemplateName(smsTemplateDto.getTemplateName());
            req.setTemplateContent(smsTemplateDto.getTemplateContent());
            req.setSmsType(smsTemplateDto.getSmsType());
            req.setInternational(smsTemplateDto.getInternational());
            req.setRemark(smsTemplateDto.getRemark());
            // 返回的resp是一个ModifySmsTemplateResponse的实例，与请求对象对应
            ModifySmsTemplateResponse resp = client.ModifySmsTemplate(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //修改短信模板
    public static HashMap describeSmsTemplateList(SmsTemplateDto smsTemplateDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsTemplateDto.getSecretId(), smsTemplateDto.getSecretKey(), smsTemplateDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            DescribeSmsTemplateListRequest req = new DescribeSmsTemplateListRequest();
            req.setTemplateIdSet(smsTemplateDto.getTemplateIdSet());
            req.setInternational(smsTemplateDto.getInternational());
            // 返回的resp是一个DescribeSmsTemplateListResponse的实例，与请求对象对应
            DescribeSmsTemplateListResponse resp = client.DescribeSmsTemplateList(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //删除短信模板
    public static HashMap deleteSmsTemplate(SmsTemplateDto smsTemplateDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsTemplateDto.getSecretId(), smsTemplateDto.getSecretKey(), smsTemplateDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            DeleteSmsTemplateRequest req = new DeleteSmsTemplateRequest();
            req.setTemplateId(smsTemplateDto.getTemplateId());
            // 返回的resp是一个DeleteSmsTemplateResponse的实例，与请求对象对应
            DeleteSmsTemplateResponse resp = client.DeleteSmsTemplate(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }


    //添加短信模板
    public static HashMap addSmsTemplate(SmsTemplateDto smsTemplateDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsTemplateDto.getSecretId(), smsTemplateDto.getSecretKey(), smsTemplateDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            AddSmsTemplateRequest req = new AddSmsTemplateRequest();
            req.setTemplateName(smsTemplateDto.getTemplateName());
            req.setTemplateContent(smsTemplateDto.getTemplateContent());
            req.setSmsType(smsTemplateDto.getSmsType());
            req.setInternational(smsTemplateDto.getInternational());
            req.setRemark(smsTemplateDto.getRemark());
            // 返回的resp是一个AddSmsTemplateResponse的实例，与请求对象对应
            AddSmsTemplateResponse resp = client.AddSmsTemplate(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //修改短信签名
    public static HashMap modifySmsSign(SmsSignDto smsSignDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsSignDto.getSecretId(), smsSignDto.getSecretKey(), smsSignDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            ModifySmsSignRequest req = new ModifySmsSignRequest();
            req.setSignId(smsSignDto.getSignId());
            req.setSignName(smsSignDto.getSignName());
            req.setSignType(smsSignDto.getSignType());
            req.setDocumentType(smsSignDto.getDocumentType());
            req.setInternational(smsSignDto.getInternational());
            req.setSignPurpose(smsSignDto.getSignPurpose());
            req.setProofImage(smsSignDto.getProofImage());
            // 返回的resp是一个ModifySmsSignResponse的实例，与请求对象对应
            ModifySmsSignResponse resp = client.ModifySmsSign(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }

    //短信签名状态查询
    public static HashMap describeSmsSignList(SmsSignDto smsSignDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsSignDto.getSecretId(), smsSignDto.getSecretKey(), smsSignDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            DescribeSmsSignListRequest req = new DescribeSmsSignListRequest();
            req.setSignIdSet(smsSignDto.getSignIdSet());
            req.setInternational(smsSignDto.getInternational());
            // 返回的resp是一个DescribeSmsSignListResponse的实例，与请求对象对应
            DescribeSmsSignListResponse resp = client.DescribeSmsSignList(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }


    //删除短信签名
    public static HashMap deleteSmsSign(SmsSignDto smsSignDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsSignDto.getSecretId(), smsSignDto.getSecretKey(), smsSignDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            DeleteSmsSignRequest req = new DeleteSmsSignRequest();
            req.setSignId(smsSignDto.getSignId());
            // 返回的resp是一个DeleteSmsSignResponse的实例，与请求对象对应
            DeleteSmsSignResponse resp = client.DeleteSmsSign(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }


    //添加短信签名
    public static HashMap addSmsSign(SmsSignDto smsSignDto) {
        String rt = "";
        try {
            SmsClient client = null;
            try {
                client = createClient(smsSignDto.getSecretId(), smsSignDto.getSecretKey(), smsSignDto.getRegion());
            } catch (Exception e) {
                rt = "";
            }
            AddSmsSignRequest req = new AddSmsSignRequest();
            req.setSignName(smsSignDto.getSignName());
            req.setSignType(smsSignDto.getSignType());
            req.setDocumentType(smsSignDto.getDocumentType());
            req.setInternational(smsSignDto.getInternational());
            req.setSignPurpose(smsSignDto.getSignPurpose());
            req.setProofImage(smsSignDto.getProofImage());
            req.setCommissionImage(smsSignDto.getCommissionImage());
            req.setRemark(smsSignDto.getRemark());
            // 返回的resp是一个AddSmsSignResponse的实例，与请求对象对应
            AddSmsSignResponse resp = client.AddSmsSign(req);
            // 输出json格式的字符串回包
            rt = SmsPackagesStatisticsResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            rt = "";
        }
        return getResMap(rt);
    }


    public static HashMap  getResMap(String  rt){
        HashMap resultMap=new HashMap();
        if(rt==""){
            resultMap.put("code",1);
            return resultMap;
        }else{
            JSONObject paramJson = JSONObject.parseObject(rt);
            //发送数据统计响应包体
            if(paramJson.get("SmsPackagesStatisticsSet")!=null){
                SmsPackagesStatistics smsPackagesStatisticsSet= (SmsPackagesStatistics) paramJson.get("SmsPackagesStatisticsSet");
                resultMap.put("smsPackagesStatistics",smsPackagesStatisticsSet);
            }
            //发送数据统计响应包体。
            if(paramJson.get("SendStatusStatistics")!=null){
                SendStatusStatistics sendStatusStatistics= (SendStatusStatistics) paramJson.get("SendStatusStatistics");
                resultMap.put("sendStatusStatistics",sendStatusStatistics);
            }
            //回执数据统计响应包体
            if(paramJson.get("CallbackStatusStatistics")!=null){
                CallbackStatusStatistics callbackStatusStatistics= (CallbackStatusStatistics) paramJson.get("CallbackStatusStatistics");
                resultMap.put("callbackStatusStatistics",callbackStatusStatistics);
            }
            //短信发送状态
            if(paramJson.get("SendStatusSet")!=null){
                SendStatus	 sendStatusSet= (SendStatus	) paramJson.get("SendStatusSet");
                resultMap.put("sendStatusSet",sendStatusSet);
            }
            //下发状态响应集合
            if(paramJson.get("PullSmsSendStatusSet")!=null){
                PullSmsSendStatus pullSmsSendStatusSet= (PullSmsSendStatus) paramJson.get("PullSmsSendStatusSet");
                resultMap.put("pullSmsSendStatusSet",pullSmsSendStatusSet);
            }
            //回复状态响应集合
            if(paramJson.get("PullSmsReplyStatusSet")!=null){
                PullSmsReplyStatus pullSmsReplyStatus= (PullSmsReplyStatus) paramJson.get("PullSmsReplyStatusSet");
                resultMap.put("pullSmsReplyStatus",pullSmsReplyStatus);
            }

            //修改模板参数响应
            if(paramJson.get("ModifyTemplateStatus")!=null){
                ModifyTemplateStatus modifyTemplateStatus= (ModifyTemplateStatus) paramJson.get("ModifyTemplateStatus");
                resultMap.put("modifyTemplateStatus",modifyTemplateStatus);
            }

            //获取短信模板信息响应
            if(paramJson.get("DescribeTemplateStatusSet")!=null){
                DescribeTemplateListStatus describeTemplateStatusSet= (DescribeTemplateListStatus) paramJson.get("DescribeTemplateStatusSet");
                resultMap.put("describeTemplateStatusSet",describeTemplateStatusSet);
            }
            //获删除模板响应
            if(paramJson.get("DeleteTemplateStatus")!=null){
                DeleteTemplateStatus deleteTemplateStatus= (DeleteTemplateStatus) paramJson.get("DeleteTemplateStatus");
                resultMap.put("deleteTemplateStatus",deleteTemplateStatus);
            }
            //添加短信模板响应包体
            if(paramJson.get("AddTemplateStatus")!=null){
                AddTemplateStatus addTemplateStatus= (AddTemplateStatus) paramJson.get("AddTemplateStatus");
                resultMap.put("addTemplateStatus",addTemplateStatus);
            }
            //修改签名响应
            if(paramJson.get("ModifySignStatus")!=null){
                ModifySignStatus modifySignStatus= (ModifySignStatus) paramJson.get("ModifySignStatus");
                resultMap.put("modifySignStatus",modifySignStatus);
            }
            //获取签名信息响应
            if(paramJson.get("DescribeSignListStatusSet")!=null){
                DescribeSignListStatus describeSignListStatusSet= (DescribeSignListStatus) paramJson.get("DescribeSignListStatusSet");
                resultMap.put("describeSignListStatusSet",describeSignListStatusSet);
            }
            //	删除签名响应
            if(paramJson.get("DeleteSignStatus")!=null){
                DeleteSignStatus deleteSignStatus= (DeleteSignStatus) paramJson.get("DeleteSignStatus");
                resultMap.put("deleteSignStatus",deleteSignStatus);
            }
            //添加签名响应
            if(paramJson.get("AddSignStatus")!=null){
                AddSignStatus addSignStatus= (AddSignStatus) paramJson.get("AddSignStatus");
                resultMap.put("addSignStatus",addSignStatus);
            }
            String requestId=paramJson.getString("RequestId");
            resultMap.put("requestId",requestId);
            resultMap.put("code",0);
            return resultMap;
        }
    }
}
