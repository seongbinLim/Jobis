<template>
    <div>
        <div class="page-header clear-filter">
            <parallax class="page-header-image" style="background-image:url('img/bg22.jpg')"></parallax>
            <div class="content-center">
                <div class="container">
                    <h1 class="title">{{username}}의 블로그</h1>
                    <div class="text-center"></div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import axios from 'axios'
const API_URL = 'http://localhost:8080/'
import { sync } from 'vuex-pathify'

    export default {
      name: 'mypicture', 
    bodyClass: 'profile-page',
    data(){
      return{
        username:'',
        
            API_URL: '',
      }      
    },
    methods:{
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
    },
    created() {
        this.getUserdata()
    },
    
    computed: {
        curUid: sync('curUid'),
    },

    };
</script>

<style></style>