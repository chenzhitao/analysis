package com.elai.analytics.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elai.analytics.domain.HeatVO;
import com.elai.analytics.domain.OvervivwResVO;
import com.elai.analytics.domain.OvervivwVO;
import com.elai.analytics.domain.QueryParam;
import com.elai.common.core.controller.BaseController;
import com.elai.common.core.domain.AjaxResult;
import com.elai.common.utils.DateUtils;
import com.elai.common.utils.StringUtils;
import com.elai.common.utils.ThirdProperties;
import com.elai.common.utils.WebUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *基础指标监控
 * @author elai
 * @date 2021-04-04
 */
@RestController
@RequestMapping("/report/base")
public class BasicIndicatorsController extends BaseController
{

    private static final Logger log = LoggerFactory.getLogger(BasicIndicatorsController.class);
    @Autowired
    private ThirdProperties thirdProperties;
    //全站流量
    private static final String allSiteFlowDataApiName ="basicindicators/findOverview";

    //同比
    private  final  static  String ONYEARFLOW="01";
    //环比
    private  final  static  String COMPARISON="02";
    //全站流量
    private  final  static  String TOTALNUM="TOTALNUM";
    //全站日活
    private  final  static  String DAYNUM="DAYNUM";
    //注册用户数
    private  final  static  String LOGINUSERNUM="LOGINUSERNUM";
    //下单人数
    private  final  static  String SUBMITORDERNUM="SUBMITORDERNUM";
    //GMV
    private  final  static  String AMOUNTPAIDLIST="AMOUNTPAIDLIST";
    //订单量
    private  final  static  String ORDERVOLUMENUM="ORDERVOLUMENUM";
    //下单转换
    private  final  static  String PAYORDERNUM="PAYORDERNUM";
    //客单价
    private  final  static  String AVGPRICENUM="AVGPRICENUM";
    //app使用时长
    private  final  static  String AVGEVENTTIMENUM="AVGEVENTTIMENUM";
    //过去7天日活
    private  final  static  String DAILYLIFELIST="DAILYLIFELIST";
    //过去7天新用户访问数
    private  final  static  String NEWUSERLIST="NEWUSERLIST";
    //各端过去7天日活
    private  final  static  String PLATFORMLIST="PLATFORMLIST";
    //过去7天新访问用户占比
    private  final  static  String USERTAGEMLIST="USERTAGEMLIST";
    //Web端平均访问时长
    private  final  static  String WEBAVGDURATIONLIST="WEBAVGDURATIONLIST";
    //全站流量用户数
    private  final  static  String WTOTALNUMLIST="WTOTALNUMLIST";


    //查询单个指标方法
    public   Map<String,Object>  queryData(String taskType,String requestJson){

        log.info("BasicIndicatorsControllerData："+requestJson);
        JSONObject paramJson = JSONObject.parseObject(requestJson);
        JSONObject eventJson = requestJson.isEmpty()?new JSONObject(): paramJson.getJSONObject("requestJson");
        if (JSONUtil.isNull(eventJson)){
            eventJson = new JSONObject();
        }
        Map<String, Object> paramMap =new HashedMap();
        JSONArray dateRangeArray = eventJson.getJSONArray("dateRange");
        JSONArray applicationCodeArray = eventJson.getJSONArray("applicationCode");
        String startDate = null;
        String endDate = null;
        if (JSONUtil.isNull(dateRangeArray)){
            startDate = DateUtils.getYYYYMMDD(-6);
            endDate = DateUtils.getYYYYMMDD(-1);
        }else{
            String tempStartDate = dateRangeArray.getString(0);
            String tempEndDate = dateRangeArray.getString(1);
            startDate = tempStartDate.length()>10?tempStartDate.substring(0,10):tempStartDate;
            endDate = tempEndDate.length()>10?tempEndDate.substring(0,10):tempEndDate;
        }
        paramMap.put("startDate",startDate);
        paramMap.put("endDate",endDate);
        paramMap.put("taskType",taskType);
        paramMap.put("applicationCodeArray",applicationCodeArray);
        paramMap.put("jsonParam",eventJson);
//        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//        JSONArray eventItemsArray = eventJson.getJSONArray("eventItems");
//        if(eventItemsArray!=null&&eventItemsArray.size()>0){
//            for (int i=0;i<=eventItemsArray.size()-1;i++){
//                Map<String,Object> outMap=new HashMap<String,Object>();
//                JSONObject jsMap= JSONObject.parseObject(eventItemsArray.getString(i));
//                outMap.put("eventItemExpression",jsMap.get("eventItemExpression"));
//                outMap.put("value1Str",jsMap.get("value1Str"));
//                JSONObject eventItemArray = JSONObject.parseObject(jsMap.get("eventItem").toString());
//                outMap.put("eventItemCode",eventItemArray.get("eventItemCode"));
//                list.add(outMap);
//            }
//        }
//        paramMap.put("ruleExpression",list);
        JSONObject obj=WebUtils.postCkObj(thirdProperties.getCkUrl(),allSiteFlowDataApiName,paramMap);
        JSONObject objdata=new JSONObject();
        if(obj!=null){
            objdata=obj.getJSONObject("data");
        }
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("beginTime", startDate);
        data.put("endTime", endDate);
        data.put("today", "今天");
        data.put("showDate", "今天");

        //全站流量
        if(TOTALNUM.equals(taskType)){
            data.put("totalNum", objdata.getString("totalNum"));
            data.put("totalNumA", objdata.getString("totalNumA"));
            data.put("totalNumB", objdata.getString("totalNumB"));
        }else if (DAYNUM.equals(taskType)){
            data.put("dayNum", objdata.getString("dayNum"));
            data.put("dayNumA", objdata.getString("dayNumA"));
            data.put("dayNumB", objdata.getString("dayNumB"));
        }else if (LOGINUSERNUM.equals(taskType)){
            data.put("loginUserNum", objdata.getString("loginUserNum"));
            data.put("loginUserNumA", objdata.getString("loginUserNumA"));
            data.put("loginUserNumB", objdata.getString("loginUserNumB"));
        }else if (SUBMITORDERNUM.equals(taskType)){
            data.put("submitOrderNum", objdata.getString("submitOrderNum"));
            data.put("submitOrderNumA", objdata.getString("submitOrderNumA"));
            data.put("submitOrderNumB", objdata.getString("submitOrderNumB"));
        }else if (ORDERVOLUMENUM.equals(taskType)){
            data.put("orderVolumeNum", objdata.getString("orderVolumeNum"));
            data.put("orderVolumeNumA", objdata.getString("orderVolumeNumA"));
            data.put("orderVolumeNumB", objdata.getString("orderVolumeNumB"));
        }else if (PAYORDERNUM.equals(taskType)){
            data.put("payOrderNum", objdata.getString("payOrderNum"));
            data.put("payOrderNumA", objdata.getString("payOrderNumA"));
            data.put("payOrderNumB", objdata.getString("payOrderNumB"));
        }else if (AVGPRICENUM.equals(taskType)){
            data.put("avgPriceNum", objdata.getString("avgPriceNum"));
            data.put("avgPriceNumA", objdata.getString("avgPriceNumA"));
            data.put("avgPriceNumB", objdata.getString("avgPriceNumB"));
        }else if (AVGEVENTTIMENUM.equals(taskType)){
            data.put("avgEventTimeNum", objdata.getString("avgEventTimeNum"));
            data.put("avgEventTimeNumA", objdata.getString("avgEventTimeNumA"));
            data.put("avgEventTimeNumB", objdata.getString("avgEventTimeNumB"));
        }
        return data;
    }

    //全站流量
    @PostMapping("/totalNum")
    public AjaxResult queryTotalNumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(TOTALNUM,requestJson));
    }

    //全站日活
    @PostMapping("/dayNum")
    public AjaxResult queryDayNumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(DAYNUM,requestJson));
    }

    //注册用户数
    @PostMapping("/loginUsernum")
    public AjaxResult queryLoginUsernumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(LOGINUSERNUM,requestJson));
    }
    //下单人数
    @PostMapping("/submitOrderNum")
    public AjaxResult querySubmitOrdernumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(SUBMITORDERNUM,requestJson));
    }
    //订单量
    @PostMapping("/orderVolumeNum")
    public AjaxResult queryOrderVolumenumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(ORDERVOLUMENUM,requestJson));
    }
    //下单转换
    @PostMapping("/payOrderNum")
    public AjaxResult queryPayOrdernumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(PAYORDERNUM,requestJson));

    }
    //客单价
    @PostMapping("/avgPriceNum")
    public AjaxResult queryAvgPricenumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(AVGPRICENUM,requestJson));
    }
    //app使用时长
    @PostMapping("/avgEventTimeNum")
    public AjaxResult queryAvgEventTimenumData(@RequestBody String requestJson){
        return AjaxResult.success(queryData(AVGEVENTTIMENUM,requestJson));
    }
    //过去7天日活
    @PostMapping("/dailyLifelist")
    public AjaxResult queryDailyLifelistData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-7,DAILYLIFELIST,requestJson));
    }
    //过去7天新用户访问数
    @PostMapping("/newUserlist")
    public AjaxResult queryNnewUserlistData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-7,NEWUSERLIST,requestJson));
    }
    //各端过去7天日活
    @PostMapping("/platFormlist")
    public AjaxResult queryPlatFormlistData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-7,PLATFORMLIST,requestJson));
    }
    //过去7天新访问用户占比
    @PostMapping("/userTagemList")
    public AjaxResult queryUserTagemListData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-7,USERTAGEMLIST,requestJson));
    }

    //Web端平均访问时长
    @PostMapping("/webAvgDurationList")
    public AjaxResult queryWebAvgDurationListData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-7,WEBAVGDURATIONLIST,requestJson));
    }
    //全站流量用户数
    @PostMapping("/wtotalNumList")
    public AjaxResult queryWtotalNumListData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-7,WTOTALNUMLIST,requestJson));
    }

    //GMV，过去7天
    @PostMapping("/queryGMVSevenData")
    public AjaxResult queryGMVSevenData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-7,AMOUNTPAIDLIST,requestJson));
    }
    //GMV，过去30天
    @PostMapping("/queryGMVThirtyData")
    public AjaxResult queryGMVThirtyData(@RequestBody String requestJson){
        return AjaxResult.success(resultGMVMap(-30,AMOUNTPAIDLIST,requestJson));
    }

    @PostMapping("/keepList")
    public AjaxResult queryKeepData(@RequestBody String requestJson){
        log.info("Basic queryKeepData："+requestJson);
        JSONObject paramJson = JSONObject.parseObject(requestJson);
        JSONObject eventJson = requestJson.isEmpty()?new JSONObject(): paramJson.getJSONObject("requestJson");
        if (JSONUtil.isNull(eventJson)){
            eventJson = new JSONObject();
        }
        Map<String, Object> paramMap =new HashedMap();
        JSONArray dateRangeArray = eventJson.getJSONArray("dateRange");
        JSONArray applicationCodeArray = eventJson.getJSONArray("applicationCode");
        String startDate = null;
        String endDate = null;
        if (JSONUtil.isNull(dateRangeArray)){
            startDate = DateUtils.getYYYYMMDD(-6);
            endDate = DateUtils.getYYYYMMDD(-1);
        }else{
            String tempStartDate = dateRangeArray.getString(0);
            String tempEndDate = dateRangeArray.getString(1);
            startDate = tempStartDate.length()>10?tempStartDate.substring(0,10):tempStartDate;
            endDate = tempEndDate.length()>10?tempEndDate.substring(0,10):tempEndDate;
        }
        paramMap.put("startDate",startDate);
        paramMap.put("endDate",endDate);
        paramMap.put("jsonParam",eventJson);

        paramMap.put("initEvent","MPViewScreen"); //小程序页面浏览
        paramMap.put("followUpEvent","SubmitOrder"); //提交订单

        paramMap.put("keepDay",7);
        paramMap.put("jsonParam",eventJson);
        if (eventJson.containsKey("applicationCode")){
            JSONArray applicationCodeList = eventJson.getJSONArray("applicationCode");
            if (applicationCodeList != null && !applicationCodeList.isEmpty()){
                paramMap.put("applicationCodeList",applicationCodeList.toJavaList(String.class));
            }
        }


        return AjaxResult.success(resultGMVMap(-30,AMOUNTPAIDLIST,requestJson));
    }

  /*  //热力分析
    @PostMapping("/heatListView")
    public AjaxResult heatListView(@RequestBody String requestJson)
    {
        List<Integer> data=new ArrayList<>();
        Map<String, Object> paramMap =new HashedMap();
        //开始日期
        String startDate="2021-06-01";
        //结束日期
        String endDate=DateUtils.getYYYYMMDD(0);
        //事件属性
        List<Object> list=new ArrayList<>();
        paramMap.put("startDate",startDate);
        paramMap.put("endDate",endDate);
        paramMap.put("ruleExpression",list);
        //全站流量
        JSONObject obj= WebUtils.postCkObj(thirdProperties.getCkUrl(),allSiteFlowDataApiName,paramMap);
        String code = obj.getString("code");
        JSONObject objdata=JSONObject.parseObject(obj.getString("data"));
        List<HeatVO> heatList=JSON.parseArray(objdata.getString("heatList"), HeatVO.class);
        return AjaxResult.success(data);
    }*/


    public  Map<String,Object> resultGMVMap(int w,String taskType,String requestJson){
        log.info("BasicIndicatorsControllerData："+requestJson);
        JSONObject paramJson = JSONObject.parseObject(requestJson);
        JSONObject eventJson = requestJson.isEmpty()?new JSONObject(): paramJson.getJSONObject("requestJson");
        if (JSONUtil.isNull(eventJson)){
            eventJson = new JSONObject();
        }
        Map<String, Object> paramMap =new HashedMap();
        JSONArray dateRangeArray = eventJson.getJSONArray("dateRange");
        JSONArray applicationCodeArray = eventJson.getJSONArray("applicationCode");
        String startDate = null;
        String endDate = null;
        if (JSONUtil.isNull(dateRangeArray)){
            startDate = DateUtils.getYYYYMMDD(-6);
            endDate = DateUtils.getYYYYMMDD(-1);
        }else{
            String tempStartDate = dateRangeArray.getString(0);
            String tempEndDate = dateRangeArray.getString(1);
            startDate = tempStartDate.length()>10?tempStartDate.substring(0,10):tempStartDate;
            endDate = tempEndDate.length()>10?tempEndDate.substring(0,10):tempEndDate;
        }
        paramMap.put("startDate",startDate);
        paramMap.put("endDate",endDate);
        paramMap.put("taskType",taskType);
        paramMap.put("applicationCodeArray",applicationCodeArray);
        paramMap.put("beforeDay",w);
        paramMap.put("jsonParam",eventJson);
//        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//        JSONArray eventItemsArray = eventJson.getJSONArray("eventItems");
//        if(eventItemsArray!=null&&eventItemsArray.size()>0){
//            for (int i=0;i<=eventItemsArray.size()-1;i++){
//                Map<String,Object> outMap=new HashMap<String,Object>();
//                JSONObject jsMap= JSONObject.parseObject(eventItemsArray.getString(i));
//                outMap.put("eventItemExpression",jsMap.get("eventItemExpression"));
//                outMap.put("value1Str",jsMap.get("value1Str"));
//                JSONObject eventItemArray = JSONObject.parseObject(jsMap.get("eventItem").toString());
//                outMap.put("eventItemCode",eventItemArray.get("eventItemCode"));
//                list.add(outMap);
//            }
//        }
//        paramMap.put("ruleExpression",list);

        JSONObject obj=WebUtils.postCkObj(thirdProperties.getCkUrl(),allSiteFlowDataApiName,paramMap);
        JSONObject objdata=new JSONObject();
        if(obj!=null){
            objdata=obj.getJSONObject("data");
        }
        Map<String,Object> resultMap=new HashMap();
        List<OvervivwVO> voList=null;
        OvervivwResVO resVO = null;
        String eventName="";
        if(DAILYLIFELIST.equals(taskType)){
            resVO = JSON.parseObject(objdata.getString("dailyLife"),OvervivwResVO.class);
//            voList=JSON.parseArray(objdata.getString("dailyLifeList"), OvervivwVO.class);
            voList = resVO.getOvervivwList();
            eventName="过去7天日活";
        }else if(NEWUSERLIST.equals(taskType)){
            resVO = JSON.parseObject(objdata.getString("newUser"),OvervivwResVO.class);
//            voList=JSON.parseArray(objdata.getString("newUserList"), OvervivwVO.class);
            voList = resVO.getOvervivwList();
            eventName="过去7天新用户访问数";
        }else if(PLATFORMLIST.equals(taskType)){
            resVO = JSON.parseObject(objdata.getString("platform"),OvervivwResVO.class);
            voList = resVO.getOvervivwList();
//            voList=JSON.parseArray(objdata.getString("platformList"), OvervivwVO.class);
            eventName="各端过去7天日活";
        }else if(USERTAGEMLIST.equals(taskType)){
            resVO = JSON.parseObject(objdata.getString("userTagem"),OvervivwResVO.class);
            voList = resVO.getOvervivwList();
//            voList=JSON.parseArray(objdata.getString("userTagemList"), OvervivwVO.class);
            eventName="过去7天新访问用户占比";
        }else if(WEBAVGDURATIONLIST.equals(taskType)){
            resVO = JSON.parseObject(objdata.getString("webAvgDuration"),OvervivwResVO.class);
            voList = resVO.getOvervivwList();
//            voList=JSON.parseArray(objdata.getString("webAvgDurationList"), OvervivwVO.class);
            eventName="Web端平均访问时长";
        }else if(WTOTALNUMLIST.equals(taskType)){
            resVO = JSON.parseObject(objdata.getString("wtotalNum"),OvervivwResVO.class);
            voList = resVO.getOvervivwList();
//            voList=JSON.parseArray(objdata.getString("wtotalNumList"), OvervivwVO.class);
            eventName="全站流量用户数";
        }
        else {
            resVO = JSON.parseObject(objdata.getString("amountPaid"),OvervivwResVO.class);
//            voList=JSON.parseArray(objdata.getString("amountPaidList"), OvervivwVO.class);
            voList=resVO.getOvervivwList();
            eventName="GMV";
        }

        List title=new ArrayList();
        List<Double> chartData=new ArrayList<>();

        BigDecimal eventCount=resVO.getEventAmount();
        String onyearFlow=resVO.getOnyearFlow();
        String comparison=resVO.getComparison();
        if(voList!=null&&voList.size()>0){
            if(!PLATFORMLIST.equals(taskType)){
                for (OvervivwVO overvivwVO:voList){
                    title.add(overvivwVO.getEventDate().substring(5,10));
//                    if(overvivwVO.getEventAmount() != null){
//                        chartData.add(overvivwVO.getEventAmount().setScale(2,BigDecimal.ROUND_HALF_UP));
//                    }else{
//                        chartData.add(BigDecimal.ZERO);
//                    }
                    chartData.add(overvivwVO.getEventAmount().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                }
            }

        }

        Map<String,Object> dataMap1=new HashMap();
        dataMap1.put("name",eventName);
        dataMap1.put("type","line");
        dataMap1.put("smooth",false);
        dataMap1.put("data",chartData);

//        DoubleSummaryStatistics statistics = chartData.stream().mapToDouble(Number::doubleValue).summaryStatistics();
        Map summaryData=new HashMap();
        BigDecimal onew = new BigDecimal(10000);

        summaryData.put("gmvStartDate",startDate);
        if (startDate.equals(endDate)){
            summaryData.put("gmvStartDate",DateUtils.getOneDayYYYYMMDD(startDate,w));
        }
        summaryData.put("gmvEndDate",endDate);
        summaryData.put("gmvWeek",StringUtils.dateToWeek(endDate));

        summaryData.put("gmvData",eventCount.setScale(2,BigDecimal.ROUND_HALF_UP));
        if (AMOUNTPAIDLIST.equals(taskType)){
            summaryData.put("gmvData",eventCount.divide(onew).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        if(USERTAGEMLIST.equals(taskType) || PLATFORMLIST.equals(taskType)){
            summaryData.put("gmvDataA",onyearFlow);
            summaryData.put("gmvDataB",comparison);
        }else{
            if (AMOUNTPAIDLIST.equals(taskType)){
                String gmvDataA = StringUtils.bigDecimalFormat(eventCount,new BigDecimal(onyearFlow));
                String gmvDataB = StringUtils.bigDecimalFormat(eventCount,new BigDecimal(comparison));
                summaryData.put("gmvDataA",new BigDecimal(gmvDataA).divide(onew).setScale(2,BigDecimal.ROUND_HALF_UP));
                summaryData.put("gmvDataB",new BigDecimal(gmvDataB).divide(onew).setScale(2,BigDecimal.ROUND_HALF_UP));
            }else{
                summaryData.put("gmvDataA",StringUtils.bigDecimalFormat(eventCount,new BigDecimal(onyearFlow)));
                summaryData.put("gmvDataB",StringUtils.bigDecimalFormat(eventCount,new BigDecimal(comparison)));
            }

        }

        summaryData.put("gmvTotal",resVO.getTotalNum().setScale(2, BigDecimal.ROUND_HALF_UP));
        summaryData.put("gmvAvg", resVO.getAvgNum().setScale(2, BigDecimal.ROUND_HALF_UP));
        if (AMOUNTPAIDLIST.equals(taskType)){
            summaryData.put("gmvTotal",resVO.getTotalNum().divide(onew).setScale(2, BigDecimal.ROUND_HALF_UP));
            summaryData.put("gmvAvg", resVO.getAvgNum().divide(onew).setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        resultMap.put("gmvSevenData",summaryData);
        resultMap.put("chartData",dataMap1);
        resultMap.put("chartTitle",title);
        if(PLATFORMLIST.equals(taskType)){
            List<String> platforms=  new ArrayList<>();
            Map<String, List<OvervivwVO>> platformMap = voList.stream().collect(
                    Collectors.groupingBy(a -> a.getPlatformType()));

            List<String> dateList=  voList.stream().map(OvervivwVO::getEventDate).collect(Collectors.toList())
                    .stream().distinct().collect(Collectors.toList());
            List<String> titleList = new ArrayList<>();
            dateList.forEach(s -> {
                titleList.add(s.substring(5,10));
            });


            List<Map<String,Object>> dataList = Lists.newArrayList();
            for (Map.Entry<String, List<OvervivwVO>> m : platformMap.entrySet()) {
                String key = m.getKey();
                platforms.add(key);
                List<OvervivwVO> overvivwVOList = m.getValue();
                Map<String,Object> data = new HashMap<>();
                data.put("stack","总量");
                data.put("type","line");
                data.put("smooth",false);
                data.put("name",key);
                data.put("data",overvivwVOList.stream().map(OvervivwVO::getEventAmount).collect(Collectors.toList()));
                dataList.add(data);
            }
            resultMap.put("chartLegend",platforms);
            resultMap.put("chartTitle",titleList);
            resultMap.put("chartData",dataList);
        }

        return resultMap;
    }

    @PostMapping("/allFlowData")
    public AjaxResult allFlow(@RequestBody String requestJson)
    {
        logger.info(requestJson);
        JSONObject jsonObj=JSONObject.parseObject(requestJson);
//        JSONObject object=jsonObj.getJSONObject("requestJson");
//        QueryParam queryParam=JSONObject.parseObject(object.toJSONString(),QueryParam.class);
        List<Integer> data=new ArrayList<>();
        data.add(180);
        data.add(120);
        data.add(161);
        data.add(134);
        data.add(145);
        data.add(160);
        data.add(165);
        return AjaxResult.success(data);
    }



}
