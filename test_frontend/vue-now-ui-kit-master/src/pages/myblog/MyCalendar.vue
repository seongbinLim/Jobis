<template>
  <div>
      
  <v-range-selector
  :start-date.sync="range.start"
  :end-date.sync="range.end"
/>

<v-day-selector
  v-model="date"
/>
  </div>
</template>

<script>
import VRangeSelector from 'vuelendar/components/vl-range-selector';
import VDaySelector from 'vuelendar/components/vl-day-selector';

export default {
  name: 'MyCalendar',
  components: {
    VRangeSelector,
    VDaySelector
  },
  data () {
    return {
      range: {},
      date: null
    }
  }
  // ...
}

</script>

<style>

</style>