<!--
 * @Author: Topskys
 * @Date: 2022-11-04 13:07:35
 * @LastEditTime: 2022-11-07 13:50:28
-->
<template>
    <div class="order__container">
        <el-form :model="form" ref="form" :inline="true" @keyup.enter.native="getPageList()" style="margin-top:20px">
            <el-form-item label="名称：">
                <el-input v-model="form.input" prefix-icon="el-icon-search" clearable placeholder="请输入内容">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getPageList">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="dialogVisible = true">新增</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="list" style="width: 100%" border v-loading="loading">
            <el-table-column type="index" label="序号" width="80px" align="center">
            </el-table-column>
            <el-table-column prop="name" label="名称" align="center"></el-table-column>
            <el-table-column label="状态" align="center">正常</el-table-column>
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
            <el-form ref="dialogForm" :model="dialogForm" label-width="80px">
                <el-form-item label="名称">
                    <el-input v-model="dialogForm.name" placeholder="请输入分类名称"></el-input>
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
    name: "OrderList",
    data() {
        return {
            list: [
                {
                    id: 1,
                    name: "分类1",
                }
            ],
            page: 1, // 代表的分页器第几页
            limit: 5,// 当前页数展示数据条数
            total: 0, // 数据总条数
            form: {
                input: "",
            },
            dialogVisible: false,
            dialogForm: {
                id: '',
                name: ""
            },
            loading: false,
        }
    },
    mounted() {
        this.getPageList();
    },
    methods: {
        // 获取订单列表或查询
        async getPageList() {
            this.loading = true
            let { input } = this.form
            if (input) {
                const { code, data } = await this.$API.category.reqCategoryQuery(input);
                code === 200 && ([this.total, this.list] = [data.total, data])
            } else {
                const { code, data } = await this.$API.category.reqCategoryList();
                code === 200 && ([this.total, this.list] = [data.total, data])
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
            this.$confirm(`你确定删除${row.name}?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(async () => {
                const { code } = await this.$API.category.reqDelCategory(row.id);
                if (code == 200) {
                    this.$message({
                        type: "success",
                        message: "删除成功!",
                    });
                    this.getPageList(this.list.length > 1 ? this.page : this.page - 1);//还有数据则停留，否则page-1
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
            const { code, data, msg } = await this.$API.category.reqAddOrUpdateCategory(this.dialogForm)
            code == 200 ? this.getPageList() : this.$message({
                type: "error",
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
                this.getPageList()
            },
        }
    }
}
</script>

<style>

</style>