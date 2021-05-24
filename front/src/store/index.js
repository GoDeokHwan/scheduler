import { createStore } from 'vuex'
import axios from 'axios'

const LOCAL_STORAGE_TOKEN_KEY = 'token'

export default createStore({
  state: {

  },
  mutations: {
  },
  actions: {
    login: function ({commit}, loginParam) {
      let loginResponse = {
        isSuccess: false,
        data: {}
      }
      return axios({
        method: 'post',
        url: 'http://localhost:8180/auth/login',
        data: loginParam,
        headers: {
          'X-Requested-With': 'XMLHttpRequest',
          'Access-Control-Allow-Credentials': true
        }
      }).then(res => {
        const responseData = res.data
        if (res.status === 200 && responseData.token) {
          const token = responseData.token
          localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token)
          loginResponse.isSuccess = true
        }
        loginResponse.data = res.data
      }).catch(err => {
        alert(err)
      }).finally(() => {
        return loginResponse
      })
    }
  },
  modules: {
  }
})
