<!--
 * @Author: Topskys
 * @Date: 2022-10-06 17:21:41
 * @LastEditTime: 2022-11-17 23:47:57
-->
<template>
  <div class="top__container">
    <el-row :gutter="10">
      <el-col :span="6">
        <el-card>
          <Detail title="总销售额" :count="`￥${saleTotal}`">
            <template slot="charts">
              <div style="flex: 1;"><span>周同比</span><span style="color:#13ae7c;margin-left:10px;">-{{ rate.week }}%</span>
              </div>
              <div style="flex: 1;"><span>日同比</span><span style="color:#f21e37;margin-left:10px;">+{{ rate.day }}%</span>
              </div>
            </template>
            <template slot="footer">
              <span class="footer__left">日销售额</span>
              <span class="footer__right">￥{{ saleDay ? saleDay : 0 }}</span>
            </template>
          </Detail>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <Detail title="月访问量" :count="visitor">
            <template slot="charts">
              <line-chart />
            </template>
            <template slot="footer">
              <span class="footer__left">日访问量</span>
              <span class="footer__right">68</span>
            </template>
          </Detail>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <Detail title="支付笔数" :count="payNum">
            <template slot="charts">
              <bar-chart />
            </template>
            <template slot="footer">
              <span class="footer__left">转化率</span>
              <span class="footer__right">8%</span>
            </template>
          </Detail>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <Detail title="运营活动效果" count="66%">
            <template slot="charts">
              <ProgressChart />
            </template>
            <template slot="footer">
              <div style="flex: 1;"><span>周同比</span><span style="color:#13ae7c;margin-left:10px;">-5%</span></div>
              <div style="flex: 1;"><span>日同比</span><span style="color:#f21e37;margin-left:10px;">+9%</span></div>
            </template>
          </Detail>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Detail from './detail/index.vue'
import LineChart from './lineChart/index.vue'
import BarChart from './barChart/index.vue'
import ProgressChart from './progressChart/index.vue'
export default {
  name: 'Card',
  components: {
    Detail,
    LineChart,
    BarChart,
    ProgressChart
  },
  data() {
    return {
      visitor: 0,
      payNum: 0,
      saleTotal: 0,
      saleDay: 0,
      rate: {
        week: 2,
        day: 6,
      }
    }
  },
  mounted() {
    this.getVisitor();
    this.getPayNumber();
    this.getSaleTotal();
    this.getWeekDayRate();
  },
  methods: {
    getVisitor() {
      this.$API.home.reqVisitor().then(({ code, data, msg }) => {
        code === 200 && (this.visitor = data.brose)
      });
    },
    getPayNumber() {
      this.$API.home.reqPayNum().then(({ code, data, msg }) => code === 200 && (this.payNum = data));
    },
    getSaleTotal() {
      this.$API.home.reqSaleTotal().then(({ code, data, msg }) => code === 200 && (this.saleTotal = data));
    },
    getSaleDay() {
      this.$API.home.reqSaleDay().then(({ code, data, msg }) => code === 200 && (this.saleDay = data));
    },
    getWeekDayRate() {
      this.$API.home.reqWeekDayRate().then(({ code, data, msg }) => {
        code === 200 && (this.rate.week = data.week, this.rate.day = data.day)
      });
    },
  }

}
</script>

<style lang="scss" scoped>
.top__container {
  margin-top: 10px;
}

::v-deep .el-card__body {
  padding: 0 20px !important;
}

.footer__left {
  margin-right: 10px;
  font-size: 12px;
}

.footer__right {
  font-size: 13px;
}
</style>
