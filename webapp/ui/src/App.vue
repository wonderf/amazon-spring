<template>
  <v-app>
    <v-main>
      <v-container>
        <v-row>
          <v-col offset="4" cols="4">
            <span>AMS.research v.09b</span>
            <SearchForm/>
            <Status/>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Status from "./components/Status";
import SearchForm from "@/components/SearchForm";

export default {
  name: 'App',

  components: {
    SearchForm,
    Status
  },

  data: () => ({
    //
  }),
};
</script>
