<template>
<div>
    <b-button v-b-modal.modal-1 class="btn-primary btn-round" v-if="curUid">글쓰기</b-button>
    <b-button class="btn-primary btn-round" v-if="!curUid" @click="loginAlert">글쓰기</b-button>

    <b-modal id="modal-1" title="Community 글쓰기" size="lg" hide-footer>
        <hr class="mt-0">
        <div class="pt-0">
            <form class="container">
                <div class="row">
                    <input type="text" id="title" class="col-md-12 mb-md-2" placeholder="제목을 입력해 주세요." v-model="boardData.subject">           
                </div>
                <div class="row">
                    <textarea class="col-md-12 mb-md-1" rows="10" style="resize:none;" placeholder="내용을 입력해 주세요." v-model="boardData.content"></textarea>
                </div>
                <div class="row d-flex justify-content-center">
                    <b-button @click="createBoard" class="btn-primary btn-round col-md-8 m-md-1">등록</b-button>
                </div>
            </form>
        </div>
    </b-modal>
</div>
</template>

<script>
import axios from 'axios'
import {sync} from 'vuex-pathify'

export default {
    name: 'BoardCreate',

    data() {
        return {
            boardData: {
                uid: '',
                subject: '',
                content: '',
            },
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

    methods: {
        createBoard(){
            this.boardData.uid = this.curUid
            axios.post(`${this.API_URL}api/board/create/`, this.boardData)
            .then(res=>{
                alert('글 작성 완료')
                location.reload()
            })
            .catch(err=> {
                console.log(err)
            })

        },
        loginAlert() {
            alert('로그인 해주세요')
        }
    },
    

}
</script>

<style>

</style>