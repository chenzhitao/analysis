import request from '@/utils/request'

// 查询用户属性列表
export function listUserattribute(query) {
  return request({
    url: '/config/userattribute/list',
    method: 'get',
    params: query
  })
}

// 查询用户属性详细
export function getUserattribute(id) {
  return request({
    url: '/config/userattribute/' + id,
    method: 'get'
  })
}

// 新增用户属性
export function addUserattribute(data) {
  return request({
    url: '/config/userattribute',
    method: 'post',
    data: data
  })
}

// 修改用户属性
export function updateUserattribute(data) {
  return request({
    url: '/config/userattribute',
    method: 'put',
    data: data
  })
}

// 删除用户属性
export function delUserattribute(id) {
  return request({
    url: '/config/userattribute/' + id,
    method: 'delete'
  })
}

// 导出用户属性
export function exportUserattribute(query) {
  return request({
    url: '/config/userattribute/export',
    method: 'get',
    params: query
  })
}


export function getUserAttributeList(query) {
  return request({
    url: '/config/userattribute/getUserAttributeList',
    method: 'get',
    params: query
  })
}
