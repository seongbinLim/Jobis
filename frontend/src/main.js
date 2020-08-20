import Vue from 'vue'
import Vuex from 'vuex'
import BootstrapVue from 'bootstrap-vue'
import pathify from 'vuex-pathify';
import { make } from 'vuex-pathify';
import createdPersistedState from 'vuex-persistedstate'

import App from './App.vue'
import router from './router'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(Vuex)
Vue.use(BootstrapVue)

Vue.config.devtools = true;

const state = {
  curUid: '',
};

const mutations = make.mutations(state);

const store = new Vuex.Store({
  plugins: [
    pathify.plugin,
    createdPersistedState()
  ],
  state,
  mutations
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router, 
  components: { App }, 
  template: '<App/>'
})
 