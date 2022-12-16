/*
 * @Author: Topskys
 * @Date: 2022-10-07 10:46:56
 * @LastEditTime: 2022-11-09 00:08:32
 */
import mockRequest from '../../utils/mock-request'

const state = {
    list: {},
};
const mutations = {
    GETDATA(state, val) {
        state.list = val
    }
};
const actions = {
    // 发请求获取首页的mock数据
    async getHomeData({ commit }) {
        let { data } = await mockRequest.get('/home/list')
        if (data.code == 200) {
            commit('GETDATA', data.data)
        }
    },

    // 发请求获取首页图表数据
    // async getHomeData({ commit }) {
    //     let { code, data, msg } = await this.$API.home.reqHomeChart();
    //     code === 200 ? commit('GETDATA', data) : this.$message({ type: "error", message: msg })
    // },
};
const getters = {};

export default {
    state,
    mutations,
    actions,
    getters,
}