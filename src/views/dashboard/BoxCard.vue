<template>
  <el-card class="box-card-component" style="margin-left:8px;">
    <div slot="header" class="box-card-header">
      <img src="@/icons/svg/painting.jpg">
    </div>
    <div style="position:relative;">
<!--      <pan-thumb :image="avatar" class="panThumb" />-->
      <mallki class-name="mallki-text" text='Admin' />
      <div style="padding-top:35px;width: 50%" class="progress-item">
        <span>Server ID</span>
        <el-divider ></el-divider>
        <span>{{status.id}}</span>
        <el-tag :type="status.status | statusFilter">{{ status.status }}</el-tag>
      </div>
      <el-divider></el-divider>
      <div style="padding-top:35px;" class="progress-item">
        <span>CPU</span>
        <el-divider></el-divider>
        <el-progress :percentage=status.cpu />
      </div>
      <el-divider></el-divider>
      <div class="progress-item" style="width: 50%">
        <span>InternetRX</span>
        <el-divider></el-divider>
        <count-to :start-val="0" :end-val=status.receive :duration="2" class="card-panel-num" />
      </div>
      <el-divider></el-divider>
      <div class="progress-item" style="width: 50%">
        <span>InternetTX</span>
        <el-divider></el-divider>
        <count-to :start-val="0" :end-val=status.send :duration="2" class="card-panel-num" />
      </div>
      <el-divider></el-divider>
      <div class="progress-item" style="width: 50%">
        <span>Time</span>
        <el-divider></el-divider>
        <div class ="time" style="font-size: medium;font-style: unset">{{date}}</div>
      </div>
    </div>
  </el-card>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import Mallki from '@/components/TextHoverEffect/Mallki'
import CountTo from 'vue-count-to'


export default {
  components: { PanThumb, Mallki ,CountTo},

  filters: {
    statusFilter(status) {
      const statusMap = {
        Running: 'success',
        Stop: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      statisticsData: {
        article_count: 1024,
        pageviews_count: 1024
      },
      date: new Date(),
      status: {
        receive: 0,
        cpu: 5,
        id: "i-2ze2etqbqencg0qw12x8",
        send: 0,
        status: "Running"
      }
    }
  },
  mounted(){
    let _this = this;
    this.timer = setInterval(function(){
      _this.date = new Date().toLocaleString();
    });
    this.timer = setInterval(this.getStatus, 30000);
  },
  beforeDestroy:function(){
    if(this.timer){
      clearInterval(this.timer);
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ])
  },
  methods:{
    getStatus() {
      this.axios.get('/server/status')
        .then(response => {
          this.status = response.data.data;
        })
    }
  }
}
</script>

<style lang="scss" >
.box-card-component{
  .el-card__header {
    padding: 0px!important;
  }
}
</style>
<style lang="scss" scoped>
.box-card-component {
  .box-card-header {
    position: relative;
    height: 220px;
    img {
      width: 100%;
      height: 100%;
      transition: all 0.2s linear;
      &:hover {
        transform: scale(1.1, 1.1);
        filter: contrast(130%);
      }
    }
  }
  .mallki-text {
    position: absolute;
    top: 0px;
    right: 0px;
    font-size: 20px;
    font-weight: bold;
  }
  .panThumb {
    z-index: 100;
    height: 70px!important;
    width: 70px!important;
    position: absolute!important;
    top: -45px;
    left: 0px;
    border: 5px solid #ffffff;
    background-color: #fff;
    margin: auto;
    box-shadow: none!important;
    /deep/ .pan-info {
      box-shadow: none!important;
    }
  }
  .progress-item {
    margin-bottom: 10px;
    font-size: 14px;
  }
  @media only screen and (max-width: 1510px){
    .mallki-text{
      display: none;
    }
  }
}
</style>
