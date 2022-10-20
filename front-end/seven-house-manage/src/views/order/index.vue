<!--
 * @Author: Topskys
 * @Date: 2022-10-19 20:14:53
 * @LastEditTime: 2022-10-20 14:32:28
-->
<template>
    <div class="order__container">
        <el-table :data="list" style="width: 100%" border>
            <el-table-column type="index" label="序号" width="80px" align="center">
            </el-table-column>
            <el-table-column prop="orderId" label="订单编号" width="width"></el-table-column>
            <el-table-column prop="name" label="名称" width="width"></el-table-column>
            <!-- <el-table-column prop="imgUrl" label="图片" width="80px" align="center">
                <template slot-scope="{ row } ">
            <img :src="row.imgUrl" style="width: 50px; height: 50px" />
                </template>
            </el-table-column>  -->
            <el-table-column prop="price" label="单价" width="width"></el-table-column>
            <el-table-column prop="count" label="数量" width="width"></el-table-column>
            <el-table-column prop="status" label="状态" width="width"></el-table-column>
            <el-table-column prop="prop" label="操作" width="width">
                <!-- 插槽 -->
                <template slot-scope="{ row, $index }">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="updateOrder(row,$index )">确认
                    </el-button>
                    <!-- <el-button type="warning" icon="el-icon-edit" size="mini" @click="updateOrder(row,$index )">修改
                    </el-button> -->
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteOrder(row,$index )">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <el-pagination @size-change="handleSizeChange" @current-change="getPageList" :current-page="6"
            :page-sizes="[3, 5, 10]" :page-size="10" layout=" prev, pager, next, jumper,->,sizes,total " :total="total"
            style="margin-top: 20px; text-align: center" :pager-count="7">
        </el-pagination>
    </div>
</template>

<script>
export default {
    name: "OrderList",
    data() {
        return {
            list: [
                {
                    name: "订单1",
                    orderId: "XF3274690",
                    count: 1,
                    price: 8.9,
                    status: "交易成功",
                    imgUrl: "@/assets/images/girl.jpg"
                }
            ],
            page: 1, // 代表的分页器第几页
            limit: 5,// 当前页数展示数据条数
            total: 0, // 数据总条数
        }
    },
    mounted() { // 组件挂载完毕发送请求
        // this.getPageList();
    },
    methods: {
        // 获取订单列表
        async getPageList(pager = 1) {
            this.page = pager;
            const { page, limit } = this;// 解构出参数
            const { code, data } = await this.$API.order.reqOrderList(page, limit);
            code === 200 && ([this.total, this.list] = [data.total, data.list])
        },
        //展示的数据条数发生变化时触发
        handleSizeChange(limit) {
            this.limit = limit;
            this.getPageList();
        },
        //修改
        updateOrder(row) {
            this.dialogFormVisible = true;
            //将已有的品牌信息（row）展示出来
            //...row前拷贝，否则tmForm会直接操作数据表
            this.tmForm = { ...row };
        },
        //删除品牌
        deleteOrder(row) {
            //弹窗
            this.$confirm(`你确定删除${row.tmName}?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(async () => {
                let res = await this.$API.order.reqDeleteOrder(row.id);
                if (res.code == 200) {
                    this.$message({
                        type: "success",
                        message: "删除成功!",
                    });
                    //还有数据则停留，否则page-1
                    this.getPageList(this.list.length > 1 ? this.page : this.page - 1);
                }
            }).catch(() => {
                this.$message({
                    type: "info",
                    message: "已取消删除",
                });
            });
        },
    },
}
</script>

<style>

</style>