<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-row :gutter="40" type="flex" justify="center">
        <el-col :span="10">
          <div>
            <el-divider>Payment Details</el-divider>
            <el-form-item label="Order ID">
              <el-input :disabled="true" v-model="order.orderId" />
            </el-form-item>
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
            <el-form-item label="Address">
              <el-tag style="width:60px">country</el-tag>
              <el-select v-model="order.billCountry" @focus="focus" >
                <el-option label="CHINA" value="CHINA" />
                <el-option label="USA" value="USA" />
                <el-option label="SPANISH" value="SPANISH" />
              </el-select>
             </el-form-item>
            <el-form-item>
              <el-tag style="width:60px">state</el-tag>
              <el-select v-model="order.billState" @focus="focus">
                <el-option label="Hunan" value="HUNAN" />
                <el-option label="Shanghai" value="CHONGQING" />
                <el-option label="GuangDong" value="SHANGHAI" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-tag style="width:60px">city</el-tag>
              <el-select v-model="order.billCity" @focus="focus">
                <el-option label="ChangSha" value="CHANGSHA" />
                <el-option label="ShangHai" value="CHANGSHA" />
                <el-option label="ShenZhen" value="CHANGSHA" />
              </el-select>
            </el-form-item>
            <el-form-item label="Zip">
              <el-input v-model="order.billZip" style="width: 360px"/>
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
            <el-divider>Operation</el-divider>
            <el-form-item style="width: border-box">
              <el-row>
                <el-button style="width: 150px" type="primary" @click="onSubmit">Submit</el-button>
                <el-button style="width: 150px" type="success" @click="onDeliver">Deliver</el-button>
              </el-row>
              <el-row>
                <el-divider></el-divider>
                <!--              <label style="margin-right:100px;"></label>-->
                <el-button style="width: 150px" type="info" @click="onCancel">Cancel</el-button>
                <el-button style="width: 150px" type="danger" @click="onDelete">Delete</el-button>
              </el-row>
            </el-form-item>
        </el-col>
      </el-row>
    </el-form>
<!--    <el-divider>Your Pets</el-divider>-->
<!--    <el-table :data="lineItems">-->
<!--      <el-table-column align="center" label="Item ID" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span style="margin-left: 10px">-->
<!--            <el-link type="primary" @click="$router.push('/item?id='+scope.row.itemId)">{{ scope.row.itemId }}</el-link>-->
<!--          </span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column align="center" label="Description" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span style="margin-left: 10px" v-html="scope.row.item.product.description" />-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column align="center" label="Quantity" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span style="margin-left: 10px">{{ scope.row.quantity }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column align="center" label="Price" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span style="margin-left: 10px">${{ scope.row.unitPrice }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column align="center" label="Total Cost" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span style="margin-left: 10px">${{ scope.row.total }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      // order: null,
      lineItems: null,
      order: {
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
    onSubmit() {
      // this.axios({url:'/user/edit', method:'post',data:JSON.stringify(account),
      //   headers:{'Content-Type':'application/json'}})
      this.axios.post('order/edit',{
        orderId: this.order.orderId,
        username: this.order.username,
        orderDate: this.order.orderDate,
        cardType: this.order.cardType,
        creditCard: this.order.creditCard,
        expiryDate: this.order.expiryDate,
        billToFirstName: this.order.billToFirstName,
        billToLastName: this.order.billToLastName,
        billAddress1: this.order.billAddress1,
        billAddress2: this.order.billAddress2,
        billCity: this.order.billCity,
        billState: this.order.billState,
        billZip: this.order.billZip,
        billCountry: this.order.billCountry,
        shipToFirstName: this.order.shipToFirstName,
        shipToLastName: this.order.shipToLastName,
        shipAddress1: this.order.shipAddress1,
        shipAddress2: this.order.shipAddress2,
        shipCity: this.order.shipCity,
        shipState: this.order.shipState,
        shipZip: this.order.shipZip,
        shipCountry: this.order.shipCountry,
        locale: 'CA',
        lineItems: [],
        courier: this.order.courier,
        totalPrice: this.order.totalPrice,
        status: 'P'
      },{
        headers: {
          'Content-Type': 'text/plain;charset=UTF-8'
        }}).then(res => {
        if (res.data.status) {
          this.$message.success('success')
        } else {
          this.$message('fail to edit')
        }
      }).catch(err => {
        // eslint-disable-next-line eqeqeq
        if (err.response.status == 400) {
          this.$message('cannot connect to server')
          // 跳转到登录页
        }
        window.console.error(err)
      })
      this.$message('submit!')
    },
    onDeliver() {
      // this.axios({url:'/user/edit', method:'post',data:JSON.stringify(account),
      //   headers:{'Content-Type':'application/json'}})
      this.axios.post('order/edit',{
        orderId: this.order.orderId,
        username: this.order.username,
        orderDate: this.order.orderDate,
        cardType: this.order.cardType,
        creditCard: this.order.creditCard,
        expiryDate: this.order.expiryDate,
        billToFirstName: this.order.billToFirstName,
        billToLastName: this.order.billToLastName,
        billAddress1: this.order.billAddress1,
        billAddress2: this.order.billAddress2,
        billCity: this.order.billCity,
        billState: this.order.billState,
        billZip: this.order.billZip,
        billCountry: this.order.billCountry,
        shipToFirstName: this.order.shipToFirstName,
        shipToLastName: this.order.shipToLastName,
        shipAddress1: this.order.shipAddress1,
        shipAddress2: this.order.shipAddress2,
        shipCity: this.order.shipCity,
        shipState: this.order.shipState,
        shipZip: this.order.shipZip,
        shipCountry: this.order.shipCountry,
        locale: 'CA',
        lineItems: [],
        courier: this.order.courier,
        totalPrice: this.order.totalPrice,
        status: 'D'
      },{
        headers: {
          'Content-Type': 'text/plain;charset=UTF-8'
        }}).then(res => {
        if (res.data.status) {
          this.$message.success('success')
        } else {
          this.$message('fail to edit')
        }
      }).catch(err => {
        // eslint-disable-next-line eqeqeq
        if (err.response.status == 400) {
          this.$message('cannot connect to server')
          // 跳转到登录页
        }
        window.console.error(err)
      })
      this.$message('submit!')
    },
    onCancel() {
      this.$nextTick(function() {
        this.$router.push({
          path: '/orders/list',
        })
        this.$message({
          message: 'cancel!',
          type: 'warning'
        })
      })
    },
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
            this.$message('fail')
          }
        })
        .catch(err => {
          if (err.response.status === 400) {
            this.$message('disconnect with server')
          }
          window.console.error(err)
        })
    },
    onDelete() {
      console.log(this.order.orderId)
      this.axios.post('/order/delete', {
        orderId: this.order.orderId
      },{
        headers: {
          'Content-Type': 'text/plain;charset=UTF-8'
        }})
        .then(res => {
          if (res.data.status) {
            this.$message({
              message: 'Deleted',
              type: 'warning'
            })
            this.$router.push({
              path: '/orders/list',
            })
          } else {
            this.$message('fail')
          }
        })
        .catch(err => {
          if (err.response.status === 400) {
            this.$message('disconnect with server')
          }
          window.console.error(err)
        })
    }
  }
}
</script>

<style scoped>

</style>
