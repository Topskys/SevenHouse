<!--
 * @Author: Topskys
 * @Date: 2022-10-06 21:20:29
 * @LastEditTime: 2022-10-06 22:17:55
-->
<template>
  <el-card class="box-card">
    <div slot="header" class="header">
      <div class="search-header">
        <span>线上热门搜索</span>
        <el-dropdown>
          <span><i class="el-icon-more"></i></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>黄金糕</el-dropdown-item>
            <el-dropdown-item>狮子头</el-dropdown-item>
            <el-dropdown-item>螺蛳粉</el-dropdown-item>
            <el-dropdown-item>双皮奶</el-dropdown-item>
            <el-dropdown-item>蚵仔煎</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <div>
      <el-row :gutter="10">
        <el-col :span="12">
          <lineCharts></lineCharts>
        </el-col>
        <el-col :span="12">
          <lineCharts></lineCharts>
        </el-col>
      </el-row>
      <!-- 表格 :default-sort="{prop: 'date', order: 'descending'}":formatter="formatter" -->
      <el-table :data="tableData" style="width: 100%" border>
        <el-table-column prop="date" label="排名" type="index" width="80">
        </el-table-column>
        <el-table-column prop="name" label="搜索关键字" width="180">
        </el-table-column>
        <el-table-column prop="address" label="用户数" sortable>
        </el-table-column>
        <el-table-column prop="address" label="周涨幅" sortable>
        </el-table-column>
      </el-table>
      <el-pagination layout="prev, pager, next" :total="1000" class="pagination"></el-pagination>
    </div>
  </el-card>
</template>

<script>
import LineCharts from './lineCharts'
export default {
  data() { return { tableData: [{}] } },
  components: {
    LineCharts,
  }
}
</script>

<style scoped>
.search-header {
  display: flex;
  justify-content: space-between;
}

.header {
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}
.pagination{
  float:right;
}
</style>