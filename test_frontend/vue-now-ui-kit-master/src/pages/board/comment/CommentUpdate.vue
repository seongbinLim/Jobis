<template>
  <div class="row">
        <div class="col-md-8 pl-0"><fg-input v-model="commentContent"></fg-input></div>
        <div class="col-md-4">
            <n-button type="primary" class="mt-0 mb-0" round @click="updateComment">등록</n-button>
            <n-button type="primary" class="mt-0 mb-0" round @click="changePosition">취소</n-button>
        </div>
  </div>
</template>

<script>
import axios from 'axios'
import {sync} from 'vuex-pathify'
const API_URL = 'http://localhost:8080/'

import Button from '@/pages/board/comment/Button.vue'
import formGroupInput from '@/pages/board/comment/formGroupInput.vue'


export default {
    name: 'CommentUpdate',
    props: {
        comment: {
            type: Object
        }
    },
    data() {
        return {
            commentContent: this.comment.content,
        }
    },
    methods: {
        updateComment() {
            this.comment.content = this.commentContent
            axios.put(`${API_URL}comment/modify`, this.comment)
            .then(res => {
                this.changePosition()
            })
            .catch(err => {
                console.log(err)
            })
        },

        changePosition() {
            this.$emit('changePosition')
        }
    },
    components: {
        [Button.name]: Button,
        [formGroupInput.name]: formGroupInput,
    },

}
</script>

<style>

</style>