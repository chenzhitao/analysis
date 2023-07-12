package com.elai.common.constant;

public interface ThirdInterface {

    /************分群**********************/
    String create_users = "user/group/createUsers";  //创建分群
    String find_count = "user/group/findCount";  //人数测算
    String find_users = "user/group/findUsers";  //根据分群（table）id查询人员列表，分页
    /************标签**********************/
    String create_tags_users = "user/tags/createUsers";  //创建标签
    String find_tags_count = "user/tags/findCount";  //人数测算（标签）
    String find_tags_users = "user/tags/findUsers";  //根据标签（table）id查询人员列表，分页
    /************画像**********************/
    String create_portrait_users = "user/portrait/createUsers";  //创建画像
    String find_portrait_count = "user/portrait/findCount";  //人数测算（portrait）
    String find_portrait_users = "user/portrait/findUsers";  //根据标签（table）id查询人员列表，分页
    String find_predict_users = "user/portrait/findPredictUsers";  //画像展示 未保存前使用
    /************用户详情**********************/
    String find_user_info = "user/info";
    String find_user_tags = "user/tags/findTagsByUser";
    String find_user_track = "user/track";  //用户轨迹
    String find_user_track_group = "user/track/group";  //用户轨迹时间分组
    String find_user_event_item = "user/event/item";  //用户事件属性

    /************定时任务*************************/
    String createJobInfo = "api/jobinfo/save";
    String groupXxJobTask = "base/group/userGroupXxjobTask";  //执行用户分群的定时任务
    String tagsXxJobTask = "tag/info/userTagsXxjobTask";  //执行用户标签的执行任务

}
