<template>
<span>
    <div class="row mb-2">
        <div class="col-md-8">
            <h6 class="d-inline-block text-muted mb-lg-3 mr-md-2">{{comment.uid}}</h6>
            <h6 id="reg_date" class="d-inline-block text-muted mb-lg-3">{{comment.reg_date}}</h6>
        </div>
        <div class="col-md-4">
            <n-button type="primary" class="mt-0 mb-0" round @click="changePosition" v-if="this.position && comment.uid === curUid">수정</n-button>
            <n-button type="primary" class="mt-0 mb-0" round @click="deleteComment" v-if="this.position && comment.uid === curUid">삭제</n-button>
        </div>
    </div>
    <div class="container" v-if="this.position">
        <div class="row">
            <p>{{comment.content}}</p>
        </div>
        <div class="row"><hr class="col-md-12"></div>
    </div>
    <div class="container" v-if="!this.position">
        <CommentUpdate :comment="comment" @changePosition="changePosition"/>
        <div class="row"><hr class="col-md-12"></div>
    </div>
</span>
</template>


<script>
import axios from 'axios'
import {sync} from 'vuex-pathify'
const API_URL = 'http://localhost:8080/'

import CommentUpdate from '@/pages/board/comment/CommentUpdate.vue'
import Button from '@/pages/board/comment/Button.vue'
import formGroupInput from '@/pages/board/comment/formGroupInput.vue'

export default {
    name: 'commentList',
    props: {
        comment: {
            type: Object,
        }
    },
    data() {
        return {
            position: true,
        }
    },
    methods: {
        changePosition() {
            if(this.position === true) {
                this.position = false
            } else {
                this.position = true
            }
        },
        deleteComment() {
            const commentno = this.comment.commentno
            axios.delete(`${API_URL}comment/${commentno}/`)
            .then(res => {
                location.reload()
            })
            .catch(err =>{
                console.log(err)
            })
        }
    },
    computed: {
        curUid: sync('curUid')
    },
    components: {
        [Button.name]: Button,
        [formGroupInput.name]: formGroupInput,
        CommentUpdate,
    },
}
</script>

<style>
#reg_date{
    font-size: x-small;
}
</style>