import request from '@/utils/request'

// 获取公寓列表
export function getApartmentList(params) {
  return request({
    url: '/apartment/list',
    method: 'get',
    params
  })
}

// 新增公寓
export function addApartment(data) {
  return request({
    url: '/apartment/add',
    method: 'post',
    data
  })
}

// 编辑公寓
export function updateApartment(data) {
  return request({
    url: '/apartment/update',
    method: 'put',
    data
  })
}

// 删除公寓
export function deleteApartment(id) {
  return request({
    url: `/apartment/delete/${id}`,
    method: 'delete'
  })
}

// 更新公寓状态
export function updateApartmentStatus(apartmentId, status) {
  return request({
    url: '/apartment/status',
    method: 'put',
    params: {
      apartmentId,
      status
    }
  })
}
