<template>
    <div>
        <el-row :gutter="10">
            <el-col :span="12">
                <Search></Search>
            </el-col>
            <el-col :span="12">
                <Category></Category>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import Search from './Search.vue'
import Category from './Category.vue'
export default {
    name: "Observe",
    components: {
        Search,
        Category,
    }
}
</script>

<style>

</style>