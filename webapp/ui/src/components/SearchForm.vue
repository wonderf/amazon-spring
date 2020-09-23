<template>
  <v-form>
    <v-container>
      <v-row>
        <v-col
            cols="12"
            md="12"
        >
          <v-text-field
              v-model="form.search"
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
              <v-radio-group v-model="form.market" class="align-content-sm-end" row>
                <v-radio label="UK" value="https://completion.amazon.co.uk/search/complete?method=completion&q={0}&search-alias=aps&mkt=4&callback=AmazonJSONPCallbackHandler_{1}&noCacheIE=1295031912518"></v-radio>
                <v-radio label="US" value="https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q={0}"></v-radio>
              </v-radio-group>
            </v-col>
          </v-row>

        </v-col>
        <v-col cols="12">
          <v-checkbox v-model="form.deep" label="Deep Search"></v-checkbox>
          <v-checkbox v-model="form.reverse" label="Reverse Search"></v-checkbox>

        </v-col>
        <v-col cols="12">
          <v-row>
            <v-col cols="4">
              <v-checkbox v-model="form.filtering" label="Filtering"></v-checkbox>
            </v-col>
            <v-col cols="8">
              <v-text-field
                  v-model="form.filters"
                  label="Filters"
                  required
              ></v-text-field>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <div class="my-2">
      <v-btn @click="startSearch">Search</v-btn>
    </div>
  </v-form>
</template>

<script>
  import {createNamespacedHelpers} from 'vuex';
  const {mapActions} = createNamespacedHelpers('task_api');
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
      form:{},
    };
  },
  methods: {
    startSearch:function () {

      this.START_NEW_TASK(this.form);
    },
    ...mapActions(['START_NEW_TASK'])
  },
  computed: {},
  mounted() {

  }
}
</script>
