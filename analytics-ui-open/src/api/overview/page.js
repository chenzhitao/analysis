import request from '@/utils/request'

// 查询概览页面列表
export function listPage(query) {
  return request({
    url: '/overview/page/list',
    method: 'get',
    params: query
  })
}

// 查询概览页面详细
export function getPage(id) {
  return request({
    url: '/overview/page/' + id,
    method: 'get'
  })
}

// 新增概览页面
export function addPage(data) {
  return request({
    url: '/overview/page',
    method: 'post',
    data: data
  })
}

// 修改概览页面
export function updatePage(data) {
  return request({
    url: '/overview/page',
    method: 'put',
    data: data
  })
}

// 删除概览页面
export function delPage(id) {
  return request({
    url: '/overview/page/' + id,
    method: 'delete'
  })
}

// 导出概览页面
export function exportPage(query) {
  return request({
    url: '/overview/page/export',
    method: 'get',
    params: query
  })
}