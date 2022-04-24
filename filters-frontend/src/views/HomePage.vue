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
            <tr v-for="filter in filters" :key="filter.name">
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
              <td>{{ filter.selection }}</td>
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
        name: "Test Filter 1",
        criteria: [
          {
            id: 0,
            type: "Amount",
            operator: "greater than",
            value: "5",
          },
          {
            id: 1,
            type: "Title",
            operator: "starts with",
            value: "asbf",
          },
          {
            id: 2,
            type: "Title",
            operator: "ends with",
            value: "kdege",
          },
        ],
        selection: "Selection 1",
      },
      {
        name: "Test Filter 2",
        criteria: [
          {
            id: 0,
            type: "Amount",
            operator: "less than",
            value: "1",
          },
          {
            id: 1,
            type: "Title",
            operator: "starts with",
            value: "jkvev",
          },
          {
            id: 2,
            type: "Title",
            operator: "ends with",
            value: "821h",
          },
        ],
        selection: "Selection 2",
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
