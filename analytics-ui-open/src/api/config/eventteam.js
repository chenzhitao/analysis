import request from '@/utils/request'

// 查询事件分组列表
export function listEventteam(query) {
  return request({
    url: '/config/eventteam/list',
    method: 'get',
    params: query
  })
}

// 查询事件分组详细
export function getEventteam(id) {
  return request({
    url: '/config/eventteam/' + id,
    method: 'get'
  })
}

// 新增事件分组
export function addEventteam(data) {
  return request({
    url: '/config/eventteam',
    method: 'post',
    data: data
  })
}

// 修改事件分组
export function updateEventteam(data) {
  return request({
    url: '/config/eventteam',
    method: 'put',
    data: data
  })
}

// 删除事件分组
export function delEventteam(id) {
  return request({
    url: '/config/eventteam/' + id,
    method: 'delete'
  })
}

// 导出事件分组
export function exportEventteam(query) {
  return request({
    url: '/config/eventteam/export',
    method: 'get',
    params: query
  })
}


export function eventTeamDataFun(query) {
  return request({
    url: '/config/eventteam/getAllTeamList',
    method: 'get',
    params: query
  })
}
