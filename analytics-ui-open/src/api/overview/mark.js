import request from '@/utils/request'

// 查询我的书签列表
export function listMark(query) {
  return request({
    url: '/overview/mark/list',
    method: 'get',
    params: query
  })
}

// 查询我的书签详细
export function getMark(id) {
  return request({
    url: '/overview/mark/' + id,
    method: 'get'
  })
}

// 新增我的书签
export function addMark(data) {
  return request({
    url: '/overview/mark',
    method: 'post',
    data: data
  })
}

// 修改我的书签
export function updateMark(data) {
  return request({
    url: '/overview/mark',
    method: 'put',
    data: data
  })
}

// 删除我的书签
export function delMark(id) {
  return request({
    url: '/overview/mark/' + id,
    method: 'delete'
  })
}

// 导出我的书签
export function exportMark(query) {
  return request({
    url: '/overview/mark/export',
    method: 'get',
    params: query
  })
}