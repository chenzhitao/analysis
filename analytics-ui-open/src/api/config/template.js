import request from '@/utils/request'

// 查询模板配置列表
export function listTemplate(query) {
  return request({
    url: '/config/template/list',
    method: 'get',
    params: query
  })
}

// 查询模板配置详细
export function getTemplate(id) {
  return request({
    url: '/config/template/' + id,
    method: 'get'
  })
}

// 新增模板配置
export function addTemplate(data) {
  return request({
    url: '/config/template',
    method: 'post',
    data: data
  })
}

// 修改模板配置
export function updateTemplate(data) {
  return request({
    url: '/config/template',
    method: 'put',
    data: data
  })
}

// 删除模板配置
export function delTemplate(id) {
  return request({
    url: '/config/template/' + id,
    method: 'delete'
  })
}

// 导出模板配置
export function exportTemplate(query) {
  return request({
    url: '/config/template/export',
    method: 'get',
    params: query
  })
}