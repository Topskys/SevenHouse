<template>
  <div>
    <!-- 表格 -->
    <el-table :data="records" style="width:100%" border>
      <el-table-column type="index" label="序号" width="80" align="center"></el-table-column>
      <el-table-column prop="skuName" label="名称" width="100"></el-table-column>
      <el-table-column prop="skuDesc" label="描述"></el-table-column>
      <el-table-column label="默认图片" width="120" align="center">
        <template slot-scope="{ row, $index }">
          <img :src="row.skuDefaultImg" :alt="$index" style="width:80px;height:80px">
        </template>
      </el-table-column>
      <el-table-column prop="weight" label="重量" width="80"></el-table-column>
      <el-table-column prop="price" label="价格" width="80"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="{ row }">
          <el-button type="info" icon="el-icon-sort-down" size="mini" v-if="row.isSale==0" @click="pushSale(row)">
          </el-button>
          <el-button type="success" icon="el-icon-sort-up" size="mini" v-else @click="pullSale(row)"></el-button>
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(row)"></el-button>
          <el-button type="info" icon="el-icon-info" size="mini" @click="getSkuInfo(row)"></el-button>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="del(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination style="text-align: center" @size-change="handleSizeChange" @current-change="getSkuList"
      :current-page="page" :page-sizes="[3, 5, 10]" :page-size="limit" layout="prev, pager, next, jumper,->,sizes,total"
      :total="total">
    </el-pagination>
    <!-- 详情信息抽屉效果 -->
    <el-drawer :visible.sync="drawer" :direction="direction" :before-close="beforeClose" :show-close="true" size="50%">
      <el-row>
        <el-col :span="5">名称</el-col>
        <el-col :span="16">{{skuInfo.skuName}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="5">描述</el-col>
        <el-col :span="16">{{skuInfo.skuDesc}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="5">价格</el-col>
        <el-col :span="16">{{skuInfo.price}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="5">平台属性</el-col>
        <el-col :span="16">
          <template slot-scope="{ row}">
            <el-tag type="success" v-for="(attr) in row.skuAttrValueList" :key="attr.id" style="margin-right: 10px">
              {{attr.attrId}}-{{attr.attrValue}}
            </el-tag>
          </template>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="5">商品图片</el-col>
        <el-col :span="16">
          <el-carousel height="200px">
            <el-carousel-item v-for="item in skuInfo.skuImageList" :key="item.id">
              <img :src="item.imgUrl" alt="404" style="width:100%;background-size:cover;">
            </el-carousel-item>
          </el-carousel>
        </el-col>
      </el-row>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: 'sku',
  data() {
    return {
      page: 1,
      limit: 10,
      total: 0,// 分页器一共展示的数据
      records: [],// 存sku列表数据
      skuInfo: {},// 存储sku信息
      drawer: false,
      direction: 'rtl',
    }
  },
  mounted() {
    this.getSkuList()
  },
  methods: {
    // 获取sku列表
    async getSkuList(pages = 1) {
      this.page = pages
      const { page, limit } = this
      let res = await this.$API.sku.reqSkuList(page, limit)
      if (res.code == 200) {
        this.records = res.data.records
        this.total = res.data.total
      }
    },
    // page/size
    handleSizeChange(limit) {
      this.limit = limit
      this.getSkuList()
    },
    //  上架按钮的回调
    async pushSale(row) {
      let res = await this.$API.sku.reqPushSale(row.id)
      if (res.code == 200) {
        row.isSale = 1
        this.$message({ type: "success", message: "上架成功" })
      }
    },
    //  上架按钮的回调
    async pullSale(row) {
      let res = await this.$API.sku.reqPullSale(row.id)
      if (res.code == 200) {
        row.isSale = 0
        this.$message({ type: "success", message: "下架成功" })
      }
    },
    // 编辑按钮的回调
    edit(row) {
      window.alert("开发中")
     },
    // 详情按钮的回调
    async getSkuInfo(sku) {
      this.drawer = true
      let res = await this.$API.sku.reqSkuById(sku.id)
      if (res.code == 200) {
        this.skuInfo = res.data
      }
    },
    // 关闭drawer
    beforeClose() {
      this.drawer = false
    },
  }

}
</script>

<style scoped>
/* scoped：局部样式
1:scoped属性的作用-------加上scoped的作用是只是对于当前的组件有用（样式) 对于某一个组件，
如果style添加上scoped属性，给当前子组件的结构中都添加上一个data-v-xXxx自定属性会发现vue是通过属性选择器，
给需要添加的元素添加上样式 h3[data-v-7ba5bd90]{color:red;}
2:子组件的跟标签（拥有父组件当中自定义属性:一样的)，如果子组件的根节点，和父组件中书写的样式相同，也会添加上相应的样式。

3:注意，如果父组件加了scoped，还想影响子组件的样式，可以使用深度选择器。
深度选择器可实现样式穿透：
css：>>>
scss：::v-deep
less：/deep/

*/
.el-row .el-col-5 {
  font-size: 18px;
  text-align: right;
}

.el-col {
  margin-right: 10px;
}

/* >>>.el-carousel__button {
  width: 10px;
  height: 10px;
  background-color: red;
  border-radius: 50%;
} */
</style>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

/* .el-carousel__button解构复杂，不能放在scoped中（某些），要放在scoped需要使用深度选择器 */
.el-carousel__button{
  width:10px;
  height:10px;
  background-color: #fff;
  border-radius: 50%;
}
</style>