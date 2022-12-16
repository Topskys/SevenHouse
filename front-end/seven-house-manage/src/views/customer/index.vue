<!--
 * @Author: Topskys
 * @Date: 2022-10-19 20:14:53
 * @LastEditTime: 2022-11-24 16:42:28
-->
<template>
    <div class="customer__container">
        <el-form :model="form" ref="form" :inline="true" @keyup.enter.native="getPageList()" style="margin-top:20px">
            <el-form-item label="名称：">
                <el-input v-model="form.input" prefix-icon="el-icon-search" clearable placeholder="请输入内容">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getPageList()">查询</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="list" stripe border v-loading="listLoading" fit highlight-current-row style="width: 100%;">
            <el-table-column type="index" label="序号" width="80px" align="center">
            </el-table-column>
            <el-table-column prop="nickname" label="名称"></el-table-column>
            <el-table-column prop="email" label="账号"></el-table-column>
            <el-table-column prop="phone" label="手机号"></el-table-column>
            <el-table-column prop="integral" label="余额"></el-table-column>
            <el-table-column prop="type" label="类型"></el-table-column>
            <el-table-column prop="avatarUrl" label="头像" width="80px" align="center">
                <template slot-scope="{ row } ">
                    <img :src="row.avatarUrl" style="width: 50px; height: 50px;border-radius:5px;" />
                </template>
            </el-table-column>
            <el-table-column prop="birthday" label="生日"></el-table-column>
            <el-table-column prop="gender" label="性别" width="100px" align="center"></el-table-column>
            <el-table-column prop="address" label="住址"></el-table-column>
            <el-table-column prop="status" label="状态" width="100px" align="center"></el-table-column>
            <el-table-column prop="prop" label="操作" align="center">
                <template slot-scope="{ row, $index }">
                    <el-button type="text" @click="edit(row, $index)">编辑</el-button>
                    <el-button type="text" @click="del(row, $index)" style="color:#F56C6C;">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <el-pagination @size-change="handleSizeChange" @current-change="getPageList" :current-page="1"
            :page-sizes="[3, 5, 10]" :page-size="limit" layout=" prev, pager, next, jumper,->,sizes,total "
            :total="total" style="margin-top: 20px; text-align: left" :pager-count="7">
        </el-pagination>
        <!-- 编辑弹窗 -->
        <Dialog :config="config" @cancelHandler="cancel" @confirmHandler="confirm">
            <!-- 弹窗内容 -->
            <el-form ref="dialogForm" :model="dialogForm" :rules="formRules" label-width="80px" inline>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="昵称" required prop="nickname">
                            <el-input v-model="dialogForm.nickname" placeholder="昵称"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱" required prop="email">
                            <el-input v-model="dialogForm.email" placeholder="账号或邮箱"></el-input>
                        </el-form-item>
                        <el-form-item label="手机号" required prop="phone">
                            <el-input v-model="dialogForm.phone" :maxlength="11" placeholder="手机号" type="number">
                            </el-input>
                        </el-form-item>
                        <el-form-item label="充值">
                            <el-input v-model="dialogForm.integral" placeholder="充值" type="number"></el-input>
                        </el-form-item>
                        <el-form-item label="住址">
                            <el-input v-model="dialogForm.address" placeholder="住址"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="性别" required prop="gender">
                            <el-select placeholder="请选择" v-model="dialogForm.gender">
                                <el-option label="男" value="0"></el-option>
                                <el-option label="女" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="生日">
                            <el-input v-model="dialogForm.birthday" type="date" placeholder="生日"></el-input>
                        </el-form-item>
                        <el-form-item label="类型" required prop="type">
                            <el-select placeholder="请选择" v-model="dialogForm.type">
                                <el-option :label="item" :value="item" v-for="item in typeList" :key="item">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="状态" required prop="status">
                            <el-select placeholder="请选择" v-model="dialogForm.status">
                                <el-option :label="item" :value="`${index}`" v-for="item, index in statusList"
                                    :key="item">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="头像">
                    <el-upload action="http://106.14.40.97:8989/api/ali/uploadFile" list-type="picture-card"
                        :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :file-list="imgList"
                        :on-success="handleSuccess">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisibleImg">
                        <img width="100%" :src="dialogImageUrl" alt="404" />
                    </el-dialog>
                </el-form-item>
            </el-form>
        </Dialog>
    </div>
</template>

<script>
import Dialog from '@/components/Dialog'
import { validatePhone } from "@/utils/validate";
export default {
    name: "CustomerList",
    components: {
        Dialog,
    },
    data() {
        const validPhone = (rule, value, callback) => validatePhone(value) ? callback() : callback(new Error("Please enter the correct phone"));
        return {
            list: [],
            page: 1, // 代表的分页器第几页
            limit: 10,// 当前页数展示数据条数
            total: 0, // 数据总条数
            form: {
                input: "",// 搜索输入框
            },
            listLoading: false,
            // 弹窗配置信息
            config: {
                id: -1,
                dialogVisible: false,
            },
            typeList: ["管理员", "用户"],
            statusList: ["正常", "封禁"],
            // 收集编辑数据
            dialogForm: {},
            dialogVisibleImg: false,
            dialogImageUrl: '',
            imgList: [],
            // 表单验证
            formRules: {
                nickname: [{ required: true, message: "不能为空", trigger: "blur" }],
                type: [{ required: true, message: "不能为空", trigger: "blur" }],
                email: [{ required: true, message: "不能为空", trigger: "blur" }],
                gender: [{ required: true, message: "不能为空", trigger: "blur" }],
                status: [{ required: true, message: "不能为空", trigger: "blur" }],
                phone: [{ required: true, trigger: "blur", validator: validPhone }],
            },
        }
    },
    mounted() { // 组件挂载完毕发送请求
        this.getPageList();
    },
    methods: {
        // 获取订单列表
        getPageList(pager = 1) {
            this.page = pager;
            this.listLoading = true;
            let phone = '', username = '';
            validatePhone(this.form.input) ? (phone = this.form.input) : (username = this.form.input);
            this.$API.customer.reqCustomerList(
                this.page,
                this.limit,
                phone,
                username,
            ).then(({ code, data, msg }) => {
                // code === 200 && ([this.list, this.total] = [data.records, data.records.length])
                if (code === 200) {
                    this.list = data.records || []
                    this.total = data.total || list.total
                    this.list.map(item => {
                        item.gender = item.gender == '1' ? '女' : '男';
                        item.status = item.status == '1' ? '封禁' : '正常';
                    });
                }
            })
            this.listLoading = false;
        },
        //展示的数据条数发生变化时触发
        handleSizeChange(limit) {
            this.limit = limit;
            this.getPageList();
        },
        // 编辑按钮
        edit(row, index) {
            this.config.id = row.id;
            this.config.dialogVisible = true;
            this.dialogForm = { ...row };
        },
        // 删除按钮
        del(row, index) {
            this.$confirm(`你确定删除第${index + 1}个数据?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(async () => {
                const { code } = await this.$API.customer.reqDeleteCustomer(row.id);
                if (code === 200) {
                    this.$message({
                        type: "success",
                        message: "删除成功!",
                    });
                    this.getPageList(this.list.length > 1 ? this.page : this.page - 1);
                }
            }).catch(() => {
                this.$message({
                    type: "info",
                    message: "已取消删除",
                });
            });
        },

        // 弹窗取消按钮
        cancel: function () { },
        // 弹窗确定按钮
        confirm: function () {
            this.$refs.dialogForm.validate((valid) => {
                let { dialogForm } = this;
                dialogForm.gender = dialogForm.gender == '男' ? '0' : dialogForm.gender == '0' ? '0' : '1';
                dialogForm.status = dialogForm.status == '正常' ? '0' : dialogForm.status == '0' ? "0" : '1';
                if (valid) {
                    this.$API.customer.reqCustomerUpdate(dialogForm)
                        .then(({ code, data, msg }) => {
                            code === 200 && (this.$message({ type: 'success', message: msg }), this.getPageList())
                        }).catch((err) => {
                            this.$message({ type: 'error', message: '修改失败' })
                            console.log(err)
                        });
                } else {
                    this.$message('表单验证错误')
                    console.log("validate is error");
                }
            });
        },


        handlePictureCardPreview(file) {
            // 将图片地址赋值给dialogImageUrl
            this.dialogImageUrl = file.url;
            this.dialogVisibleImg = true;
        },
        handleRemove(file, fileList) {
            // file删除的那张图，剩下的照片墙fileList
            // console.log(file, fileList);
            // 含name和url等服务器不需要的字段
            this.imgList = fileList;
        },
        handleSuccess(response, file, fileList) {
            // 收集刚上传的图片
            this.imgList = fileList;
            this.dialogForm.avatarUrl = response.data
        },
    },
    watch: {
        'form.input': {
            handler: function (newValue, oldValue) {
                newValue ? '' : this.getPageList()
            }
        }
    },
}
</script>

<style>

</style>