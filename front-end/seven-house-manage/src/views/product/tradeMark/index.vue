<template>
  <div class="tradeMark">
    <el-button
      type="primary"
      icon="el-icon-plus"
      style="margin: 10px 0px"
      @click="showDialog"
      >添加</el-button
    >
    <el-table :data="list" style="width: 100%" border>
      <el-table-column type="index" label="序号" width="80px" align="center">
      </el-table-column>
      <el-table-column prop="tmName" label="品牌名称" width="width">
      </el-table-column>
      <el-table-column prop="logoUrl" label="品牌LOGO" width="width">
        <template slot-scope="{ row, $index }">
          <img :src="row.logoUrl" style="width: 100px; height: 100px" />
        </template>
      </el-table-column>
      <el-table-column prop="prop" label="操作" width="width">
        <!-- 插槽 -->
        <template slot-scope="{ row, $index }">
          <el-button
            type="warning"
            icon="el-icon-edit"
            size="mini"
            @click="updateTradeMark(row)"
            >修改</el-button
          >
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="deleteTradeMark(row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页器 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="getPageList"
      :current-page="6"
      :page-sizes="[3, 5, 10]"
      :page-size="10"
      layout=" prev, pager, next, jumper,->,sizes,total "
      :total="total"
      style="margin-top: 20px; text-align: center"
      :pager-count="7"
    >
    </el-pagination>
    <!-- 
      对话框
      :visible.sync：控制对话框是否显示
    -->
    <el-dialog
      :title="this.tmForm.id ? '修改品牌' : '添加品牌'"
      :visible.sync="dialogFormVisible"
      width="width"
    >
      <!-- 添加数据表单 -->
      <!-- Form 组件提供了表单验证的功能，只需要通过 rules 属性传入约定的验证规则，并将 Form-Item 的 prop 属性设置为需校验的字段名即可。 -->
      <el-form style="width: 80%" :model="tmForm" :rules="rules" ref="ruleForm">
        <el-form-item label="品牌名称" label-width="100px" prop="tmName">
          <el-input autocomplete="off" v-model="tmForm.tmName"></el-input>
        </el-form-item>
        <el-form-item label="品牌LOGO" label-width="100px" prop="logoUrl">
          <!-- 上传图片，不是表单，这里不能使用v-model收集信息 -->
          <el-upload
            class="avatar-uploader"
            action="/dev-api/admin/product/fileUpload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="tmForm.logoUrl" :src="tmForm.logoUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            <div slot="tip" class="el-upload__tip">
              只能上传jpg/png文件，且不超过500kb
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrUpdateTradeMark"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "tradeMark",
  data() {
    //自定义效验规则
    var validateTmName = (rule, value, callback) => {
      if (value.length < 2 || value.length > 15) {
        callback(new Error("品牌名称长度应该在2-15个字符！"));
      } else {
        callback();
      }
    };
    return {
      // 代表的分页器第几页
      page: 1,
      // 当前页数展示数据条数
      limit: 5,
      // 数据总条数
      total: 0,
      // 品牌列表展示
      list: [],
      //是否显示添加Form
      dialogFormVisible: false,
      //收集品牌信息form
      tmForm: {
        tmName: "", //上传图片属性
        logoUrl: "",
      },
      //表单验证规则（required：带星星必须效验的数据、trigger：事件触发方式）
      rules: {
        tmName: [
          { required: true, message: "请输入品牌名称", trigger: "blur" },
          // {
          //   min: 3,
          //   max: 5,
          //   message: "长度在 3 到 5 个字符",
          //   trigger: "change",
          // },
          //自定义效验规则
          { validator: validateTmName, trigger: "change" },
        ],
        logoUrl: [{ required: true, message: "请选择品牌的图片" }],
      },
    };
  },
  // 组件挂载完毕发送请求
  mounted() {
    this.getPageList();
  },
  methods: {
    // 获取品牌列表
    async getPageList(pager = 1) {
      this.page = pager;
      // 解构出参数
      const { page, limit } = this;
      // 获取品牌列表的接口
      let res = await this.$API.trademark.reqTradeMarkList(page, limit);
      //console.log("res:",res)
      if (res.code == 200) {
        this.total = res.data.total;
        this.list = res.data.records;
      }
    },

    //展示的数据条数发生变化时触发
    handleSizeChange(limit) {
      this.limit = limit;
      this.getPageList();
    },

    //点击增加品牌按钮显示对话框
    showDialog() {
      this.dialogFormVisible = true;
      //清空表单
      this.tmForm = {
        tmName: "",
        logoUrl: "",
      };
    },

    //修改
    updateTradeMark(row) {
      this.dialogFormVisible = true;
      //将已有的品牌信息（row）展示出来
      //...row前拷贝，否则tmForm会直接操作数据表
      this.tmForm = { ...row };
    },

    //上传图片
    handleAvatarSuccess(res, file) {
      // this.imageUrl = URL.createObjectURL(file.raw);
      //收集服务器返回的服务器图片地址将来再提交给服务器
      this.tmForm.logoUrl = res.data;
      // console.log(this.tmForm.logoUrl)
    },
    //图片处理
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    //添加按钮（添加品牌||修改品牌）
    addOrUpdateTradeMark() {
      //验证规则全部通过后
      this.$refs.ruleForm.validate(async (valid) => {
        if (valid) {
          this.dialogFormVisible = false;
          let res = await this.$API.trademark.reqAddOrUpdateTradeMark(
            this.tmForm
          );
          if (res.code == 200) {
            this.$message({
              type: "success",
              message: this.tmForm.id ? "修改品牌成功" : "添加品牌成功",
            });
            //再次获取品牌列表
            //if有id则是修改操作留在当前页，if添加品牌留在第一页
            this.getPageList(this.tmForm.id ? this.page : 1);
          }
        } else {
          return;
        }
      });
    },

    //删除品牌
    deleteTradeMark(row) {
      //弹窗
      this.$confirm(`你确定删除${row.tmName}?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
          let res = await this.$API.trademark.reqDeleteTradeMark(row.id);
          if (res.code == 200) {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            //还有数据则停留，否则page-1
            this.getPageList(this.list.length>1?this.page:this.page-1);
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>


<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

