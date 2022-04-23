<template>
  <v-card v-show="isShown">
    <v-card-title class="pb-1">
      <v-row>
        <v-col>Add Filter</v-col>
        <v-col class="text-right">
          <v-btn icon @click="closeAddFilterDialog"
            ><v-icon>mdi-close</v-icon></v-btn
          >
        </v-col>
      </v-row>
    </v-card-title>
    <form @submit.prevent="saveNewFilter">
      <v-container>
        <!-- Filter name row -->
        <v-row>
          <v-col cols="2" class="pl-0">
            <v-subheader>Filter name</v-subheader>
          </v-col>
          <v-col class="ma-0 pa-0">
            <v-container>
              <v-row>
                <v-col>
                  <v-text-field
                    outlined
                    dense
                    hide-details="auto"
                    required
                    maxlength="64"
                    class="pt-1"
                    v-model="filterName"
                  ></v-text-field>
                </v-col>
                <!-- Some empty cols to align this row's name input box with next row's criteria input boxes -->
                <v-col></v-col>
                <v-col></v-col>
                <v-col cols="1"></v-col>
              </v-row>
            </v-container>
          </v-col>
        </v-row>

        <!-- Criteria list row -->
        <v-row>
          <v-col cols="2" class="pl-0">
            <v-subheader>Criteria</v-subheader>
          </v-col>
          <v-col class="ma-0 pa-0">
            <v-container>
              <!-- Inner criteria rows -->
              <v-row>
                <v-col>
                  <v-select
                    :items="criteriaTypes"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                  ></v-select>
                </v-col>
                <v-col>
                  <v-select
                    :items="criteriaTypes"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                  ></v-select>
                </v-col>
                <v-col>
                  <v-select
                    :items="criteriaTypes"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                  ></v-select>
                </v-col>
                <v-col cols="1">
                  <v-btn icon class="mt-1"><v-icon>mdi-delete</v-icon></v-btn>
                </v-col>
              </v-row>

              <!-- Add criteria button inner row -->
              <v-row>
                <v-col class="text-center">
                  <v-btn color="secondary" @click="addCriteriaRow">
                    <v-icon class="mr-2">mdi-plus</v-icon>ADD ROW
                  </v-btn>
                </v-col>
                <v-col cols="1"></v-col>
              </v-row>
            </v-container>
          </v-col>
        </v-row>

        <!-- Selection row -->
        <v-row>
          <v-col cols="2" class="pl-0">
            <v-subheader>Selection</v-subheader>
          </v-col>
          <v-col>
            <v-radio-group
              v-model="radioSelection"
              row
              dense
              hide-details="auto"
              class="mt-2"
            >
              <v-radio label="Select 1" value="1"></v-radio>
              <v-radio label="Select 2" value="2"></v-radio>
              <v-radio label="Select 3" value="3"></v-radio>
            </v-radio-group>
          </v-col>
        </v-row>

        <!-- Form action buttons row -->
        <v-row>
          <v-col class="text-center">
            <v-btn
              color="secondary"
              style="width: 120px"
              @click="closeAddFilterDialog"
            >
              CLOSE
            </v-btn>
            <v-btn
              color="primary"
              style="width: 120px"
              class="ml-5"
              type="submit"
              @click="addCriteriaRow"
              >SAVE
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </form>
  </v-card>
</template>

<script>
export default {
  props: ["isShown"],

  data: () => ({
    filterName: "",
    radioSelection: "1",

    criteriaTypes: ["Amount", "Title", "Date"],
  }),

  methods: {
    /**
     * Tell the parent component of this component's visibility status.
     */
    setIsShown(value) {
      this.$emit("update:isShown", value);
    },

    closeAddFilterDialog() {
      this.setIsShown(false);
    },

    addCriteriaRow() {},

    saveNewFilter() {},
  },
};
</script>
