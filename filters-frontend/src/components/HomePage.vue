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
            <tr v-for="item in filters" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.criteria }}</td>
              <td>{{ item.selection }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card>
    <add-filter-dialog
      v-if="isAddFilterDialogShown"
      class="mt-3"
      @close="onAddFilterDialogClosed"
      @save="onAddFilterDialogSaved"
    />
  </v-container>
</template>

<script>
import AddFilterDialog from "@/components/AddFilterDialog.vue";

export default {
  components: { AddFilterDialog },
  data: () => ({
    isAddFilterDialogShown: false,

    filters: [
      {
        name: "test-filter-1",
        criteria: "filter criteria goes here",
        selection: "selection 2",
      },
      {
        name: "test-filter-2",
        criteria: "filter criteria goes here",
        selection: "selection 1",
      },
      {
        name: "test-filter-3",
        criteria: "filter criteria goes here",
        selection: "selection 3",
      },
    ],
  }),

  methods: {
    /**
     * Fetch existing filters from the back end.
     */
    loadFilters() {},

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
};
</script>
