<!--
 * @Author: Topskys
 * @Date: 2022-11-07 15:58:31
 * @LastEditTime: 2022-11-08 16:08:41
-->
<template>
    <div class="dialogForm">
        <el-dialog :title="config.id ? '修改' : '新增'" :visible.sync="config.dialogVisible"
            :width="config.width ? config.width : '50vw'" :before-close="handleClose" appendToBody>
            <slot></slot>
            <span slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "Dialog",
    props: ['config'],
    methods: {
        cancel() {
            this.$emit("cancelHandler")
            this.config.dialogVisible = false
        },
        confirm() {
            this.$emit("confirmHandler")
            this.config.dialogVisible = false
        },
        handleClose() {
            this.config.dialogVisible = false
            // this.config = {}
        },
    },
    watch: {
        'config': {
            handler() {
                // console.log(this.config)
            }
        }
    }
}
</script>

<style>

</style>