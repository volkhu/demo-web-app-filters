import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import axios from "axios";
import { getRuntimeConfigMixin } from "@/mixins/runtimeConfigMixin";

Vue.config.productionTip = false;

const runtimeConfigURL = "/runtimeConfig.json";
getRuntimeConfigMixin(runtimeConfigURL)
  .then((mixin) => {
    // set API base URL for axios requests and pass config to components as a mixin
    axios.defaults.baseURL = mixin.data().RUNTIME_CONFIG.API_BASE_URL;
    Vue.mixin(mixin);

    // create Vue instance
    new Vue({
      vuetify,
      render: (h) => h(App),
    }).$mount("#app");
  })
  .catch((error) => {
    alert(
      `Failed to load runtime config from "${runtimeConfigURL}"\n\n${error}`
    );
  });
