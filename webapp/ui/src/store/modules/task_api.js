import Axios from 'axios';

const state = {
    tasks:[]
};
const getters = {
    TASKS: state => {
        return state.tasks;
    },
};
const mutations = {
    SET_TASKS: (state, payload) => {
        state.tasks = payload;
        for(let i=0;i<state.tasks.length;i++){
            state.tasks[i].percents=Math.ceil(state.tasks[i].currentWorks/state.tasks[i].totalWorkls*100);
        }
    },
};
const actions = {
    LOAD_TASKS: async (context) => {
        let tasks = await Axios.get(`${process.env.VUE_APP_HOST_URL}/api/task`);
        if (tasks.status === 200) {
            context.commit('SET_TASKS', tasks.data);
        }
    },

    START_NEW_TASK: async (context,form) =>{
        console.log(form);
        let task = await Axios.post(`${process.env.VUE_APP_HOST_URL}/api/task/create`, form)
        if (task.status === 200) {
            context.dispatch('LOAD_TASKS');
        }
    }
};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions,
};
