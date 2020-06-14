<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item :label="form.name" class="mc-ui-flex-center" style="word-spacing: 0px;">
        <el-avatar shape="square" :size="100" fit="fit" />
      </el-form-item>
      <el-form-item label="Name">
        <el-tag>first name</el-tag>
        <el-input v-model="form.firstname" style="width: 20%;" placeholder="请输入内容" />
        <el-tag>last name</el-tag>
        <el-input v-model="form.lastname" style="width: 20%;" placeholder="请输入内容" />
        <el-divider />
      </el-form-item>
      <el-form-item label="Address">
        <el-tag>country</el-tag>
        <el-select v-model="form.country" @focus="focus">
          <el-option label="CHINA" value="CHINA" />
          <el-option label="USA" value="USA" />
          <el-option label="SPANISH" value="SPANISH" />
        </el-select>
        <el-tag>state</el-tag>
        <el-select v-model="form.state" @focus="focus">
          <el-option label="HUNAN" value="HUNAN" />
          <el-option label="CHONGQING" value="CHONGQING" />
          <el-option label="SHANGHAI" value="SHANGHAI" />
        </el-select>
        <el-tag>city</el-tag>
        <el-select v-model="form.city" @focus="focus">
          <el-option label="CHANGSHA" value="CHANGSHA" />
          <el-option label="CHANGSHA" value="CHANGSHA" />
          <el-option label="CHANGSHA" value="CHANGSHA" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-tag>address1</el-tag>
        <el-input v-model="form.address1" placeholder="请输入内容" />
        <el-tag>address2</el-tag>
        <el-input v-model="form.address2" placeholder="请输入内容" />
        <el-divider />
      </el-form-item>
      <el-form-item label="Phone Number">
        <el-tag>region</el-tag>
        <el-select @focus="focus">
          <el-option label="+86" value="+86" />
          <el-option label="+86" value="+86" />
          <el-option label="+86" value="+86" />
        </el-select>
        <el-input v-model="form.phone" style="width: 36%;" placeholder="请输入内容" @blur="check_phone" />
        <el-divider />
      </el-form-item>
      <el-form-item label="Password">
        <md-input />
        <el-input v-model="form.password" style="width: 20%;" placeholder="请输入密码" />
        <el-divider />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Submit</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: this.$route.query.param,
        firstname: '',
        lastname: '',
        country: '',
        state: '',
        city: '',
        address1: '',
        address2: '',
        phone: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit() {
      const account = {
        username: this.form.id,
        password: this.form.password,
        // email: this.form.email,
        firstName: this.form.firstname,
        lastName: this.form.lastname,
        addr1: this.form.address1,
        addr2: this.form.address2,
        city: this.form.city,
        state: this.form.state,
        // zip: this.form.zip,
        country: this.form.country,
        phone: this.form.phone
        // favouriteCategoryId: this.form.favouriteCategory,
        // languagePreference: this.form.languagePreference,
        // listOption: this.form.list,
        // bannerOption: this.form.banner,
        // bannerName: this.form.banner
      }
      this.axios.put('/user/detailinfo', account)
        .then(res => {
          if (res.data.status) {
            this.$message.success('更新成功')
          } else {
            this.$message('请先登入')
          }
        }).catch(err => {
        // eslint-disable-next-line eqeqeq
          if (err.response.status == 400) {
            this.$message('请先登入')
          // 跳转到登录页
          }
          window.console.error(err)
        })
      this.$message('submit!')
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    created() {
      this.axios.get('/user/detailinfo', { params: {
        id: this.$route.query.param
      }})
        .then(res => {
          const account = res.data.data.account
          this.form.name = account.username
          this.form.firstname = account.firstName
          this.form.lastname = account.lastName
          // this.form.phone = account.phone
          this.form.address1 = account.addr1
          this.form.address2 = account.addr2
          this.form.country = account.country
          this.form.city = account.city
          this.form.state = account.state
          // this.form.zip = account.zip
          // this.form.languagePreference = account.languagePreference
          // this.form.favouriteCategory = account.favouriteCategoryId
          // this.form.list = account.listOption
          // this.form.banner = account.bannerOption
        })
        // eslint-disable-next-line handle-callback-err
        .catch(err => {
          this.$message('请先登入')
        })
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

