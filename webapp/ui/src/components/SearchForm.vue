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
                <v-radio label="UK" value="https://completion.amazon.co.uk/search/complete?method=completion&q={0}&search-alias=aps&mkt=4&noCacheIE=1295031912518"></v-radio>
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
      form:{
        filtering:true,
        filters:'shirt, gift, appare',
        market:"https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q={0}"},
    };
  },
  methods: {
    startSearch:function () {
      if(this.form.market === "https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q={0}")
        this.form.amazonResult="https://www.amazon.com/s?k={r}&i=fashion-novelty&bbn=12035955011&rh=p_6%3AATVPDKIKX0DER&hidden-keywords=ORCA"
      else this.form.amazonResult="https://www.amazon.co.uk/s?k={r}&hidden-keywords=%22Solid+colors%3A+100%25+Cotton%3B+Heather+Grey%3A+90%25+Cotton%2C+10%25+Polyester%3B+All+Other+Heathers%3A+50%25+Cotton%2C+50%25+Polyester%22"
      this.START_NEW_TASK(this.form);
    },
    ...mapActions(['START_NEW_TASK'])
  },
  computed: {},
  mounted() {

  }
}
</script>
