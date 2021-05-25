import { createApp } from 'vue'
import vuetify from './plugins/vuetify'
import App from './App.vue'
import router from './router'
import store from './store'
import VueAxios from 'vue-axios'
import axios from 'axios'
import config from "v-calendar/src/utils/config/index";

axios.defaults.baseURL = 'http://localhost:8180'
axios.interceptors.request.use(config => {
  if (!config.url.includes('/auth/login')) {
    config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
  }
  return config
})

const app = createApp(App)
app.use(router)
app.use(store)
app.use(vuetify)
app.use(VueAxios, axios)

app.mount('#app')
