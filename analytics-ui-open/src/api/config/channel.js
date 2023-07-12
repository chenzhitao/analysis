import request from '@/utils/request'

// 查询触达通道列表
export function listChannel(query) {
  return request({
    url: '/config/channel/list',
    method: 'get',
    params: query
  })
}

// 查询触达通道详细
export function getChannel(id) {
  return request({
    url: '/config/channel/' + id,
    method: 'get'
  })
}

// 新增触达通道
export function addChannel(data) {
  return request({
    url: '/config/channel',
    method: 'post',
    data: data
  })
}

// 修改触达通道
export function updateChannel(data) {
  return request({
    url: '/config/channel',
    method: 'put',
    data: data
  })
}

// 删除触达通道
export function delChannel(id) {
  return request({
    url: '/config/channel/' + id,
    method: 'delete'
  })
}

// 导出触达通道
export function exportChannel(query) {
  return request({
    url: '/config/channel/export',
    method: 'get',
    params: query
  })
}