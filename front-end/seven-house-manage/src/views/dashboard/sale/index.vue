<!--
 * @Author: Topskys
 * @Date: 2022-10-06 19:44:24
 * @LastEditTime: 2022-11-17 23:41:43
-->
<template>
    <div>
        <el-card class="box-card" style="margin:10px 0;">
            <div slot="header" class="clearfix">
                <el-tabs class="tabs" v-model="activeName">
                    <el-tab-pane label="销售额" name="sale"></el-tab-pane>
                    <el-tab-pane label="支付笔数" name="access"></el-tab-pane>
                </el-tabs>
                <div class="right">
                    <span @click="setDay">今日</span>
                    <span @click="setWeek">本周</span>
                    <span @click="setMonth">本月</span>
                    <span @click="setYear">本年</span>
                    <el-date-picker class="date" v-model="date" value-format="yyyy-MM-dd" type="datetimerange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" size="mini">
                    </el-date-picker>
                </div>

            </div>
            <el-row :gutter="10">
                <el-col :span="18">
                    <div class="charts" ref="charts"></div>
                </el-col>
                <el-col :span="6" class="middle-right">
                    <h3>热销榜</h3>
                    <ul>
                        <li v-for="item, index in hotSale" :key="index">
                            <span>{{ index + 1 }}</span><span>{{ item.alco_name }} </span>
                        </li>
                    </ul>
                </el-col>
            </el-row>
        </el-card>
    </div>
</template>

<script>
import * as echarts from 'echarts';
// 也可用moment
import dayjs from 'dayjs';
import { mapState } from 'vuex';
import resize from '@/layout/mixin/ResizeHandler.js';
export default {
    name: "Sale",
    data() {
        return {
            activeName: "sale",
            date: "",
            chart: null,
            hotSale: [],
            monthSale: {
                time: [],
                count: [],
            },
        }
    },
    computed: {
        title() {
            return this.activeName == "sale" ? '销售额' : '支付笔数'
        },
        ...mapState({
            list: (state) => state.home.list
        })
    },
    mounted() {
        // mounted(){}只执行一次，没有数据，所以监听list
        this.getHotSale();
        this.getMonthSale();
        this.initEChart();
    },
    methods: {
        getHotSale() {
            this.$API.home.reqHotSale().then(({ code, data, msg }) => code === 200 && (this.hotSale = data || []));
        },
        getMonthSale() {
            this.$API.home.reqMonthSale().then(({ code, data, msg }) => {
                if (code === 200) {
                    let month = [], count = [];
                    data.forEach(x => {
                        month.push(x.time)
                        count.push(x.count)
                    });
                    this.monthSale.time = month;
                    this.monthSale.count = count;
                    console.log(month, count, this.monthSale)
                }
            });
        },
        initEChart() {
            this.chart = echarts.init(this.$refs.charts)
            this.chart.setOption(
                {
                    title: {
                        text: this.title + '趋势',
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            // data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                            data: [],
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: 'Direct',
                            type: 'bar',
                            barWidth: '60%',
                            // data: [10, 52, 200, 334, 390, 330, 220, 100, 190, 124, 33, 134]
                            data: [],
                        }
                    ]
                }
            )
            window.addEventListener("resize", () => {
                this.chart.resize();
            });
        },
        setDay() {
            const day = dayjs().format('YYYY-MM-DD')
            this.date = [day, day]
        },
        setWeek() {
            const start = dayjs().day(1).format('YYYY-MM-DD')
            const end = dayjs().day(7).format('YYYY-MM-DD')
            this.date = [start, end]
        },
        setMonth() {
            const start = dayjs().startOf('month').format('YYYY-MM-DD')
            const end = dayjs().endOf('month').format('YYYY-MM-DD')
            this.date = [start, end]
        },
        setYear() {
            const start = dayjs().startOf('year').format('YYYY-MM-DD')
            const end = dayjs().endOf('year').format('YYYY-MM-DD')
            this.date = [start, end]
        },

    },
    watch: {
        // 监听属性
        title() {
            // 重新修改图表的部分配置数据
            this.chart.setOption({
                title: {
                    text: this.title + '趋势'
                },
                xAxis: { data: this.title === "销售额" ? this.monthSale.time : this.list.userFullYearAxis },
                series: [
                    {
                        name: 'Direct',
                        type: 'bar',
                        barWidth: '60%',
                        data: this.title === "销售额" ? this.monthSale.count : this.list.userFullYear,
                        color: this.title === "销售额" ? '' : "#845EC2"
                    }
                ]
            })
        },
        // 监听list，list: (state) => state.home.list
        'monthSale.count':function() {
            this.chart.setOption(
                {
                    title: {
                        text: this.title + '趋势',
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            // data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                            data: this.monthSale.time,
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: 'Direct',
                            type: 'bar',
                            barWidth: '60%',
                            // data: [10, 52, 200, 334, 390, 330, 220, 100, 190, 124, 33, 134]
                            data: this.monthSale.count
                        }
                    ]
                }
            )
        },
    }
}
</script>

<style lang="scss" scoped>
.clearfix {
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
}

.tabs {
    width: 100%;
}

.right {
    position: absolute;
    top: 0px;
    right: 0px;

    span {
        margin: 0 10px;
    }
}

.date {
    width: 250px;
    margin: 0 10px;
}

.charts {
    width: 100%;
    // height: 300px;
    height: calc(100vh - 450px);
}

.middle-right {
    padding: 0;
    margin: 0,
}

h3 {
    margin-top: 3px;
}

ul {
    list-style: none;
    padding: 0;
    width: 100%;
    // height: 300px;
    // height: 48vh;
    height: calc(100vh - 450px);

    li {
        height: 8%;
        margin: 10px 0;
        padding: 0 10px;

        span:nth-child(1) {
            margin-right: 15px;
        }

        .l-index {
            float: left;
            width: 20px;
            height: 20px;
            margin-right: 10px;
            border-radius: 50%;
            background-color: #000;
            color: white;
            text-align: center;
        }

        .r-value {
            float: right;
        }
    }
}

.bg1,
.bg2,
.bg3 {
    color: #fff;
}

.bg1 {
    background-color: #F56C6C;
}

.bg2 {
    background-color: #E6A23C;
}

.bg3 {
    background-color: #67C23A;
}
</style>