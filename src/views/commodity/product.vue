<template>
  <div>
    <div id="telten">
      <h1 class="playful" aria-label="Wash your hands">
        <span aria-hidden="true">品</span><span aria-hidden="true">种</span><span aria-hidden="true">大</span><span aria-hidden="true">类</span><span aria-hidden="true">表</span><span aria-hidden="true">~</span>
      </h1>
    </div>
    <Chart id="chart"></Chart>
    <div>
      <el-button @click="save" id="save">保存</el-button>
    </div>
  </div>
</template>
<script>
import Chart from '../../components/commodity/productTable'
import store from '@/store'

export default {
  name: 'Product',
  components: {
    // eslint-disable-next-line vue/no-unused-components
    Chart
  },
  methods: {
    hhh() {
      console.log(store.state.category.tableData)
    }
  }
}
</script>

<style scoped>
  #telten{
    margin-top: -1%;
    margin-left: 25%;
  }
  #chart{
    margin-left: 22%;
  }
  #save{
    margin-top: 3%;
    margin-left: 46.5%;
  }

  /*下面的用来实现标题特效*/
  .playful span {
    position: relative;
    color: #5362F6;
    text-shadow: textShadow(0.25, 6, #E485F8);
    animation: scatter 1.75s infinite;
  }

  .playful span:nth-child(2n) {
    color: #ED625C;
    text-shadow: textShadow(0.25, 6, #F2A063);
    animation-delay: 0.3s;
  }

  .playful span:nth-child(3n) {
    color: #FFD913;
    text-shadow: textShadow(0.25, 6, #6EC0A9);
    animation-delay: 0.15s;
  }

  .playful span:nth-child(5n) {
    color: #555BFF;
    text-shadow: textShadow(0.25, 6, #E485F8);
    animation-delay: 0.4s;
  }

  .playful span:nth-child(7n), .playful span:nth-child(11n) {
    color: #FF9C55;
    text-shadow: textShadow(0.25, 6, #FF5555);
    animation-delay: 0.25s;
  }

  @keyframes scatter {
    0% {
      top: 0;
    }
    50% {
      top: -10px;
    }
    100% {
      top: 0;
    }
  }

  h1 {
    font-size: 60px;
    text-transform: uppercase;
    font-family: "Archivo Black", "Archivo", sans-serif;
    font-weight: normal;
    display: block;
    width: 666px;
    max-width: 80vw;
    min-height: 90px;
    height: auto;
    text-align: center;
  }
</style>
