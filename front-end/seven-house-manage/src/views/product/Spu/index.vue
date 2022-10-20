<template>
  <div class="SPU">
    <el-card style="margin: 20px 0px">
      <CategorySelect @getCategoryId="getCategoryId" :show="scene!=0" />
    </el-card>
    <el-card>
      <div v-show="scene == 0">
        <!-- 展示SPU列表 -->
        <el-button type="primary" icon="el-icon-plus" :disabled="!category3Id" @click="addSPU">添加SPU</el-button>
        <!-- 展示平台属性表格 -->
        <el-table :data="SPUList" style="width: 100%; margin: 20px 0" border>
          <el-table-column type="index" label="序号" width="80" align="center">
          </el-table-column>
          <el-table-column prop="spuName" label="SPU名称" width="width">
          </el-table-column>
          <el-table-column prop="description" label="SPU描述" width="width">
          </el-table-column>
          <el-table-column label="操作" width="width" align="center">
            <template slot-scope="{ row, $index}">
              <hint-button type="success" icon="el-icon-plus" size="mini" title="添加SKU" @click="addSKU(row)"></hint-button>
              <hint-button type="warning" icon="el-icon-edit" size="mini" @click="updateSPU(row)" title="修改SPU">
              </hint-button>
              <hint-button type="info" icon="el-icon-info" size="mini" title="查看当前SPU全部sku列表" @click="lookAllSku(row)"></hint-button>
              <el-popconfirm :title="`确定删除第${$index+1}条数据？`" @onConfirm="deleteSpu(row,$index)">
                <hint-button slot="reference" type="danger" icon="el-icon-delete" size="mini" title="删除SPU">
                </hint-button>
              </el-popconfirm>

            </template>
          </el-table-column>
        </el-table>
        <el-pagination style="text-align: center" @size-change="handleSizeChange" @current-change="getSPUList"
          :current-page="page" :page-sizes="[3, 5, 10]" :page-size="limit"
          layout="prev, pager, next, jumper,->,sizes,total" :total="total">
        </el-pagination>
      </div>
      <div>
        <SpuForm v-show="scene==1" @changeScene="changeScene" ref="SpuForm" />
        <SkuForm v-show="scene==2" @changeSceneFromSku="changeSceneFromSku" ref="SkuForm"/>
      </div>
    </el-card>
    <!-- 查看当前SPU全部sku列表dialog -->
    <el-dialog :title="`${spu.spuName}`" :visible.sync="dialogTableVisible" :before-close="beforeClose">
      <el-table :data="skuList" v-loading="loading" style="width:100%" border>
        <el-table-column prop="skuName" label="名称"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="weight" label="重量"></el-table-column>
        <el-table-column label="默认图片">
          <template slot-scope="{ row, $index }">
            <img :src="row.skuDefaultImg" :alt="$index" style="width:100px;width: 100px">
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import SkuForm from './SkuForm'
import SpuForm from './SpuForm'

export default {
  name: "SPU",
  data() {
    return {
      category1Id: "",
      category2Id: "",
      category3Id: "",
      // 控制三级联动的可用性，已使用scene!=0代替
      // show: true, 
      // 控制表格的显示
      isShowTable: true,
      page: 1,
      limit: 3,
      // SPU数据列表
      SPUList: [],
      total: 0,
      // 标记切换的场景
      scene: 0,
      // 控制查看spu的sku列表dialog
      dialogTableVisible:false,
      spu:{},
      skuList:[],
      // 加载sku列表动画
      loading: true,
    };
  },
  components: {
    SpuForm,
    SkuForm,
  },
  methods: {
    // 自定义子传父回调事件
    getCategoryId({ categoryId, level }) {
      // 区分几级的id并存储
      if (level == 1) {
        this.category1Id = categoryId;
        this.category2Id = "";
        this.category3Id = "";
      } else if (level == 2) {
        this.category2Id = categoryId;
        this.category3Id = "";
      } else {
        this.category3Id = categoryId;
        // 获取SPU类列表数据
        this.getSPUList();
      }
      // console.log("cateId:",categoryId,"level:",level)
    },
    // 点击分页按钮的回调获取SPU数据列表
    async getSPUList(defPage = 1) {
      this.page = defPage;
      // 解构 对象的属性
      const { page, limit, category3Id } = this;
      let res = await this.$API.spu.reqSPUList(page, limit, category3Id);
      if (res.code == 200) {
        this.total = res.data.total;
        this.SPUList = res.data.records;
      }
    },
    // 检测3/page
    handleSizeChange(limit) {
      this.limit = limit;
      this.getSPUList();
    },
    // 添加SPU按钮事件
    addSPU() {
      this.scene = 1
      // 通知子组件请求品牌数组和销售属性数组
      this.$refs.SpuForm.addSpuData(this.category3Id)
    },
    // 修改某个SPU
    updateSPU(row) {
      this.scene = 1
      // 获取子组件元素，就可以间接得到子组件的方法
      this.$refs.SpuForm.initSpuData(row)
    },
    // 自定义事件回调 子传父(SpuForm)
    changeScene({ scene, flag }) {
      this.scene = scene // 切换场景
      // 编辑数据，重新获取数据，并决定是否停留在编辑数据的当前页
      if (flag == "修改") {
        this.getSPUList(this.page)
      } else {
        this.getSPUList()
      }
    },
    // 删除SPU按钮的回调
    async deleteSpu(row, index) {
      let { code } = await this.$API.spu.reqDeleteSpu(row.id)
      if (code == 200) {
        this.$message({ type: "success", message: "删除成功" })
        // SPU列表数据个数>1留在当前页，否则显示上一页
        this.getSPUList(this.SPUList.length > 1 ? this.page : this.page - 1)
      }
    },
    // 添加SKU按钮的回调
    addSKU(row){
      this.scene=2
      // 通知子组件请求数据
      this.$refs.SkuForm.initSkuData(this.category1Id,this.category2Id,row)
    },
    // 切换场景(SkuForm)
    changeSceneFromSku(scene) {
      this.scene=scene
    },
    // 查看当前SPU全部sku列表
    async lookAllSku(row){
      this.dialogTableVisible=true
      this.spu=row
      let res=await this.$API.spu.reqSkuList(row.id)
      if(res.code == 200){
        this.skuList=res.data
        this.loading=false
      }
    },
    // 关闭dialog前的回调
    beforeClose(done){
      this.loading=true
      this.skuList=[]
      done()// 执行一次
    },


  },
};
</script>

<style>

</style>