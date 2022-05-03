<template>
  <v-container style="max-width: 1200px !important">
    <v-card>
      <v-card-title>
        <v-row>
          <v-col>Filters</v-col>
          <v-col class="text-right">
            <v-btn
              color="primary"
              @click="openAddFilterDialog"
              :disabled="isAddFilterDialogShown"
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
                <!-- List all criteria as bullet points here -->
                <ul class="pl-4 pt-2 pb-2">
                  <li v-for="criteria in filter.criteria" :key="criteria.id">
                    {{ criteria.type }}
                    {{ criteria.operator }}
                    {{ criteria.value }}
                  </li>
                </ul>
              </td>
              <td>
                {{ FILTERS_RUNTIME_CONFIG.FILTER_SELECTIONS[filter.selection] }}
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <v-progress-linear
        indeterminate
        v-show="areFiltersLoading"
      ></v-progress-linear>
    </v-card>

    <!-- Wrap the add filter dialog in Vuetify dialog box if modal mode is configured -->
    <v-dialog
      v-if="FILTERS_RUNTIME_CONFIG.ADD_FILTER_DIALOG_MODAL"
      v-model="isAddFilterDialogShown"
      max-width="1200"
      persistent
    >
      <add-filter-dialog
        v-if="isAddFilterDialogShown"
        class="mt-3"
        @close="onAddFilterDialogClosed"
        @save="onAddFilterDialogSaved"
      />
    </v-dialog>

    <!-- Otherwise display just the dialog embedded in this page -->
    <add-filter-dialog
      v-else-if="isAddFilterDialogShown"
      class="mt-3"
      @close="onAddFilterDialogClosed"
      @save="onAddFilterDialogSaved"
    />
  </v-container>
</template>

<script>
import AddFilterDialog from "@/components/AddFilterDialog.vue";
import axios from "axios";

export default {
  components: { AddFilterDialog },
  data: () => ({
    isAddFilterDialogShown: false,
    areFiltersLoading: true,

    filters: [],
  }),

  methods: {
    /**
     * Fetch existing filters from the back end.
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
     * Just hide the add filter dialog if user decided to close it.
     */
    onAddFilterDialogClosed() {
      this.isAddFilterDialogShown = false;
    },

    /**
     * Close the add filter dialog and reload the filter list since the user added a new filter.
     */
    onAddFilterDialogSaved() {
      this.isAddFilterDialogShown = false;
      this.loadFilters();
    },
  },

  mounted() {
    this.loadFilters();
  },
};
</script>
