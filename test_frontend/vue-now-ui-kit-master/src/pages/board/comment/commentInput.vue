<template>
  <div class="row">
      <div class="col-sm-6 col-lg-9">
          <fg-input placeholder="댓글을 입력해주세요." v-model="commentData.content"></fg-input>
      </div> 
      <div class="col-sm-3 col-lg-3"><n-button type="primary" class="mt-0 mb-0" round @click="createComment" v-if="curUid && commentData.content !==''">등록</n-button></div>
  </div>
</template>


<script>
import axios from 'axios'
const API_URL = 'http://localhost:8080/'
import {sync} from 'vuex-pathify'

import Button from '@/pages/board/comment/Button.vue'
import formGroupInput from '@/pages/board/comment/formGroupInput.vue'

export default {
  name: 'commentInput',
  data() {
    return {
      commentData: {
        uid: '',
        boardno: null,
        content: '',
      }
    }
  },
  methods: {
    createComment() {
      this.commentData.boardno = this.$route.params.boardno
      this.commentData.uid = this.curUid
      axios.post(`${API_URL}comment/create/`, this.commentData)
      .then(res => {
        this.$emit('submitComment')
        this.commentData.content = ''
      })
      .catch(err => {
        console.log(err)
      })
    }
  },
  components: {
    [Button.name]: Button,
    [formGroupInput.name]: formGroupInput,
  },
  computed: {
    curUid: sync('curUid')
  },
  
}
</script>

<style>

</style>