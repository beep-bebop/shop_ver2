<template>
  <div class="app-container">
    <el-form ref="ruleForm" :disabled="true" :model="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-row :gutter="40" type="flex" justify="center">
        <el-col :span="10">
          <div>
            <el-divider>Payment Details</el-divider>
            <el-form-item label="Card Type">
              <el-select v-model="order.cardType">
                <el-option label="Visa" value="Visa" />
                <el-option label="Mastercard" value="Mastercard" />
                <el-option label="American Express" value="American Express" />
                <el-option label="Discover" value="Discover" />
                <el-option label="JCB" value="JCB" />
                <el-option label="UnionPay" value="UnionPay" />
                <el-option label="Maestro" value="Maestro" />
              </el-select>
            </el-form-item>
            <el-form-item label="Order ID">
              <el-input v-model="order.id" />
            </el-form-item>
            <el-form-item label="Card Number">
              <el-input v-model="order.creditCard" />
            </el-form-item>
            <el-form-item label="Expiry Date">
              <el-form-item>
                <el-date-picker v-model="order.expiryDate" type="month" style="width: 100%;" />
              </el-form-item>
            </el-form-item>
            <el-divider>Billing Address</el-divider>
            <el-form-item label="First Name">
              <el-input v-model="order.billToFirstName" />
            </el-form-item>
            <el-form-item label="Last Name">
              <el-input v-model="order.billToLastName" />
            </el-form-item>
            <el-form-item label="Address 1">
              <el-input v-model="order.billAddress1" />
            </el-form-item>
            <el-form-item label="Address 2">
              <el-input v-model="order.billAddress2" />
            </el-form-item>
            <el-form-item label="City">
              <el-input v-model="order.billCity" />
            </el-form-item>
            <el-form-item label="State">
              <el-input v-model="order.billState" />
            </el-form-item>
            <el-form-item label="Zip">
              <el-input v-model="order.billZip" />
            </el-form-item>
            <el-form-item label="Country">
              <el-input v-model="order.billCountry" />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="10">
          <div>
            <el-divider>Shipping Address</el-divider>
            <el-form-item label="First Name">
              <el-input v-model="order.shipToFirstName" />
            </el-form-item>
            <el-form-item label="Last Name">
              <el-input v-model="order.shipToLastName" />
            </el-form-item>
            <el-form-item label="Address 1">
              <el-input v-model="order.shipAddress1" />
            </el-form-item>
            <el-form-item label="Address 2">
              <el-input v-model="order.shipAddress2" />
            </el-form-item>
            <el-form-item label="City">
              <el-input v-model="order.shipCity" />
            </el-form-item>
            <el-form-item label="State">
              <el-input v-model="order.shipState" />
            </el-form-item>
            <el-form-item label="Zip">
              <el-input v-model="order.shipZip" />
            </el-form-item>
            <el-form-item label="Country">
              <el-input v-model="order.shipCountry" />
            </el-form-item>
          </div>
        </el-col>
      </el-row>
    </el-form>
    <el-divider>Your Pets</el-divider>
    <el-table :data="lineItems">
      <el-table-column align="center" label="Item ID" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">
            <el-link type="primary" @click="$router.push('/item?id='+scope.row.itemId)">{{ scope.row.itemId }}</el-link>
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Description" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px" v-html="scope.row.item.product.description" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="Quantity" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.quantity }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Price" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">${{ scope.row.unitPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Total Cost" width="180">
        <template slot-scope="scope">
          <span style="margin-left: 10px">${{ scope.row.total }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      order: null,
      lineItems: null,
      ruleForm: {
        id: this.$route.query.param,
        cardType: '',
        cardNumber: '',
        expiryTime: '',
        firstName: '',
        lastName: '',
        addr1: '',
        addr2: '',
        city: '',
        state: '',
        zip: '',
        country: '',
        shipToDifferentAddr: false,
        shipFirstName: '',
        shipLastName: '',
        shipAddr1: '',
        shipAddr2: '',
        shipCity: '',
        shipState: '',
        shipZip: '',
        shipCountry: ''
      }
    }
  },
  watch: {
    '$route'() {
      this.getData()
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      this.axios.get('/user/order', {
        params: {
          id: this.$route.query.param
        }
      })
        .then(res => {
          if (res.data.status) {
            this.order = res.data.data.order
            this.lineItems = this.order.lineItems
          } else {
            this.$message('请先登入')
          }
        })
        .catch(err => {
          if (err.response.status == 400) {
            this.$message('请先登入')
          }
          window.console.error(err)
        })
    }
  }
}
</script>

<style scoped>

</style>
