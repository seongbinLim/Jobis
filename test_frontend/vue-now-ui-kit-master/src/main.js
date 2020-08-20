/*!

 =========================================================
 * Vue Now UI Kit - v1.1.0
 =========================================================

 * Product Page: https://www.creative-tim.com/product/now-ui-kit
 * Copyright 2019 Creative Tim (http://www.creative-tim.com)

 * Designed by www.invisionapp.com Coded by www.creative-tim.com

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 */
import Vue from 'vue';
// Vuex
import Vuex from 'vuex'
import pathify from 'vuex-pathify';
import { make } from 'vuex-pathify';
import createdPersistedState from 'vuex-persistedstate'
// BootstrapVue
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import App from './App.vue';
// You can change this import to `import router from './starterRouter'` to quickly start development from a blank layout.
import router from './router';
import NowUiKit from './plugins/now-ui-kit';

Vue.config.productionTip = false;

Vue.use(NowUiKit);

Vue.use(Vuex);
Vue.use(BootstrapVue);

const state = {
  isLocal: true,
  curUid: '',
  DisURL: 'http://i3c104.p.ssafy.io:8080/',
  LocalURL: 'http://localhost:8080/',
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

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
