import request from '@/utils/request'

// 查询图标类型列表
export function listType(query) {
  return request({
    url: '/overview/type/list',
    method: 'get',
    params: query
  })
}

// 查询图标类型详细
export function getType(id) {
  return request({
    url: '/overview/type/' + id,
    method: 'get'
  })
}

// 新增图标类型
export function addType(data) {
  return request({
    url: '/overview/type',
    method: 'post',
    data: data
  })
}

// 修改图标类型
export function updateType(data) {
  return request({
    url: '/overview/type',
    method: 'put',
    data: data
  })
}

// 删除图标类型
export function delType(id) {
  return request({
    url: '/overview/type/' + id,
    method: 'delete'
  })
}

// 导出图标类型
export function exportType(query) {
  return request({
    url: '/overview/type/export',
    method: 'get',
    params: query
  })
}