<!--
 * @Author: Topskys
 * @Date: 2022-10-06 21:35:46
 * @LastEditTime: 2022-10-07 10:24:38
-->
<template>
    <div>
        <div class="header">
            <span class="search-user">搜索用户数</span>
            <svg t="1665048739438" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="2554" width="24" height="24">
                <path
                    d="M512 958.016C266.08 958.016 65.984 757.952 65.984 512 65.984 266.08 266.08 65.984 512 65.984c245.952 0 446.016 200.064 446.016 446.016C958.016 757.952 757.952 958.016 512 958.016zM512 129.984C301.344 129.984 129.984 301.344 129.984 512c0 210.624 171.36 382.016 382.016 382.016 210.624 0 382.016-171.36 382.016-382.016C894.016 301.344 722.624 129.984 512 129.984z"
                    p-id="2555"></path>
                <path d="M512 304m-48 0a1.5 1.5 0 1 0 96 0 1.5 1.5 0 1 0-96 0Z" p-id="2556"></path>
                <path
                    d="M512 768c-17.664 0-32-14.304-32-32l0-288c0-17.664 14.336-32 32-32s32 14.336 32 32l0 288C544 753.696 529.664 768 512 768z"
                    p-id="2557"></path>
            </svg>
        </div>
        <div class="main">
            <span class="main-title">12321</span>
            <span class="main-content">17.1</span>
            <svg t="1665049776270" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="5146" width="16" height="16">
                <path
                    d="M698.7 480.9v429.5c0 54.8-44.5 99.3-99.3 99.3H434c-54.8 0-99.3-44.5-99.3-99.3V480.9H70l446.9-463 446.9 463H698.7z m0 0"
                    fill="#1afa29" p-id="5147"></path>
            </svg>
            <svg t="1665049804832" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="6288" width="16" height="16">
                <path
                    d="M258.048 568.832l253.952 279.04 253.952-279.04c22.016-24.064 13.312-43.52-18.944-43.52H629.76v-322.56c0-32.768-26.112-58.88-58.88-58.88h-117.76c-32.256 0-58.368 26.112-58.88 58.368v323.072H276.992c-31.744 0-40.448 19.456-18.944 43.52z"
                    fill="#d81e06" p-id="6289"></path>
            </svg>
        </div>
        <div class="footer">
            <div class="charts" ref="charts"></div>
        </div>
    </div>
</template>

<script>
import * as echarts from "echarts"
export default {
    mounted() {
        let chart = echarts.init(this.$refs.charts)
        chart.setOption({
            xAxis: {
                show: false,
                type: "category",
            },
            yAxis: { show: false, },
            series: [
                {
                    type: "line",
                    data: [78, 50, 45, 38, 89, 67, 90],
                    // 拐点有样式设置
                    itemStyle: { opacity: 0 },
                    // 线条样式
                    lineStyle: {
                        color: "#335eea"
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
                                    color: "blue"
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

<style lang="scss" scoped>
.header {
    display: flex;
    align-items: center;
    color: #999;
}

.search-user {
    margin-right: 20px;
}

.main {
    margin: 10px 0;

    .main-title {
        margin-right: 30px;
    }

    .main-content {
        margin-right: 10px;
    }
}

.charts {
    width: 100%;
    height: 50px;
}
</style>