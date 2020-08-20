<template>
    <nav-link to="#">
      <i class="now-ui-icons ui-1_lock-circle-open"></i> Logout
    </nav-link>
</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'

export default {
    name: 'Logout',

    data() {
        return {
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
        logout() {
            const url = `${this.API_URL}account/logout`;
            axios
            .get(url)
            .then(({data}) => {
                console.log('???????????????')
                console.log(data);
                this.curUid = ''
                console.log('logout success')
                location.reload()
            })

            .catch(() => {
                alert("로그아웃 에러");
                return
            });
        },
    },

}

</script>

<style>

</style>