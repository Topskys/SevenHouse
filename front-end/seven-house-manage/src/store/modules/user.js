import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
    return {
        token: getToken(),
        name: '',
        avatar: ''
    }
}

const state = getDefaultState()

const mutations = {
    RESET_STATE: (state) => {
        Object.assign(state, getDefaultState())
    },
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_NAME: (state, name) => {
        state.name = name
    },
    SET_AVATAR: (state, avatar) => {
        state.avatar = avatar
    }
}

const actions = {
    // user login 登录业务
    async login({ commit }, userInfo) {
        const { username, password } = userInfo
        let res = await login({ username: username.trim(), password: password })
        if (res.code === 20000) {
            commit('SET_TOKEN', res.data.token)
            setToken(res.data.token)
            return "login OK"
        } else {
            return Promise.reject(new Error("login fail"))
        }


        //源码
        // return new Promise((resolve, reject) => {
        //   login({ username: username.trim(), password: password }).then(response => {
        //     const { data } = response
        //     commit('SET_TOKEN', data.token)
        //     setToken(data.token)
        //     resolve()
        //   }).catch(error => {
        //     reject(error)
        //   })
        // })
    },

    // get user info
    getInfo({ commit, state }) {
        return new Promise((resolve, reject) => {
            getInfo(state.token).then(response => {
                const { data } = response

                if (!data) {
                    return reject('Verification failed, please Login again.')
                }

                const { name, avatar } = data

                commit('SET_NAME', name)
                commit('SET_AVATAR', avatar)
                resolve(data)
            }).catch(error => {
                reject(error)
            })
        })
    },

    // user logout
    logout({ commit, state }) {
        return new Promise((resolve, reject) => {
            logout(state.token).then(() => {
                removeToken() // must remove  token  first
                resetRouter()
                commit('RESET_STATE')
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },

    // remove token
    resetToken({ commit }) {
        return new Promise(resolve => {
            removeToken() // must remove  token  first
            commit('RESET_STATE')
            resolve()
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}