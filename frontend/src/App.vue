<script setup>
import DogShelter from "@/components/DogShelter.vue";
</script>

<template>
  <header></header>
  <main>
    <DogShelter />
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
  float: left;
}

@media (min-width: 1024px) {
  header {
    width: 100%;
  }

  .logo {
    margin: 0 2rem 0 0;
  }
}
</style>
