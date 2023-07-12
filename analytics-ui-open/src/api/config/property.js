import request from '@/utils/request'

// 查询事件项属性列表
export function listProperty(query) {
  return request({
    url: '/config/property/list',
    method: 'get',
    params: query
  })
}

// 查询事件项属性详细
export function getProperty(id) {
  return request({
    url: '/config/property/' + id,
    method: 'get'
  })
}

// 新增事件项属性
export function addProperty(data) {
  return request({
    url: '/config/property',
    method: 'post',
    data: data
  })
}

// 修改事件项属性
export function updateProperty(data) {
  return request({
    url: '/config/property',
    method: 'put',
    data: data
  })
}

// 删除事件项属性
export function delProperty(id) {
  return request({
    url: '/config/property/' + id,
    method: 'delete'
  })
}

// 导出事件项属性
export function exportProperty(query) {
  return request({
    url: '/config/property/export',
    method: 'get',
    params: query
  })
}

export function listPropertyByType(query) {
  return request({
    url: '/config/property/getPropertyListByType',
    method: 'get',
    params: query
  })
}

export function listAllPropertyResult() {
  return request({
    url: '/config/property/getAllItemPropertyMap',
    method: 'get'
  })
}
