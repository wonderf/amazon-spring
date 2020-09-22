<template>
  <v-form>
    <v-container>
      <v-row>
        <v-col
            cols="12"
            md="12"
        >
          <v-text-field
              v-model="search"
              label="Search"
              required
          ></v-text-field>
        </v-col>
        <v-col cols="12">
          <v-file-input label="File input"></v-file-input>
        </v-col>
        <v-col cols="12">
          <v-row>
            <v-col cols="6">
              <div class="d-flex align-center">Market</div>
            </v-col>
            <v-col cols="4" offset="2">
              <v-radio-group v-model="market" class="align-content-sm-end" row>
                <v-radio label="UK" value="UK"></v-radio>
                <v-radio label="US" value="US"></v-radio>
              </v-radio-group>
            </v-col>
          </v-row>

        </v-col>
        <v-col cols="12">
          <v-checkbox v-model="deepSearch" :label="`Deep Search: ${deepSearch.toString()}`"></v-checkbox>
          <v-checkbox v-model="reverseSearch" :label="`Reverse Search: ${reverseSearch.toString()}`"></v-checkbox>

        </v-col>
        <v-col cols="12">
          <v-row>
            <v-col cols="4">
              <v-checkbox v-model="filtering" :label="`Filtering: ${filtering.toString()}`"></v-checkbox>
            </v-col>
            <v-col cols="8">
              <v-text-field
                  v-model="filters"
                  label="Filters"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <div class="my-2">
      <v-btn >Search</v-btn>
    </div>
  </v-form>
</template>

<script>

export default {
  name: 'SearchForm',
  data: function () {
    return {
      search:'',
      market: undefined,
      deepSearch:false,
      reverseSearch:false,
      filtering:false,
      filters:'',
    };
  },
  methods: {},
  computed: {},
  mounted() {

  }
}
</script>
