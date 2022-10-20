<template>
  <div class="attr">
    <el-card style="margin: 20px 0">
      <!-- 三级联动 全局注册 -->
      <!-- 子传父，使用自定义事件 -->
      <CategorySelect @getCategoryId="getCategoryId" :show="!isShowTable" />
    </el-card>
    <el-card>
      <div v-show="isShowTable">
        <el-button type="primary" icon="el-icon-plus" :disabled="!category3Id" @click="addAttr">添加属性</el-button>
        <!-- 展示平台属性表格 -->
        <el-table :data="attrList" style="width: 100%; margin: 20px 0" border>
          <el-table-column type="index" label="序号" width="80" align="center">
          </el-table-column>
          <el-table-column prop="attrName" label="属性名称" width="150">
          </el-table-column>
          <el-table-column prop="attrValueList" label="属性值列表" width="width">
            <template slot-scope="{ row }">
              <el-tag type="success" v-for="(attrValue, index) in row.attrValueList" :key="attrValue.id || index"
                style="margin: 0 20px">{{ attrValue.valueName }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="index" label="操作" width="150">
            <template slot-scope="{ row}">
              <el-button type="warning" icon="el-icon-edit" size="mini" @click="updateAttr(row)"></el-button>
              <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 添加属性||修改属性的结构 -->
      <div v-show="!isShowTable">
        <el-form inline ref="form" label-width="80px" :model="attrInfo">
          <el-form-item label="属性名">
            <el-input placeholder="请输入属性名" v-model="attrInfo.attrName"></el-input>
          </el-form-item>
        </el-form>
        <el-button type="primary" icon="el-icon-plus" @click="addAttrValue" :disabled="!attrInfo.attrName">添加属性值
        </el-button>
        <el-button @click="isShowTable = true">取消</el-button>
        <el-table :data="attrInfo.attrValueList" style="width: 100%; margin: 20px 0px" border>
          <el-table-column type="index" label="序号" width="80" align="center">
          </el-table-column>
          <el-table-column prop="prop" label="属性值名称" width="width">
            <template slot-scope="{ row, $index }">
              <!-- 输入完后切换span显示输入的数据 -->
              <el-input v-model="row.valueName" placeholder="请输入属性值名称" size="mini" v-if="row.flag" @blur="showSpan(row)"
                @keyup.native.enter="row.flag = false" :ref="$index"></el-input>
              <span v-else @click="edit(row, $index)" style="display: block">{{row.valueName}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="prop" label="操作" width="width">
            <template slot-scope="{ row, $index }">
              <!-- 气泡确认删除弹窗 -->
              <el-popconfirm :title="`确定删除${row.valueName}？`" @onConfirm="deleteAttrValue($index)">
                <el-button type="danger" icon="el-icon-delete" size="mini" slot="reference"></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" @click="addOrUpdateAttr" :disabled="attrInfo.attrValueList.length<1">保存</el-button>
        <el-button @click="isShowTable = true">取消</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import cloneDeep from "lodash/cloneDeep";
// import { encrypt, decrypt } from '@/views/rsa';
export default {
  name: "Attr",
  data() {
    return {
      category1Id: "",
      category2Id: "",
      category3Id: "",
      // 属性数据列表
      attrList: [],
      // 控制表格的显示
      isShowTable: true,
      // 收集新增属性和修改属性
      attrInfo: {
        attrName: "",
        attrValueList: [
          {
            attrId: 0,
            valueName: "",
          },
        ],
        categoryId: 0, // 三级分类id
        categoryLevel: 3, // 服务器也需要区分几级id
      },
    };
  },
  methods: {

    // 自定义事件回调
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
        // 获取平台属性数据
        this.getAttrList();
      }

    },

    async getAttrList() {
      // let str="23+罗兰波+物联网1901物联网信息安全课程实验"
      // console.log( "明文：",str,"\n加密：",encrypt(str))
      // 解构对象的属性
      const { category1Id, category2Id, category3Id } = this;
      let res = await this.$API.attr.reqAttrList(
        category1Id,
        category2Id,
        category3Id
      );
      if (res.code == 200) {
        this.attrList = res.data;
        // console.log(res)
      }
    },

    // 添加属性值
    addAttrValue() {
      this.attrInfo.attrValueList.push({
        attrId: this.attrInfo.id,
        valueName: "",
        flag: true, // 隐藏控制输入和展示span
      });
      this.$nextTick(() => {
        this.$refs[this.attrInfo.attrValueList.length - 1].focus();// 获取相应的input表单元素实现聚焦// 枚举
      });
    },

    // 添加属性按钮的回调
    addAttr() {
      this.isShowTable = false;
      // 清除数据// 收集三级分类的id
      this.attrInfo = {
        attrName: "",
        attrValueList: [],
        categoryId: this.category3Id,
        categoryLevel: 3,
      };
    },

    // 修改某一个属性
    updateAttr(row) {
      this.isShowTable = false;
      // 把选中的属性赋值给attrInfo
      // 由于数据结构中存在对象里面嵌套数组，数组里有对象，前拷贝不中用了，需用深拷贝解决
      this.attrInfo = cloneDeep(row);
      // 补充flag
      // 在修改某一个属性的时候，将相应的属性值元素添加上flag这个标记
      // 给对象添加新属性时vue底层无法检测到
      this.attrInfo.attrValueList.forEach((item) => {
        // 这样书写也可以给属性值添加flag自动，但是会发现视图不会跟着变化（因为flag不是响应式数据)
        // 因为vue无法探测普通的新增 property ,读样书写的属性并非响应式属性（数据变化视图跟着变)
        // item.flag=false;
        this.$set(item, "flag", false);
      });
    },

    // 显示span隐藏input
    showSpan(row) {
      // 新增属性值为空，则return
      if (row.valueName.trim() == "") {
        this.$message("请输入一个正常的属性值");
        return;
      }
      // 遍历查重返回boolean
      let isRepeat = this.attrInfo.attrValueList.some((item) => {
        if (row !== item) {
          return row.valueName == item.valueName;
        }
      });
      if (isRepeat) return;
      // row当前新增的属性值
      row.flag = false;
    },

    // 点击span显示input实现编辑
    edit(row, index) {
      row.flag = true;
      // 获取input节点,实现自动聚焦
      // 需要注意:点击span的时候，切换为input变为编辑模式，但是需要注意，对于浏览器而言，页面重绘与重拍耗时间的
      // 点击span的时候，重绘一个input它是需要耗费时间，因此我们不可能一点击span立马获取到input
      // $nextTick,当节点渲染完毕了，会执行一次
      this.$nextTick(() => {
        this.$refs[index].focus();// 获取相应的input表单元素实现聚焦
      });
    },


    // 气泡确认删除弹窗当前使用模板vue版本为2.13.3而vue最新版本为2.5.x
    // 所以使用@onConfirm or 使用@confirm
    deleteAttrValue(index) {
      this.attrInfo.attrValueList.splice(index, 1);// index为索引值 // 删除一个
    },


    // 保存按钮：添加属性or修改属性的操作
    async addOrUpdateAttr() {
      // 过滤掉空值和服务器不需要的flag字段
      this.attrInfo.attrValueList = this.attrInfo.attrValueList.filter(item => {
        if (item.valueName != '') {
          delete item.flag
          return true
        }
      })
      // 发送保存请求
      try {
        await this.$API.attr.reqAddOrUpdateAttr(this.attrInfo)
        this.$message({ type: 'success', message: '保存成功' })
        // 刷新
        this.getAttrList()
        this.isShowTable = true
      } catch (error) {
        this.$message('保存失败')
      }
    }
  },
};
</script>

<style>

</style>