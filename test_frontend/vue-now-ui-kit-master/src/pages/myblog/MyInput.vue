<template>
  <div class="mt-md-3">
  <div id="inputs" class="row d-flex justify-content-center">
    <fg-input placeholder="검색어를 입력해주세요" class="col-sm-8 "></fg-input>
  </div>
    
  </div>
</template>

<script>
import formGroupInput from '@/pages/myblog/formGroupInput.vue';

export default {
  name: 'myinput',
  bodyClass: 'myinput-page',
  components: {
    [formGroupInput.name]: formGroupInput,

  }

}


</script>


<style>

</style>