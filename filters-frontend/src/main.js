import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import axios from "axios";

Vue.config.productionTip = false

new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')

// define the back end REST API base URL here for all axios requests
axios.defaults.baseURL = "http://localhost:8080/api";
