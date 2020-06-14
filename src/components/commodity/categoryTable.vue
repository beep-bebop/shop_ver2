<template>
  <div>
    <el-button type="primary" @click="() => spreadsheet.insertRow()" id="newRow">新增一行</el-button>
    <div ref="spreadsheet" id="spreadsheet"></div>
  </div>
</template>

<script>
import jexcel from 'jexcel'
import 'jexcel/dist/jexcel.css'
import store from '@/store' //添加仓库

var data = store.state.category.tableData //从vux中获取后端返回的数据

var options = {
  data: data,
  allowToolbar: true,
  columns: [
    { type: 'text', title: 'catid', width: '120px' },
    { type: 'text', title: 'name', width: '250px' },
    { type: 'image', title: 'Photo', width: '200px' }
  ]
}  //约定表格的表头及其类型

export default {
  name: 'CategoryTable',
  mounted: function() {
    const spreadsheet = jexcel(this.$el, options)
    Object.assign(this, { spreadsheet })
  }
}
</script>

<style scoped>
  #newRow{
    margin-top: -3.5%;
    margin-left: 44%;
  }
  #spreadsheet{
    margin-top: 8%;
    margin-left: 20%;
    align-items: center;
  }

</style>
