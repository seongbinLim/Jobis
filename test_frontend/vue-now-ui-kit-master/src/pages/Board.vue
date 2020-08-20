<template>
  <div>
    <MyPicture/>
    <div class="container">
        <div class="row">
          <div class="col-md-12">
            <h2 class="title mb-md-1 d-flex justify-content-start">Community</h2>
            <div class="container">
              <div class="row mb-3">
                <div class="col-md-10 d-flex justify-content-end">
                  <BoardCreate/>
                  <div v-if="!curUid"></div>
                </div>
              </div>
            </div>
            <div class="col-md-10">
              <MainBoard v-for="board in boards" :key="board.boardno" :board="board"/>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'

import MyPicture from '@/pages/board/MyPicture.vue'
import MainBoard from '@/pages/board/MainBoard.vue'
import BoardCreate from '@/pages/board/BoardCreate.vue'

export default {
  name: 'board',
  bodyClass: 'myboard-page',
  data() {
    return {
      API_URL: '',
      boards: [],
    }
  },
  methods: {
    getBoardList() {
      axios.get(`${this.API_URL}api/board/listAll/`)
      .then(res => {
        this.boards = res.data
      })
      .catch(err => {
        console.log(err)
      })
    },
  },
  created() {
    this.getBoardList()
  },
  mounted() {
      if (this.isLocal){
          this.API_URL = this.LocalURL
      } else {
          this.API_URL = this.DisURL
      }
  },
  computed: {
      curUid: sync('curUid'),
      DisURL: sync('DisURL'),
      LocalURL: sync('LocalURL'),
      isLocal: sync('isLocal'),
  },
  components: {
    MyPicture,
    MainBoard,
    BoardCreate,
  },
}
</script>

<style>

</style>