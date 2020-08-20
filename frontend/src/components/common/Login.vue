<template>
    <div class="d-inline">
        <b-button v-b-modal.modal-prevent-closing>로그인</b-button>

        <b-modal
        id="modal-prevent-closing"
        ref="modal"
        title="로그인"
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
                v-model="uid"
                required
            ></b-form-input>
            </b-form-group>
            <b-form-group
                label="PASSWORD"
                label-for="password-input"
            >
            <b-form-input
                type="password"
                id="password-input"
                v-model="password"
                required
            ></b-form-input>
            </b-form-group>
            <b-button class="mt-3" block @click=handleSubmit>Login</b-button>
        </form>
        </b-modal>
    </div>

</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'

const API_URL = 'http://localhost:8080/'

export default {
    name: 'Login',
    data() {
        return {
            uid: '',
            password: '',
        }
    },

    computed: {
        curUid: sync('curUid')
    },

    methods: {
        resetModal() {
            this.uid = ''
            this.password = ''
        },
        handleSubmit() {
            // Exit when the form isn't valid
            if (this.uid === '') {
                alert('ID를 입력해주세요')
                return
            }
            if (this.password === '') {
                alert('PASSWORD를 입력해주세요')
                return
            }

            this.login();
        },
        login() {
            const url = `${API_URL}account/login?uid=${this.uid}&password=${this.password}`;
            console.log(url);
            axios
            .get(url)
            .then(({data}) => {
                console.log(data);
                this.curUid = this.uid;
                this.$nextTick(() => {
                this.$bvModal.hide('modal-prevent-closing')
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