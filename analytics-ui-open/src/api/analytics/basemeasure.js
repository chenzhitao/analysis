import request from '@/utils/request'

// 查询触达通道列表
export function getAllSiteFlowList(data) {
  return request({
    url: '/report/base/allFlowData',
    method: 'post',
    data: data
  })
}

// export function getSiteFlowData(query) {
//   return request({
//     url: '/report/base/allSiteFlowData',
//     method: 'get',
//     params: query
//   })
// }

export function getChartDataOptions(data) {
  return request({
    url: '/event/base/eventAnalyzeCharData',
    method: 'post',
    data: data
  })
}


export function getFunnelChartDataFun(data) {
  return request({
    url: '/config/funnel/eventAnalyzeFunnel',
    method: 'post',
    data: data
  })
}


export function getKeepDataFun(data) {
  return request({
    url: '/report/keep/keepAnalyQuery',
    method: 'post',
    data: data
  })
}

export function getSpreadDataFun(data) {
  return request({
    url: '/report/spread/spreadAnalyQuery',
    method: 'post',
    data: data
  })
}

export function getActionPathDataFun(data) {
  return request({
    url: '/report/path/pathAnalyQuery',
    method: 'post',
    data: data
  })
}

export function getAscribeDataFun(data) {
  return request({
    url: '/report/ascribe/ascribeAnalyQuery',
    method: 'post',
    data: data
  })
}

//GMV7天
export function getGMVSevenData(query) {
  return request({
    url: '/report/base/queryGMVSevenData',
    method: 'post',
    data: query
  })
}
//GMV30天
export function getGMVThirtyData(query) {
  return request({
    url: '/report/base/queryGMVThirtyData',
    method: 'post',
    data: query
  })
}

//全站流量
export function getTotalNumData(query) {
  return request({
    url: '/report/base/totalNum',
    method: 'post',
    data: query
  })
}
//全站日活
export function getDayNumData(query) {
  return request({
    url: '/report/base/dayNum',
    method: 'post',
    data: query
  })
}
//注册用户数
export function getLoginUsernumData(query) {
  return request({
    url: '/report/base/loginUsernum',
    method: 'post',
    data: query
  })
}

//下单人数
export function getSubmitOrderNumData(query) {
  return request({
    url: '/report/base/submitOrderNum',
    method: 'post',
    data: query
  })
}
//订单量
export function getOrderVolumeNumData(query) {
  return request({
    url: '/report/base/orderVolumeNum',
    method: 'post',
    data: query
  })
}
//下单转换
export function getPayOrderNumData(query) {
  return request({
    url: '/report/base/payOrderNum',
    method: 'post',
    data: query
  })
}
//客单价
export function getAvgPriceNumData(query) {
  return request({
    url: '/report/base/avgPriceNum',
    method: 'post',
    data: query
  })
}
//app使用时长
export function getAvgEventTimeNumData(query) {
  return request({
    url: '/report/base/avgEventTimeNum',
    method: 'post',
    data: query
  })
}

//过去7天日活
export function getDailyLifeListData(query) {
  return request({
    url: '/report/base/dailyLifelist',
    method: 'post',
    data: query
  })
}

//过去7天新用户访问数
export function getNewUserlistData(query) {
  return request({
    url: '/report/base/newUserlist',
    method: 'post',
    data: query
  })
}

//各端过去7天日活
export function getPlatFormlistData(query) {
  return request({
    url: '/report/base/platFormlist',
    method: 'post',
    data: query
  })
}

//过去7天新访问用户占比
export function getUserTagemListData(query) {
  return request({
    url: '/report/base/userTagemList',
    method: 'post',
    data: query
  })
}

//Web端平均访问时长
export function getWebAvgDurationListData(query) {
  return request({
    url: '/report/base/webAvgDurationList',
    method: 'post',
    data: query
  })
}

//全站流量用户数
export function getWtotalNumListData(query) {
  return request({
    url: '/report/base/wtotalNumList',
    method: 'post',
    data: query
  })
}

export function getLTVQueryData(query) {
  return request({
    url: '/report/ltv/ltvAnalyQuery',
    method: 'post',
    data: query
  })
}

export function getIntervalQueryData(query) {
  return request({
    url: '/report/interval/intervalAnalyQuery',
    method: 'post',
    data: query
  })
}



