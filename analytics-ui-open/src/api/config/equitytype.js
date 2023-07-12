import request from '@/utils/request'

// 查询权益类型列表
export function listEquitytype(query) {
  return request({
    url: '/config/equitytype/list',
    method: 'get',
    params: query
  })
}

// 查询权益类型详细
export function getEquitytype(id) {
  return request({
    url: '/config/equitytype/' + id,
    method: 'get'
  })
}

// 新增权益类型
export function addEquitytype(data) {
  return request({
    url: '/config/equitytype',
    method: 'post',
    data: data
  })
}

// 修改权益类型
export function updateEquitytype(data) {
  return request({
    url: '/config/equitytype',
    method: 'put',
    data: data
  })
}

// 删除权益类型
export function delEquitytype(id) {
  return request({
    url: '/config/equitytype/' + id,
    method: 'delete'
  })
}

// 导出权益类型
export function exportEquitytype(query) {
  return request({
    url: '/config/equitytype/export',
    method: 'get',
    params: query
  })
}