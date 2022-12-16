<!--
 * @Author: Topskys
 * @Date: 2022-11-04 13:07:35
 * @LastEditTime: 2022-11-12 15:04:01
-->
<template>
    <div class="order__container">
        <el-form :model="form" ref="form" :inline="true" @keyup.enter.native="getPageList()" style="margin-top:20px">
            <el-form-item label="座号：">
                <el-input v-model="form.input" prefix-icon="el-icon-search" clearable placeholder="请输入内容">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getPageList()">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="dialogVisible = true">新增</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="list" style="width: 100%" border v-loading="loading" stripe>
            <el-table-column type="index" label="序号" width="80px" align="center">
            </el-table-column>
            <el-table-column prop="deskNo" label="座号" align="center"></el-table-column>
            <el-table-column prop="deskStatus" label="状态" align="center"></el-table-column>
            <el-table-column prop="prop" label="操作" align="center">
                <template slot-scope="{ row, $index }">
                    <el-button type="warning" icon="el-icon-edit" size="mini" @click="edit(row, $index)">修改
                    </el-button>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="del(row, $index)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <!-- <el-pagination @size-change="handleSizeChange" @current-change="getPageList" :current-page="6"
            :page-sizes="[3, 5, 10]" :page-size="10" layout=" prev, pager, next, jumper,->,sizes,total " :total="total"
            style="margin-top: 20px; text-align: left;" :pager-count="7">
        </el-pagination> -->
        <!-- 新增或编辑弹窗 -->
        <el-dialog :title="dialogForm.id ? '修改' : '新增'" :visible.sync="dialogVisible" width="50%"
            :before-close="handleClose" appendToBody>
            <el-form ref="dialogForm" :model="dialogForm" :rules="formRules" label-width="80px">
                <el-form-item label="座号" required prop="deskNo">
                    <el-input v-model="dialogForm.deskNo" placeholder="请输入座号"></el-input>
                </el-form-item>
                <el-form-item label="状态" required prop="deskStatus">
                    <el-select placeholder="请选择" v-model="dialogForm.deskStatus">
                        <el-option :label="item" :value="`${index}`" v-for="item, index in statusList" :key="item">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "DeskList",
    data() {
        return {
            list: [],
            page: 1, // 代表的分页器第几页
            limit: 5,// 当前页数展示数据条数
            total: 0, // 数据总条数
            form: {
                input: "",
            },
            dialogVisible: false,
            dialogForm: {},
            loading: false,
            statusList: ["空闲", "非空闲", "损坏"],
            // 表单验证
            formRules: {
                deskNo: [{ required: true, message: "不能为空", trigger: "blur" }],
                deskStatus: [{ required: true, message: "不能为空", trigger: "blur" }],
            },
        }
    },
    mounted() {
        this.getPageList();
    },
    methods: {
        // 获取订单列表#008号
        async getPageList() {
            this.loading = true
            let { input } = this.form
            const { code, data } = await this.$API.desk.reqDeskList(input);
            // code === 200 && (this.list = data || [])
            if (code === 200) {
                this.list = data || [];
                this.list.map(item => {
                    item.deskStatus = item.deskStatus == '0' ? '空闲' : item.deskStatus == '1' ? '非空闲' : '损坏';
                })
            }
            this.loading = false
        },
        //展示的数据条数发生变化时触发
        handleSizeChange(limit) {
            this.limit = limit;
            this.getPageList();
        },
        //修改
        edit(row) {
            this.dialogForm = { ...row }
            this.dialogVisible = true
        },
        // 删除
        del(row) {
            this.$confirm(`你确定删除${row.deskNo}?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(async () => {
                const { code, msg } = await this.$API.desk.reqDelDesk(row.id);
                if (code == 200) {
                    this.$message({
                        type: "success",
                        message: msg || "删除成功!",
                    });
                    this.getPageList();
                }
            }).catch(() => {
                this.$message({
                    type: "info",
                    message: "已取消删除",
                });
            });
        },
        cancel() {
            this.dialogVisible = false
            this.dialogForm = {}
        },
        async confirm() {
            // #008号桌
            let { dialogForm } = this;
            dialogForm.deskStatus = dialogForm.deskStatus.trim();
            this.statusList.forEach((item, index) => item == dialogForm.deskStatus && (dialogForm.deskStatus = `${index}`))
            const { code, msg } = await this.$API.desk.reqAddOrUpdateDesk(dialogForm)
            code === 200 && this.getPageList()
            this.$message({
                type: code === 200 ? "success" : "error",
                message: msg,
            })
            this.dialogVisible = false
            this.dialogForm = {}
        },
        handleClose() {
            this.dialogVisible = false
            this.dialogForm = {}
        },
    },
    watch: {
        'form.input': {
            handler(newVal, oldVal) {
                newVal && this.getPageList()
            },
        }
    }
}
</script>

<style>

</style>