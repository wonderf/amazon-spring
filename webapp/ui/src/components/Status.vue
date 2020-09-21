<template>
    <v-row>
        <v-col cols="12" v-for="(item,index) in TASKS" :key="index">
            <div class="text--center">{{item.name}}</div>
            <v-progress-linear
                    v-model="item.percents"
                    height="25"
            >
                <strong>{{ Math.ceil(item.percents) }}%</strong>
            </v-progress-linear>
        </v-col>
    </v-row>
</template>

<script>
    import {createNamespacedHelpers} from 'vuex';
    const {mapActions, mapGetters} = createNamespacedHelpers('task_api');
    export default {
        name: 'Status',
        methods:{
            ...mapActions(['LOAD_TASKS'])
        },
        computed:{
            ...mapGetters(['TASKS'])
        },
        mounted() {
            this.LOAD_TASKS();
        }
    }
</script>
