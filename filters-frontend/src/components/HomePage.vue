<template>
  <v-container>
    <v-card>
      <v-card-title>
        <v-row>
          <v-col><h3>Filters</h3></v-col>
          <v-col class="text-right">
            <v-btn color="primary" @click="openAddFilterDialog">
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
  </v-container>
</template>

<script>
export default {
  name: "HomePage",

  data: () => ({
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
    openAddFilterDialog() {
      alert("openAddFilterDialog");
    },
  },
};
</script>
