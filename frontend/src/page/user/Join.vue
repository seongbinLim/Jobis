<template>
    <div class="user" id="join"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>회원가입</h1>
                <div class="form-wrap">
                    <div class="input-wrap">
                        <input v-model="nickName"
                            id="nickname"
                            placeholder="닉네임을 입력해주세요" type="text"/>
                    </div>

                    <div class="input-wrap">
                        <input v-model="email" 
                            id="email"
                            placeholder="이메일을 입력해주세요"
                            type="text"/>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="password"
                            id="password" 
                            :type="passwordType"
                            placeholder="비밀번호를 입력해주세요"/>
                        <span :class="{active : passwordType==='text'}">
                                <i class="fas fa-eye"></i>
                            </span>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="passwordConfirm" 
                            id="password-confirm"
                            :type="passwordConfirmType"
                            placeholder="비밀번호를 한번 더 입력해주세요"/>
                        <span :class="{active : passwordConfirmType==='text'}">
                                <i class="fas fa-eye"></i> 
                            </span>
                    </div>
                </div>

                <label>
                    <input v-model="isTerm" type="checkbox" id="term"/>
                    <span>약관에 동의합니다</span>
                </label>

                <span class="go-term">약관 보기</span>

                <button class="btn" v-on:click="join">
                    <span>
                        작성완료
                    </span>
                </button>
            </div>


        </div> 
        

    </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import http from '@/http-common.js'

    export default {
        components: {
        },
        created(){
        },
        methods: {
            join() {
                let err = true;
                let msg ="";
                if(!(this.email && this.password) ) {
                    err = false;
                    msg = '빈칸잇수요';
                }
                if(!err) {
                    alert(msg);
                    return;
                }
                http
                .post('/account/join', {
                    email: this.email,
                    nickName: this.nickName,
                    password: this.password
                })
                .then(({data}) => {
                    console.log(data);
                    location.href='/user/login';
                })
                .catch(() => {
                    alert("에러발생");
                });

            }
        },
        watch: {
        },
        data: () => {
            return {
                email: '',
                nickName: '',
                password: '',
                passwordConfirm: '',
                isTerm: false,
                passwordType:"password",
                passwordConfirmType:"password",
            }
        }

    }

</script>


