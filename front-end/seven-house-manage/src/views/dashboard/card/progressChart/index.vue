<!--
 * @Author: Topskys
 * @Date: 2022-10-06 18:07:24
 * @LastEditTime: 2022-10-06 18:45:34
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
                // 设置x轴的最大最小值范围
                min: 0,
                max: 100,
            },
            yAxis: {
                show: false,
                // 均分，让柱状图横着排列
                type: "category",
            },
            series: [
                {
                    type: "bar",
                    data: [78],
                    color: "cyan",
                    // 柱状的宽度
                    barWidth: 10,
                    color: "yellowgreen",
                    // 设置背景颜色
                    showBackground: true,
                    backgroundStyle: {
                        color: "#eee",
                    },
                    // 文本
                    label: {
                        show: true,
                        // 修改文本内容
                        formatter: '|',
                        position: 'right',
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