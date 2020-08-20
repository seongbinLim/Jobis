<template>
  <div>
    <div class="container" v-for="article in articles" :key="article.boardno">
      <div class="row mb-md-1">
        <div class="col-md-12 pl-0">
            <h2 class="d-flex justify-content-start mb-sm-1" @click="articleDetailPush" role="button">{{article.subject}}</h2>
        </div>
      </div>
      <div class="row mt-md-1">
        <div class="col-md-12 pl-0">
          <p class="text-muted d-flex justify-content-start" @click="articleDetailPush" role="button">
            {{article.content}}
          </p>
        </div>
      </div>
      <hr class="row">
    </div>

    <div class="mt-3">
      <b-pagination-nav v-model="currentPage" :number-of-pages="pages" base-url="#/myboard?page=" align="fill"></b-pagination-nav>
    </div>

  </div>
</template>

<script>
import axios from 'axios'

const API_URL = 'http://localhost:8080/'

export default {
    name: 'Article',

    data() {
      return {
        // 프론트 표시 페이지
        currentPage: 1,
        // 백엔드 요청 페이지
        page: 0,
        // 총 페이지 수
        pages: '',

        articles: '',
      }
    },

    methods: {
      articleDetailPush(){
        this.$router.push('myboard/introduction/introno')
      },
      articlesGet() {
      }
    },

    mounted() {
      // 자바 페이지네이터는 0부터 인덱싱
      console.log(this.$route.params.page)
      if (this.$route.params.page !== undefined) {
        this.currentPage = this.$route.params.page
      }
      this.page = this.currentPage-1

      const url = `${API_URL}api/board/paging?page=${this.page}`
      console.log(url);
      axios
      .get(url)
      .then(({data}) => {
        this.articles = data.content
        this.pages = data.totalPages
        return
      })
      .catch(() => {
        alert("페이지를 불러오지 못했습니다.");
        return
      })
    }

}
</script>

<style>

</style>