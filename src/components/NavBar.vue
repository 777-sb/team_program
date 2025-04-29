<template>
  <nav>
    <router-link to="/">首頁</router-link> |
    <router-link to="/login" v-if="!isAuthenticated">登錄</router-link>
    <router-link to="/register" v-if="!isAuthenticated">註冊</router-link>
    <span v-if="isAuthenticated">歡迎，{{ username }}</span>
    <router-link to="/vocabulary">詞彙記憶</router-link> |
    <router-link to="/listening">聽力訓練</router-link> |
    <router-link to="/reading">閱讀訓練</router-link> |
    <router-link to="/settings">設置</router-link> |
    <router-link to="/statistics">數據統計</router-link>
    <button v-if="isAuthenticated" @click="handleLogout">登出</button>
  </nav>
</template>

<script>
import store from '../store';

export default {
  name: 'NavBar',
  computed: {
    isAuthenticated() {
      return store.state.isAuthenticated;
    },
    username() {
      return store.state.username;
    },
  },
  methods: {
    handleLogout() {
      store.mutations.logout();
      this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
nav {
  padding: 10px;
  background-color: #007bff;
  text-align: center;
}
router-link {
  margin: 0 10px;
  text-decoration: none;
  color: white;
  font-weight: bold;
}
router-link:hover {
  color: #f0f0f0;
}
button {
  margin-left: 10px;
  padding: 5px 10px;
  background-color: white;
  color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #f0f0f0;
}
span {
  color: white;
  font-weight: bold;
}
</style>
