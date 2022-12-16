<template>
    <div>
        <el-form :model="form" ref="form" :inline="true" @keyup.enter.native="getPageList()" style="margin-top:20px">
            <el-form-item label="商品名称：">
                <el-input v-model="form.input" prefix-icon="el-icon-search" clearable placeholder="请输入内容">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getPageList()">查询</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addOrEdit">新增</el-button>
            </el-form-item>
            <!-- <el-form-item>
                <el-button type="primary" @click="addOrEdit">导入xlxs</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addOrEdit">导出xlxs</el-button>
            </el-form-item> -->
        </el-form>
        <!-- 表格 -->
        <el-table :data="records" style="width:100%" border v-loading="listLoading">
            <!-- <el-table-column prop="alcoNumber" label="编号" width="80" align="center"></el-table-column> -->
            <el-table-column prop="name" label="商品名称" width="100" align="center"></el-table-column>
            <el-table-column prop="alcoDec" label="描述"></el-table-column>
            <el-table-column label="图片" width="100px" align="center">
                <template slot-scope="{ row, $index }">
                    <img :src="row.imgUrl" :alt="$index" style="width:80px;height:80px;border-radius: 5px;">
                </template>
            </el-table-column>
            <el-table-column prop="alcoOriginprice" label="价格" width="60" align="center">
                <template slot-scope="{ row}">
                    <span>{{ '￥' + row.alcoOriginprice }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="alcoProfit" label="利润" width="60" align="center">
                <template slot-scope="{ row}">
                    <span>{{ row.alcoProfit*100 +'%'  }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="alcoCap" label="规格" width="60" align="center">
                <template slot-scope="{ row}">
                    <span>{{ row.alcoCap + 'ml' }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="alcoTypeName" label="类别" width="80" align="center"></el-table-column>
            <el-table-column prop="alcoOrigin" label="产地" width="80" align="center"></el-table-column>
            <el-table-column prop="alcoSupplier" label="供货商"  align="center"></el-table-column>
            <el-table-column prop="alcoRemarks" label="备注" align="center"></el-table-column>
            <el-table-column prop="alcoStatus" label="状态" width="60" align="center">
                <template slot-scope="{ row}">
                    <span>{{ row.alcoStatus == '0' ? '正常' : row.alcoStatus == '1' ? '下架' : '删除' }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="time" label="入库日期" width="100" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{ row,$index }">
                    <el-button type="success" size="mini" icon="el-icon-sort-up" v-if="row.alcoStatus == 1"
                        @click="pushSale(row)">
                    </el-button>
                    <!-- icon="el-icon-sort-down" icon="el-icon-sort-up" -->
                    <el-button type="info" size="mini" icon="el-icon-sort-down" v-else @click="pullSale(row)">
                    </el-button>
                    <el-button type="warning" icon="el-icon-edit" size="mini" @click="addOrEdit(row)">
                    </el-button>
                    <el-button type="info" icon="el-icon-info" size="mini" @click="spreadSku(row)">
                    </el-button>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="del(row, $index, 'pro')">
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination style="text-align: left" @size-change="handleSizeChange" @current-change="getPageList"
            :current-page="page" :page-sizes="[3, 5, 10]" :page-size="limit"
            layout="prev, pager, next, jumper,->,sizes,total" :total="total">
        </el-pagination>
        <!-- 详情信息及新增规格 -->
        <el-dialog :title="showDialogTable ? `规格详情` : `新增规格`" :visible.sync="dialogVisible" width="50%"
            :before-close="handleClose" appendToBody>
            <el-table :data="dialogTable" style="width:100%" border stripe v-if="showDialogTable">
                <el-table-column prop="skuCap" label="规格名称" align="center"> </el-table-column>
                <el-table-column prop="skuSellprice" label="价格" align="center">
                    <template slot-scope="{ row}">
                        <span>{{ '￥' + row.skuSellprice }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="skuStock" label="库存" align="center"></el-table-column>
                <!-- <el-table-column prop="skuStatus" label="状态" align="center">
                    <template slot-scope="{ row}">
                        <span>{{ row.skuStatus == '0' ? '正常' : '异常' }}</span>
                    </template>
                </el-table-column> -->
                <el-table-column prop="createTime" label="创建日期"></el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="{ row,$index}">
                        <el-button type="primary" plain size="mini" @click="editSku(row, $index)">修改</el-button>
                        <el-button type="danger"  plain size="mini" @click="del(row, $index, 'sku')">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-form ref="form" :model="dialogForm" :rules="formRules" label-width="80px" v-else>
                <el-form-item label="名称" required prop="skuCap">
                    <el-input v-model="dialogForm.skuCap" placeholder="规格名称"></el-input>
                </el-form-item>
                <el-form-item label="价格" required prop="skuSellprice">
                    <el-input v-model="dialogForm.skuSellprice" placeholder="规格价格" type="number"></el-input>
                </el-form-item>
                <el-form-item label="库存" required prop="skuStock">
                    <el-input v-model="dialogForm.skuStock" placeholder="库存" type="number"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="confirm">{{ showDialogTable ? "新增" : "确定" }}</el-button>
            </span>
        </el-dialog>
        <!-- 新增或修改 -->
        <DialogForm :config="config" @getPageList="getPageList()"></DialogForm>
    </div>
</template>

<script>
import DialogForm from '@/views/product/DialogForm.vue'
import Dialog from '@/components/Dialog'
export default {
    name: 'ProductList',
    components: { DialogForm, Dialog },
    data() {
        return {
            page: 1,
            limit: 10,
            total: 0,// 分页器一共展示的数据
            records: [],// 存sku列表数据
            listLoading: false,
            form: {
                input: ""
            },
            config: { dialogVisible: false, form: { id: '' } }, // 新增或修改的弹窗控制变量
            // 规格详情弹窗
            dialogVisible: false,
            dialogTable: [],
            dialogForm: {
                createTime: new Date,
                skuStatus: "0",
                updateTime: new Date,
            },// 新增规格
            showDialogTable: true,// 属性详情控制表格和表单的显隐
            // 表单验证
            formRules: {
                // nickname: [{ required: true, message: "不能为空", trigger: "blur" }],
            },
        }
    },
    mounted() {
        this.getPageList()
    },
    methods: {
        // 新增按钮的回调
        addOrEdit(row) {
            this.config.dialogVisible = true
            // this.config.form.id = row.id ? row.id : ''
            this.config.form = { ...row };
        },
        // 获取列表
        async getPageList(pages = 1) {
            this.page = pages
            this.listLoading = true
            const { page, limit } = this
            const { code, data, msg } = await this.$API.product.reqProductList(this.form.input, page, limit, '')
            code == 200 && ([this.records, this.total] = [data.records || [], data.total])
            this.listLoading = false
        },
        // page/size
        handleSizeChange(limit) {
            this.limit = limit
            this.getPageList()
        },
        //  上架按钮的回调
        async pushSale(row) {
            // let res = await this.$API.sku.reqPushSale(row.id)
            // if (res.code == 200) {
            //     row.isSale = 1
            //     this.$message({ type: "success", message: "上架成功" })
            // }
            this.pullSale(row);
        },
        //  下架按钮的回调
        async pullSale(row) {
            const { code, data, msg } = await this.$API.product.reqDelOrPullProduct(row.alcoId, "sub")
            // (code == 200) &&( row.alcoStatus = '1',this.getPageList());
            if (code == 200) {
                row.alcoStatus = '1';
                this.getPageList();
            }
            this.$message({ type: code == 200 ? "success" : "error", message: msg })
        },
        // 展开详情
        spreadSku(row) {
            this.dialogForm = {};
            this.dialogTable = [...row.alcoholsSku];
            this.dialogForm.alcoId = row.alcoId;
            this.dialogVisible = true;
            this.showDialogTable = true;
        },
        // 删除按钮
        del(row, index, which) {
            this.$confirm(`你确定删除第${index + 1}项数据?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(async (res) => {
                    const { code,msg } = await (which == 'pro' ? this.$API.product.reqDelOrPullProduct(row.alcoId, "del") : this.$API.product.reqDelProductSKU(row.id));
                    code === 200 && this.getPageList(this.records.length > 1 ? this.page : this.page - 1);//还有数据则停留，否则page-1
                    this.$message({
                        type: code == 200 ? "success" : "error",
                        message: msg,
                    });
            }).catch((e) => {
                this.$message({
                    type: "info",
                    message: "已取消删除",
                });
                console.log(e)
            });
            this.dialogVisible = false;
        },
        // 弹窗确定按钮
        handleClose() {
            this.dialogVisible = false
            this.dialogTable = []
        },
        cancel() {
            this.dialogVisible = false;
            this.dialogForm = {};
        },
        confirm() {
            this.showDialogTable = this.showDialogTable ? false : true;
            this.showDialogTable && this.$API.product.reqAddOrUpdateSKU(this.dialogForm).then(({ code, msg }) => {
                code == 200 && this.getPageList();
                this.$message({ type: code == 200 ? "success" : "error", message: msg });
                this.dialogVisible = false;
            })
        },
        // 修改规格
        editSku(row,index){
           this.showDialogTable=false;
           row.createTime='';
           row.updateTime='';
           row.skuStatus=null;
           this.dialogForm={...row};
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

<style scoped>
/* scoped：局部样式
1:scoped属性的作用-------加上scoped的作用是只是对于当前的组件有用（样式) 对于某一个组件，
如果style添加上scoped属性，给当前子组件的结构中都添加上一个data-v-xXxx自定属性会发现vue是通过属性选择器，
给需要添加的元素添加上样式 h3[data-v-7ba5bd90]{color:red;}
2:子组件的跟标签（拥有父组件当中自定义属性:一样的)，如果子组件的根节点，和父组件中书写的样式相同，也会添加上相应的样式。

3:注意，如果父组件加了scoped，还想影响子组件的样式，可以使用深度选择器。
深度选择器可实现样式穿透：
css：>>>
scss：::v-deep
less：/deep/

*/
.el-row .el-col-5 {
    font-size: 18px;
    text-align: right;
}

.el-col {
    margin-right: 10px;
}

/* >>>.el-carousel__button {
  width: 10px;
  height: 10px;
  background-color: red;
  border-radius: 50%;
} */
</style>

<style>
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

/* .el-carousel__button解构复杂，不能放在scoped中（某些），要放在scoped需要使用深度选择器 */
.el-carousel__button {
    width: 10px;
    height: 10px;
    background-color: #fff;
    border-radius: 50%;
}
</style>
