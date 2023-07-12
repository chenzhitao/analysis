import request from '@/utils/request'

// 查询事件类型列表
export function listType(query) {
  return request({
    url: '/config/type/list',
    method: 'get',
    params: query
  })
}

// 查询事件类型详细
export function getType(id) {
  return request({
    url: '/config/type/' + id,
    method: 'get'
  })
}

// 新增事件类型
export function addType(data) {
  return request({
    url: '/config/type',
    method: 'post',
    data: data
  })
}

// 修改事件类型
export function updateType(data) {
  return request({
    url: '/config/type',
    method: 'put',
    data: data
  })
}

// 删除事件类型
export function delType(id) {
  return request({
    url: '/config/type/' + id,
    method: 'delete'
  })
}

// 导出事件类型
export function exportType(query) {
  return request({
    url: '/config/type/export',
    method: 'get',
    params: query
  })
}