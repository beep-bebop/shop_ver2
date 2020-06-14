const state = {
  tableData: [
    ['Birds', 'hhh', 123,100,5],
    ['Civic', 'test', 123,100,10]
  ] //表格数据
}

const mutations = {
  setTableData(state, TableData) {
    state.tableData = TableData  //通过commit修改表格数据函数
  }
}

export default {
  namespaced: true,
  state,
  mutations
}
