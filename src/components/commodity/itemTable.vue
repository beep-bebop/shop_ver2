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

  var data = store.state.item.tableData //从vux中获取后端返回的数据

  var options = {
    data: data,
    allowToolbar: true,
    columns: [
      { type: 'text', title: 'itemid', width: '120px' },
      { type: 'dropdown', title: 'productid', width: '200px',
        source: ["hhh","test"]},
      { type: 'numeric', title: 'list price', width: '120px',mask: "¥ #" },
      { type: 'numeric', title: 'unit cost', width: '120px' ,mask: "¥ #" },
      { type: 'numeric', title: 'supplier', width: '120px' ,mask: "#" },
      { type: 'text', title: 'status', width: '120px' },
      { type: 'text', title: 'attr1', width: '200px' }
    ]
  } //约定表格的表头及其类型

  export default {
    name: 'ItemTable',
    mounted: function() {
      const spreadsheet = jexcel(this.$el, options)
      Object.assign(this, { spreadsheet })
    },
    methods: {
      test(){
        options.columns[1].source = ["123"];
      }
    }
  }
</script>

<style scoped>
  #newRow{
    margin-top: -3.5%;
    margin-left: 44%;
  }
  #spreadsheet{
    margin-top: 5%;
    margin-left: 20%;
    align-items: center;
  }
</style>
