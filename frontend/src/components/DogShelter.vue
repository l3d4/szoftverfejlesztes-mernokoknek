<script>
import axios from "axios"
import Home from "@/components/Home.vue";

export default {
  name: 'DogShelter',
  components: {Home},
  data() {
    return {
      contextList: ['Dashboard','Dogs','Adopters','Staff'],
      selectedModel: String,
      selectedColumns: {},
      selectedList: [],
      dogList: [],
      staffList: [],
      adopterList: [],
      selectedRow: {},
      dogColumns: { "id": "ID", "name": "Name", "breed": "Breed" },
      staffColumns: { "id": "ID", "firstName": "First Name", "lastName": "Last Name", "role": "Role", "email": "E-mail", "phone": "Phone" },
      adopterColumns: { "id": "ID", "firstName": "First Name", "lastName": "Last Name", "email": "E-mail", "phone": "Phone" },
      loggingIn: Boolean,
      userName: String
    }
  },

  async mounted() {
    this.selectedModel = 'Dashboard';
    this.loggingIn = false;
    this.userName = 'Látogató';


    await this.loadData();
  },

  methods: {

    async loadData() {
      try {
        console.log("Adatok betöltése...");
        let dogsResponse = await axios.get("http://localhost:8080/dogs");
        this.dogList = dogsResponse.data;

        let staffResponse = await axios.get("http://localhost:8080/staff");
        this.staffList = staffResponse.data;

        let adopterResponse = await axios.get("http://localhost:8080/adopters");
        this.adopterList = adopterResponse.data;


        if (this.selectedModel !== 'Dashboard') {
          this.selectContext(this.selectedModel);
        }
      } catch (error) {
        console.error("Hiba az adatok betöltésekor:", error);
      }
    },

    createItem() {
      this.selectedRow = {};
    },

    async saveItem() {
      if (!this.selectedModel || this.selectedModel === 'Dashboard') return;

      let endpoint = "/" + this.selectedModel.toLowerCase();
      let baseUrl = "http://localhost:8080";

      try {
        if (this.selectedRow.id) {

          await axios.put(baseUrl + endpoint + "/" + this.selectedRow.id, this.selectedRow);
          alert("Sikeres módosítás!");
        } else {
          // Új létrehozása (POST)
          await axios.post(baseUrl + endpoint, this.selectedRow);
          alert("Sikeresen hozzáadva!");
          this.selectedRow = {};
        }


        await this.loadData();

      } catch (error) {
        console.error("Hiba a mentéskor:", error);
        alert("Hiba történt a mentéskor!");
      }
    },

    async deleteItem() {

      if (!confirm("Biztosan törölni szeretnéd ezt az elemet?")) return;

      if (!this.selectedRow.id) return;

      let endpoint = "/" + this.selectedModel.toLowerCase();
      let baseUrl = "http://localhost:8080";

      try {
        await axios.delete(baseUrl + endpoint + "/" + this.selectedRow.id);

        alert("Sikeres törlés!");

        this.selectedRow = null;
        await this.loadData();

      } catch (error) {
        console.error("Hiba törléskor:", error);
        alert("Nem sikerült a törlés.");
      }
    },


    selectContext(context) {
      this.selectedModel = context;
      switch (this.selectedModel) {
        case 'Dashboard': {
          this.selectedColumns = null;
          this.selectedList = null;
          this.selectedRow = null;
        } break;
        case 'Dogs': {
          this.selectedColumns = this.dogColumns;
          this.selectedList = this.dogList;
          this.selectedRow = null;
        } break;
        case 'Adopters': {
          this.selectedColumns = this.adopterColumns;
          this.selectedList = this.adopterList;
          this.selectedRow = null;
        } break;
        case 'Staff': {
          this.selectedColumns = this.staffColumns;
          this.selectedList = this.staffList;
          this.selectedRow = null;
        } break;
      }
    },

    getMessage() {
      return "Kedves " + this.userName  + " Üdvözöllek az UNIDEB Kutyamenhely weboldalán!";
    },

    selectRow(item) {
      this.selectedRow = item;
    },

    login() {
      this.getMessage();
      this.loggingIn = false;
    },

    logout() {
      this.loggingIn = true;
      this.userName = 'Látogató';
      this.loggingIn = false;
    }
  }
}
</script>

<template>
  <div class="header-panel lefty">
    <img class="badge lefty" src="../assets/logo.png" />
    <Home v-if="!loggingIn" v-bind:msg="getMessage()" />
    <button v-if="!loggingIn && userName === 'Látogató'" v-on:click="userName = ''; loggingIn = true" class="login-button lefty">bejelentkezés</button>
    <button v-if="!loggingIn && userName !== 'Látogató'" v-on:click="logout()" class="login-button lefty">kijelentkezés</button>
  </div>
  <div class="lefty">
    <div class="menu-panel lefty">
      <div v-for="item of contextList" class="menu-item" @click.left="selectContext(item)" v-bind:class="selectedModel === item ? 'selected-context' : ''">{{ item }}</div>
    </div>
    <div class="data-table lefty">

      <div v-if="selectedModel === 'Dogs'" style="margin-bottom: 0.5rem; text-align: right;">
        <button @click="createEmptyDog" class="action-btn">Új kutya</button>
      </div>

      <table v-if="selectedList !== null" >
        <tbody>
          <tr>
            <th v-for="colVal of Object.values(selectedColumns)">{{ colVal }}</th>
            <th></th>
          </tr>
          <tr v-for="row in selectedList" @click.left="selectRow(row)" class="row-action">
            <td v-for="colKey in Object.keys(selectedColumns)">{{ row[colKey] }}</td>
            <td style="cursor: pointer" v-bind:class="[selectedRow !== null && row.id === selectedRow.id ? 'selected-row' : '']">
              --&gt
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="selectedModel === 'Dashboard'" class="dashboard">
        <h2>2025 évi Magyarországi kutya örökbefogadások alakulása</h2>
        <img src="../assets/img/img.png" alt="Chart" style="margin: 0 auto">
      </div>
    </div>
    <div class="details-panel lefty">

      <div v-if="loggingIn">
        <p>Hogy szólíthatlak?</p>
        <input type="text" v-model="userName">
        <button v-on:click="login()">login</button>
      </div>

      <div v-if="selectedModel !== 'Dashboard' && !loggingIn" style="margin-bottom: 1rem; text-align: center;">

        <button @click="createItem" class="action-btn" style="background: #27ae60; margin-right: 10px;">+ Új felvétele</button>

        <button v-if="selectedRow && selectedRow.id" @click="deleteItem" class="action-btn delete-btn">Törlés</button>

      </div>

      <div v-if="selectedRow !== null && !loggingIn && selectedModel !== 'Dashboard'">

        <h1 v-if="selectedRow.name">{{ selectedRow.name }}</h1>
        <h1 v-if="selectedRow.firstName">{{ selectedRow.lastName }} {{ selectedRow.firstName }}</h1>

        <div v-for="colKey in Object.keys(selectedColumns)" class="data-field fill">
          <label>{{ selectedColumns[colKey] }}</label>
          <input class="fill" type="text" v-model="selectedRow[colKey]" v-bind:readonly="colKey === 'id'"/>
        </div>

        <div style="margin-top: 1rem; text-align: right;">
          <button @click="saveItem" class="action-btn">Adatok Mentése</button>
        </div>

      </div>

    </div>
  </div>
</template>

<style scoped>
table {
  min-width: 800px;
  border: #2c3e50 solid 1rem;
  border-left: none;
  border-right: none;
  border-spacing: 0;
}

th {
  position: sticky;
  color: white;
  background: #2c3e50;
}

tr:nth-child(even) {
  background: azure;
}

tr:nth-child(odd) {
  background: white;
}

td {
  padding: 1rem
}

.row-action:hover {
  background: rgba(200,220,255,.9);
  cursor: pointer;
}

.header-panel {
  width: 1440px;
  background: rgba(155,175,210,.6);
  border-bottom: .3rem solid #2c3e50;
}

.login-button {
  background: transparent;
  border: none;
  padding: 2.5rem;
}

.menu-panel, .data-table, .details-panel {
  height: 700px;
  border-bottom: .3rem solid #2c3e50;
}

.menu-panel {
  width: 200px;
  background: #6c7e90;
}

.menu-item {
  color: black;
  font-size: larger;
  font-weight: bolder;
  height: 25%;
  padding: 4rem 2rem;
  cursor: pointer;
}

.menu-item:nth-child(even) {
  background: rgba(155,175,210,.2);
}

.data-table {
  min-width: 800px;
  background: #2c3e50;
  overflow-y: auto;
}

.dashboard {
  color: white;
  text-align: center;
}

.dashboard img {
  margin-top: 6rem !important;
}

.details-panel {
  background: rgba(155,175,210,.6);
  width: 440px;
  padding: 2rem;
  overflow-y: auto;
}

.selected-context {
  background: #2c3e50 !important;
  color: white;
}

.selected-row {
  background: rgba(155,175,210,.6);
  border-left: .6rem inset #eeeeee;
  border-radius: 2rem 0 0 2rem;
}

.badge {
  float: left;
  width: 8rem;
}

.action-btn {
  background-color: #2c3e50;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  font-weight: bold;
  border-radius: 4px;
}

.action-btn:hover {
  background-color: #3e5871;
}

.delete-btn {
  background-color: #e74c3c !important; /* Piros szín */
  margin-right: 10px; /* Távolság a mentés gombtól */
}

.delete-btn:hover {
  background-color: #c0392b !important; /* Sötétebb piros ha ráviszed az egeret */
}
</style>