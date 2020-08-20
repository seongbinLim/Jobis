<template>
  <navbar
    position="fixed"
    type="primary"
    :transparent="transparent"
    :color-on-scroll="colorOnScroll"
    menu-classes="ml-auto"
  >
    <template>
      <router-link v-popover:popover1 class="navbar-brand" to="/">
        <p>Jobis</p>
      </router-link>
      <el-popover
        ref="popover1"
        popper-class="popover"
        placement="bottom"
        width="200"
        trigger="hover"
      >
        <div class="popover-body">
          Designed by Invision. Coded by Creative Tim
        </div>
      </el-popover>
    </template>
    <template slot="navbar-menu">

      <li class="nav-item" v-if="curUid">
        <nav-link class="nav-link" to="/myhome">
          <p class="text-white">내 블로그</p>
        </nav-link>
      </li>

      <li class="nav-item" v-if="curUid">
        <nav-link class="nav-link" to="/introduction">
          <p class="text-white">자소서</p>
        </nav-link>
      </li>

      <li class="nav-item" v-if="curUid">
        <nav-link class="nav-link" to="/board">
          <p class="text-white">게시판</p>
        </nav-link>
      </li>

      <li class="nav-item" v-if="curUid">
        <nav-link class="nav-link" to="/logout" @click.native="logout">
          <p class="text-white">logout</p>
        </nav-link>
      </li>

      <li class="nav-item" v-if="!curUid">
        <LoginTemp/>
      </li>
      <li class="nav-item" v-if="!curUid">
        <Signup/>
      </li>

      <drop-down
              tag="li"
              icon="now-ui-icons users_single-02"
              class="nav-item"
              
      >
        <nav-link to="/">
          <i class="now-ui-icons shopping_shop"></i> Jobis
        </nav-link>
        <nav-link to="/profile" v-if="curUid">
          <i class="now-ui-icons users_single-02"></i> Profile
        </nav-link>
        <nav-link to="/logout" @click.native="logout" v-if="curUid">
            <i class="now-ui-icons ui-1_lock-circle-open"></i> Logout
        </nav-link>
      </drop-down>
      
    </template>
  </navbar>
</template>

<script>
import { DropDown, Navbar, NavLink } from '@/components';
import LoginTemp from '@/pages/LoginTemp.vue'
import Signup from '@/pages/Signup.vue'
import { Popover } from 'element-ui';
import { sync } from 'vuex-pathify'
import axios from 'axios'

export default {
  name: 'main-navbar',

  data() {
    return {
      API_URL: '',
    }
  },

  computed: {
      curUid: sync('curUid'),
      DisURL: sync('DisURL'),
      LocalURL: sync('LocalURL'),
      isLocal: sync('isLocal'),
  },

  mounted() {
      if (this.isLocal){
          this.API_URL = this.LocalURL
      } else {
          this.API_URL = this.DisURL
      }
  },

  props: {
    transparent: Boolean,
    colorOnScroll: Number
  },
  components: {
    DropDown,
    Navbar,
    NavLink,
    LoginTemp,
    Signup,
    [Popover.name]: Popover
  },
  methods: {
    logout() {
      const url = `${this.API_URL}account/logout`;
        axios
        .get(url)
        .then(({data}) => {
            console.log(data);
            this.curUid = ''
            console.log('logout success')
            this.$router.push('/')
        })

        .catch(() => {
            alert("로그아웃 에러");
            return
        });
    }
  }
};
</script>

<style scoped></style>
