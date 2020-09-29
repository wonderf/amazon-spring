<template>
    <v-row>
        <v-col cols="12" v-for="item in TASKS" :key="item.id">
            <v-row>
                <v-col cols="9">
                    <div class="text--center">{{item.words.slice(0,3).join(" ")}}</div>
                    <v-progress-linear
                            v-model="item.percents"
                            height="25"
                    >
                        <strong>{{ item.percents }}%</strong>
                    </v-progress-linear>
                </v-col>
                <v-col cols="3" class="mt-4">

                        <v-icon class = "mt-2" @click="downloadWithAxios(`/api/task/downloadZip/${item.id}`)">
                            mdi-download
                        </v-icon>
                        <v-icon v-if="item.percents===100" class="mt-1" @click="cancelWork(item.id)">
                          mdi-close
                        </v-icon>

                </v-col>
            </v-row>
        </v-col>
    </v-row>
</template>

<script>
    import Axios from 'axios';
    import {createNamespacedHelpers} from 'vuex';

    const {mapActions, mapGetters} = createNamespacedHelpers('task_api');
    export default {
        name: 'Status',
        methods: {
            perncents: function (item) {
                return item.currentWorks / item.totalWorkls;
            },
            forceFileDownload(response){
                const url = window.URL.createObjectURL(new Blob([response.data]))
                const link = document.createElement('a')
                link.href = url
                link.setAttribute('download', 'download.zip') //or any other extension
                document.body.appendChild(link)
                link.click()
            },
            cancelWork(id){
              Axios.get(process.env.VUE_APP_HOST_URL+"/api/task/stop/"+id).then(()=>{console.log("stoped")})
            },
            downloadWithAxios(url){
                Axios({
                    method: 'get',
                    url: url,
                    responseType: 'arraybuffer'
                })
                    .then(response => {

                        this.forceFileDownload(response)

                    })
                    .catch(() => console.log('error occured'))
            },
            ...mapActions(['LOAD_TASKS'])
        },
        computed: {

            ...mapGetters(['TASKS'])
        },
        mounted() {
            this.LOAD_TASKS();
            const self = this;
            this.interval = setInterval(function () {
                self.LOAD_TASKS();
            }, 4000);
        },
        beforeDestroy() {
            clearInterval(this.interval);
        }
    }
</script>
