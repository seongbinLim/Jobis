<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax
        class="page-header-image"
        style="background-image:url('img/bg5.jpg')"
      >
      </parallax>
      <div class="container">
        <div class="photo-container">
          <img :src="this.imgPath" alt="" />
        </div>
        <h3 class="title">{{this.userData.name}}</h3>
        <p class="category">{{this.userData.email}}</p>
        <div class="row justify-content-center">
          <div class="social-description col-12 col-md-4">
            <h3>{{userData.interest1}}</h3>
            <p>제 1 관심 분야</p>
          </div>
          <div class="social-description col-12 col-md-4">
            <h3>{{userData.interest2}}</h3>
            <p>제 2 관심분야</p>
          </div>
        </div>
      </div>
    </div>

    <b-form-select v-model="selected" :options="tags" size="lg" class="mt-3"></b-form-select>

    <div class="row">
      <div class="col-md-10 d-flex justify-content-end">
        <IntroCreate/>
      </div>
    </div>
    <Intro v-for="intro in intros" :key="intro.introduction.introno" :intro="intro"/>
    <div class="mt-3">
      <b-pagination-nav 
        v-model="currentPage" 
        :number-of-pages="pages"
        :link-gen="linkGen"
        align="fill"></b-pagination-nav>
    </div>

  </div>
</template>
<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'
import IntroCreate from '@/pages/introduction/IntroCreate.vue'
import Intro from '@/pages/introduction/Intro.vue'

// 1 ~ 5까지 랜덤 수
var rand2 = Math.floor(Math.random()*5) + 1

export default {
  name: 'hashtagging',
  bodyClass: 'profile-page',

  data() {
    return {
      API_URL: '',
      imgPath: "img/random_profile/default_profile.jpg",
      userData: "",
      description: "내 소개를 입력해주세요",
      isMyPage: false,
      
      // 태그 관련
      selected: null,
      tags: [
        { value: null, text: '전체 보기' },
      ],

      // 프론트 표시 페이지
      currentPage: 1,
      // 총 페이지 수
      pages: '',

      intros: '',
    }
  },

  components: {
    IntroCreate,
    Intro,
  },

  methods: {
    isMine(a, b) {
      return a === b
    },

    linkGen(pageNum) {
      return pageNum === 1 ? `/#/hashtag/${this.$route.params.tagno}` : `/#/hashtag/${this.$route.params.tagno}/${pageNum}`
    }
  },

  computed: {
    curUid: sync('curUid'),
    DisURL: sync('DisURL'),
    LocalURL: sync('LocalURL'),
    isLocal: sync('isLocal'),
  },

  watch: {
    currentPage: function(newCurPage) {
      const url = `${this.API_URL}hashtag/${this.$route.params.tagno}`
      axios
      .get(url)
      .then(({data}) => {
        // url로 페이지 전달 시
        if (this.$route.params.page) {
          this.currentPage = this.$route.params.page
        }

        // 총 페이지 수
        var totalPage = parseInt(data.length/8)
        if (data.length % 8 !== 0) {
          totalPage += 1
        }
        this.pages = totalPage

        // data에서 빼올 시작 인덱스 지정
        var startIdx = 0
        var lastCnt = data.length % 8
        for(var i=1; i<this.currentPage; i++) {
          startIdx += 8
        }

        if (this.pages === this.currentPage) {
          this.intros = data
        } else {
          this.intros = data.slice(startIdx, startIdx+8)
        }

        return
      })
      .catch(() => {
        alert("페이지를 불러오지 못했습니다.");
        return
      })
    },

    selected: function(newTagno) {
      if (newTagno !== null) {

        this.$router.push({name: 'hashTagging', params: {tagno: newTagno,}})
        
        const url = `${this.API_URL}hashtag/${this.$route.params.tagno}`
        axios
        .get(url)
        .then(({data}) => {

          // 총 페이지 수
          var totalPage = parseInt(data.length/8)
          if (data.length % 8 !== 0) {
            totalPage += 1
          }
          this.pages = totalPage

          // data에서 빼올 시작 인덱스 지정
          var startIdx = 0
          var lastCnt = data.length % 8
          for(var i=1; i<this.currentPage; i++) {
            startIdx += 8
          }

          if (this.pages === this.currentPage) {
            this.intros = data
          } else {
            this.intros = data.slice(startIdx, startIdx+8)
          }
          return
        })
        .catch(() => {
          alert("페이지를 불러오지 못했습니다.");
          return
        })
      } else {
        this.$router.push({name: 'introduction'})
      }
    }
  },

  mounted() {

    if (this.isLocal){
        this.API_URL = this.LocalURL
    } else {
        this.API_URL = this.DisURL
    }

    var url = `${this.API_URL}account/userinfo`
    var postInfo = {
      uid: this.curUid,
    }
    axios.post(url, postInfo)
    .then(res => {
      this.userData = res.data
      
      if (this.userData.img !== "") {
        this.imgPath = userData.img
      }
      if (this.userData.description !== "") {
        this.description = this.userData.description
      }
      this.isMyPage = this.isMine(this.curUid, this.userData.uid)
    })
    .catch(() => {
      alert("프로필에 접근 권한이 없습니다.")
    })

    if (this.$route.params.page) {
      this.currentPage = this.$route.params.page
    }

    // 설정된 태그 초기화 및 해당 자소서 및 초기 페이지네이션
    url = `${this.API_URL}hashtag/${this.$route.params.tagno}`

    axios
    .get(`${this.API_URL}introduction/uid/${this.curUid}`)
    .then(({data}) => {

      // 전체 태그 배열 생성
      var tempTagArray = []
      for(var i=0; i<data.length; i++) {
        tempTagArray.push([data[i].hashTag.tagno, data[i].hashTag.tagname])
      }
      
      // 태그 중복 제거
      var chkArray = []
      var tagArray = []
      for(var i=0; i<tempTagArray.length; i++) {
        if(chkArray.indexOf(tempTagArray[i][1]) === -1) {
          chkArray.push(tempTagArray[i][1])
          tagArray.push([tempTagArray[i][0], tempTagArray[i][1]])
        }
      }

      // 중복 제거된 태그 select 옵션으로 전달
      for(var i=0; i<tagArray.length; i++) {
        this.tags.push( { value: tagArray[i][0], text: tagArray[i][1] } )
      }
      this.selected = this.$route.params.tagno
      
    })

    axios
    .get(url)
    .then(({data}) => {

      // 총 페이지 수
      var totalPage = parseInt(data.length/8)
      if (data.length % 8 !== 0) {
        totalPage += 1
      }
      this.pages = totalPage

      // data에서 빼올 시작 인덱스 지정
      var startIdx = 0
      var lastCnt = data.length % 8
      for(var i=1; i<this.currentPage; i++) {
        startIdx += 8
      }

      if (this.pages === this.currentPage) {
        this.intros = data
      } else {
        this.intros = data.slice(startIdx, startIdx+8)
      }
      return
    })
    .catch(() => {
      alert("페이지를 불러오지 못했습니다.");
      return
    })
  },
};
</script>
<style></style>