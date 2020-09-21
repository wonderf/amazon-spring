<template>
  <v-app>
    <v-main>
      <v-container>
        <Status/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Status from "./components/Status";

export default {
  name: 'App',

  components: {
    Status
  },

  data: () => ({
    //
  }),
};
</script>
