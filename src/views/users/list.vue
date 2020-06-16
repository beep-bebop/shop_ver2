<template>
  <div class="app-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="peoples" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Number
            </div>
            <count-to :start-val="0" :end-val=this.list.length :duration="1" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('messages')">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="message" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Online
            </div>
            <count-to :start-val="0" :end-val="81212" :duration="3000" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('messages')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="message" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Most Popular
            </div>
            <font class="card-panel-num">DOGS</font>
          </div>
        </div>
      </el-col>
    </el-row>

    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="Title" style="width: 900px;margin-right: 15px" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" type="primary"  icon="el-icon-search" @click="handleSearch">
        Search
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        Export
      </el-button>
    </div>
    <el-divider></el-divider>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="Index" min-width="10%">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="Username" min-width="20%">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column label="isVIP" min-width="10%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.isVIP }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Orders" min-width="10%" align="center">
        <template slot-scope="scope">
          {{ scope.row.zip }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="Favourite" min-width="10%">
        <template slot-scope="scope">
          <span>{{ scope.row.favouriteCategoryId }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="Language" min-width="10%">
        <template slot-scope="scope">
          <span>{{ scope.row.languagePreference }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="Language" min-width="10%">
        <template slot-scope="scope">
          <a href="mailto:1143432728@qq.com">check</a>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="Status" min-width="10%" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Action"min-width="20%">
        <template slot-scope="{row}">
          <el-button @click.native="edit(row)" type="primary" size="small" icon="el-icon-edit">
            Edit
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getList } from '@/api/table'
import CountTo from 'vue-count-to'
import { parseTime } from '@/utils'

export default {
  components: {
    CountTo
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        OK: 'success',
        EDITED: 'info',
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      downloadLoading: false
    }
  },
  created() {
    this.fetchData();
    // this.edit()
  },
  methods: {
    fetchData() {
      this.listLoading = true;
      this.axios.get('/users')
        .then(response => {
        this.list = response.data.data.accounts;
        this.listLoading = false;
      })
    },
    edit(row) {
      if (row) {
        const id = row.username
        const that = this
        that.showDetails(id)
      }
    },
    showDetails(id) {
      this.$nextTick(function() {
        this.$router.push({
          path: '/users/edit',
          query: { param: id }
        })
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    handleSearch() {
      this.listLoading = true;
      console.log("search"+this.listQuery.title)
      this.axios.get('/user/search',{ params: {
          username: this.listQuery.title
        }})
        .then(response => {
          this.list = response.data.data.accounts;
          this.listLoading = false;
        })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'username', 'orders', 'isVIP', 'order_time', 'status']
        const filterVal = ['timestamp', 'username', 'orders', 'isVIP', 'order_time', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .panel-group {
    margin-top: 18px;

    .card-panel-col {
      margin-bottom: 32px;
    }

    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-people {
          background: #40c9c6;
        }

        .icon-message {
          background: #36a3f7;
        }

        .icon-money {
          background: #f4516c;
        }

        .icon-shopping {
          background: #34bfa3
        }
      }

      .icon-people {
        color: #40c9c6;
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-money {
        color: #f4516c;
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }

  @media (max-width:550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }
</style>
