<template>
  <div>
    <h1>歡迎來到四六級備考網站</h1>
    <p>這是一個專為四六級備考設計的網站，提供詞彙記憶、聽力訓練和閱讀訓練等功能，幫助你高效備考。</p>
    <router-link to="/vocabulary" v-if="isAuthenticated">開始學習</router-link>
    <router-link to="/login" v-else>登錄以開始學習</router-link>
  </div>
</template>

<script>
import store from '../store';

export default {
  name: 'Home',
  computed: {
    isAuthenticated() {
      return store.state.isAuthenticated;
    },
  },
};
</script>

<style scoped>
p {
  max-width: 600px;
  margin: 20px auto;
  text-align: left;
  line-height: 1.6;
}
router-link {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}
router-link:hover {
  background-color: #0056b3;
}
</style>
