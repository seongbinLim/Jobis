<template>
    <div class="d-inline">

        <nav-link class="nav-link" to="/signup" v-b-modal.sign-up>
          <p class="text-white" v-b-modal.sign-up>signup</p>
        </nav-link>

        <b-modal
        id="sign-up"
        ref="modal"
        title="회원가입"
        @show="resetModal"
        @hidden="resetModal"
        hide-footer
        >
        <form ref="form" @submit.stop.prevent="handleSubmit">
            <b-form-group
                label="ID"
                label-for="uid-input"
            >
            <b-form-input
                id="uid-input"
                v-model="userData.uid"
                required
                placeholder="Enter ID"
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="NICKNAME"
                label-for="name-input"
            >
            <b-form-input
                id="name-input"
                v-model="userData.name"
                required
                placeholder="Enter NICKNAME"
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="PASSWORD"
                label-for="password-input"
            >
            <b-form-input
                type="password"
                id="password-input"
                v-model="password1"
                required
                placeholder="Enter Password"
            ></b-form-input>
            </b-form-group>

            <b-form-group
                label="CONFIRM PASSWORD"
                label-for="password-input"
            >
            <b-form-input
                type="password"
                id="password-input"
                v-model="password2"
                required
                placeholder="Check Password"
            ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-1"
              label="Email"
              label-for="email-input"
              description="이메일 인증이 필요합니다."
            >
              <b-form-input
                id="email-input"
                v-model="userData.email"
                type="email"
                required
                placeholder="Enter email"
              ></b-form-input>
            </b-form-group>

            

            <b-form-group id="input-group-3" label="관심사1" label-for="inter1-input">
              <b-form-select
                id="inter1-input"
                v-model="userData.interest1"
                :options="interests"
                required
              ></b-form-select>
            </b-form-group>

            <b-form-group id="input-group-3" label="관심사2" label-for="inter1-input" description="관심사 1과 달라야 합니다">
              <b-form-select
                id="inter1-input"
                v-model="userData.interest2"
                :options="interests"
                required
              ></b-form-select>
            </b-form-group>

            <b-button class="mt-3" block @click=handleSubmit>Signup</b-button>
        </form>
        </b-modal>
    </div>

</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'

const API_URL = 'http://localhost:8080/'

export default {
    name: 'Signup',
    data() {
        return {
            userData: {
              email: '',
              uid: '',
              password: '',
              name: '',
              phone: '',
              interest1: '',
              interest2: '',
            },

            password1: '',
            password2: '',

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
        }
    },

    computed: {
        curUid: sync('curUid')
    },

    methods: {
        resetModal() {
            this.userData.uid = ''
            this.userData.email = ''
            this.password1 = ''
            this.password2 = ''
            this.userData.password = ''
            this.userData.name = ''
            this.userData.phone = ''
            this.userData.interest1 = ''
            this.userData.interest2 = ''
        },
        handleSubmit() {
            // Exit when the form isn't valid
            if (this.userData.uid === '') {
                alert('ID를 입력해주세요')
                return
            }
            if (this.userData.email === '') {
              alert('EMAIL을 입력해야 합니다')
              return
            }
            if (this.userData.name === '') {
              alert('NICKNAME을 입력해야 합니다')
              return
            }
            if (this.password1 === '' || this.password2 === '') {
                alert('PASSWORD를 입력해주세요')
                return
            }
            if (this.password1 !== this.password2) {
              alert('PASSWORD가 일치하지 않습니다')
              return
            }
            if (this.userData.interest1 === '' || this.userData.interest2 === '') {
              alert('관심사를 선택해주세요')
              return
            }
            if (this.userData.interest1 === this.userData.interest2) {
              alert('서로 다른 관심사를 선택해주세요')
              return
            }

            this.signup();
        },

        signup() {
          const url = `${API_URL}account/join`
          this.userData.password = this.password1

          console.log(url)
          axios.post(url, this.userData)
          .then(res => {
            console.log(res)
            this.login()
          })
          .catch(() => {
            alert("회원가입에 문제가 발생했습니다")
            return
          });
        },


        login() {
            const url = `${API_URL}account/login?uid=${this.userData.uid}&password=${this.userData.password}`;
            console.log(url);
            axios
            .get(url)
            .then(({data}) => {
                console.log(data);
                this.curUid = this.userData.uid;
                this.$nextTick(() => {
                this.$bvModal.hide('sign-up')
                })
                console.log('login_success');
                this.$router.push('/')
            })
            .catch(() => {
                alert("ID 또는 PASSWORD가 틀렸습니다");
                return
            });
        },
    }
}
</script>

<style>

</style>