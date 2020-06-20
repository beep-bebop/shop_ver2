<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;">
    <el-table-column label="Time" min-width="200">
      <template slot-scope="scope">
        {{ scope.row.timeStamp | orderNoFilter }}
      </template>
    </el-table-column>
    <el-table-column label="User" width="195" align="center">
      <template slot-scope="scope">
        {{ scope.row.userid }}
      </template>
    </el-table-column>
    <el-table-column label="Action" width="195" align="center">
      <template slot-scope="scope">
        {{ scope.row.log }}
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    filters: {
      orderNoFilter(str) {
        return str.substring(0, 19)
      }
    },
    data() {
      return {
        list: null
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.axios.get('/log')
          .then(response => {
            this.list = response.data.data.logList;
            console.log(response.data)
          })
      }
    }
  }
</script>
