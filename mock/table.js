import Mock from 'mockjs'

const data = Mock.mock({
  'items|30': [{
    id: '@id',
    username: '@word(5, 10)',
    'status|1': ['online', 'offline', 'banned'],
    isVIP: '?',
    order_time: '@datetime',
    orders: '@integer(300, 5000)'
  }]
})

export default [
  {
    url: '/vue-admin-template/table/list',
    type: 'get',
    response: config => {
      const items = data.items
      return {
        code: 20000,
        data: {
          total: items.length,
          items: items
        }
      }
    }
  }
]
