<template>
  <div class="CategorySelect">
    <!-- 三级联动 -->
    <!-- inline:行内表单，一行可放置多个表单元素 -->
    <el-form inline class="demo-form-inline">
      <el-form-item label="一级分类">
        <el-select
          placeholder="请选择"
          v-model="cForm.category1Id"
          @change="handleSelect1"
          :disabled="show"
        >
          <el-option
            :label="s1.name"
            :value="s1.id"
            v-for="(s1, index) in select1"
            :key="s1.id || index"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级分类">
        <el-select
          placeholder="请选择"
          v-model="cForm.category2Id"
          @change="handleSelect2"
          :disabled="show"
        >
          <el-option
            :label="s2.name"
            :value="s2.id"
            v-for="(s2, index) in select2"
            :key="s2.id || index"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="三级分类">
        <el-select
          placeholder="请选择"
          v-model="cForm.category3Id"
          @change="handleSelect3"
          :disabled="show"
        >
          <el-option
            :label="s3.name"
            :value="s3.id"
            v-for="(s3, index) in select3"
            :key="s3.id || index"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "CategorySelect",
  props:['show'],// 接收Attr/index的show----父传子
  data() {
    return {
      // 一、二、三级分类数据
      select1: [],
      select2: [],
      select3: [],
      // 收集一、二、三级分类id
      cForm: {
        category1Id: "",
        category2Id: "",
        category3Id: "",
      },
    };
  },
  mounted() {
    this.getCategory1List();
  },
  methods: {
    // 获取分类数据
    async getCategory1List() {
      let res = await this.$API.attr.reqCategory1List();
      if (res.code == 200) {
        this.select1 = res.data;
        // console.log(res)
      }
    },

    // 一级分类事件回调，获取二级分类数据
    async handleSelect1() {
      // 清除select和id
      this.select2 = [];
      this.select3 = [];
      this.cForm.category2Id = "";
      this.cForm.category3Id = "";
      // 解构
      const { category1Id } = this.cForm;
      // 子传父
      this.$emit("getCategoryId", { categoryId: category1Id, level: 1 });
      let res = await this.$API.attr.reqCategory2List(category1Id);
      if (res.code == 200) {
        this.select2 = res.data;
      }
    },

    // 二级分类事件回调，获取三级分类数据
    async handleSelect2(){
        this.select3=[]
        this.cForm.category3Id=''
        // 解构
        const {category2Id}=this.cForm
        this.$emit("getCategoryId", {categoryId:category2Id,level:2})
        let res=await this.$API.attr.reqCategory3List(category2Id)
        if(res.code == 200){
            this.select3=res.data
        }
    },

    // 三级分类事件回调，获取数据
    async handleSelect3(){
        // 解构
        const {category3Id}=this.cForm
        this.$emit("getCategoryId", {categoryId:category3Id,level:3})
    }
  },
};
</script>

<style>
</style>