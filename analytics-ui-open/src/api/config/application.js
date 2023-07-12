import request from '@/utils/request'

// 查询应用管理列表
export function listApplication(query) {
  return request({
    url: '/config/application/list',
    method: 'get',
    params: query
  })
}

// 查询应用管理详细
export function getApplication(id) {
  return request({
    url: '/config/application/' + id,
    method: 'get'
  })
}

// 新增应用管理
export function addApplication(data) {
  return request({
    url: '/config/application',
    method: 'post',
    data: data
  })
}

// 修改应用管理
export function updateApplication(data) {
  return request({
    url: '/config/application',
    method: 'put',
    data: data
  })
}

// 删除应用管理
export function delApplication(id) {
  return request({
    url: '/config/application/' + id,
    method: 'delete'
  })
}

// 导出应用管理
export function exportApplication(query) {
  return request({
    url: '/config/application/export',
    method: 'get',
    params: query
  })
}
