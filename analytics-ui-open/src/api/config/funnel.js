import request from '@/utils/request'

// 查询分析漏斗列表
export function listFunnel(query) {
  return request({
    url: '/config/funnel/list',
    method: 'get',
    params: query
  })
}

// 查询分析漏斗详细
export function getFunnel(id) {
  return request({
    url: '/config/funnel/' + id,
    method: 'get'
  })
}

// 新增分析漏斗
export function addFunnel(data) {
  return request({
    url: '/config/funnel',
    method: 'post',
    data: data
  })
}

// 修改分析漏斗
export function updateFunnel(data) {
  return request({
    url: '/config/funnel',
    method: 'put',
    data: data
  })
}

// 删除分析漏斗
export function delFunnel(id) {
  return request({
    url: '/config/funnel/' + id,
    method: 'delete'
  })
}

// 导出分析漏斗
export function exportFunnel(query) {
  return request({
    url: '/config/funnel/export',
    method: 'get',
    params: query
  })
}

export function allListFunnel(query) {
  return request({
    url: '/config/funnel/allFunnelList',
    method: 'get',
    params: query
  })
}
