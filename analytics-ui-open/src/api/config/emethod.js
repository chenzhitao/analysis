import request from '@/utils/request'

// 查询事件函数列表
export function listEmethod(query) {
  return request({
    url: '/config/emethod/list',
    method: 'get',
    params: query
  })
}

// 查询事件函数详细
export function getEmethod(id) {
  return request({
    url: '/config/emethod/' + id,
    method: 'get'
  })
}

// 新增事件函数
export function addEmethod(data) {
  return request({
    url: '/config/emethod',
    method: 'post',
    data: data
  })
}

// 修改事件函数
export function updateEmethod(data) {
  return request({
    url: '/config/emethod',
    method: 'put',
    data: data
  })
}

// 删除事件函数
export function delEmethod(id) {
  return request({
    url: '/config/emethod/' + id,
    method: 'delete'
  })
}

// 导出事件函数
export function exportEmethod(query) {
  return request({
    url: '/config/emethod/export',
    method: 'get',
    params: query
  })
}


export function getEventMethodOptions(query) {
  return request({
    url: '/config/emethod/getEventMethodList',
    method: 'get',
    params: query
  })
}

export function getAllEventMethodResult() {
  return request({
    url: '/config/item/getEventItemMethods',
    method: 'get'
  })
}
