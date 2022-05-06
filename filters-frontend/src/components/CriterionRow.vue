<template>
  <v-row>
    <v-col cols="2" class="pl-0">
      <v-subheader v-if="showRowPrefix">Criteria</v-subheader>
    </v-col>

    <!-- Criterion type -->
    <v-col cols="3">
      <v-select
        class="pt-1"
        outlined
        dense
        hide-details="auto"
        :items="criterionTypes"
        :value="criterion.type"
        :disabled="disabled"
        @change="changeType"
      ></v-select>
    </v-col>

    <!-- Criterion operator -->
    <v-col cols="3">
      <v-select
        class="pt-1"
        outlined
        dense
        hide-details="auto"
        :items="criterionOperators"
        :value="criterion.operator"
        :disabled="disabled"
        @change="changeOperator"
      ></v-select>
    </v-col>

    <!-- Criterion value (different input type depending on selected criterion type) -->
    <v-col cols="3">
      <!-- Value input if the current criterion is of number format -->
      <v-text-field
        v-if="RUNTIME_CONFIG.CRITERION_TYPES[criterion.type].FORMAT == 'NUMBER'"
        class="pt-1"
        outlined
        dense
        hide-details="auto"
        type="number"
        required
        :value="criterion.value"
        :disabled="disabled"
        @change="changeValue"
      ></v-text-field>

      <!-- Value input if the current criterion is of text format -->
      <v-text-field
        v-if="RUNTIME_CONFIG.CRITERION_TYPES[criterion.type].FORMAT == 'TEXT'"
        class="pt-1"
        outlined
        dense
        hide-details="auto"
        required
        maxlength="64"
        :value="criterion.value"
        :disabled="disabled"
        @change="changeValue"
      ></v-text-field>

      <!-- Value input if the current criterion is of date format -->
      <v-menu
        v-if="RUNTIME_CONFIG.CRITERION_TYPES[criterion.type].FORMAT == 'DATE'"
        v-model="isDatePickerOpen"
        transition="scale-transition"
        offset-overflow
        min-width="auto"
        offset-y
        :close-on-content-click="false"
        :disabled="disabled"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            class="pt-1"
            v-bind="attrs"
            v-on="on"
            outlined
            dense
            hide-details="auto"
            required
            readonly
            maxlength="64"
            :value="criterion.value"
            :disabled="disabled"
          ></v-text-field>
        </template>

        <v-date-picker
          class="pt-1"
          first-day-of-week="1"
          :value="criterion.value"
          @input="isDatePickerOpen = false"
          @change="changeValue"
        ></v-date-picker>
      </v-menu>
    </v-col>

    <!-- Delete criterion button (disabled if this is the only criterion left) -->
    <v-col cols="1">
      <v-btn
        class="mt-1"
        icon
        :disabled="disabled || !allowDeletion"
        @click="$emit('delete-criterion', index)"
        ><v-icon>mdi-delete</v-icon></v-btn
      >
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: ["index", "criterion", "showRowPrefix", "allowDeletion", "disabled"],

  data: () => ({
    isDatePickerOpen: false,
  }),

  computed: {
    /**
     * Map criterion types from config to a format suitable for Vuetify select box.
     */
    criterionTypes() {
      const types = this.RUNTIME_CONFIG.CRITERION_TYPES;

      return Object.keys(types).map((key) => ({
        text: types[key].NAME,
        value: key,
      }));
    },

    /**
     * Map criterion operators from config to a format suitable for Vuetify select box.
     * Note that the set of available operators depends on the selected criterion type.
     */
    criterionOperators() {
      const operators =
        this.RUNTIME_CONFIG.CRITERION_TYPES[this.criterion.type].OPERATORS;

      return Object.keys(operators).map((key) => ({
        text: operators[key],
        value: key,
      }));
    },
  },

  methods: {
    changeType(event) {
      this.$emit("change-type", {
        index: this.index,
        newType: event,
      });
    },

    changeOperator(event) {
      this.$emit("change-operator", {
        index: this.index,
        newOperator: event,
      });
    },

    changeValue(event) {
      this.$emit("change-value", {
        index: this.index,
        newValue: event,
      });
    },
  },
};
</script>
