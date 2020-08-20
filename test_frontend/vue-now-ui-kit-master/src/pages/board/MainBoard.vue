<template>
  <div class="container">
    <div class="row mb-md-1">
      <div class="col-md-12 pl-0 d-flex justify-content-between">
          <h2 id="boardSubject" class="d-inline-block mb-sm-1" @click="boardDetailPush" role="button">{{board.subject}}</h2>
          <h5 id="boardUid" class="d-inline-block mb-0">{{board.uid}}</h5>
      </div>
    </div>
    <div class="row mt-md-1">
      <div class="col-md-12 pl-0">
        <p class="text-muted d-flex justify-content-start" @click="boardDetailPush" role="button">
          {{board.content}}
        </p>
      </div>
    </div>
    <div class="row mt-0">
      <div>
        <!-- <i class="now-ui-icons ui-2_favourite-28 text-danger p-1"></i> -->
        <i id="heartMainBoard" class="fas fa-heart fa-lg " ></i>
        <p class="d-inline-block text-muted pr-3 ml-2"> {{board.likes}}</p>
        <p class="d-inline-block text-muted">댓글 {{commentNumber}}</p>
      </div>
    </div>
    <hr class="row">


  </div>
</template>

<script>
export default {
    name: 'MainBoard',
    props: {
      board: {
        type: Object,
      }
    },
    data() {
      return {
        commentNumber: '10',
      }
    },
    methods: {
      boardDetailPush() {
        this.$router.push({name:'BoardDetail', params:{boardno :this.board.boardno}})
      },
    },

}
</script>

<style>
#heartMainBoard {
    color:#ff3636;
}

#boardUid {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

#boardSubject {
    font-weight: bold;
}
</style>