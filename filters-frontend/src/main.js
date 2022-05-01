import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import axios from "axios";

Vue.config.productionTip = false

// get runtime config
const runtimeConfigURL = "/runtimeConfig.json";
axios.get(runtimeConfigURL).then((response) => {
  const runtimeConfig = response.data;

  // set API base URL for all axios requests from now on
  axios.defaults.baseURL = runtimeConfig.API_BASE_URL;

  // create global Vue mixin to access this config from any component
  Vue.mixin({
    data: () => ({
      FILTERS_RUNTIME_CONFIG: runtimeConfig
    }),
  });

  // create Vue instance
  new Vue({
    vuetify,
    render: h => h(App)
  }).$mount('#app')
}).catch((error) => {
  alert(`Failed to load runtime config from "${runtimeConfigURL}"\n\n${error}`);
});
