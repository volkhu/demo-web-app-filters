// this is referenced from jest.config.js
import Vue from "vue";
import Vuetify from "vuetify";
import fs from "fs";

// since this project is using Vuetify it needs to be initialized before running any tests
Vue.use(Vuetify);

// and also load runtime config as a mixin
const runtimeConfigContents = fs.readFileSync("public/runtimeConfig.json");
const runtimeConfig = JSON.parse(runtimeConfigContents);
const runtimeConfigMixin = {
  data: () => ({
    RUNTIME_CONFIG: runtimeConfig,
  }),
};
Vue.mixin(runtimeConfigMixin);
