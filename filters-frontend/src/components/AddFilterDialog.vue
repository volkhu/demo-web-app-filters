<template>
  <v-card>
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
                    v-model="filter.name"
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
              <v-row
                v-for="criteria in filter.criteria"
                v-bind:key="criteria.id"
              >
                <!-- Criteria type -->
                <v-col>
                  <v-select
                    :items="criteriaTypes"
                    v-model="criteria.type"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                    @change="criteriaTypeChanged(criteria.id)"
                  ></v-select>
                </v-col>

                <!-- Criteria operator -->
                <v-col>
                  <v-select
                    :items="criteriaOperators[criteria.type]"
                    v-model="criteria.operator"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                  ></v-select>
                </v-col>

                <!-- Criteria value (different input type depending on selected criteria type) -->
                <v-col>
                  <!-- Number input for "Amount" criteria type -->
                  <v-text-field
                    v-if="criteria.type == 'Amount'"
                    v-model="criteria.value"
                    type="number"
                    outlined
                    dense
                    hide-details="auto"
                    required
                    maxlength="64"
                    class="pt-1"
                  ></v-text-field>

                  <!-- Text input for "Title" criteria type -->
                  <v-text-field
                    v-if="criteria.type == 'Title'"
                    v-model="criteria.value"
                    outlined
                    dense
                    hide-details="auto"
                    required
                    maxlength="64"
                    class="pt-1"
                  ></v-text-field>

                  <!-- Date picker input for "Date" criteria type (also update datepicker
                  instantly on selection with @change event since we are using it on its
                  own without an accompanying text field -->
                  <v-date-picker
                    v-if="criteria.type == 'Date'"
                    v-model="criteria.value"
                    class="pt-1"
                    show-current="true"
                    first-day-of-week="1"
                    @change="$forceUpdate()"
                  ></v-date-picker>
                </v-col>

                <!-- Delete criteria button (disable if there is only one last criteria left) -->
                <v-col cols="1">
                  <v-btn
                    icon
                    class="mt-1"
                    :disabled="filter.criteria.length == 1"
                    @click="deleteCriteria(criteria.id)"
                    ><v-icon>mdi-delete</v-icon></v-btn
                  >
                </v-col>
              </v-row>

              <!-- Add criteria button inner row -->
              <v-row>
                <v-col class="text-center">
                  <v-btn color="secondary" @click="addCriteria">
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
              v-model="filter.selection"
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
              >SAVE
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </form>
  </v-card>
</template>

<script>
import _ from "lodash";

export default {
  data: () => ({
    // add filter data object
    filter: {
      name: "",
      criteria: [],
      selection: "1",
    },

    // data and default values for criteria boxes
    criteriaTypes: ["Amount", "Title", "Date"],
    criteriaOperators: {
      Amount: ["greater than", "equals", "less than"],
      Title: ["starts with", "equals", "contains", "ends with"],
      Date: ["from", "until"],
    },
    criteriaDefaultValues: {
      Amount: 0,
      Title: "",
      Date: new Date().toISOString().substr(0, 10), // set the date picker to today as default
    },
  }),

  methods: {
    /**
     * Close this dialog by notifying the parent component as such.
     * Called when either the dialog "close" or "X" button has been pressed.
     */
    closeAddFilterDialog() {
      this.$emit("close");
    },

    /**
     * Add a new criteria to the list with default values. Called either by the
     * user by clicking the "add row" button or by the system when the dialog
     * is first created (to create the initial criteria).
     */
    addCriteria() {
      this.filter.criteria.push({
        id: _.uniqueId(), // give this criteria an unique id for Vue v-for loop
        type: "Amount",
      });

      // set initial criteria operator and value depending on the type
      this.criteriaTypeChanged(
        this.filter.criteria[this.filter.criteria.length - 1].id
      );
    },

    /**
     * A criteria type has been changed. Update available criteria
     * operators and values.
     */
    criteriaTypeChanged(criteriaId) {
      var index = this.filter.criteria.findIndex(
        (criteria) => criteria.id == criteriaId
      );
      var newType = this.filter.criteria[index].type;

      // reset new criteria operator and default value based on the new type
      this.filter.criteria[index].operator = this.criteriaOperators[newType][0];
      this.filter.criteria[index].value = this.criteriaDefaultValues[newType];
    },

    /**
     * Delete an element from the filter criteria array.
     */
    deleteCriteria(rowId) {
      this.filter.criteria = this.filter.criteria.filter(
        (criteria) => criteria.id != rowId
      );
    },

    /**
     * Save the new filter and its data to the back end and notify the parent component.
     */
    saveNewFilter() {
      console.log(JSON.stringify(this.filter));
      this.$emit("save");
    },
  },

  mounted() {
    // there must be at least 1 criteria so add it when the dialog is initialized
    this.addCriteria();
  },
};
</script>
