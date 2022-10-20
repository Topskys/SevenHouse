<template>
    <div>
        <el-form ref="form" :model="skuInfo" label-width="80px">
            <el-form-item label="SPU名称">{{spu.spuName}} </el-form-item>
            <el-form-item label="SKU名称">
                <el-input v-model="skuInfo.skuName" placeholder="sku名称"></el-input>
            </el-form-item>
            <el-form-item label="价格(元)">
                <el-input v-model="skuInfo.price" placeholder="价格(元)" type="number"></el-input>
            </el-form-item>
            <el-form-item label="重量(千克)">
                <el-input v-model="skuInfo.weight" placeholder="重量(千克)"></el-input>
            </el-form-item>
            <el-form-item label="规格描述">
                <el-input v-model="skuInfo.skuDesc" placeholder="规格描述" type="textarea" rows="4"></el-input>
            </el-form-item>
            <el-form-item label="平台属性">
                <el-form ref="form" :inline="true" label-width="80px">
                    <el-form-item :label="attr.attrName" v-for="(attr) in attrInfoList" :key="attr.id">
                        <el-select placeholder="请选择" v-model="attr.attrIdAndAttrValueId">
                            <el-option :label="attrValue.valueName" :value="`${attr.id}:${attrValue.id}`"
                                v-for="attrValue in attr.attrValueList" :key="attrValue.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-form-item>
            <el-form-item label="销售属性">
                <el-form ref="form" :inline="true" label-width="80px">
                    <el-form-item :label="saleAttr.saleAttrName" v-for="saleAttr in spuSaleAttrList" :key="saleAttr.id">
                        <el-select placeholder="请选择" v-model="saleAttr.saleAttrIdAndSaleAttrValueId">
                            <el-option :label="saleAttrValue.saleAttrValueName"
                                :value="`${saleAttr.id}:${saleAttrValue.id}`"
                                v-for="saleAttrValue in saleAttr.spuSaleAttrValueList" :key="saleAttrValue.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-form-item>
            <el-form-item label="图片列表">
                <el-table :data="spuImageList" border style="width:100%" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" prop="" width="80"></el-table-column>
                    <el-table-column prop="" label="图片" width="width">
                        <template slot-scope="{row,$index}">
                            <img :src="row.imgUrl" :alt="$index" style="width:100px;height: 100px">
                        </template>
                    </el-table-column>
                    <el-table-column prop="imgName" label="名称" width="width"></el-table-column>
                    <el-table-column prop="" label="操作" width="width">
                        <template slot-scope="{row,$index}">
                            <el-button type="primary" v-if="row.isDefault==0" @click="changeDefault(row,$index)">设置默认
                            </el-button>
                            <el-button v-else>默认</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="save">保存</el-button>
                <el-button @click="cancel">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    name: "SkuForm",
    data() {
        return {
            spuImageList: [],
            spuSaleAttrList: [],
            attrInfoList: [],
            // 收集提交对象
            skuInfo: {
                category3Id: null,
                spuId: null,
                tmId: null,
                price: null,
                skuDefaultImg: "",
                skuDesc: "",
                skuName: "",
                weight: "",
                // 平台属性
                skuAttrValueList: [
                    // {
                    //     attrId: null,
                    //     valueId: null,
                    // }
                ],
                skuImageList: [
                    // {
                    //     id: null,
                    //     skuId: null,
                    //     imgName: "",
                    //     imgUrl: "",
                    //     attrName: "",
                    //     isDefault: "",
                    //     spuImgId: null,
                    // }
                ],
                // 销售属性
                skuSaleAttrValueList: [
                    // {
                    // skuId: null,
                    // spuId: null,
                    // saleAttrId: null,
                    // saleAttrName: "",
                    // saleAttrValueId: null,
                    // saleAttrValueName: "",
                    // }
                ],
            },
            spu: {},
            // 收集图片的数据字段，先缺少isDefault字段，故imageList为预存储数组
            imageList: [],
        }
    },
    methods: {
        // 初始化skuForm数据
        async initSkuData(category1Id, category2Id, spu) {
            // 收集父组件给予的数据
            this.spu = spu
            this.skuInfo.spuId = spu.id
            this.skuInfo.tmId = spu.tmId
            this.skuInfo.category3Id = spu.category3Id
            // 请求
            let res1 = await this.$API.spu.reqSpuImageList(spu.id)
            if (res1.code == 200) {
                let list1 = res1.data
                list1.forEach(item => {
                    item.isDefault = 0
                })
                this.spuImageList = list1
            }
            let res2 = await this.$API.spu.reqSpuSaleAttrList(spu.id)
            if (res2.code == 200) {
                this.spuSaleAttrList = res2.data
            }
            let res3 = await this.$API.spu.reqAttrInfoList(category1Id, category2Id, spu.category3Id)
            if (res3.code == 200) {
                this.attrInfoList = res3.data
            }
        },

        // 表格复选框按钮事件，获取用户选中的图片信息，缺少isDefault字段
        handleSelectionChange(params) {
            this.imageList = params
        },

        // 设置默认，唯一
        changeDefault(row, index) {
            this.spuImageList.forEach(item => {
                item.isDefault = 0
            })
            row.isDefault = 1
            this.skuInfo.skuDefaultImg = row.imgUrl
        },

        // 保存按钮
        async save() {
            // 整理参数，准备提交服务器
            // 整理平台属性
            const { attrInfoList, skuInfo, spuSaleAttrList, imageList } = this
            // 方法一：
            // let tmpArr=[]
            // attrInfoList.forEach(item=>{
            //     if (item.attrIdAndAttrValueId) {// attrIdAndAttrValueId：代表用户已经选择
            //         const [attrId, valueId]=item.attrIdAndAttrValueId.split(':')
            //         // 将整理好的数据存入临时tmpArr数组
            //         let obj = { attrId, valueId }
            //         tmpArr.push(obj)
            //     }
            // })
            // 方法二：
            skuInfo.skuAttrValueList = attrInfoList.reduce((prev, item) => {
                if (item.attrIdAndAttrValueId) {
                    const [attrId, valueId] = item.attrIdAndAttrValueId.split(':')
                    prev.push({ attrId, valueId })
                }
                return prev
            }, [])
            // 整理销售属性
            skuInfo.skuSaleAttrValueList = spuSaleAttrList.reduce((prev, item) => {
                if (item.attrIdAndAttrValueId) {
                    const [saleAttrId, saleAttrValueId] = item.saleAttrIdAndSaleAttrValueId.split(':')
                    prev.push({ saleAttrId, saleAttrValueId })
                }
                return prev
            }, [])
            // 整理图片的数据
            skuInfo.skuImageList = imageList.map((item) => {
                return {
                    spuImgId: item.id,
                    imgName: item.imgName,
                    imgUrl: item.imgUrl,
                    isDefault: item.isDefault,
                }
            })
            let res = await this.$API.spu.reqAddSku(skuInfo)
            if(res.code == 200){
                this.$message({ type: "success", message: "保存成功" })
                this.$emit('changeSceneFromSku',0)
            }
        },

        // 取消按钮
        cancel() {
            this.$emit('changeSceneFromSku', 0)
            Object.assign(this._data, this.$options.data())
        },
    }
}
</script>

<style>

</style>