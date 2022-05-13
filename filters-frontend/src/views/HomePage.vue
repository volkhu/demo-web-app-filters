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

      <v-data-table
        :headers="headers"
        :items="filters"
        :loading="areFiltersLoading"
        :no-data-text="errorMessage ? errorMessage : $vuetify.noDataText"
      >
        <!-- List every criterion of this filter as a bullet point -->
        <template v-slot:[`item.criteria`]="{ item }">
          <ul>
            <li v-for="criterion in item.criteria" :key="criterion.id">
              {{ criterionTextRepresentation(criterion) }}
            </li>
          </ul>
        </template>
        <!-- Map match type enum to a text value -->
        <template v-slot:[`item.matchType`]="{ item }">
          {{ matchTypeTextRepresentation(item.matchType) }}
        </template>
      </v-data-table>
    </v-card>

    <div id="dialogArea">
      <!-- Wrap the add filter dialog in Vuetify dialog box if modal mode is configured -->
      <v-dialog
        v-if="RUNTIME_CONFIG.MODAL_DIALOG"
        v-model="isAddFilterDialogShown"
        max-width="1200"
        persistent
      >
        <add-filter-dialog
          v-if="isAddFilterDialogShown"
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
    </div>

    <!-- Notification displayed when a filter is saved -->
    <v-snackbar v-model="showSavedMessage" timeout="2000" color="success">
      Filter successfully saved.
    </v-snackbar>
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
    errorMessage: null,
    showSavedMessage: false,

    headers: [
      { text: "Name", value: "name" },
      { text: "Criteria", value: "criteria" },
      { text: "Match type", value: "matchType" },
    ],
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
        this.errorMessage = null;
      } catch (error) {
        this.filters = [];
        this.errorMessage = `Cannot load filters. ${error}`;
      }

      this.areFiltersLoading = false;
    },

    /**
     * Create a human readable representation of a criterion, suitable
     * for displaying in a table.
     */
    criterionTextRepresentation(criterion) {
      const typeConfig = this.RUNTIME_CONFIG.CRITERION_TYPES[criterion.type];
      if (!typeConfig) return "Unknown criterion";

      const name = typeConfig.NAME;
      const operator = typeConfig.OPERATORS[criterion.operator];
      if (!operator) return "Unknown operator";

      return `${name} ${operator} ${criterion.value}`;
    },

    /**
     * Convert match type enum to a human readable text equivalent.
     */
    matchTypeTextRepresentation(matchType) {
      const matchTypeValue = this.RUNTIME_CONFIG.MATCH_TYPES[matchType];
      if (!matchTypeValue) return "Unknown";

      return matchTypeValue;
    },

    /**
     * Open the dialog that allows the user to define new filters.
     */
    openAddFilterDialog() {
      this.isAddFilterDialogShown = true;

      if (!this.RUNTIME_CONFIG.MODAL_DIALOG) {
        this.$vuetify.goTo("#dialogArea");
      }
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
      this.showSavedMessage = true;
      this.loadFilters();
    },
  },

  mounted() {
    this.loadFilters();
  },
};
</script>
