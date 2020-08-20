<template>
  <div class="container mb-md-4">
    <div class="row" id="checkRadios">
        <div class="col-sm-12 col-lg-12">
          <n-checkbox v-model="checkboxes.unchecked" class="d-inline-block mr-2">Unchecked</n-checkbox>
          <n-checkbox v-model="checkboxes.unchecked" class="d-inline-block mr-2">Unchecked</n-checkbox>
          <n-checkbox v-model="checkboxes.unchecked" class="d-inline-block mr-2">Unchecked</n-checkbox>
          <n-checkbox v-model="checkboxes.checked" class="d-inline-block">Checked</n-checkbox>
        </div>
    </div>
  </div>
</template>

<script>
import Checkbox from '@/pages/myblog/Checkbox.vue';

export default {
  name:'MyCheckbox',
  components: {
    [Checkbox.name]: Checkbox,
  },
  data() {
    return {
      checkboxes: {
        unchecked: false,
        checked: true,
        disabledUnchecked: false,
        disabledChecked: true
      },
    }
  }
}
</script>

<style>

</style>