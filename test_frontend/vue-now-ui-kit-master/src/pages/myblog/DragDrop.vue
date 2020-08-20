<template>
  <div class="container">
    <div class="row w-100"  >
      <div
        class="col-4"
        @drop="drop"
        @dragover="allowDrop"
        data-type="started"
      >
        <div class="mt-5 align-center">제출전</div>
        <div
          class="border mt-3 margin-center"
          v-for="(article,index) in started"
          :key="index"
          :data-index="index"
          data-type="started"
          draggable="true"
          @dragstart="dragStart"
        >{{article}}</div>
      </div>

      <div
        class="col-4"
        @drop="drop"
        @dragover="allowDrop"
        data-type="proceed"
      >
        <div class="mt-5 align-center" >진행중</div>
        <div
          class="border mt-3 margin-center"
          v-for="(article,index) in proceed"
          :key="index"
          :data-index="index"
          data-type="proceed"
          draggable="true"
          @dragstart="dragStart"
        >{{article}}</div>
      </div>

      <div
        class="col-4"
        @drop="drop"
        @dragover="allowDrop"
        data-type="finished"
      >
        <div class="mt-5 align-center">제출완료</div>
        <div
          class="border mt-3 margin-center"
          v-for="(article,index) in finished"
          :key="index"
          :data-index="index"
          data-type="finished"
          draggable="true"
          @dragstart="dragStart"
        >{{article}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DragDrop",
  data() {
    return {
      started: ["한전", "현대자동차"],
      proceed: ["KT", "LG 유플러스", "AWS"],
      finished: ["삼성전자", "SKT"],
    };
  },
  methods: {
    dragStart(event) {
      event.dataTransfer.setData("fromarray", event.target.dataset.type);
      event.dataTransfer.setData("fromindex", event.target.dataset.index);
    },
    allowDrop(event) {
      event.preventDefault();
    },
    drop(event) {
      event.preventDefault();
      var fromarray = event.dataTransfer.getData("fromarray");
      var fromindex = event.dataTransfer.getData("fromindex");
      var removed = this[fromarray].splice(fromindex, 1);
      var toarray = event.target.dataset.type;
      this[toarray].push(removed[0]);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.align-center { 
  text-align: center;
  padding: 10px 0px;
  font-family:  sans-serif,  "맑은 고딕", serif; 
  font-size: 24px;
  color:#010720;
  font-weight: bolder;
  border-radius:10px;
  background-color:#FFEF99; 
  height:8vh
  }
.align-justify { text-align: justify;}
.margin-center {
  margin-left:auto;
  margin-right:auto;
  height: 7vh;  
  text-align: center;
  font-size: 20px;
  color: #0e0e0e;
  font-weight: bold;
  padding: 10px 0px;
  border-radius:10px;
  background-color:#F5F5F5;
  font-family: sans-serif,  "맑은 고딕", serif; 
}
.col-4{
  height: 70vh; 
  background-color: #735FE6; 
  border-radius:10px; 
  border: 6px solid white;
} 
</style>
