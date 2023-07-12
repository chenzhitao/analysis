import request from '@/utils/request'

// 查询书签与概览页面关联列表
export function listMarkrela(query) {
  return request({
    url: '/overview/markrela/list',
    method: 'get',
    params: query
  })
}

// 查询书签与概览页面关联详细
export function getMarkrela(markId) {
  return request({
    url: '/overview/markrela/' + markId,
    method: 'get'
  })
}

// 新增书签与概览页面关联
export function addMarkrela(data) {
  return request({
    url: '/overview/markrela',
    method: 'post',
    data: data
  })
}

// 修改书签与概览页面关联
export function updateMarkrela(data) {
  return request({
    url: '/overview/markrela',
    method: 'put',
    data: data
  })
}

// 删除书签与概览页面关联
export function delMarkrela(markId) {
  return request({
    url: '/overview/markrela/' + markId,
    method: 'delete'
  })
}

// 导出书签与概览页面关联
export function exportMarkrela(query) {
  return request({
    url: '/overview/markrela/export',
    method: 'get',
    params: query
  })
}