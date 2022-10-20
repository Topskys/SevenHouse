<!--
 * @Author: Topskys
 * @Date: 2022-10-06 17:21:41
 * @LastEditTime: 2022-10-06 18:44:41
-->
<template>
    <div>
        <el-row :gutter="10">
            <el-col :span="6">
                <el-card>
                    <Detail title="总销售额" count="￥ 126560">
                        <template slot="charts">
                            <span>周同比&nbsp;&nbsp;56%</span><svg t="1665049776270" class="icon" viewBox="0 0 1024 1024"
                                version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5146" width="16" height="16">
                                <path
                                    d="M698.7 480.9v429.5c0 54.8-44.5 99.3-99.3 99.3H434c-54.8 0-99.3-44.5-99.3-99.3V480.9H70l446.9-463 446.9 463H698.7z m0 0"
                                    fill="#1afa29" p-id="5147"></path>
                            </svg>
                            &nbsp;&nbsp;
                            <span>日同比&nbsp;&nbsp;19%</span><svg t="1665049804832" class="icon" viewBox="0 0 1024 1024"
                                version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6288" width="16" height="16">
                                <path
                                    d="M258.048 568.832l253.952 279.04 253.952-279.04c22.016-24.064 13.312-43.52-18.944-43.52H629.76v-322.56c0-32.768-26.112-58.88-58.88-58.88h-117.76c-32.256 0-58.368 26.112-58.88 58.368v323.072H276.992c-31.744 0-40.448 19.456-18.944 43.52z"
                                    fill="#d81e06" p-id="6289"></path>
                            </svg>
                        </template>
                        <template slot="footer">
                            <span>日同比销售额 ￥&nbsp;&nbsp;12423</span>
                        </template>
                    </Detail>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <Detail title="月访问量" count="88460">
                        <template slot="charts">
                            <line-chart></line-chart>
                        </template>
                        <template slot="footer">
                            <span>日访问量&nbsp;&nbsp;7346</span>
                        </template>
                    </Detail>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <Detail title="支付笔数" count="84532">
                        <template slot="charts">
                            <bar-chart></bar-chart>
                        </template>
                        <template slot="footer">
                            <span>转化率&nbsp;&nbsp;65%</span>
                        </template>
                    </Detail>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <Detail title="运营活动效果" count="78%">
                        <template slot="charts">
                            <ProgressChart></ProgressChart>
                        </template>
                        <template slot="footer">
                            <span>周同比&nbsp;&nbsp;12%</span><svg t="1665049776270" class="icon" viewBox="0 0 1024 1024"
                                version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5146" width="16" height="16">
                                <path
                                    d="M698.7 480.9v429.5c0 54.8-44.5 99.3-99.3 99.3H434c-54.8 0-99.3-44.5-99.3-99.3V480.9H70l446.9-463 446.9 463H698.7z m0 0"
                                    fill="#1afa29" p-id="5147"></path>
                            </svg>
                            &nbsp;&nbsp;
                            <span>日同比&nbsp;&nbsp;8%</span><svg t="1665049804832" class="icon" viewBox="0 0 1024 1024"
                                version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6288" width="16" height="16">
                                <path
                                    d="M258.048 568.832l253.952 279.04 253.952-279.04c22.016-24.064 13.312-43.52-18.944-43.52H629.76v-322.56c0-32.768-26.112-58.88-58.88-58.88h-117.76c-32.256 0-58.368 26.112-58.88 58.368v323.072H276.992c-31.744 0-40.448 19.456-18.944 43.52z"
                                    fill="#d81e06" p-id="6289"></path>
                            </svg>
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
    name: "Card",
    data() {
        return {}
    },
    components: {
        Detail,
        LineChart,
        BarChart,
        ProgressChart,
    },
    methods: {},

}
</script>

<style>

</style>