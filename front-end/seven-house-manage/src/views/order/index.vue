<!--
 * @Author: Topskys
 * @Date: 2022-10-19 20:14:53
 * @LastEditTime: 2022-11-25 18:57:40
-->
<template>
    <div class="order__container">
        <div style="display:flex;justify-content:space-between;align-items:center;">
            <el-form :model="form" ref="form" :inline="true" @keyup.enter.native="getPageList()"
                style="margin-top:20px">
                <el-form-item label="状态：">
                    <el-input v-model="form.input" prefix-icon="el-icon-search" clearable placeholder="请输入支付状态或手机号">
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getPageList()">查询</el-button>
                </el-form-item>
            </el-form>
            <el-switch v-model="switchValue" active-color="#13ce66" inactive-color="#ff4949"
                :inactive-text="switchValue ? '酒馆营业中' : ''" @change="closeStore">
            </el-switch>
        </div>

        <el-table :data="list" style="width: 100%" border>
            <el-table-column prop="id" label="订单编号" width="120px" align="center"></el-table-column>
            <el-table-column prop="userId" label="顾客" width="120px" align="center"></el-table-column>
            <!-- <el-table-column prop="nickname" label="顾客" width="120px" align="center"></el-table-column> -->
            <el-table-column prop="phone" label="手机号" width="120px" align="center"></el-table-column>
            <el-table-column prop="count" label="数量" width="80px" align="center"></el-table-column>
            <el-table-column prop="payType" label="支付方式" width="80px" align="center"></el-table-column>
            <el-table-column prop="total" label="支付金额(元)" width="120px" align="center"></el-table-column>
            <el-table-column prop="deskId" label="座号" width="100px" align="center"></el-table-column>
            <el-table-column prop="eatType" label="送餐方式" width="100px" align="center"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="status" label="状态" width="80px" align="center"></el-table-column>
            <el-table-column prop="createTime" label="下单时间" width="120px" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{ row, $index }">
                    <!-- <div style="display:flex;flex-wrap:nowrap;">
                        style="color:#E6A23C;"style="color:#909399;"icon="el-icon-info"style="color:#F56C6C;"-->
                    <el-button type="primary" size="mini" @click="confirmOrder(row, $index)"
                        :disabled="row.status == '已支付' ? false : true">确认
                    </el-button>
                    <!-- <el-button type="warning" icon="el-icon-edit" size="mini" @click="updateOrder(row, $index)">
                    </el-button> -->
                    <el-button type="info" size="mini" @click="spreadOrder(row, $index)">详情
                    </el-button>
                    <!-- <el-button type="success" size="mini" @click="depositOrder(row, $index)">寄存
                    </el-button> -->
                    <!-- </div> -->
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <el-pagination @size-change="handleSizeChange" @current-change="getPageList" :current-page="1"
            :page-sizes="[3, 5, 10, 15, 20]" :page-size="10" layout=" prev, pager, next, jumper,->,sizes,total "
            :total="total" style="margin-top: 20px; text-align: left" :pager-count="7">
        </el-pagination>
        <!-- 订单详情 -->
        <el-dialog :title="`订单详情-${dialogTable.id}`" :visible.sync="dialogVisible" :before-close="beforeClose">
            <el-table :data="dialogTable.orderItemList" v-loading="loading" style="width:100%" border>
                <el-table-column prop="alcoName" label="名称" align="center"></el-table-column>
                <el-table-column label="图片" align="center">
                    <template slot-scope="{ row, $index }">
                        <img :src="row.alcoImage" :alt="$index" style="width:100px;height: 100px">
                    </template>
                </el-table-column>
                <el-table-column prop="alcoSku" label="规格" align="center"></el-table-column>
                <el-table-column prop="buyNum" label="数量" align="center"></el-table-column>
                <el-table-column prop="acloPrice" label="单价" align="center"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="{ row, $index }">
                        <el-button type="success" size="mini" @click="depositOrder(row, $index)"
                            :disabled="row.deposit == '1' ? true : dialogTable.status == '完成' ? false : true"> {{
        row.deposit == "1" ? "已寄存" : "寄存"
                            }}
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script>
import { validatePhone } from "@/utils/validate";
export default {
    name: "OrderList",
    data() {
        return {
            list: [],
            page: 1, // 代表的分页器第几页
            limit: 10,// 当前页数展示数据条数
            total: 0, // 数据总条数
            form: {
                input: "",
            },
            dialogVisible: false,// 控制弹窗的显隐
            loading: false, // 弹窗表格加载中
            dialogTable: {},// 弹窗表格数据
            switchValue: false,
        }
    },
    mounted() {
        this.getPageList();// 组件挂载完毕发送请求
        this.isOpenStore();
    },
    methods: {
        // 开启或关闭酒馆
        async closeStore() {
            const { code, msg, data } = await this.$API.order.reqCloseStore();
            this.switchValue = data.isOpen
            this.$message({ type: code === 200 ? "success" : "error", message: msg })
        },
        async isOpenStore() {
            const { data } = await this.$API.order.reqIsOpenStore();
            this.switchValue = data.status == '1' ? false : true;
        },
        // 获取订单列表
        async getPageList(pager = 1) {
            this.page = pager;
            let phone = '', statusType = '';
            validatePhone(this.form.input) ? (phone = this.form.input) : (statusType = this.form.input);
            const { code, data } = await this.$API.order.reqOrderList(this.page, this.limit, phone, statusType);
            code === 200 && ([this.total, this.list] = [data.total, data.records])
        },
        //展示的数据条数发生变化时触发
        handleSizeChange(limit) {
            this.limit = limit;
            this.getPageList();
        },
        //修改
        updateOrder(row) {
            window.alert('开发中')
            // this.dialogFormVisible = true;
            // //将已有的品牌信息（row）展示出来
            // //...row前拷贝，否则tmForm会直接操作数据表
            // this.tmForm = { ...row };
        },
        // 删除订单
        deleteOrder(row) {
            this.$confirm(`你确定删除${row.id}?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(async () => {
                const { code } = await this.$API.order.reqDeleteOrder(row.id);
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
        confirmOrder(row) {
            this.$API.order.reqConfirmOrder(row.id).then(({ code, msg }) => {
                code === 200 && this.getPageList();
                this.$message({ type: code === 200 ? "success" : "error", message: msg })
            })
        },
        // 订单详情
        spreadOrder(row) {
            this.loading = true;
            this.dialogVisible = true;
            this.dialogTable = { ...row };
            this.loading = false;
        },
        // 寄存
        depositOrder(row) {
            this.$prompt('请输入寄存酒的数量', '寄存数量', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputPattern: /^[1-9][0-9]*$/, // 匹配非零开头的数字
                inputErrorMessage: '寄存数量不正确'
            }).then(({ value }) => {
                // row.depNum = value <= row.buyNum ? value : "0";
                value=value.trim()
                if (value<= row.buyNum) {
                    row.depNum = value;
                } else {
                    this.$message({ type: 'info', message: '寄存数量必须小于等于购买数量' })
                    return;
                }
                row.deposit = "1";
                row.orderId = row.id;
                this.$API.order.reqDeposit({
                    ...row, ...{
                        userId: this.dialogTable.userId,
                        phone: this.dialogTable.phone,
                    }
                }).then(({ code, msg }) => {
                    this.$message({
                        type: code === 200 ? 'success' : 'error',
                        message: msg || "寄存成功"
                    });
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消'
                });
            });
        },
        // 弹出关闭之前的回调
        beforeClose() {
            this.dialogVisible = false;
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
