<!--
 * @Author: Topskys
 * @Date: 2022-10-06 21:20:29
 * @LastEditTime: 2022-10-06 22:56:13
-->
<template>
  <el-card class="box-card">
    <div slot="header" class="header">
      <div class="category-header">
        <span>销售额类别占比</span>
        <el-radio-group v-model="radio" size="mini">
          <el-radio-button label="全部渠道"></el-radio-button>
          <el-radio-button label="线上"></el-radio-button>
          <el-radio-button label="门店"></el-radio-button>
        </el-radio-group>
      </div>
    </div>
    <div>
      <div class="charts" ref="charts"></div>
    </div>
  </el-card>
</template>

<script>
import * as echarts from 'echarts'
export default {
  data() {
    return {
      radio: "全部渠道",
    }
  },
  mounted() {

    let chart = echarts.init(this.$refs.charts)
    chart.setOption({
      title: {
        text: "视频",
        subtext: 1048,
        top: 'center',
        left: 'center',
      },
      tooltip: {
        trigger: 'item'
      },
      // legend: {
      //   top: '5%',
      //   left: 'center'
      // },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: true,
            position: 'outside',// 把提示文本放到外边显示
          },
          labelLine: {
            show: true,
          },
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ]
        }
      ]
    })
    //  绑定事件
    chart.on('mouseover', (params) => {
      // 获取鼠标移上去的那条数据
      const { name, value } = params
      // 重新设置标题
      chart.setOption({
        title: {
          text: name,
          subtext: 'value',
        }
      })
    })
  },
}
</script>

<style scoped>
.category-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header {
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.charts {
  width: 100%;
  height: 280px;
}
</style>