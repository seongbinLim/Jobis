<template>
    <div>
        <b-button class="btn-primary" v-if="!curUid" @click="loginAlert">자소서 작성</b-button>
        <b-button v-b-modal.modal-multi-1 class="btn-primary" @click="openModal">자소서 작성</b-button>

        <b-modal id="modal-multi-1" size="lg" title="자소서 기본 사항" hide-footer no-stacking>

            <form ref="form" @submit.stop.prevent="handleSubmit" accept-charset="utf-8">

            <label for="tags-pills">태그명</label>
            <b-form-tags
            input-id="tags-pills"
            v-model="tagList"
            tag-variant="primary"
            tag-pills
            size="lg"
            separator=" "
            placeholder="태그를 입력해주세요"
            class="mb-2"
            ></b-form-tags>

            <b-form-group
                label="회사명"
                label-for="company-input"
            >
            <b-form-input
                type="text"
                id="company-input"
                v-model="introduction.company"
                required
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="채용 시작일"
                label-for="startdate-input"
            >
            <b-form-input
                type="text"
                id="startdate-input"
                v-model="introduction.startdate"
                placeholder="연도-월-일 ex> 2020-8-11"
                required
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="채용 마감일"
                label-for="enddate-input"
            >
            <b-form-input
                type="text"
                id="enddate-input"
                v-model="introduction.enddate"
                placeholder="연도-월-일 ex> 2020-8-11"
                required
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="항목 번호"
                label-for="subjectno-input"
            >
            <b-form-input
                id="subjectno-input"
                v-model="introduction.subjectno"
                required
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="제한 글자 수"
                label-for="limit-input"
            >
            <b-form-input
                id="limit-input"
                v-model="introduction.limitlength"
                required
            ></b-form-input>
            </b-form-group>

            </form>
            
            <div class="d-flex flex-row-reverse">
                <b-button class="btn-round btn-info" @click="nextModal">다음</b-button>
            </div>
        </b-modal>

        <b-modal id="modal-multi-2" size="lg" title="자소서 작성하기" hide-footer no-stacking>

            <b-form-group
                label="제목"
                label-for="subject-input"
            >
            <b-form-input
                id="subject-input"
                v-model="introduction.subject"
                required
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="내용"
                label-for="name-input"
            >
            <b-form-textarea
            id="textarea-state"
            placeholder="자소서를 입력해주세요"
            rows="12"
            v-model="introduction.contents"
            ></b-form-textarea>
            </b-form-group>

            <b-button class="btn-round btn-info" @click="prevModal">이전</b-button>

            <div class="d-flex flex-row-reverse">
                <b-button clas="d-flex flex-row-reverse btn-primary" size="md" @click=handleSubmit>작성</b-button>
            </div>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
import {sync} from 'vuex-pathify'


export default {
    name: 'IntroCreate',

    computed: {
        curUid: sync('curUid'),
        DisURL: sync('DisURL'),
        LocalURL: sync('LocalURL'),
        isLocal: sync('isLocal'),
    },

    data() {
        return {
            API_URL: '',

            hashTag: {
                tagname: ''
            },

            introduction: {
                uid: '',
                company: '',
                startdate: '',
                enddate: '',
                subjectno: '',
                subject: '',
                contents: '',
                limitlength: '',
            },
            
            tagList: [],
        }
    },
    methods: {
        openModal() {
            this.resetModal()
            this.$bvModal.show('modal-multi-1')
        },

        resetModal() {
            this.hashTag.tagname = ''
            this.introduction.uid = this.curUid
            this.introduction.company = ''
            this.introduction.startdate = ''
            this.introduction.enddate = ''
            this.introduction.subjectno = ''
            this.introduction.subject = ''
            this.introduction.contents = ''
            this.introduction.limitlength = ''
            this.introduction.subject = ''
            this.introduction.contents = ''

            return
        },

        nextModal() {
            if (this.tagList.length === 0) {
                alert('태그를 입력해주세요')
                return
            }
            if (this.introduction.company === '') {
                alert('회사명을 입력해주세요')
                return
            }
            if (this.introduction.startdate === '') {
                alert('시작일을 입력해주세요')
                return
            }
            if (this.introduction.enddate === '') {
                alert('마감일을 입력해주세요')
                return
            }
            if (this.introduction.subjectno === '') {
                alert('항목 번호를 입력해주세요')
                return
            }
            if (this.introduction.limitlength === '') {
                alert('제한 글자 수를 입력해주세요')
                return
            }

            this.$bvModal.hide('modal-multi-1')
            this.$bvModal.show('modal-multi-2')
        },
        
        prevModal() {
            this.$bvModal.hide('modal-multi-2')
            this.$bvModal.show('modal-multi-1')
        },

        handleSubmit() {
            if (this.introduction.subject === '') {
                alert('자소서 제목을 입력해주세요')
                return
            }
            if (this.introduction.contents === '') {
                alert('자소서 내용을 입력해주세요')
                return
            }

            const url = `${this.API_URL}introduction/create?uid=${this.curUid}`

            var hashTag = []
            for(var i=0; i<this.tagList.length; i++) {
                hashTag.push({tagname: this.tagList[i], uid: this.curUid,})
            }
        
            var requestBody = {
                introduction: this.introduction,
                hashTag: hashTag,
            }

            axios.post(url, requestBody)
            .then(res => {
                console.log(res)
                this.$bvModal.hide('modal-multi-2')
                location.reload()
                alert('작성되었습니다')
            })
            .catch((err) => {
                console.log(err)
                alert('자소서를 저장하지 못했습니다')
                return
            })
        },

        loginAlert() {
            alert('로그인이 필요합니다')
        }
    },

    mounted() {
        this.introduction.uid = this.curUid

        if (this.isLocal){
            this.API_URL = this.LocalURL
        } else {
            this.API_URL = this.DisURL
        }
        
    },
    

}
</script>

<style>

</style>