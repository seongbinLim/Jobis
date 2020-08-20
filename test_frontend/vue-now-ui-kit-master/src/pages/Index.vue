<template>
    <div>
        <div>
            <div class="mb-4">
                <el-carousel class="page-header-image" height="650px">
                    <el-carousel-item>
                        <img class="main-images" src="img/cs01.gif" alt="First slide"/>
                        <div class="carousel-caption d-none d-md-block"></div>
                    </el-carousel-item>
                    <el-carousel-item>
                        <img class="main-images" src="img/cs02.gif" alt="Second slide"/>
                        <div class="carousel-caption d-none d-md-block"></div>
                    </el-carousel-item>
                    <el-carousel-item>
                        <img class="main-images" src="img/cs03.gif" alt="Third slide"/>
                        <div class="carousel-caption d-none d-md-block"></div>
                    </el-carousel-item>
                </el-carousel>
            </div>
        </div>

        <div class="main">
          <div class="container">
            <div class="row">
              <Jobpost v-for="jobpost in jobposts" :key="jobpost.id" :jobpost="jobpost"/>
            </div>
          </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <h2 class="title mb-md-5">Top Topic</h2>
                        <div class="col-md-10">
                            <TopListBoard
                                v-for="topBoard in topBoards"
                                :key="topBoard.boardno"
                                :topBoard="topBoard"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import axios from 'axios';
    import {sync} from 'vuex-pathify'

    import {Carousel, CarouselItem} from 'element-ui'
    import Button from '@/pages/board/Button.vue'
    import TopListBoard from '@/pages/board/TopListBoard.vue'
    import Jobpost from '@/pages/Jobpost.vue'
    export default {
        name: 'index',
        bodyClass: 'index-page',
        data() {
            return {
                API_URL: '',
                topBoards: [],
                jobposts:[],
                modals: {
                    classic: false
                }
            }
        },
        methods: {
            getTopBoardList() {
                axios
                    .get(`${this.API_URL}api/board/listOrderByLikes/`)
                    .then(res => {
                        this.topBoards = res.data
                    })
                    .catch(err => {
                        console.log(err)
                    })
                },
                getJobpostList(){
                  axios.get(`${this.API_URL}/jobpost`)
                  .then(res=>{
                    this.jobposts = res.data
                    this.jobposts = this.jobposts.slice(0,6)
                  })
                  .catch(err=>{
                    console.log(err)
                  })
                },

        },
        created() {
            this.getTopBoardList()
            this.getJobpostList()
        },

        computed: {
            curUid: sync('curUid'),
            DisURL: sync('DisURL'),
            LocalURL: sync('LocalURL'),
            isLocal: sync('isLocal')
        },

        mounted() {
            if (this.isLocal) {
                this.API_URL = this.LocalURL
            } else {
                this.API_URL = this.DisURL
            }
        },
        components: {
            [Carousel.name]: Carousel,
            [CarouselItem.name]: CarouselItem,
            [Button.name]: Button,
            TopListBoard,
            Jobpost,
        }
    };
</script>
<style>
    .main-images {
        height: 100%;
        width: 100%;
    }
</style>