package com.elai.controller;

import com.getui.push.v2.sdk.api.AuthApi;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.api.StatisticApi;
import com.getui.push.v2.sdk.api.UserApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.*;
import com.getui.push.v2.sdk.dto.req.message.PushBatchDTO;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.res.*;
import com.getui.push.v2.sdk.dto.res.statistic.StatisticDTO;
import com.getui.push.v2.sdk.dto.res.statistic.UserStatisticDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*getui消息发送*/
@RestController
@RequestMapping("/push/getuinmsg")
public class GetuiController {
//        implements UserApi {
//
//    @Autowired
//    private AuthApi authApi;
//    @Autowired
//    private PushApi pushApi;
//    @Autowired
//    private StatisticApi statisticApi;
//    @Autowired
//    private UserApi sserApi;
//
//    //获取鉴权token接口
//    @GetMapping("auth")
//    public ApiResult<TokenDTO> auth(AuthDTO authDTO) {
//        return authApi.auth(authDTO);
//    }
//
//    //关闭鉴权token
//    @GetMapping("close")
//    public ApiResult<Void> close(String token) {
//        return authApi.close(token);
//    }
//
//    //根据cid推送
//    @GetMapping("pushToSingleByCid")
//    public ApiResult<Map<String, Map<String, String>>> pushToSingleByCid(PushDTO<Audience> pushDTO) {
//        return pushApi.pushToSingleByCid(pushDTO);
//    }
//
//    //根据别名推送
//    @GetMapping("pushToSingleByAlias")
//    public ApiResult<Map<String, Map<String, String>>> pushToSingleByAlias(PushDTO<Audience> pushDTO) {
//        return pushApi.pushToSingleByAlias(pushDTO);
//    }
//
//    //根据cid批量推送
//    @GetMapping("pushBatchByCid")
//    public ApiResult<Map<String, Map<String, String>>> pushBatchByCid(PushBatchDTO pushBatchDTO) {
//        return pushApi.pushBatchByCid(pushBatchDTO);
//    }
//
//    //根据别名批量推送
//    @GetMapping("pushBatchByAlias")
//    public ApiResult<Map<String, Map<String, String>>> pushBatchByAlias(PushBatchDTO pushBatchDTO) {
//        return pushApi.pushBatchByAlias(pushBatchDTO);
//    }
//
//    //创建消息体
//    @GetMapping("pushBatchByAlias")
//    public ApiResult<TaskIdDTO> createMsg(PushDTO pushDTO) {
//        return pushApi.createMsg(pushDTO);
//    }
//
//    //根据cid批量推送
//    @GetMapping("pushListByCid")
//    public ApiResult<Map<String, Map<String, String>>> pushListByCid(AudienceDTO audienceDTO) {
//        return pushApi.pushListByCid(audienceDTO);
//    }
//
//    //根据别名批量推送
//    @GetMapping("pushListByAlias")
//    public ApiResult<Map<String, Map<String, String>>> pushListByAlias(AudienceDTO audienceDTO) {
//        return pushApi.pushListByAlias(audienceDTO);
//    }
//
//    //执行群推
//    @GetMapping("pushAll")
//    public ApiResult<TaskIdDTO> pushAll(PushDTO<String> pushDTO) {
//        return pushApi.pushAll(pushDTO);
//    }
//
//    //根据条件筛选用户推送
//    @GetMapping("pushByTag")
//    public ApiResult<TaskIdDTO> pushByTag(PushDTO<Audience> pushDTO) {
//        return pushApi.pushByTag(pushDTO);
//    }
//
//    //使用标签快速推送
//    @GetMapping("pushByFastCustomTag")
//    public ApiResult<TaskIdDTO> pushByFastCustomTag(PushDTO<Audience> pushDTO) {
//        return pushApi.pushByFastCustomTag(pushDTO);
//    }
//
//    //停止任务
//    @GetMapping("stopPush")
//    public ApiResult<Void> stopPush(String taskId) {
//        return pushApi.stopPush(taskId);
//    }
//
//    //查询任务
//    @GetMapping("queryScheduleTask")
//    public ApiResult<Map<String, ScheduleTaskDTO>> queryScheduleTask(String taskId) {
//        return pushApi.queryScheduleTask(taskId);
//    }
//
//    //删除定时任务
//    @GetMapping("deleteScheduleTask")
//    public ApiResult<Void> deleteScheduleTask(String taskId) {
//        return pushApi.deleteScheduleTask(taskId);
//    }
//
//    //获取推送结果
//    @GetMapping("queryPushResultByTaskIds")
//    public ApiResult<Map<String, Map<String, StatisticDTO>>> queryPushResultByTaskIds(Set<String> taskIds) {
//        return statisticApi.queryPushResultByTaskIds(taskIds);
//    }
//
//    //获取推送结果
//    @GetMapping("queryPushResultByTaskIdsAndActionIds")
//    public ApiResult<Map<String, Map<String, StatisticDTO>>> queryPushResultByTaskIdsAndActionIds(Set<String> taskIds, Set<String> actionIds) {
//        return statisticApi.queryPushResultByTaskIdsAndActionIds(taskIds,actionIds);
//    }
//
//    //根据组名查询报表
//    @GetMapping("queryPushResultByGroupName")
//    public ApiResult<Map<String, Map<String, StatisticDTO>>> queryPushResultByGroupName(String groupName) {
//        return statisticApi.queryPushResultByGroupName(groupName);
//    }
//
//    //获取单日推送数据
//    @GetMapping("queryPushResultByDate")
//    public ApiResult<Map<String, Map<String, StatisticDTO>>> queryPushResultByDate(String date) {
//        return statisticApi.queryPushResultByDate(date);
//    }
//
//    // 获取单日用户数据
//    @GetMapping("queryUserDataByDate")
//    public ApiResult<Map<String, Map<String, UserStatisticDTO>>> queryUserDataByDate(String date) {
//        return statisticApi.queryUserDataByDate(date);
//    }
//
//    //获取24小时在线用户数
//    @GetMapping("queryOnlineUserData")
//    public ApiResult<Map<String, Map<String, Integer>>> queryOnlineUserData() {
//        return statisticApi.queryOnlineUserData();
//    }
//
//    //绑定别名
//    @GetMapping("bindAlias")
//    public ApiResult<Void> bindAlias(CidAliasListDTO cidAliasListDTO) {
//        return sserApi.bindAlias(cidAliasListDTO);
//    }
//
//    //根据cid查询别名
//    @GetMapping("queryAliasByCid")
//    public ApiResult<AliasResDTO> queryAliasByCid(Set<String> cidList) {
//        return sserApi.queryAliasByCid(cidList);
//    }
//
//    //根据别名查询cid
//    @GetMapping("queryCidByAlias")
//    public ApiResult<QueryCidResDTO> queryCidByAlias(String alias) {
//        return sserApi.queryCidByAlias(alias);
//    }
//
//    //批量解绑别名
//    @GetMapping("batchUnbindAlias")
//    public ApiResult<Void> batchUnbindAlias(CidAliasListDTO cidAliasListDTO) {
//        return sserApi.batchUnbindAlias(cidAliasListDTO);
//    }
//
//    //解绑所有别名
//    @GetMapping("unbindAllAlias")
//    public ApiResult<Void> unbindAllAlias(String alias) {
//        return sserApi.unbindAllAlias(alias);
//    }
//
//    // 一个用户绑定一批标签
//    @GetMapping("userBindTags")
//    public ApiResult<Void> userBindTags(String cid, TagDTO tagDTO) {
//        return sserApi.userBindTags(cid,tagDTO);
//    }
//
//    // 一个用户绑定一批标签
//    @GetMapping("usersBindTag")
//    public ApiResult<Map<String, String>> usersBindTag(String customerTag, UserDTO userDTO) {
//        return sserApi.usersBindTag(customerTag,userDTO);
//    }
//
//    // 删除标签
//    @GetMapping("deleteUsersTag")
//    public ApiResult<Map<String, String>> deleteUsersTag(String customerTag, UserDTO userDTO) {
//        return sserApi.deleteUsersTag(customerTag,userDTO);
//    }
//
//    // 查询用户标签
//    @GetMapping("queryUserTags")
//    public ApiResult<Map<String, List<String>>> queryUserTags(String cid) {
//        return sserApi.queryUserTags(cid);
//    }
//
//    //添加黑名单用户
//    @GetMapping("addBlackUser")
//    public ApiResult<Void> addBlackUser(Set<String> cidSet) {
//        return sserApi.addBlackUser(cidSet);
//    }
//
//    //移除黑名单用户
//    @GetMapping("removeBlackUser")
//    public ApiResult<Void> removeBlackUser(Set<String> cidSet) {
//        return sserApi.removeBlackUser(cidSet);
//    }
//
//    //查询用户状态
//    @GetMapping("queryUserStatus")
//    public ApiResult<Map<String, CidStatusDTO>> queryUserStatus(Set<String> cidSet) {
//        return sserApi.queryUserStatus(cidSet);
//    }
//
//    //设置角标
//    @GetMapping("setBadge")
//    public ApiResult<Void> setBadge(Set<String> cidSet, BadgeDTO badgeDTO) {
//        return sserApi.setBadge(cidSet,badgeDTO);
//    }
//
//    //询符合条件的用户总量
//    @GetMapping("queryUser")
//    public ApiResult<Map<String, Integer>> queryUser(ConditionListDTO conditionListDTO) {
//        return sserApi.queryUser(conditionListDTO);
//    }
}
