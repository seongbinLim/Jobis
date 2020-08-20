<template>
  <div>
    <b-button v-b-modal.modal-2 class="btn-primary btn-round mt-0 mb-0 mr-1" v-if="curUid===boardData.uid">수정</b-button>

    <b-modal id="modal-2" title="Community 글쓰기" size="lg" hide-footer>
    <hr class="mt-0">
    <div class="pt-0">
        <form class="container">
            <div class="row">
                <input type="text" class="col-md-12 mb-md-2" id="title" v-model="boardSubject">              
            </div>
            <div class="row">
                <textarea class="col-md-12 mb-md-1"  cols="30" rows="10" style="resize:none;" v-model="boardContent"></textarea>
            </div>
            <div class="row d-flex justify-content-center">
                <b-button @click="BoardUpdate" class="btn-primary btn-round col-md-8 m-md-1">수정</b-button>
            </div>
        </form>
    </div>
    </b-modal>
</div>
</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'

export default {
    name: 'BoardUpdate',
    props: {
        boardData: {
            type: Object
        }
    },
    data() {
        return {
            boardSubject: this.boardData.subject,
            boardContent: this.boardData.content,

            API_URL: '',
        }
    },
    methods: {
        BoardUpdate() {
            const boardno = this.$route.params.boardno
            this.boardData.subject = this.boardSubject
            this.boardData.content = this.boardContent
            axios.put(`${API_URL}api/board/${boardno}/`, this.boardData)
            .then(res => {
                this.$bvModal.hide('modal-2')
            })
            .catch(res => {
                console.log(err)
            })
        },
        hidemodal() {

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
    }
}
</script>

<style>

</style>