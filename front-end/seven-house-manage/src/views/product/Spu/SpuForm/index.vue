<template>
  <div>
    <el-form ref="form" :model="spu" label-width="80px">
      <el-form-item label="SPU名称">
        <el-input v-model="spu.spuName" placeholder="SPU名称"></el-input>
      </el-form-item>
      <el-form-item label="品牌">
        <el-select v-model="spu.tmId" placeholder="选择品牌">
          <el-option :label="tm.tmName" :value="tm.id" v-for="(tm, index) in tradeMarkList" :key="tm.id || index">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="SPU描述">
        <!-- rows变高 -->
        <el-input v-model="spu.description" type="textarea" rows="4" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item label="SPU图片">
        <!-- 照片墙，可以上传多张图片 -->
        <!-- action 图片上传地址 -->
        <el-upload action="/dev-api/admin/product/fileUpload" list-type="picture-card"
          :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :file-list="spuImageList"
          :on-success="handleSuccess">
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="404" />
        </el-dialog>
      </el-form-item>
      <el-form-item label="销售属性">
        <el-select :placeholder="`还有${unSelectSaleAttr.length}未选择`" v-model="attrIdAndAttrName">
          <el-option :label="unselect.name" :value="`${unselect.id}:${unselect.name}`"
            v-for="unselect,index in unSelectSaleAttr" :key="unselect.id||index"> </el-option>
        </el-select>
        <el-button type="primary" icon="el-icon-plus" :disabled="!attrIdAndAttrName" @click="addSaleAttr">添加销售属性
        </el-button>
        <el-table :data="spu.spuSaleAttrList" style="width: 100%; margin: 20px 0px" border>
          <el-table-column type="index" label="序号" width="80px" align="center">
          </el-table-column>
          <el-table-column prop="saleAttrName" label="属性名" width="width">
          </el-table-column>
          <el-table-column prop="prop" label="属性名称列表" width="width">
            <template slot-scope="{ row}">
              <!-- @close="handleClose(tag)" -->
              <el-tag :key="tag.id" v-for="(tag,index) in row.spuSaleAttrValueList" closable
                :disable-transitions="false" @close="row.spuSaleAttrValueList.splice(index,1)"> {{
                tag.saleAttrValueName }}</el-tag>
              <!-- @keyup.enter.native="handleInputConfirm" -->
              <!-- @blur="handleInputConfirm" -->
              <el-input class="input-new-tag" v-if="row.inputVisible" v-model="row.inputValue" ref="saveTagInput"
                size="small" @blur="handleInputConfirm(row)">
              </el-input>
              <!-- @click="showInput" -->
              <el-button v-else class="button-new-tag" size="small" @click="addSaleAttrValue(row)">添加</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="prop" label="操作" width="width">
            <template slot-scope="{ $index }">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="spu.spuSaleAttrList.splice($index,1)">
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addOrUpdateSpu">保存</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "SpuForm",
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      //存储服务器spu 和 收集表单SPU信息发给服务器
      spu: {
        category3Id: 0,// 三级分类的id
        description: "",// 描述
        tmId: "",// 品牌的id
        spuName: "",
        // 收集spu图片信息
        spuImageList: [
          {
            // id: 0,
            // imgName: "",
            // imgUrl: "",
            // spuId: 0,
          },
        ],
        // 平台属性与属性值得收集
        spuSaleAttrList: [
          //   {
          //     baseSaleAttrId: 6,
          //     id: 0,
          //     saleAttrName: "string",
          //     spuId: 0,
          //     spuSaleAttrValueList: [
          //       {
          //         baseSaleAttrId: 0,
          //         id: 0,
          //         isChecked: "string",
          //         saleAttrName: "string",
          //         saleAttrValueName: "string",
          //         spuId: 0,
          //       },
          //     ],
          //   },
        ],
      },
      tradeMarkList: [],
      // 存储spu图片信息
      spuImageList: [],
      saleAttrList: [],
      attrIdAndAttrName: '',// 未选中的属性id和AttrName//attrIdAndAttrName
    };
  },
  methods: {

    handlePictureCardPreview(file) {
      // 将图片地址赋值给dialogImageUrl
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleRemove(file, fileList) {
      // file删除的那张图，剩下的照片墙fileList
      // console.log(file, fileList);
      // 含name和url等服务器不需要的字段
      this.spuImageList = fileList;
    },
    handleSuccess(response, file, fileList) {
      // 收集刚上传的图片
      this.spuImageList = fileList;
    },

    // 初始化SpuForm数据以备父组件调用，由于使用了v-show，mounted(){}只会执行一次，故不能在mounted发请求
    async initSpuData(row) {
      // 获取SPU信息、品牌、图片、销售属性数据
      let SPURes = await this.$API.spu.reqSPU(row.id);
      if (SPURes.code == 200) {
        this.spu = SPURes.data;
      }

      let tradeMarkRes = await this.$API.spu.reqTradeMarkList();
      if (tradeMarkRes.code == 200) {
        this.tradeMarkList = tradeMarkRes.data;
      }

      // 获取图片接口暂时失败，以防影响后面接口正常执行先注释
      // let spuImageRes = await this.$API.spu.reqSPUImageList(row.id);
      // if (spuImageRes.code == 200) {
      //   let preSpuImageList = spuImageRes.data;
      //   // 由于照片墙（elementUI限制）显示图片的数据需要数组，数组里面的元素需要有name和url字段
      //   // 需要把服务器返回的数据进行修改，把name和url插入数组中再赋值给spuImageList
      //   preSpuImageList.forEach((item) => {
      //     item.name = item.imgName;
      //     item.url = item.imgUrl;
      //   });
      //   // 数组为响应式，数组新增的字段是可以检测到的
      //   this.spuImageList = preSpuImageList;
      // }else{
      //   this.$message({type:"warning",message:"获取图片失败"})
      // }

      let saleAttrRes = await this.$API.spu.reqBaseSaleAttrList();
      if (saleAttrRes.code == 200) {
        this.saleAttrList = saleAttrRes.data;
      }
    },


    // 添加销售属性
    addSaleAttr() {
      // 筛选服务器所需字段//分割
      const [baseSaleAttrId, saleAttrName] = this.attrIdAndAttrName.split(":")
      // 将以上两个字段假如spu中发送给服务器
      // 向spu对象的spuSaleAttrList属性添加新的销售属性
      let newSaleAttr = { baseSaleAttrId, saleAttrName, spuSaleAttrValueList: [] }
      this.spu.spuSaleAttrList.push(newSaleAttr)
      this.attrIdAndAttrName = ''
    },

    // 添加销售属性值
    addSaleAttrValue(row) {
      // row.inputVisible=true
      // 展示input，给row添加inputVisible属性，
      // (直接点)不是响应式不能收集inputVisible数据，用$set()
      this.$set(row, 'inputVisible', true)
      // 收集新增的销售属性值
      this.$set(row, 'inputValue', '')
    },


    // input失去焦点事件
    handleInputConfirm(row) {
      // 解构出销售属性收集的新增数据，新增的销售属性值
      const { baseSaleAttrId, inputValue } = row
      if (inputValue.trim() == "") {
        return this.$message("属性值不能为空")
      }
      // 防止销售属性值重复，重复返回true，some()也可以
      if (!row.spuSaleAttrValueList.every(item => item.saleAttrValueName != inputValue)) return;
      let newSaleAttrValue = { baseSaleAttrId, saleAttrValueName: inputValue }
      row.spuSaleAttrValueList.push(newSaleAttrValue)
      row.inputVisible = false // 给row添加响应式属性inputVisible
    },


    // 保存按钮事件，新增||修改
    async addOrUpdateSpu() {
      // 整理参数：整理需要携带的图片墙数据
      // 携带参数：对于图片，需要携带imageName和imageUrl字段
      this.spu.spuImageList = this.spuImageList.map(item => {
        return {
          imageName: item.name,
          imageUrl: (item.response && item.response.data) || item.url,
        }
      })
      let res = await this.$API.spu.reqAddOrUpdateSpu(this.spu)
      if (res.code == 200) {
        this.$message({ type: "success", message: "保存成功" })
        // 通知父组件回到scene==0
        this.$emit("changeScene", { scene: 0, flag: this.spu.id ? '修改' : '新增' })
      }
      // 清空数据
      Object.assign(this._data, this.$options.data())
    },


    // 点击添加SPU按钮回调
    async addSpuData(category3Id) {
      this.spu.category3Id = category3Id
      let tmRes = await this.$API.spu.reqTradeMarkList();
      if (tmRes.code == 200) {
        this.tradeMarkList = tmRes.data;
      }
      let saleAttrRes = await this.$API.spu.reqBaseSaleAttrList();
      if (saleAttrRes.code == 200) {
        this.saleAttrList = saleAttrRes.data;
      }
    },


    // 取消按钮
    cancel() {
      this.$emit('changeScene', { scene: 0, flag: '' })
      // 清空数据，Object.assign()合并对象，this._data为组件实例，可以操作data(){}
      Object.assign(this._data, this.$options.data())
    },
  },
  computed: {
    // 计算出还未选择的销售属性
    unSelectSaleAttr() {
      // 过滤返回本数组this.saleAttrList
      return this.saleAttrList.filter(item => {
        // every数组的方法，返回布尔值
        return this.spu.spuSaleAttrList.every(item1 => {
          return item.name != item1.saleAttrName
        })
      })

    }
  },
};
</script>

<style>
.el-tag+.el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>