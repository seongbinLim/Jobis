<template>
    <div>
        <div class="page-header page-header-small">
            <parallax
                class="page-header-image"
                style="background-image: url('img/bg23.jpg')"
            >
            </parallax>
            <div class="content-center">
                <div class="container">
                <h1 class="title">{{username}}의 블로그</h1>
                <div class="text-center"></div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row mt-5">
                <div class="col-md-12">
                    <h2 id="boardDetailSubject" class="d-flex justify-content-center">{{boardData.subject}}</h2>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-8">
                        <h5 id="boardDetailUid" class="d-inline text-muted ">{{boardData.uid}}</h5>
                        <h6 id="boardDetailDate" class="d-inline text-muted ml-2">{{boardData.updatedat}}</h6>
                    </div>
                        <div class="col-md-4 d-flex justify-content-end">
                            <BoardUpdate class="d-inline-block" :boardData="boardData"/>
                            <n-button type="primary" class="mt-0 mb-0 d-inline-block" round @click="deleteBoard" v-if="curUid===boardData.uid">삭제</n-button>
                        </div>
                    <hr>
                </div>  
            </div>
            <div class="container">
                <div class="row mt-md-3">
                    <div class="col-md-12">
                        <p class="mt-md-2">
                            {{boardData.content}}
                        </p>
                        <br />
                        <br />
                <div class="row">
                    <div class="col-md-6">
                        <i id="heart" class="far fa-heart fa-lg" roll="button" @click="loginAlert" v-if="!curUid"></i>
                        <i id="heart" class="fas fa-heart fa-lg" roll="button" @click="changeLike" v-if="curUid && like===true"></i>
                        <i id="heart" class="far fa-heart fa-lg" roll="button" @click="changeLike" v-if="curUid && like===false"></i>
                        <p class="d-inline-block text-muted pr-3 pl-2"> {{boardData.likes}}</p>
                        <p class="d-inline-block text-muted">댓글 {{commentNumber}}</p>
                    </div>
                </div>        
                        <hr>
                    </div>
                </div>
            </div>
            <div class="container">
                <h4 class="text-muted mt-lg-1">댓글</h4>
                <commentList v-for="comment in comments" :key="comment.commentno" :comment="comment"/>
                <commentInput @submitComment="getComment"/>
            </div>

        </div>
    </div>

</template>

<script>
import axios from 'axios'
const API_URL = 'http://localhost:8080/'
import { sync } from 'vuex-pathify'

import Button from '@/pages/board/Button.vue'
import BoardUpdate from '@/pages/board/BoardUpdate.vue'
import commentList from '@/pages/board/comment/commentList.vue'
import commentInput from '@/pages/board/comment/commentInput.vue'

export default {
    name: 'BoardDetail',
    data() {
        return {
            boardData: [],
            commentNumber: '',
            like: null,
            comments: [],
            username: 'Jobis',

            API_URL: '',
        }
    },
    methods: {
        getBoardData() {
            const boardno = this.$route.params.boardno
            axios.get(`${this.API_URL}api/board/boardno/${boardno}/`)
            .then(res=>{
                this.boardData = res.data
                console.log(curUid)
            })
            .catch(err => {
                console.log(err)
            })
        },
        deleteBoard() {
            const boardno = this.$route.params.boardno
            axios.delete(`${this.API_URL}api/board/${boardno}/`)
            .then(res => {
                this.$router.push({name:'index'})
            })
            .catch(err => {
                console.log(err)
            })
        },
        changeLike() {
            const boardno = this.$route.params.boardno
            const uid = this.curUid
            axios.get(`${this.API_URL}api/board/like/${boardno}/${uid}/`)
            .then(res =>{
                if (res.data == 'cancle'){
                    this.boardData.likes -= 1
                    this.like = false
                } else {
                    this.boardData.likes += 1
                    this.like = true
                }
                this.changeBoardLike()
                
            })
            .catch(err => {
                console.log(err)
            })
        },
        changeBoardLike() {
                    const boardno = this.$route.params.boardno
                    axios.put(`${this.API_URL}api/board/${boardno}/`, this.boardData.likes)
                    .then(res => {
                        console.log(this.boardData.likes)
                    })
                    .catch(err => {
                        console.log(err)
                    })
                },
        checkLike() {
            const boardno = this.$route.params.boardno
            const uid = this.curUid
            axios.get(`${this.API_URL}api/board/likeCheck/${boardno}/${uid}/`)
            .then(res => {
                this.like = res.data
            })
            .catch(err => {
                console.log(err)
            })
        },
        getComment() {
            const boardno = this.$route.params.boardno
            axios.get(`${this.API_URL}comment/list/${boardno}/`)
            .then(res => {
                this.commentNumber = res.data.length
                this.comments = res.data
                this.$emit('commentNumber', this.commentNumber)
            })
            .catch(err => {
                console.log(err)
            })
        },
        getUserdata() {
            var postData = {
                uid: this.curUid,
            }
            axios.post(`${this.API_URL}account/userinfo/`, postData)
            .then(res => {
                this.username = res.data.name
            })
            .catch(err => {
                console.log(err)
            })
        },
        loginAlert() {
            alert('로그인 해주세요')
        },
    },
    created() {
        this.getBoardData()
        this.checkLike()
        this.getComment()
        this.getUserdata()
    },
    computed: {
        curUid: sync('curUid'),
    },
    components: {
        BoardUpdate,
        commentList,
        commentInput,
        [Button.name]: Button,
    },

}
</script>

<style>
#heart {
    color:#ff3636;
    cursor:pointer !important;
}

#boardDetailUid {
    font-weight: bold;
}

#boardDetailDate {
    font-weight: normal;
}

#boardDetailSubject {
    font-weight: bold;
}
</style>