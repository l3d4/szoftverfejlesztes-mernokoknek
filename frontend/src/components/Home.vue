<script setup>
defineProps({
  msg: {
    type: String,
    required: true,
  },
})
</script>

<template>
  <h1 class="greetings">
    {{ msg }}
  </h1>
</template>

<style scoped>

.greetings {
  font-weight: bolder;
  float: left;
  padding: 1.4rem;
}

</style>
