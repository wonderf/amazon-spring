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
    },
};
const actions = {
    LOAD_TASKS: async (context) => {
        let tasks = await Axios.get(`${process.env.VUE_APP_HOST_URL}/api/task`);
        if (tasks.status === 200) {
            context.commit('SET_TABLE', tasks.data);
        }
    },
};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions,
};
