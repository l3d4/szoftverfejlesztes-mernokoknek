<script setup>
import Home from './components/Home.vue'
import TheWelcome from './components/TheWelcome.vue'
import Dogs from "@/components/Dogs.vue";
</script>

<template>
  <header>
      <img class="badge" src="./assets/logo.png" />
      <Home msg="You did it!" />
  </header>

  <main>
    <Dogs />
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
  float: left;
}

.badge {
  float: left;
  width: 12rem;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
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
