import { createLocalVue, shallowMount } from "@vue/test-utils";
import HomePage from "@/views/HomePage.vue";

describe("HomePage.vue", () => {
  let wrapper;

  beforeEach(() => {
    const localVue = createLocalVue();

    wrapper = shallowMount(HomePage, {
      localVue,
    });
  });

  it("renders", () => {
    expect(wrapper.exists()).toBe(true);
  });
});
