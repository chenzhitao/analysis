package com.elai.controller;

import com.elai.common.core.controller.BaseController;
import com.elai.common.core.domain.AjaxResult;
import com.elai.third.tencent.sample.TencentYunUtils;
import com.elai.third.tencent.sample.dto.PullSmsDto;
import com.elai.third.tencent.sample.dto.SmsPackagesStatisticsDto;
import com.elai.third.tencent.sample.dto.SmsSignDto;
import com.elai.third.tencent.sample.dto.SmsTemplateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/*腾讯云发短信*/
@RestController
@RequestMapping("/push/touchModenmsg")
public class TouchModeController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(TouchModeController.class);

    //套餐包信息统计
    @GetMapping("smsPackagesStatistics")
    public AjaxResult smsPackagesStatistics(SmsPackagesStatisticsDto smsPackagesStatisticsDto) {
        return AjaxResult.success(TencentYunUtils.smsPackagesStatistics(smsPackagesStatisticsDto));
    }
//
//    //发送短信数据统计
//    @GetMapping("sendStatusStatistics")
//    public HashMap sendStatusStatistics(SmsPackagesStatisticsDto smsPackagesStatisticsDto) {
//        return AjaxResult.success(TencentYunUtils.sendStatusStatistics(smsPackagesStatisticsDto));
//    }
//
//
//    //回执数据统计
//    @GetMapping("callbackStatusStatistics")
//    public HashMap callbackStatusStatistics(SmsPackagesStatisticsDto smsPackagesStatisticsDto) {
//        return AjaxResult.success(TencentYunUtils.callbackStatusStatistics(smsPackagesStatisticsDto));
//    }
//
//    //发送短信
//    @GetMapping("sendSms")
//    public HashMap sendSms(com.elai.third.tencent.sample.dto.SendSmsDto sendSmsDto) {
//        return AjaxResult.success(TencentYunUtils.sendSms(sendSmsDto));
//    }
//
//
//    //拉取单个号码短信下发状态
//    @GetMapping("pullSmsSendStatusByPhoneNumber")
//    public HashMap pullSmsSendStatusByPhoneNumber(PullSmsDto pullSmsDto) {
//        return AjaxResult.success(TencentYunUtils.pullSmsSendStatusByPhoneNumber(pullSmsDto));
//    }
//
//    //拉取单个号码短信下发状态
//    @GetMapping("pullSmsSendStatus")
//    public HashMap pullSmsSendStatus(PullSmsDto pullSmsDto) {
//        return AjaxResult.success(TencentYunUtils.pullSmsSendStatus(pullSmsDto));
//    }
//
//    //拉取单个号码短信回复状态
//    @GetMapping("pullSmsReplyStatusByPhoneNumber")
//    public HashMap pullSmsReplyStatusByPhoneNumber(PullSmsDto pullSmsDto) {
//        return AjaxResult.success(TencentYunUtils.pullSmsReplyStatusByPhoneNumber(pullSmsDto));
//    }
//
//    //拉取短信回复状态
//    @GetMapping("pullSmsReplyStatus")
//    public HashMap pullSmsReplyStatus(PullSmsDto pullSmsDto) {
//        return AjaxResult.success(TencentYunUtils.pullSmsReplyStatus(pullSmsDto));
//    }
//
//    //修改短信模板
//    @GetMapping("modifySmsTemplate")
//    public HashMap modifySmsTemplate(com.elai.third.tencent.sample.dto.SmsTemplateDto smsTemplateDto) {
//        return AjaxResult.success(TencentYunUtils.modifySmsTemplate(smsTemplateDto));
//    }
//
//    //修改短信模板
//    @GetMapping("describeSmsTemplateList")
//    public HashMap describeSmsTemplateList(com.elai.third.tencent.sample.dto.SmsTemplateDto smsTemplateDto) {
//        return AjaxResult.success(TencentYunUtils.describeSmsTemplateList(smsTemplateDto));
//    }
//
//    //删除短信模板
//    @GetMapping("deleteSmsTemplate")
//    public HashMap deleteSmsTemplate(com.elai.third.tencent.sample.dto.SmsTemplateDto smsTemplateDto) {
//        return AjaxResult.success(TencentYunUtils.deleteSmsTemplate(smsTemplateDto));
//    }
//
//
//    //添加短信模板
//    @GetMapping("addSmsTemplate")
//    public HashMap addSmsTemplate(SmsTemplateDto smsTemplateDto) {
//        return AjaxResult.success(TencentYunUtils.addSmsTemplate(smsTemplateDto));
//    }
//
//    //修改短信签名
//    @GetMapping("modifySmsSign")
//    public HashMap modifySmsSign(com.elai.third.tencent.sample.dto.SmsSignDto smsSignDto) {
//        return AjaxResult.success(TencentYunUtils.modifySmsSign(smsSignDto));
//    }
//
//    //短信签名状态查询
//    @GetMapping("modifySmsSign")
//    public HashMap describeSmsSignList(com.elai.third.tencent.sample.dto.SmsSignDto smsSignDto) {
//        return AjaxResult.success(TencentYunUtils.describeSmsSignList(smsSignDto));
//    }
//
//
//    //删除短信签名
//    @GetMapping("deleteSmsSign")
//    public HashMap deleteSmsSign(com.elai.third.tencent.sample.dto.SmsSignDto smsSignDto) {
//        return AjaxResult.success(TencentYunUtils.deleteSmsSign(smsSignDto));
//    }
//
//    //添加短信签名
//    @GetMapping("addSmsSign")
//    public HashMap addSmsSign(SmsSignDto smsSignDto) {
//        return AjaxResult.success(TencentYunUtils.addSmsSign(smsSignDto));
//    }

}
