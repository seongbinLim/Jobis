import Vue from 'vue'
import Router from 'vue-router'

import constants from '../lib/constants'

// 유저
import Join from '../page/user/Join.vue'

// 포스트
import List from '../page/post/List.vue'
 
Vue.use(Router) 
 
export default new Router({
  routes: [   
    // 로그인/가입
    {
      path: '/user/join',
      name: constants.URL_TYPE.USER.JOIN,
      component: Join
    },
    // 포스트
    { 
      path: '/',
      name: constants.URL_TYPE.POST.MAIN,
      component: List,
    },
  ]
})
