<!--
 * @Author: Topskys
 * @Date: 2022-05-21 22:24:00
 * @LastEditTime: 2022-10-07 11:50:59
-->
<template>
  <div class="dashboard-container">
    <Card></Card>
    <Sale></Sale>
    <Observe></Observe>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Card from './card/index.vue';
import Sale from './sale/index.vue';
import Observe from './observe/index.vue';

export default {
  name: "Dashboard",
  computed: {
    ...mapGetters([
      "name"
    ])
  },
  components: { Card, Sale, Observe },
  mounted() { this.$store.dispatch('getHomeData') },
}
</script>

<style lang="scss" scoped>

</style>
