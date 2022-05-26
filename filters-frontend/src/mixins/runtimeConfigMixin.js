import axios from "axios";

export async function getRuntimeConfigMixin(runtimeConfigURL) {
  const response = await axios.get(runtimeConfigURL);
  const runtimeConfig = response.data;

  const runtimeConfigMixin = {
    data: () => ({
      RUNTIME_CONFIG: runtimeConfig,
    }),
  };

  return runtimeConfigMixin;
}
