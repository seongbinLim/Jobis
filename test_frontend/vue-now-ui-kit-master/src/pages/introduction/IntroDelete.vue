<template>
  <div class="d-inline-block">
    <n-button type="primary" class="mt-0 mb-0" round @click="deleteRequest">삭제</n-button>
  </div>
</template>

<script>
import axios from 'axios'
import Button from '@/components/Button.vue'
import { sync } from 'vuex-pathify'

export default {
  name: 'introdelete',

  components: {
    [Button.name]: Button,
  },

  data() {
    return {
      API_URL: '',
    }
  },

  props: {
    intro: {
      type: Object,
    }
  },

  methods: {
    deleteRequest() {
      const url = `${this.API_URL}introduction/${this.intro.introduction.introno}`
    
      axios.delete(url)
      .then(res => {
        console.log(res)
        alert('자소서가 삭제되었습니다')
        location.reload()
      })
      .catch(err => {
        console.log(err)
        alert('삭제에 실패했습니다')
      })
    },
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
  }




}
</script>

<style>

</style>