<template>
  <div>
    <h1>登錄頁面</h1>
    <form @submit.prevent="handleLogin">
      <div>
        <label for="username">用戶名：</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">密碼：</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">登錄</button>
    </form>
  </div>
</template>

<script>
import store from '../store';

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    handleLogin() {
      // 這裡可以添加登錄邏輯
      store.mutations.login(this.username);
      this.$router.push('/vocabulary');
    },
  },
};
</script>

<style scoped>
form {
  max-width: 300px;
  margin: 0 auto;
  text-align: left;
}
label {
  display: block;
  margin-bottom: 5px;
}
input {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
}
button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
