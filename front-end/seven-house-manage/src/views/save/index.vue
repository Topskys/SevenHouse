<!--
 * @Author: Topskys
 * @Date: 2022-10-19 20:14:53
 * @LastEditTime: 2022-11-15 16:35:34
-->
<template>
    <div class="save__container">
        <el-form :model="form" ref="form" :inline="true" @keyup.enter.native="getPageList()" style="margin-top:20px">
            <el-form-item label="顾客名称：">
                <el-input v-model="form.input" prefix-icon="el-icon-search" clearable placeholder="请输入顾客名称">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getPageList()">查询</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="list" style="width: 100%" border>
            <el-table-column prop="orderId" label="订单项编号" align="center"></el-table-column>
            <el-table-column prop="alcoName" label="商品名称" align="center"></el-table-column>
            <el-table-column prop="userId" label="账户" align="center"></el-table-column>
            <el-table-column prop="user.nickname" label="顾客名称" align="center"></el-table-column>
            <!-- <el-table-column prop="phone" label="手机号" align="center"></el-table-column> -->
            <el-table-column label="图片" align="center">
                <template slot-scope="{ row, $index }">
                    <img :src="row.alcoImage" :alt="$index" style="width:100px;height: 100px">
                </template>
            </el-table-column>
            <el-table-column prop="depNum" label="寄存余量" align="center"></el-table-column>

            <el-table-column prop="createTime" label="寄存时间" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{ row, $index }">
                    <!-- <el-button type="primary" size="mini" @click="confirm(row, $index)">确认</el-button> -->
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="del(row, $index)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <el-pagination @size-change="handleSizeChange" @current-change="getPageList" :current-page="1"
            :page-sizes="[3, 5, 10, 15, 20]" :page-size="10" layout=" prev, pager, next, jumper,->,sizes,total "
            :total="total" style="margin-top: 20px; text-align: left" :pager-count="7">
        </el-pagination>
    </div>
</template>

<script>
import { validatePhone } from "@/utils/validate";
export default {
    name: "SaveList",
    data() {
        return {
            list: [],
            page: 1, // 代表的分页器第几页
            limit: 10,// 当前页数展示数据条数
            total: 0, // 数据总条数
            form: {
                input: "",
            },
        }
    },
    mounted() {
        this.getPageList();// 组件挂载完毕发送请求
    },
    methods: {
        // 获取订单列表
        async getPageList(pager = 1) {
            this.page = pager;
            if (!this.form.input) {
                const { code, data } = await this.$API.save.reqSaveList(this.page, this.limit);
                code === 200 && ([this.list, this.total] = [data.records, data.total])
            } else {
                const { code, data } = await this.$API.save.reqSaveQuery(this.form.input);
                code === 200 && ([this.list, this.total] = [data || [], data.length || 1])
            }
        },
        //展示的数据条数发生变化时触发
        handleSizeChange(limit) {
            this.limit = limit;
            this.getPageList();
        },
        // 删除
        del(row, index) {
            this.$confirm(`你确定删除${index + 1}?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(async () => {
                const { code } = await this.$API.save.reqDelDeposit(row.id);
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
        // 确认订单
        confirm(row) {
            this.$API.order.reqConfirmOrder(row.id).then(({ code, msg }) => {
                code === 200 && this.getPageList();
                this.$message({ type: code === 200 ? "success" : "error", message: msg })
            })
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