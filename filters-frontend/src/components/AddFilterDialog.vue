<template>
  <v-card>
    <v-card-title class="pb-1">
      <v-row>
        <v-col>Add Filter</v-col>
        <v-col class="text-right">
          <v-btn icon :disabled="savingFilter" @click="$emit('close-dialog')"
            ><v-icon>mdi-close</v-icon></v-btn
          >
        </v-col>
      </v-row>
    </v-card-title>
    <form @submit.prevent="saveFilter">
      <v-container>
        <!-- Filter name row -->
        <v-row>
          <v-col cols="2" class="pl-0">
            <v-subheader>Filter name</v-subheader>
          </v-col>
          <v-col cols="3">
            <v-text-field
              class="pt-1"
              outlined
              dense
              hide-details="auto"
              required
              maxlength="64"
              v-model="filter.name"
              :disabled="savingFilter"
            ></v-text-field>
          </v-col>
        </v-row>

        <!-- Criteria list rows -->
        <criterion-row
          v-for="(criterion, index) in filter.criteria"
          :key="criterion.vueId"
          :index="index"
          :criterion="criterion"
          :showRowPrefix="index == 0"
          :allowDeletion="filter.criteria.length > 1"
          :disabled="savingFilter"
          @change-type="changeCriterionType"
          @change-operator="changeCriterionOperator"
          @change-value="changeCriterionValue"
          @delete-criterion="deleteCriterion"
        />

        <!-- Add criterion button row -->
        <v-row>
          <v-col cols="2"> </v-col>
          <v-col class="text-center">
            <v-btn
              color="secondary"
              @click="addCriterion"
              :disabled="savingFilter"
            >
              <v-icon class="mr-2">mdi-plus</v-icon>ADD ROW
            </v-btn>
          </v-col>
          <v-col cols="1"></v-col>
        </v-row>

        <!-- Selection row -->
        <v-row>
          <v-col cols="2" class="pl-0">
            <v-subheader>Selection</v-subheader>
          </v-col>
          <v-col>
            <v-radio-group
              class="mt-2"
              v-model="filter.selection"
              row
              dense
              hide-details="auto"
              :disabled="savingFilter"
            >
              <v-radio
                v-for="(value, key) in RUNTIME_CONFIG.SELECTION_VALUES"
                :key="key"
                :label="value"
                :value="key"
              ></v-radio>
            </v-radio-group>
          </v-col>
        </v-row>

        <!-- Form action buttons row -->
        <v-row>
          <v-col class="text-center">
            <v-btn
              color="secondary"
              style="width: 120px"
              @click="$emit('close-dialog')"
              :disabled="savingFilter"
            >
              CLOSE
            </v-btn>
            <v-btn
              class="ml-5"
              color="primary"
              style="width: 120px"
              type="submit"
              :disabled="savingFilter"
              >SAVE
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </form>
    <v-progress-linear v-show="savingFilter" indeterminate></v-progress-linear>
  </v-card>
</template>

<script>
import _ from "lodash";
import axios from "axios";
import CriterionRow from "./CriterionRow.vue";

export default {
  components: { CriterionRow },

  data: () => ({
    filter: {
      name: null,
      criteria: [],
      selection: null,
    },

    savingFilter: false,
  }),

  methods: {
    /**
     * Set add filter dialog's name and selection fields to their
     * default values from the runtime config after it has been
     * loaded.
     */
    setFormDefaultValues() {
      this.filter.name = this.RUNTIME_CONFIG.DEFAULT_NAME;
      this.filter.selection = this.RUNTIME_CONFIG.DEFAULT_SELECTION;
    },

    /**
     * Add a criterion to the list with default values. Called either by the
     * user by clicking the "add row" button or by the system when the dialog
     * is first created (to create the initial criterion).
     */
    addCriterion() {
      this.filter.criteria.push({
        vueId: _.uniqueId(), // give this criteria an unique id for Vue v-for loop
        type: null,
        operator: null,
        value: null,
        valueFormat: null,
      });

      // initialize this criterion with the default type
      this.changeCriterionType({
        index: this.filter.criteria.length - 1,
        newType: this.RUNTIME_CONFIG.DEFAULT_CRITERION_TYPE,
      });
    },

    /**
     * Change a criterion's type. The index and the new type is specified
     * inside the event object.
     */
    changeCriterionType(event) {
      const newTypeConfig = this.RUNTIME_CONFIG.CRITERION_TYPES[event.newType];
      let criterion = this.filter.criteria[event.index];

      criterion.type = event.newType;
      criterion.operator = Object.keys(newTypeConfig.OPERATORS)[0];
      criterion.valueFormat = newTypeConfig.VALUE_FORMAT;
      criterion.value = newTypeConfig.DEFAULT_VALUE;

      if (criterion.valueFormat === "DATE" && criterion.value === null) {
        // use current date instead
        criterion.value = new Date().toISOString().substr(0, 10);
      }
    },

    /**
     * Change a criterion's operator. The index and the new operator
     * is specified inside the event object.
     */
    changeCriterionOperator(event) {
      this.filter.criteria[event.index].operator = event.newOperator;
    },

    /**
     * Change a criterion's value. The index and the new value
     * is specified inside the event object.
     */
    changeCriterionValue(event) {
      this.filter.criteria[event.index].value = event.newValue;
    },

    /**
     * Delete the criterion with the specified index from the list.
     */
    deleteCriterion(index) {
      this.filter.criteria.splice(index, 1);
    },

    /**
     * Save the constructed filter and its data to the back end and notify the parent component.
     */
    async saveFilter() {
      this.savingFilter = true;

      try {
        await axios.post("/filters", this.filter);
        this.$emit("filter-saved");
      } catch (error) {
        alert(`Cannot save filter. ${error}`);
      }

      this.savingFilter = false;
    },
  },

  mounted() {
    this.setFormDefaultValues();

    // there must be at least 1 criterion at all times so add it when the dialog is initialized
    this.addCriterion();
  },
};
</script>
