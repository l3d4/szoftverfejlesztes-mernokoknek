<script>
import axios from "axios"

export default {
  name: 'Dogs',
  data() {
    return {
      doglist: [],
      selectedDog: {}
    }
  },
  async mounted() {
    let response = await axios.get("http://localhost:8080/dogs");
    this.doglist = response.data;
    console.log(response.data);
    console.log(this.doglist);
  },

  methods: {
    selectDog(dog) {
      this.selectedDog = dog;
    }
  }
}
</script>
<template>

  <div v-if="doglist" class="data-table lefty">
    <table>
      <tbody>
        <tr><th>ID</th><th>Name</th><th>Breed</th><th>Actions</th></tr>
        <tr v-for="dog in doglist">
          <td>{{ dog.id }}</td>
          <td>{{ dog.name }}</td>
          <td>{{ dog.breed }}</td>
          <td>
            <button v-on:click="selectDog(dog)">&gt;</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="details lefty">
    <h1>{{selectedDog.name}}</h1>
    <div class="data-field fill">
      <label>ID</label>
      <input class="fill" type="text" v-bind:value="selectedDog.id" readonly/>
    </div>
    <div class="data-field fill">
      <label>Name</label>
      <input class="fill" type="text" v-bind:value="selectedDog.name" />
    </div>
    <div class="data-field fill">
      <label>Breed</label>
      <input class="fill" type="text" v-bind:value="selectedDog.breed" />
    </div>
  </div>
</template>

<style scoped>
table {
  min-width: 600px;
  border: #2c3e50 solid 1rem;
}

th {
  color: white;
  background: #2c3e50;
}

tr:nth-child(even) {
  background: azure;
}

td {
  padding: 1rem
}

.details {
  padding: 2rem;
}
</style>