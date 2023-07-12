import request from '@/utils/request'

// 查询权益项目列表
export function listEquityitem(query) {
  return request({
    url: '/config/equityitem/list',
    method: 'get',
    params: query
  })
}

// 查询权益项目详细
export function getEquityitem(id) {
  return request({
    url: '/config/equityitem/' + id,
    method: 'get'
  })
}

// 新增权益项目
export function addEquityitem(data) {
  return request({
    url: '/config/equityitem',
    method: 'post',
    data: data
  })
}

// 修改权益项目
export function updateEquityitem(data) {
  return request({
    url: '/config/equityitem',
    method: 'put',
    data: data
  })
}

// 删除权益项目
export function delEquityitem(id) {
  return request({
    url: '/config/equityitem/' + id,
    method: 'delete'
  })
}

// 导出权益项目
export function exportEquityitem(query) {
  return request({
    url: '/config/equityitem/export',
    method: 'get',
    params: query
  })
}

export function cardTypeOptions(query) {
  return request({
    url: '/config/equitytype/getAllTypeList',
    method: 'get',
    params: query
  })
}

export function listEquityitemAll(query) {
  return request({
    url: '/config/equityitem/getItemByType',
    method: 'get',
    params: query
  })
}
