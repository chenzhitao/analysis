import request from '@/utils/request'

// 查询事件项目列表
export function listItem(query) {
  return request({
    url: '/config/item/list',
    method: 'get',
    params: query
  })
}

// 查询事件项目详细
export function getItem(id) {
  return request({
    url: '/config/item/' + id,
    method: 'get'
  })
}

// 新增事件项目
export function addItem(data) {
  return request({
    url: '/config/item',
    method: 'post',
    data: data
  })
}

// 修改事件项目
export function updateItem(data) {
  return request({
    url: '/config/item',
    method: 'put',
    data: data
  })
}

// 删除事件项目
export function delItem(id) {
  return request({
    url: '/config/item/' + id,
    method: 'delete'
  })
}

// 导出事件项目
export function exportItem(query) {
  return request({
    url: '/config/item/export',
    method: 'get',
    params: query
  })
}


export function eventTypeOptions(query) {
  return request({
    url: '/config/type/allEventTypeList',
    method: 'get',
    params: query
  })
}

export function eventItemsOptions(query) {
  return request({
    url: '/config/item/getItemListByType',
    method: 'get',
    params: query
  })
}

export function allEventItemsOptions() {
  return request({
    url: '/config/item/getEventItemsList',
    method: 'get'
  })
}

export function eventItemsByType(query) {
  return request({
    url: '/config/item/getItemGroupsByType',
    method: 'get',
    params: query
  })
}
