<!--
 * @Author: Topskys
 * @Date: 2022-10-06 18:07:24
 * @LastEditTime: 2022-10-06 18:45:48
-->
<template>
    <div class="chart" ref="chart"></div>
</template>

<script>
import * as echarts from "echarts"
export default {
    mounted() {
        let chart = echarts.init(this.$refs.chart)
        chart.setOption({
            xAxis: {
                show: false,
                type: "category",
            },
            yAxis: { show: false, },
            series: [
                {
                    type: "line",
                    data: [78, 50, 45, 38, 12, 67, 90],
                    // 拐点有样式设置
                    itemStyle: { opacity: 0 },
                    // 线条样式
                    lineStyle: {
                        color: "purple"
                    },
                    // 颜色渐变设置
                    areaStyle: {
                        color: {
                            type: "linear",
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [
                                {
                                    offset: 0,
                                    color: "purple"
                                },
                                {
                                    offset: 1,
                                    color: "#fff"
                                }
                            ],
                            global: false,// 缺省为 false
                        }
                    }
                }
            ],
            grid: {
                top: 0,
                left: 0,
                right: 0,
                bottom: 0,
            },
            tooltip: {},
        })
    }
}
</script>

<style scoped>
.chart {
    width: 100%;
    height: 100%;
}
</style>