import request from '@/utils/request'

// 查询黑名单列表
export function listBalcklist(query) {
  return request({
    url: '/config/balcklist/list',
    method: 'get',
    params: query
  })
}

// 查询黑名单详细
export function getBalcklist(id) {
  return request({
    url: '/config/balcklist/' + id,
    method: 'get'
  })
}

// 新增黑名单
export function addBalcklist(data) {
  return request({
    url: '/config/balcklist',
    method: 'post',
    data: data
  })
}

// 修改黑名单
export function updateBalcklist(data) {
  return request({
    url: '/config/balcklist',
    method: 'put',
    data: data
  })
}

// 删除黑名单
export function delBalcklist(id) {
  return request({
    url: '/config/balcklist/' + id,
    method: 'delete'
  })
}

// 导出黑名单
export function exportBalcklist(query) {
  return request({
    url: '/config/balcklist/export',
    method: 'get',
    params: query
  })
}