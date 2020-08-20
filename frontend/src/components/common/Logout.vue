<template>
    <nav-link to="/login">
        <i class="now-ui-icons ui-1_lock-circle-open"></i> Logout
    </nav-link>
</template>

<script>
import axios from 'axios'
import { sync } from 'vuex-pathify'

const API_URL = 'http://localhost:8080/'

export default {
    name: 'Logout',
    methods: {
        logout() {
            const url = `${API_URL}account/logout`;
            axios
            .get(url)
            .then(({data}) => {
                console.log(data);
                this.curUid = ''
                console.log('logout success')
                this.$router.push('/')
            })

            .catch(() => {
                alert("로그아웃 에러");
                return
            });
        },
    },

    computed: {
        curUid: sync('curUid')
    },
}

</script>

<style>

</style>