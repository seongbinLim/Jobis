<template>
    <div id="header" v-if="isHeader">
        <h1>
            <router-link v-bind:to="{name:constants.URL_TYPE.POST.MAIN}">
                JOBIS
            </router-link>
        </h1>
        <div class="right">
            <div class="search-input">
                <i class="fas fa-search"></i>
                <input v-model="keyword" type="text"/>
            </div> 
            
            <div v-if="curUid" class="d-inline">
                <Logout/>
                <!-- <Mypage/> -->
            </div>
            <div v-else class="d-inline">
                <Login/>
                <Signup/>
            </div>
        </div>  
        
    </div>
</template>   

<script> 
    import constants from '../../lib/constants'
    import Login from '@/components/common/Login.vue'
    import Logout from '@/components/common/Logout.vue'
    import Signup from '@/components/common/Signup.vue'
    import { sync } from 'vuex-pathify'

    export default {
        name: 'Header',
        components: { 
            Login,
            Logout,
            Signup,
        },
        props: ['isHeader'],
        computed:{
            curUid: sync('curUid')
        },
        watch: {
        },
        created() {
        },
        methods : {
        },
        data: function() {
           return {
               constants,
               keyword : "",
           }
        },

    }
</script>
