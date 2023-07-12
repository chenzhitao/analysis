package com.elai.third.aliyun.sample;
import com.aliyun.dysmsapi20170525.models.*;
import com.elai.third.aliyun.sample.dto.SendSmsDto;
import com.elai.third.aliyun.sample.dto.SignFileDto;
import com.elai.third.aliyun.sample.dto.SmsSignDto;
import com.elai.third.aliyun.sample.dto.SmsTemplateDto;
import com.aliyun.teaopenapi.models.Config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
阿里云发送短信
参考：https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/QuerySmsSign?params={}
*/
public class AliyunUtils {

    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config();
        config.accessKeyId = accessKeyId;
        config.accessKeySecret = accessKeySecret;
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    //发送短信
    public static HashMap sendSms(SendSmsDto sendSmsDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(sendSmsDto.getAccessKeyId(), sendSmsDto.getAccessKeySecret());
        SendSmsRequest sendReq = new SendSmsRequest()
                .setPhoneNumbers(sendSmsDto.getPhoneNumbers())
                .setSignName(sendSmsDto.getSignName())
                .setTemplateCode(sendSmsDto.getTemplateCode())
                .setTemplateParam(sendSmsDto.getTemplateParam());
        SendSmsResponse sendResp = client.sendSms(sendReq);
        /*
        请求状态码。
        返回OK代表请求成功。
        */
        String code = sendResp.body.code;
        //发送回执ID
        String bizId = sendResp.body.bizId;
        //状态码的描述
        String message = sendResp.body.message;
        //请求ID
        String requestId = sendResp.body.requestId;
        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("bizId",bizId);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        return resultMap;
    }

    //批量发送短信
    public static HashMap sendBatchSms(SendSmsDto sendSmsDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(sendSmsDto.getAccessKeyId(), sendSmsDto.getAccessKeySecret());
        SendBatchSmsRequest sendReq = new SendBatchSmsRequest()
                .setPhoneNumberJson(sendSmsDto.getPhoneNumbers())
                .setSignNameJson(sendSmsDto.getSignName())
                .setTemplateCode(sendSmsDto.getTemplateCode())
                .setTemplateParamJson(sendSmsDto.getTemplateParam());
        SendBatchSmsResponse sendResp = client.sendBatchSms(sendReq);
        /*
        请求状态码。
        返回OK代表请求成功。
        */
        String code = sendResp.body.code;
        //发送回执ID
        String bizId = sendResp.body.bizId;
        //状态码的描述
        String message = sendResp.body.message;
        //请求ID
        String requestId = sendResp.body.requestId;
        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("bizId",bizId);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        return resultMap;
    }


    //查看短信发送记录和发送状态
    public static HashMap querySendDetails(SendSmsDto sendSmsDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(sendSmsDto.getAccessKeyId(), sendSmsDto.getAccessKeySecret());
        QuerySendDetailsRequest querySendDetailsRequest = new QuerySendDetailsRequest()
                .setPhoneNumber(sendSmsDto.getPhoneNumbers())
                .setBizId(sendSmsDto.getBizId())
                .setSendDate(sendSmsDto.getSendDate())
                .setPageSize(sendSmsDto.getPageSize())
                .setCurrentPage(sendSmsDto.getCurrentPage());
        // 复制代码运行请自行打印 API 的返回值
        QuerySendDetailsResponse querySendDetailsResponse= client.querySendDetails(querySendDetailsRequest);
        /*
        请求状态码。
        返回OK代表请求成功。
        */
        String code = querySendDetailsResponse.body.code;
        //状态码的描述
        String message = querySendDetailsResponse.body.message;
        //请求ID
        String requestId = querySendDetailsResponse.body.requestId;
        //短信发送总条数
        String totalCount = querySendDetailsResponse.body.totalCount;
        //短信发送明细
        java.util.List<QuerySendDetailsResponseBody.QuerySendDetailsResponseBodySmsSendDetailDTOsSmsSendDetailDTO> dtos = querySendDetailsResponse.body.smsSendDetailDTOs.smsSendDetailDTO;
        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
        resultMap.put("start",message);
        resultMap.put("end",message);
        resultMap.put("requestId",requestId);
        resultMap.put("totalCount",totalCount);
        resultMap.put("smsSendDetailDTOs",dtos);
        return resultMap;
    }

    //申请短信签名
    public static HashMap addSmsSign(SmsSignDto smsSignDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsSignDto.getAccessKeyId(), smsSignDto.getAccessKeySecret());

        List<AddSmsSignRequest.AddSmsSignRequestSignFileList> signFileList=new ArrayList<>();
        List<SignFileDto> signFileDtoList=smsSignDto.getSignFileDto();
        if(signFileDtoList!=null&&signFileDtoList.size()>0){
            for (SignFileDto signFileDto:signFileDtoList){
                AddSmsSignRequest.AddSmsSignRequestSignFileList addSmsSignRequestSignFileList = new AddSmsSignRequest.AddSmsSignRequestSignFileList()
                        .setFileContents(signFileDto.getFileContents())
                        .setFileSuffix(signFileDto.getFileSuffix());
                signFileList.add(addSmsSignRequestSignFileList);
            }
        }

        AddSmsSignRequest addSmsSignRequest = new AddSmsSignRequest()
                .setSignName(smsSignDto.getSignName())
                .setSignSource(smsSignDto.getSignSource())
                .setRemark(smsSignDto.getRemark())
                .setSignFileList(signFileList);
        AddSmsSignResponse addSmsSignResponse= client.addSmsSign(addSmsSignRequest);

        String code = addSmsSignResponse.body.code;
        String message = addSmsSignResponse.body.message;
        String requestId = addSmsSignResponse.body.requestId;
        String signName = addSmsSignResponse.body.signName;

        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        resultMap.put("signName",signName);

        return resultMap;

    }


    //删除短信签名
    public static HashMap deleteSmsSign(SmsSignDto smsSignDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsSignDto.getAccessKeyId(), smsSignDto.getAccessKeySecret());

        DeleteSmsSignRequest deleteSmsSignRequest = new DeleteSmsSignRequest()
                .setSignName(smsSignDto.getSignName());
        // 复制代码运行请自行打印 API 的返回值
        DeleteSmsSignResponse deleteSmsSign=  client.deleteSmsSign(deleteSmsSignRequest);

        String code = deleteSmsSign.body.code;
        String message = deleteSmsSign.body.message;
        String requestId = deleteSmsSign.body.requestId;
        String signName = deleteSmsSign.body.signName;

        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        resultMap.put("signName",signName);

        return resultMap;

    }

    //修改短信签名
    public static HashMap modifySmsSign(SmsSignDto smsSignDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsSignDto.getAccessKeyId(), smsSignDto.getAccessKeySecret());

        List<ModifySmsSignRequest.ModifySmsSignRequestSignFileList> signFileList=new ArrayList<>();
        List<SignFileDto> signFileDtoList=smsSignDto.getSignFileDto();
        if(signFileDtoList!=null&&signFileDtoList.size()>0){
            for (SignFileDto signFileDto:signFileDtoList){
                ModifySmsSignRequest.ModifySmsSignRequestSignFileList smsSignRequestSignFileList = new ModifySmsSignRequest.ModifySmsSignRequestSignFileList()
                        .setFileContents(signFileDto.getFileContents())
                        .setFileSuffix(signFileDto.getFileSuffix());
                signFileList.add(smsSignRequestSignFileList);
            }
        }

        ModifySmsSignRequest modifySmsSignRequest = new ModifySmsSignRequest()
                .setSignName(smsSignDto.getSignName())
                .setSignSource(smsSignDto.getSignSource())
                .setRemark(smsSignDto.getRemark())
                .setSignFileList(signFileList);
        // 复制代码运行请自行打印 API 的返回值
        ModifySmsSignResponse modifySmsSignResponse= client.modifySmsSign(modifySmsSignRequest);

        String code = modifySmsSignResponse.body.code;
        String message = modifySmsSignResponse.body.message;
        String requestId = modifySmsSignResponse.body.requestId;
        String signName = modifySmsSignResponse.body.signName;

        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        resultMap.put("signName",signName);

        return resultMap;

    }

    //查询短信签名申请状态
    public static HashMap querySmsSign(SmsSignDto smsSignDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsSignDto.getAccessKeyId(), smsSignDto.getAccessKeySecret());
        QuerySmsSignRequest querySmsSignRequest = new QuerySmsSignRequest()
                .setSignName(smsSignDto.getSignName());
        // 复制代码运行请自行打印 API 的返回值
        QuerySmsSignResponse querySmsSignResponse= client.querySmsSign(querySmsSignRequest);

        String requestId = querySmsSignResponse.body.requestId;
        String message = querySmsSignResponse.body.message;
        String createDate = querySmsSignResponse.body.createDate;
        //短信签名
        String signName = querySmsSignResponse.body.signName;
        String code = querySmsSignResponse.body.code;
       /*
        0：审核中。
        1：审核通过。
        2：审核失败，请在返回参数Reason中查看审核失败原因。
        */
        Integer signStatus =querySmsSignResponse.body.signStatus;
        //审核失败的原因
        String  reason =querySmsSignResponse.body.reason;

        HashMap resultMap=new HashMap();
        resultMap.put("requestId",requestId);
        resultMap.put("message",message);
        resultMap.put("createDate",createDate);
        resultMap.put("signName",signName);
        resultMap.put("code",code);
        resultMap.put("signStatus",signStatus);
        resultMap.put("reason",reason);
        return resultMap;

    }

    //申请短信模板
    public static HashMap addSmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsTemplateDto.getAccessKeyId(), smsTemplateDto.getAccessKeySecret());
        AddSmsTemplateRequest addSmsTemplateRequest = new AddSmsTemplateRequest()
                .setTemplateType(smsTemplateDto.getTemplateType())
                .setTemplateName(smsTemplateDto.getTemplateName())
                .setTemplateContent(smsTemplateDto.getTemplateContent())
                .setRemark(smsTemplateDto.getRemark());
        // 复制代码运行请自行打印 API 的返回值
        AddSmsTemplateResponse addSmsTemplateResponse= client.addSmsTemplate(addSmsTemplateRequest);
        String code = addSmsTemplateResponse.body.code;
        String message = addSmsTemplateResponse.body.message;
        String requestId = addSmsTemplateResponse.body.requestId;

        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        return resultMap;

    }

    //删除短信模板
    public static HashMap deleteSmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsTemplateDto.getAccessKeyId(), smsTemplateDto.getAccessKeySecret());
        DeleteSmsTemplateRequest deleteSmsTemplateRequest = new DeleteSmsTemplateRequest()
                .setTemplateCode(smsTemplateDto.getTemplateContent());
        // 复制代码运行请自行打印 API 的返回值
        DeleteSmsTemplateResponse deleteSmsTemplateResponse= client.deleteSmsTemplate(deleteSmsTemplateRequest);
        String code = deleteSmsTemplateResponse.body.code;
        String message = deleteSmsTemplateResponse.body.message;
        String requestId = deleteSmsTemplateResponse.body.requestId;
        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        return resultMap;
    }

    //修改未通过审核的短信模板
    public static HashMap modifySmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsTemplateDto.getAccessKeyId(), smsTemplateDto.getAccessKeySecret());
        ModifySmsTemplateRequest modifySmsTemplateRequest = new ModifySmsTemplateRequest()
                .setTemplateType(smsTemplateDto.getTemplateType())
                .setTemplateName(smsTemplateDto.getTemplateName())
                .setTemplateCode(smsTemplateDto.getTemplateCode())
                .setTemplateContent(smsTemplateDto.getTemplateContent())
                .setRemark(smsTemplateDto.getRemark());
        // 复制代码运行请自行打印 API 的返回值
        ModifySmsTemplateResponse modifySmsTemplateResponse= client.modifySmsTemplate(modifySmsTemplateRequest);

        String code = modifySmsTemplateResponse.body.code;
        String message = modifySmsTemplateResponse.body.message;
        String requestId = modifySmsTemplateResponse.body.requestId;
        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("message",message);
        resultMap.put("requestId",requestId);
        return resultMap;
    }
    //查询短信模板的审核状态
    public static HashMap querySmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = AliyunUtils.createClient(smsTemplateDto.getAccessKeyId(), smsTemplateDto.getAccessKeySecret());
        QuerySmsTemplateRequest querySmsTemplateRequest = new QuerySmsTemplateRequest()
                .setTemplateCode(smsTemplateDto.getTemplateCode());
        // 复制代码运行请自行打印 API 的返回值
        QuerySmsTemplateResponse querySmsTemplateResponse= client.querySmsTemplate(querySmsTemplateRequest);
        String code = querySmsTemplateResponse.body.code;
       /*
        0：审核中。
        1：审核通过。
        2：审核失败，请在返回参数Reason中查看审核失败原因。
        */
        Integer signStatus =querySmsTemplateResponse.body.templateStatus;
        //审核失败的原因
        String  reason =querySmsTemplateResponse.body.reason;

        String requestId = querySmsTemplateResponse.body.requestId;
        String message = querySmsTemplateResponse.body.message;
        String createDate = querySmsTemplateResponse.body.createDate;

        HashMap resultMap=new HashMap();
        resultMap.put("code",code);
        resultMap.put("signStatus",signStatus);
        resultMap.put("reason",reason);
        resultMap.put("requestId",requestId);
        resultMap.put("message",message);
        resultMap.put("createDate",createDate);
        return resultMap;


    }
}