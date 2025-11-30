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
    Üdvözöllek az UNIDEB Kutyamenhelyén
  </h1>
</template>

<style scoped>

.greetings {
  font-weight: bolder;
  float: left;
  padding: 3rem;
}

</style>
