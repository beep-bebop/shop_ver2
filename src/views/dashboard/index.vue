<template>
  <div class="dashboard-container">
<!--    <div class="dashboard-text">name: {{ name }}</div>-->
    <el-row :gutter="8">
      <el-col  :xs="{span: 10}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;width: 50%">
        <todo-list />
        <LogTable />
      </el-col>
      <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px; width: 39%">
        <box-card />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import TodoList from'@/components/TodoList'
import BoxCard from './BoxCard'
import LogTable from "./LogTable";

export default {
  name: 'Dashboard',
  components: {
    TodoList,
    BoxCard,
    LogTable
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
