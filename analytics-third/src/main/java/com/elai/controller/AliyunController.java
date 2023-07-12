package com.elai.controller;

import com.elai.common.core.controller.BaseController;
import com.elai.common.core.domain.AjaxResult;
import com.elai.third.aliyun.sample.AliyunUtils;
import com.elai.third.aliyun.sample.dto.SendSmsDto;
import com.elai.third.aliyun.sample.dto.SmsSignDto;
import com.elai.third.aliyun.sample.dto.SmsTemplateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/*阿里云发短信*/
@RestController
@RequestMapping("/push/aliyunmsg")
public class AliyunController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(AliyunController.class);

    //发送短信
    @GetMapping("sendSms")
    public AjaxResult sendSms(SendSmsDto sendSmsDto) throws Exception {
        return AjaxResult.success(AliyunUtils.sendSms(sendSmsDto));
    }

    //批量发送短信
    @GetMapping("sendBatchSms")
    public AjaxResult sendBatchSms(SendSmsDto sendSmsDto) throws Exception {
        return AjaxResult.success(AliyunUtils.sendBatchSms(sendSmsDto));
    }


    //查看短信发送记录和发送状态
    @GetMapping("querySendDetails")
    public AjaxResult querySendDetails(SendSmsDto sendSmsDto) throws Exception {
        return AjaxResult.success(AliyunUtils.querySendDetails(sendSmsDto));
    }

    //申请短信签名
    @GetMapping("addSmsSign")
    public AjaxResult addSmsSign(SmsSignDto smsSignDto) throws Exception {
        return AjaxResult.success(AliyunUtils.addSmsSign(smsSignDto));
    }

    //删除短信签名
    @GetMapping("deleteSmsSign")
    public AjaxResult deleteSmsSign(SmsSignDto smsSignDto) throws Exception {
        return AjaxResult.success(AliyunUtils.deleteSmsSign(smsSignDto));
    }

    //修改短信签名
    @GetMapping("modifySmsSign")
    public AjaxResult modifySmsSign(SmsSignDto smsSignDto) throws Exception {
        return AjaxResult.success(AliyunUtils.modifySmsSign(smsSignDto));
    }

    //查询短信签名申请状态
    @GetMapping("querySmsSign")
    public AjaxResult querySmsSign(SmsSignDto smsSignDto) throws Exception {
        return AjaxResult.success(AliyunUtils.querySmsSign(smsSignDto));
    }

    //申请短信模板
    @GetMapping("addSmsTemplate")
    public AjaxResult addSmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        return AjaxResult.success(AliyunUtils.addSmsTemplate(smsTemplateDto));
    }

    //删除短信模板
    @GetMapping("deleteSmsTemplate")
    public AjaxResult deleteSmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        return AjaxResult.success(AliyunUtils.deleteSmsTemplate(smsTemplateDto));
    }

    //修改未通过审核的短信模板
    @GetMapping("modifySmsTemplate")
    public AjaxResult modifySmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        return AjaxResult.success(AliyunUtils.modifySmsTemplate(smsTemplateDto));
    }

    //查询短信模板的审核状态
    @GetMapping("querySmsTemplate")
    public AjaxResult querySmsTemplate(SmsTemplateDto smsTemplateDto) throws Exception {
        return AjaxResult.success(AliyunUtils.querySmsTemplate(smsTemplateDto));
    }

}
