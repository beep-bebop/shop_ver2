<template>
  <div class="app-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="money" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Purchases
            </div>
            <font class="card-panel-num">$</font>
            <count-to :start-val="0" :end-val=this.total :duration="10" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('shoppings')">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="shopping" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Orders
            </div>
            <count-to :start-val="0" :end-val=list.length :duration="1" class="card-panel-num" />
          </div>
        </div>
      </el-col>
    </el-row>

    <div class="filter-container">
      <el-input v-model="listQuery.username" placeholder="Username" style="width: 330px;margin-right: 15px" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.status" placeholder="Status" clearable style="width: 120px;margin-right: 15px" class="filter-item">
        <el-option label="Delivered" value="D" />
        <el-option label="Pending" value="P" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleSearch">
        Search
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        Export
      </el-button>
    </div>
    <el-divider></el-divider>
    <el-table v-loading="listLoading" :data="list" border fit element-loading-text="Loading"
              highlight-current-row style="width: 100%">
      <el-table-column align="center" label="ID"  min-width="10%">
        <template slot-scope="scope">
          <span>{{ scope.row.orderId }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="Date" min-width="20%">
        <template slot-scope="scope">
          <span>{{ scope.row.orderDate }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="User" min-width="10%">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>

      <el-table-column width="100px" label="Price" min-width="10%">
        <template slot-scope="scope">
          <span>{{ scope.row.totalPrice }}</span>
        </template>
      </el-table-column>

      <el-table-column class-name="status-col" label="Status" min-width="10%">
        <template slot-scope="{row}">
          <el-tag v-if="row.status==='P'" :type="row.status | statusFilter">
            Pending
          </el-tag>
          <el-tag v-if="row.status==='D'" :type="row.status | statusFilter">
            Delivered
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="Address1" min-width="20%">
        <template slot-scope="{row}">
            <span>{{ row.shipAddress1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Address2" min-width="20%">
        <template slot-scope="{row}">
          <span>{{ row.shipAddress2 }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Actions"  min-width="10%">
        <template slot-scope="{row}">
          <el-button @click.native="overallClick(row)" type="primary" size="small" icon="el-icon-edit">
            Edit
          </el-button>
        </template>
      </el-table-column>
    </el-table>

<!--    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />-->
  </div>
</template>

<script>
import { fetchList } from '@/api/article'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import CountTo from 'vue-count-to'

export default {
  name: 'ArticleList',
  components: {
    Pagination,
    CountTo},
  filters: {
    statusFilter(status) {
      const statusMap = {
        D: 'success',
        draft: 'info',
        P: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        username: undefined,
        status: undefined,
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleSearch() {
      console.log("11111111"+this.listQuery.status)
      this.listLoading = true;
      this.axios.get('/user/orders',{ params: {
          username: this.listQuery.username,
          status: this.listQuery.status
        }})
        .then(response => {
          this.list = response.data.data.orders;
          this.listLoading = false;
        })
    },
    getList() {
      this.listLoading = true
      this.axios.get('/listOrders')
        .then(response => {
          this.list = response.data.data.orders;
          this.total = response.data.data.total;
          console.log(response.data);
          this.listLoading = false;
        })
    },
    overallClick(row) {
      if (row) {
        const id = row.orderId
        const that = this
        that.showDetails(id)
      }
    },
    showDetails(id) {
      this.$nextTick(function() {
        this.$router.push({
          path: '/orders/edit',
          query: { param: id }
        })
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['orderId', 'orderDate', 'username', 'totalPrice', 'status', 'shipAddress1', 'shipAddress2']
        const filterVal = ['orderId', 'orderDate', 'username', 'totalPrice', 'status', 'shipAddress1', 'shipAddress2']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'order-list'
        })
        this.downloadLoading = false
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
    }
  }
}
</script>

<style lang="scss" scoped>
  .edit-input {
    padding-right: 100px;
  }
  .cancel-btn {
    position: absolute;
    right: 15px;
    top: 10px;
  }
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
