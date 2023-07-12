import request from '@/utils/request'

// 查询我的概览列表
export function listTeam(query) {
  return request({
    url: '/overview/team/list',
    method: 'get',
    params: query
  })
}

// 查询我的概览详细
export function getTeam(id) {
  return request({
    url: '/overview/team/' + id,
    method: 'get'
  })
}

// 新增我的概览
export function addTeam(data) {
  return request({
    url: '/overview/team',
    method: 'post',
    data: data
  })
}

// 修改我的概览
export function updateTeam(data) {
  return request({
    url: '/overview/team',
    method: 'put',
    data: data
  })
}

// 删除我的概览
export function delTeam(id) {
  return request({
    url: '/overview/team/' + id,
    method: 'delete'
  })
}

// 导出我的概览
export function exportTeam(query) {
  return request({
    url: '/overview/team/export',
    method: 'get',
    params: query
  })
}

// 导出我的概览
export function getAllGroupAndOverview(query) {
  return request({
    url: '/overview/team/getMyTeamlist',
    method: 'get',
    params: query
  })
}