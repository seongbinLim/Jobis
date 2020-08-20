import Vue from 'vue';
import Router from 'vue-router';
import Index from './pages/Index.vue';
import MyHome from './pages/MyHome.vue';
import Profile from './pages/Profile.vue';
import MainNavbar from './layout/MainNavbar.vue';
import MainFooter from './layout/MainFooter.vue';
import Board from './pages/Board.vue';
import MyIntroduction from './pages/myblog/MyIntroduction.vue';
import HashTagging from './pages/introduction/HashTagging.vue';
import BoardDetail from './pages/board/BoardDetail.vue';

Vue.use(Router);
const API_URL = 'http://localhost:8080/'

export default new Router({

  linkExactActiveClass: 'active',
  // computed: {
  //   curUid: sync('curUid'),
  // },
  routes: [
    {
      path: '/',
      name: 'index',
      components: { default: Index, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/myhome',
      name: 'myhome',
      components: { default: MyHome, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/board',
      name: 'board',
      components: { default: Board, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/introduction',
      name: 'introduction',
      components: { default: MyIntroduction, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/introduction/:page',
      name: 'introductionPagination',
      components: { default: MyIntroduction, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/hashtag/:tagno',
      name: 'hashTagging',
      components: { default: HashTagging, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/hashtag/:tagno/:page',
      name: 'hashTaggingPagination',
      components: { default: HashTagging, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/logout',
      name: 'logout',
      beforeEnter(to, from, next) {
        console.log('로그아웃 url 접근')
        next('/')
      }
    },
    {
      path: '/profile',
      name: 'profile',
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/board/:boardno',
      name: 'BoardDetail',
      components: { default: BoardDetail, header: MainNavbar, footer: MainFooter},
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }

    },
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});
