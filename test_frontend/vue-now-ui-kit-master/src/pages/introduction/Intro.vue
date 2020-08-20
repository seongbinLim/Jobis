<template>
  <div style="margin: 0">
    <div class="detailButton">
      <div v-b-modal.modal-info class="row mb-md-1" role="button" @click="curModal" style="margin: 0px 15px; border: 0px; outline: 0px;">
        <div class="col-md-12 pl-0">
            <h2 class="d-flex justify-content-start mb-sm-1 intro-title">{{intro.introduction.subject}}</h2>
        </div>
        <div class="col-md-12 pl-0">

          <n-button type="primary" class="not-hover" link>지원 회사</n-button>
          <n-button type="primary" class="not-hover" size="sm">{{intro.introduction.company}}</n-button>
          <br/>
          <n-button type="primary" class="not-hover" link>관련 태그</n-button>
          <n-button type="primary" class="not-hover" size="sm" v-for="tag in tags" :key="tag">{{tag}}</n-button>
          <br/>
          <n-button type="default" class="not-hover" link>공고 진행</n-button>
          <n-button type="default" class="not-hover" size="sm">{{intro.introduction.startdate}}</n-button> ~ 
          <n-button type="default" class="not-hover" size="sm">{{intro.introduction.enddate}}</n-button>
        </div>
      </div>
      <hr class="row intro-hr">
    </div>

    <b-modal :id="introModal" size="xl" :title="intro.introduction.subject" hide-footer @hidden="resetModal">
      
      <div v-if="!isUpdate">
        <form class="container">

          <label for="tags-pills">태그명</label>
          <b-form-tags
          input-id="tags-pills"
          v-model="tags"
          tag-variant="primary"
          tag-pills
          size="lg"
          separator=" "
          placeholder=""
          class="mb-2"
          disabled
          ></b-form-tags>

          <div class="row">
            <b-form-group
                label="지원 회사"
                label-for="company-input"
                class="col"
            >
            <b-form-input
                id="compamy-input"
                class="not-cursor"
                v-model="intro.introduction.company"
                disabled
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="항목 번호"
                label-for="subjectno-input"
                class="col"
            >
            <b-form-input
                id="subjectno-input"
                class="not-cursor"
                v-model="intro.introduction.subjectno"
                disabled
            ></b-form-input>
            </b-form-group>
          </div>

          <div class="row">
            <b-form-group
                label="지원 시작일"
                label-for="startdate-input"
                class="col"
            >
            <b-form-input
                id="startdate-input"
                class="not-cursor"
                v-model="intro.introduction.startdate"
                disabled
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="지원 마감일"
                label-for="enddate-input"
                class="col"
            >
            <b-form-input
                id="enddate-input"
                class="not-cursor"
                v-model="intro.introduction.enddate"
                disabled
            ></b-form-input>
            </b-form-group>
          </div>

          <div class="row">
            <b-form-group
                label="제한 글자 수"
                label-for="limitlen-input"
                class="col-6"
            >
            <b-form-input
                id="limitlen-input"
                class="not-cursor"
                v-model="intro.introduction.limitlength"
                disabled
            ></b-form-input>
            </b-form-group>
          </div>

          <b-form-group label="내용" label-for="contents-input">
          <b-form-textarea
            id="contents-input"
            class="not-cursor"
            v-model="intro.introduction.contents"
            rows="12"
            disabled
          ></b-form-textarea>
          </b-form-group>
        </form>

        <div class="d-flex justify-content-end">
          <n-button type="primary" class="mt-0 mb-0 d-inline-block" round @click="updateForm">수정</n-button>
          <IntroDelete :intro="intro"/>
        </div>

      </div>

      <div v-else>
        <form class="container">

          <label for="tags-pills">태그명</label>
          <b-form-tags
          input-id="tags-pills"
          v-model="tags"
          tag-variant="primary"
          tag-pills
          size="lg"
          separator=" "
          placeholder=""
          class="mb-2"
          ></b-form-tags>

          <b-form-group
              label="제목"
              label-for="subject-input"
          >
          <b-form-input
              id="subject-input"
              v-model="introData.introduction.subject"
          ></b-form-input>
          </b-form-group>

          <div class="row">
            <b-form-group
                label="지원 회사"
                label-for="company-input"
                class="col"
            >
            <b-form-input
                id="compamy-input"
                v-model="introData.introduction.company"
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="항목 번호"
                label-for="subjectno-input"
                class="col"
            >
            <b-form-input
                id="subjectno-input"
                v-model="introData.introduction.subjectno"
            ></b-form-input>
            </b-form-group>
          </div>

          <div class="row">
            <b-form-group
                label="지원 시작일"
                label-for="startdate-input"
                class="col"
            >
            <b-form-input
                id="startdate-input"
                v-model="introData.introduction.startdate"
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="지원 마감일"
                label-for="enddate-input"
                class="col"
            >
            <b-form-input
                id="enddate-input"
                v-model="introData.introduction.enddate"
            ></b-form-input>
            </b-form-group>
          </div>

          <div class="row">
            <b-form-group
                label="제한 글자 수"
                label-for="limitlen-input"
                class="col"
            >
            <b-form-input
                id="limitlen-input"
                v-model="introData.introduction.limitlength"
            ></b-form-input>
            </b-form-group>
          </div>

          <b-form-group label="내용" label-for="contents-input">
          <b-form-textarea
            id="contents-input"
            v-model="introData.introduction.contents"
            :state="introData.introduction.contents.length <= introData.introduction.limitlength"
            rows="12"
          ></b-form-textarea>
          </b-form-group>

        </form>

        <div class="d-flex justify-content-end">
          <n-button type="primary" class="mt-0 mb-0 d-inline-block" round @click="updateRequest">수정하기</n-button>
        </div>
      </div>

    </b-modal>

  </div>
</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'
import Button from '@/components/Button.vue'
import IntroDelete from './IntroDelete.vue'

export default {
    name: 'intro',

    components: {
      [Button.name]: Button,
      IntroDelete,
    },

    data() {
      return {
        API_URL: '',
        introModal: '',
        isUpdate: '',
        introData: {
          introduction: {
            subject: '',
            company: '',
            subjectno: '',
            startdate: '',
            enddate: '',
            limitlength: '',
            contents: '',
          },
        },
        tags: [],
      }
    },

    props: {
      intro: {
        type: Object,
      }
    },

    methods: {
      resetModal() {
        this.introModal = ''
        this.isUpdate = ''
      },

      curModal() {
        this.introModal = 'modal-info'
      },

      updateForm() {
        this.introData.introduction.subject = this.intro.introduction.subject
        this.introData.introduction.company = this.intro.introduction.company
        this.introData.introduction.subjectno = this.intro.introduction.subjectno
        this.introData.introduction.startdate = this.intro.introduction.startdate
        this.introData.introduction.enddate = this.intro.introduction.enddate
        this.introData.introduction.limitlength = Number(this.intro.introduction.limitlength)
        this.introData.introduction.contents = this.intro.introduction.contents

        this.isUpdate = true
      },
      
      updateRequest() {
        const url = `${this.API_URL}introduction/${this.intro.introduction.introno}/${this.curUid}`

        var hashTag = []
        for(var i=0; i<this.tags.length; i++) {
          hashTag.push({tagname: this.tags[i], uid: this.curUid,})
        }

        var putData = {
          introduction: {
            uid: this.curUid,
            company: this.introData.introduction.company,
            startdate: this.introData.introduction.startdate,
            enddate: this.introData.introduction.enddate,
            subjectno: Number(this.introData.introduction.subjectno),
            subject: this.introData.introduction.subject,
            contents: this.introData.introduction.contents,
            limitlength: Number(this.introData.introduction.limitlength),
          },
          hashTag: hashTag
        }

        axios.put(url, putData)
        .then(res => {
          console.log(res)
          
          this.intro.introduction.company = putData.introduction.company
          this.intro.introduction.startdate = putData.introduction.startdate
          this.intro.introduction.enddate = putData.introduction.enddate
          this.intro.introduction.subjectno = putData.introduction.subjectno
          this.intro.introduction.subject = putData.introduction.subject
          this.intro.introduction.contents = putData.introduction.contents
          this.intro.introduction.limitlength = putData.introduction.limitlength
          this.intro.hashTag.tagname = putData.hashTag.tagname
          this.intro.introduction.introno = res.data.introduction.introno
          
          location.reload()
          alert('자소서가 수정되었습니다')
        })
        .catch(err => {
          console.log(err)
          alert('자소서 수정에 실패했습니다')
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
      for(var i=0; i<this.intro.hashTag.length; i++) {
        this.tags.push(this.intro.hashTag[i].tagname)
      }

      if (this.isLocal){
        this.API_URL = this.LocalURL
      } else {
        this.API_URL = this.DisURL
      }
    },

}
</script>

<style>
.not-hover {
  pointer-events: none;
}

.intro-title {
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  font-weight: bold;
  margin: 0px;
}

.detailButton {
  background-color:#ffffff;
  transition: all 2s;
}
.detailButton:hover {
  background-color:#e77f67;
}

.intro-hr {
  margin: 0px;
}

.not-cursor:hover {
  cursor: default !important;
}

</style>