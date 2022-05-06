<template>
  <v-container style="max-width: 1200px !important">
    <v-card>
      <v-card-title>
        <v-row>
          <v-col>Filters</v-col>
          <v-col class="text-right">
            <v-btn
              color="primary"
              :disabled="isAddFilterDialogShown"
              @click="openAddFilterDialog"
            >
              ADD FILTER
            </v-btn>
          </v-col>
        </v-row>
      </v-card-title>

      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Criteria</th>
              <th class="text-left">Selection</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="filter in filters" :key="filter.id">
              <td>{{ filter.name }}</td>
              <td>
                <!-- List every criterion of this filter as a bullet point -->
                <ul class="pl-4 pt-2 pb-2">
                  <li v-for="criterion in filter.criteria" :key="criterion.id">
                    {{ RUNTIME_CONFIG.CRITERION_TYPES[criterion.type].NAME }}
                    {{
                      RUNTIME_CONFIG.CRITERION_TYPES[criterion.type].OPERATORS[
                        criterion.operator
                      ]
                    }}
                    {{ criterion.value }}
                  </li>
                </ul>
              </td>
              <td>
                <!-- Map the filter's selection value enum to text -->
                {{ RUNTIME_CONFIG.SELECTION_VALUES[filter.selection] }}
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <v-progress-linear
        v-show="areFiltersLoading"
        indeterminate
      ></v-progress-linear>
    </v-card>

    <!-- Wrap the add filter dialog in Vuetify dialog box if modal mode is configured -->
    <v-dialog
      v-if="RUNTIME_CONFIG.MODAL_DIALOG"
      v-model="isAddFilterDialogShown"
      max-width="1200"
      persistent
    >
      <add-filter-dialog
        v-if="isAddFilterDialogShown"
        class="mt-3"
        @close-dialog="closeAddFilterDialog"
        @filter-saved="onAddFilterDialogSaved"
      />
    </v-dialog>

    <!-- Otherwise display just the dialog embedded in this page -->
    <add-filter-dialog
      v-else-if="isAddFilterDialogShown"
      class="mt-3"
      @close-dialog="closeAddFilterDialog"
      @filter-saved="onAddFilterDialogSaved"
    />
  </v-container>
</template>

<script>
import axios from "axios";
import AddFilterDialog from "@/components/AddFilterDialog.vue";

export default {
  components: { AddFilterDialog },

  data: () => ({
    isAddFilterDialogShown: false,
    areFiltersLoading: true,

    filters: [],
  }),

  methods: {
    /**
     * Get existing filters from the back end.
     */
    async loadFilters() {
      this.areFiltersLoading = true;

      try {
        const filtersResponse = await axios.get("/filters");
        this.filters = filtersResponse.data;
      } catch (error) {
        alert(`Cannot load filters. ${error}`);
      }

      this.areFiltersLoading = false;
    },

    /**
     * Open the dialog that allows the user to define new filters.
     */
    openAddFilterDialog() {
      this.isAddFilterDialogShown = true;
    },

    /**
     * Close the add filter dialog.
     */
    closeAddFilterDialog() {
      this.isAddFilterDialogShown = false;
    },

    /**
     * When the user successfully saved a new filter to the back-end,
     * close the add filter dialog and reload the filter list.
     */
    onAddFilterDialogSaved() {
      this.closeAddFilterDialog();
      this.loadFilters();
    },
  },

  mounted() {
    this.loadFilters();
  },
};
</script>
