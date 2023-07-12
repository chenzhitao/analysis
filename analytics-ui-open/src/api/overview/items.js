import request from '@/utils/request'

// 查询概览组件列表
export function listItems(query) {
  return request({
    url: '/overview/items/list',
    method: 'get',
    params: query
  })
}

// 查询概览组件详细
export function getItems(id) {
  return request({
    url: '/overview/items/' + id,
    method: 'get'
  })
}

// 新增概览组件
export function addItems(data) {
  return request({
    url: '/overview/items',
    method: 'post',
    data: data
  })
}

// 修改概览组件
export function updateItems(data) {
  return request({
    url: '/overview/items',
    method: 'put',
    data: data
  })
}

// 删除概览组件
export function delItems(id) {
  return request({
    url: '/overview/items/' + id,
    method: 'delete'
  })
}

// 导出概览组件
export function exportItems(query) {
  return request({
    url: '/overview/items/export',
    method: 'get',
    params: query
  })
}