<template>
      <div class="col-md-4">

      <b-card 
          border-variant="primary"
        header-bg-variant="primary"
        header-text-variant="white"
        align="center" 
      :header="this.jobpost.company" 
      class="text-center">
        <b-card-text id="text-area">
        <p id="jobdetail" class="title-ellipsis">채용공고 : {{jobpost.title}}</p>    
        <p id="jobdetail">채용 시작일 : {{jobpost.startdate}}</p>    
        <p id="jobdetail">채용 마감일 : {{jobpost.enddate}}</p>    


        </b-card-text>
      </b-card>
      </div>
</template>
<script>
export default {
    name:'jobpost',
    props:{
        jobpost:{
            type:Object,
        }
    },
}
</script>
<style>
.card-header {
  font-size: large;
}

.card-text {
  font-size: medium;
}
.title-ellipsis{
  width:250px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: left !important; 
}
</style>