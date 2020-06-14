const state = {
  tableData: [
    ['Birds', 'Birds','hhh' ,''],
    ['Civic', 'Honda','hhh' , '']
  ] //表格数据
}

const mutations = {
  setTableData(state, TableData) {
    state.tableData = TableData //通过commit修改表格数据函数
  }
}

export default {
  namespaced: true,
  state,
  mutations
}
