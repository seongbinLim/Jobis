<template>
    <div class="d-inline">

        <b-button class="btn btn-primary btn-round btn-lg" v-b-modal.des-update-modal>내 소개 관리</b-button>

        <b-modal
        id="des-update-modal"
        ref="modal"
        title="Description"
        @hidden="resetModal"
        hide-footer
        >
        <form ref="form" @submit.stop.prevent="handleSubmit" accept-charset="utf-8">
            <b-form-group
                label="소개"
                label-for="name-input"
                description="500자 이하로 작성해주세요"
            >
            <b-form-textarea
              id="textarea-state"
              v-model="userData.description"
              placeholder="내 소개를 입력해주세요"
              rows="3"
            ></b-form-textarea>
            </b-form-group>

            <b-button class="mt-3" block @click=handleSubmit>내 소개 변경</b-button>
        </form>
        </b-modal>
    </div>

</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'

export default {
    name: 'ProfileUpdate',

    data() {
      return {
        API_URL: '',
        interests: [
          { text: '취업 관심 분야를 고르세요', value: '' },
          '서비스업',
          '제조·화학',
          'IT·웹·통신',
          '은행·금융업',
          '미디어·디자인',
          '교육업',
          '의료·제약·복지',
          '판매·유통',
          '건설업',
          '기관·협회',
        ],
        tempDes: '',
      }
    },
    
    props: ['userData'],

    computed: {
        curUid: sync('curUid'),
        DisURL: sync('DisURL'),
        LocalURL: sync('LocalURL'),
        isLocal: sync('isLocal'),
    },

    methods: {
        resetModal() {
            this.userData.description = this.tempDes
        },

        handleSubmit() {

            if (this.userData.description.length > 500) {
                alert('500자 이하로 작성해주세요')
                return
            }

            var putData = {
                uid: this.curUid,
                description: this.userData.description,
            }
            
            const url = `${this.API_URL}account/update/description`

            axios.put(url, putData)
            .then(res => {
                this.$nextTick(() => {
                this.$bvModal.hide('des-update-modal')
                })

                console.log('description_update_success')
                console.log('utf-8 확인')
                console.log(putData.description)
                this.tempDes = this.userData.description
                location.reload()
                alert('내 소개가 수정되었습니다')
            })
            .catch(() => {
                alert('소개 수정 권한이 없습니다.')
                return
            })
            
        },
    },
    
    mounted() {
        this.tempDes = this.userData.description

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