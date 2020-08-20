<template>
  <div>
    <div class="page-header clear-filter intro-images">
      <parallax
        class="page-header-image"
        style="background-image:url('img/bg22.jpg')"
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

    <div class="row">
      <div class="col-md-10 d-flex justify-content-end">
        <IntroCreate/>
      </div>
    </div>

    <div class="row justify-content-center">

      <div class="row dropdown company-select col-8 align-items-center"> 
        <b-form-select class="col-4" v-model="selectedCompany" :options="companies"></b-form-select>
      </div>

    </div>

    <div class="row justify-content-center">
      <div class="col-8 jumbotron jumbotron-fluid cutsom-jumbo">
      <div>
        <div class="col-2"></div>
        <h5 class="col-8"># 원하는 태그를 선택해주세요</h5>
      </div>

      <br/>
      <div>
        <div class="col-8">
          <b-button @click="tagsInput(tag)" @mouseleave="tagRe(tag.text)" @mouseover="tagOver(tag.text)" :id="tag.text" pill variant="secondary" v-for="tag in tags" :key="tag.text"># {{tag.text}}</b-button>
        </div>
      </div>
      </div>
    </div>

    <Intro v-for="intro in curPageIntros" :key="intro.introduction.introno" :intro="intro"/>

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
var tagedIntros = []

export default {
  name: 'introduction',
  bodyClass: 'profile-page',

  data() {
    return {
      imgPath: "img/random_profile/default_profile.jpg",
      userData: "",
      description: "내 소개를 입력해주세요",
      isMyPage: false,

      // 회사 필터링 관련
      selectedCompany: null,
      companies: [
        { value: null, text: '모든 회사' },
      ],
      
      // 태그 관련
      selectedTagsText: [],
      selectedTags: [],
      tags: [],

      // 프론트 표시 페이지
      currentPage: 1,
      // 총 페이지 수
      pages: 1,

      // 저장된 자소서 관련
      intros: [],
      curPageIntros: [],
      introNos: [],

      API_URL: '',
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
      return pageNum === 1 ? '/#/introduction' : `/#/introduction/${pageNum}`
    },

    tagOver(buttonId) {
      var hoverBt = document.getElementById(buttonId)
      if (hoverBt.classList[0] !== "chked-tag") {
        hoverBt.setAttribute('class', "btn btn-primary rounded-pill")
      }
    },

    tagRe(buttonId) {
      var hoverBt = document.getElementById(buttonId)
      if (hoverBt.classList[0] !== "chked-tag") {
        hoverBt.setAttribute('class', "btn btn-secondary rounded-pill")
      }
    },

    tagsInput(tag) {
      var hoverBt = document.getElementById(tag.text)

      if (hoverBt.classList[0] === "chked-tag") {
        var idx = this.selectedTagsText.indexOf(tag.text)
        this.selectedTagsText.splice(idx, 1)
        this.selectedTags.splice(idx, 1)

        hoverBt.setAttribute('class', "btn btn-secondary rounded-pill")
      } else {
        this.selectedTagsText.push(tag.text)
        this.selectedTags.push(tag)

        hoverBt.setAttribute('class', "chked-tag btn btn-primary rounded-pill")
      }
    },

  },

  computed: {
    curUid: sync('curUid'),
    DisURL: sync('DisURL'),
    LocalURL: sync('LocalURL'),
    isLocal: sync('isLocal'),

    comIntros: function() {
      return this.intros 
    },
  },

  watch: {
    currentPage: function(newCurPage) {
      if (this.currentPage === 0) {
        this.currentPage = 1
        return
      }

      // data에서 빼올 시작 인덱스 지정
      var startIdx = 0
      var lastCnt = this.intros.length % 8
      for(var i=1; i<this.currentPage; i++) {
        startIdx += 8
      }

      if (this.pages === this.currentPage) {
        this.curPageIntros = this.intros
      } else {
        this.curPageIntros = this.intros.slice(startIdx, startIdx+8)
      }
      return
    },

    selectedCompany: function(newCompany) {
      if (newCompany === null) {
        var url = `${this.API_URL}introduction/uid/${this.curUid}`
        axios.get(url) 
        .then(({data}) => {
          this.intros = data
        })
        return
      }

      var url = `${this.API_URL}introduction/company?company=${newCompany}`
      var postData = {
        uid: this.curUid,
      } 

      axios.post(url, postData)
      .then(({data}) => {
        this.intros = data
        return
      })
      .catch(err => {
        alert('회사 필터링에 실패했습니다')
        return
      })
    },

    selectedTags: function(newTags) {

      this.introNos = []
      
      if (this.selectedCompany === null) {
        var url = `${this.API_URL}introduction/uid/${this.curUid}`
        axios
        .get(url)
        .then(({data}) => {
          this.intros = data
          if (newTags.length === 0) {
            // intros 전체 다시 불러오기
            for(var i=0; i<this.intros.length; i++) {
              this.introNos.push(this.intros[i].introduction.introno)
            }
            return
          }
          
          tagedIntros = []
          for(var i=0; i<newTags.length; i++) {
            for (var k=0; k<this.intros.length; k++) {
              if (this.introNos.indexOf(this.intros[k].introduction.introno) === -1) {
                for(var j=0; j<this.intros[k].hashTag.length; j++) {
                  if (this.intros[k].hashTag[j].tagname === newTags[i].text) {
                    this.introNos.push(this.intros[k].introduction.introno)
                    tagedIntros.push(this.intros[k])
                    break
                  }
                }
              }
            }
          }
          this.intros = tagedIntros
        })
        .catch((err) => {
          console.log(err)
          alert('태그에 실패하였습니다')
        })
        
      } else {

        var url = `${this.API_URL}introduction/company?company=${this.selectedCompany}`

        axios
        .post(url, {uid: this.curUid,})
        .then(({data}) => {
          this.intros = data
          if (newTags.length === 0) {
            // intros 전체 다시 불러오기
            for(var i=0; i<this.intros.length; i++) {
              this.introNos.push(this.intros[i].introduction.introno)
            }
            return
          }

          
          tagedIntros = []
          for(var i=0; i<newTags.length; i++) {
            for (var k=0; k<this.intros.length; k++) {
              if (this.introNos.indexOf(this.intros[k].introduction.introno) === -1) {
                for(var j=0; j<this.intros[k].hashTag.length; j++) {
                  if (this.intros[k].hashTag[j].tagname === newTags[i].text) {
                    this.introNos.push(this.intros[k].introduction.introno)
                    tagedIntros.push(this.intros[k])
                    break
                  }
                }
              }
            }
          }
          this.intros = tagedIntros
        })
        .catch((err) => {
          console.log(err)
          alert('태그에 실패하였습니다')
        })
      }
    },

    intros: function() {
      
      var totalPage = parseInt(this.intros.length/8)
      if (this.intros.length === 0) {
        totalPage = 1
      }
      
      if (this.intros.length % 8 !== 0) {
        totalPage += 1
      }
      
      this.pages = totalPage
      this.currentPage = 0
      return
    },
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

    url = `${this.API_URL}introduction/uid/${this.curUid}`
    axios
    .get(url)
    .then(({data}) => {

      // 총 페이지 수
      var totalPage = parseInt(data.length/8)
      if (data.length % 8 !== 0) {
        totalPage += 1
      }
      this.pages = totalPage

      this.intros = data
      var tempCompanies = []
      var companyCnt = 1
      for(var i=0; i<this.intros.length; i++) {
        this.introNos.push(this.intros[i].introduction.introno)

        if (tempCompanies.indexOf(this.intros[i].introduction.company) === -1) {
          tempCompanies.push(this.intros[i].introduction.company)
          this.companies.push({value: this.intros[i].introduction.company, text: this.intros[i].introduction.company})
        }
      }

      // 전체 태그 배열 생성
      var tempTagArray = []
      for(var i=0; i<data.length; i++) {
        for(var j=0; j<data[i].hashTag.length; j++) {
          tempTagArray.push([data[i].hashTag[j].tagno, data[i].hashTag[j].tagname])
        }
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

      if (data.length <= 8) {
        this.curPageIntros = this.intros
      } else {
        this.curPageIntros = this.intros.slice(0, 8)
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
<style>
.intro-images {
  width: 100%;
  height: 650px;
}

.cutsom-jumbo {
  padding: 20px 0 !important;
}

.company-select {
  background-color: #ced6e0;
  height: 60px;
}
</style>