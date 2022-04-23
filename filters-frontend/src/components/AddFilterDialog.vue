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
              <v-row
                v-for="criteriaRow in criteriaRows"
                v-bind:key="criteriaRow.id"
              >
                <!-- Criteria type -->
                <v-col>
                  <v-select
                    :items="criteriaTypes"
                    v-model="criteriaRow.criteriaType"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                  ></v-select>
                </v-col>

                <!-- Criteria comparison operator -->
                <v-col>
                  <v-select
                    :items="criteriaTypes"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                  ></v-select>
                </v-col>

                <!-- Criteria comparison value -->
                <v-col>
                  <v-select
                    :items="criteriaTypes"
                    outlined
                    dense
                    hide-details="auto"
                    class="pt-1"
                  ></v-select>
                </v-col>

                <!-- Delete criteria button (disable if there is only one last criteria left) -->
                <v-col cols="1">
                  <v-btn
                    icon
                    class="mt-1"
                    :disabled="criteriaRows.length == 1"
                    @click="deleteCriteriaRow(criteriaRow.id)"
                    ><v-icon>mdi-delete</v-icon></v-btn
                  >
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
    filterName: "",
    radioSelection: "1",

    criteriaTypes: ["Amount", "Title", "Date"],

    criteriaRows: [],
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
    addCriteriaRow() {
      this.criteriaRows.push({
        id: _.uniqueId(), // give this row an unique id for Vue v-for loop
        criteriaType: "Amount",
        criteriaOperator: "",
        criteriaValue: "",
      });
    },

    /**
     * Delete an element from the criteria array.
     */
    deleteCriteriaRow(rowId) {
      this.criteriaRows = this.criteriaRows.filter(
        (criteriaRow) => criteriaRow.id != rowId
      );
    },

    /**
     * Save the new filter and its data to the back end and notify the parent component.
     */
    saveNewFilter() {
      console.log(JSON.stringify(this.criteriaRows));
      this.$emit("save");
    },
  },

  mounted() {
    this.addCriteriaRow(); // there must be at least 1 criteria so add it when the component is initialized
    console.log("MOUNTED");
  },
};
</script>
