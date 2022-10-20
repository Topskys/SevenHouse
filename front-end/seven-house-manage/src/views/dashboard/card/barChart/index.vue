<!--
 * @Author: Topskys
 * @Date: 2022-10-06 18:07:24
 * @LastEditTime: 2022-10-06 18:31:17
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
                    type: "bar",
                    data: [78, 50, 45, 38, 12, 67, 90],
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