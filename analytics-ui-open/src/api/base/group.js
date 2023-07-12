import request from '@/utils/request'

// 查询用户分群列表
export function listGroup(query) {
  return request({
    url: '/base/group/list',
    method: 'get',
    params: query
  })
}

// 查询用户分群详细
export function getGroup(id) {
  return request({
    url: '/base/group/' + id,
    method: 'get'
  })
}

// 查询用户分群详细
export function getGroupHistoryData(id) {
  return request({
    url: '/base/group/history/' + id,
    method: 'get'
  })
}

// 新增用户分群
export function addGroup(data) {
  return request({
    url: '/base/group',
    method: 'post',
    data: data
  })
}

// 修改用户分群
export function updateGroup(data) {
  return request({
    url: '/base/group',
    method: 'put',
    data: data
  })
}

// 删除用户分群
export function delGroup(id) {
  return request({
    url: '/base/group/' + id,
    method: 'delete'
  })
}

// 导出用户分群
export function exportGroup(query) {
  return request({
    url: '/base/group/export',
    method: 'get',
    params: query
  })
}

export function listGroupUserDetail(query) {
  return request({
    url: '/base/group/userDetailList',
    method: 'get',
    params: query
  })
}

// 查询用户标签列表
export function addUserGroup(data) {
  return request({
    url: '/base/group/createUserGroup',
    method: 'post',
    data: data
  })
}

export function calcUserGroupNumber(data) {
  return request({
    url: '/base/group/calcUserGroupNumber',
    method: 'post',
    data: data
  })
}

// 查询用户详情
export function getUserInfo(id) {
  return request({
    url: '/base/group/user/info/' + id,
    method: 'get'
  })
}

