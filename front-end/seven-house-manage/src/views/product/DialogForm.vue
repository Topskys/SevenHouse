<!--
 * @Author: Topskys
 * @Date: 2022-10-21 11:44:35
 * @LastEditTime: 2022-11-24 16:36:19
-->
<template>
    <div class="dialogForm">
        <el-dialog :title="config.form.alcoId ? '修改' : '新增'" :visible.sync="config.dialogVisible" width="50%"
            :before-close="handleClose" appendToBody>
            <el-form ref="form" :model="config.form" label-width="80px" :rules="formRules">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="名称" required prop="name">
                            <el-input v-model="config.form.name" placeholder="名称"></el-input>
                        </el-form-item>
                        <el-form-item label="描述" required prop="alcoDec">
                            <el-input v-model="config.form.alcoDec"></el-input>
                        </el-form-item>
                        <!-- <el-form-item label="库存" required prop="alcoNumber">
                            <el-input v-model="config.form.alcoNumber" placeholder="库存" type="number"></el-input>
                        </el-form-item> -->
                        <el-form-item label="价格" required prop="alcoOriginprice">
                            <el-input v-model="config.form.alcoOriginprice" placeholder="价格"></el-input>
                        </el-form-item>
                        <el-form-item label="利润" required prop="alcoProfit">
                            <el-input v-model="config.form.alcoProfit" placeholder="利润"></el-input>
                        </el-form-item>
                        <el-form-item label="类别" required prop="alcoTypeName">
                            <el-select placeholder="请选择" v-model="config.form.alcoTypeName">
                                <el-option :label="item.name" :value="item.id" v-for="item in categoryList"
                                    :key="item.id">
                                </el-option>
                                <!-- <el-option :label="item" :value="`${attr.id}:${attrValue.id}`" v-for="item in 5" :key="item">
                                                </el-option> -->
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="原产地" required prop="alcoOrigin">
                            <el-input v-model="config.form.alcoOrigin" placeholder="原产地"></el-input>
                        </el-form-item>
                        <el-form-item label="供货商" required prop="alcoSupplier">
                            <el-input v-model="config.form.alcoSupplier" placeholder="供货商"></el-input>
                        </el-form-item>
                        <el-form-item label="规格" required prop="alcoCap">
                            <el-input v-model="config.form.alcoCap" placeholder="规格"></el-input>
                        </el-form-item>
                        <el-form-item label="备注">
                            <el-input v-model="config.form.alcoRemarks" placeholder="备注"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="图片">
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
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "DialogForm",
    props: {
        config: {
            type: Object,
            default: {}
        }
    },
    data() {
        return {
            dialogVisibleImg: false,
            dialogImageUrl: '',
            imgList: [],
            categoryList: [],
            // 表单验证
            formRules: {
                name: [{ required: true, message: "不能为空", trigger: "blur" }],
                alcoDec: [{ required: true, message: "不能为空", trigger: "blur" }],
                alcoCap: [{ required: true, message: "不能为空", trigger: "blur" }],
                alcoOriginprice: [{ required: true, message: "不能为空", trigger: "blur" }],
                alcoProfit: [{ required: true, message: "不能为空", trigger: "blur" }],
                alcoSupplier: [{ required: true, message: "不能为空", trigger: "blur" }],
                alcoType: [{ required: true, message: "不能为空", trigger: "blur" }],
                alcoOrigin: [{ required: true, message: "不能为空", trigger: "blur" }],
            },
            formNeed: {
                alcoNumber: "",
                alcoStatus: "",
                alcoTypeName: "",
                alcoholsSku: [
                    {
                        alcoId: "",
                        createTime: "",
                        id: 0,
                        skuCap: "",
                        skuSellprice: 0,
                        skuStatus: "",
                        skuStock: 0,
                        updateTime: ""
                    }
                ],
                time: "",
                updateTime: ""
            }
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.$API.category.reqCategoryList().then(({ code, data }) => {
                code === 200 && (this.categoryList = data || [])
            })
        },
        cancel() {
            this.config.dialogVisible = false
            this.imgList = [];
        },
        confirm() {
            // this.config.form.alcoType = this.categoryList.forEach((item, index) => {
            //     if ((item == this.config.form.alcoType) || (index == this.config.form.alcoType)) {
            //         return item.alcoTypeName
            //     }
            // });
             this.config.form.alcoType=this.config.form.alcoTypeName
            this.config.form = { ...this.config.form, ...this.formNeed }
            console.log(this.config)

            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    // this.$API.product.reqAddOrUpdateProduct(this.config.form).then(({ code, msg }) => {
                    //     code === 200 && this.$emit("getPageList");
                    //     this.$message({ type: code === 200 ? 'success' : "error", message: msg })
                    // }).catch((err) => {
                    //     this.$message({ type: 'error',})
                    //     console.log(err)
                    // });
                    const { code, msg } = await this.$API.product.reqAddOrUpdateProduct(this.config.form);
                    if (code == 200) {
                        this.$emit('getPageList');
                        this.dialogVisible = false;
                    }
                    this.$message({ type: code === 200 ? 'success' : "error", message: msg })
                } else {
                    this.$message('表单验证错误')
                    console.log("validate is error");
                }
            });
            this.config.dialogVisible = false
        },
        handleClose() {
            this.config.dialogVisible = false;
            this.imgList = [];
            this.config.form = {};
        },
        handlePictureCardPreview(file) {
            // 将图片地址赋值给dialogImageUrl
            this.dialogImageUrl = file.url;
            this.dialogVisibleImg = true;
        },
        handleRemove(file, fileList) {
            // file删除的那张图，剩下的照片墙fileList
            // 含name和url等服务器不需要的字段
            this.imgList = fileList;
        },
        handleSuccess(response, file, fileList) {
            // 收集刚上传的图片
            this.imgList = fileList;
            this.config.form.imgUrl = response.data
        },
    },
    watch: {
        'config.dialogVisible': {
            handler(newVal) {
                newVal && this.init();
            }
        }
    }
}
</script>

<style>

</style>
