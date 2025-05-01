<template>
  <div>
    <h1>設置</h1>
    <form @submit.prevent="updateSettings">
      <div>
        <label for="username">用戶名：</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="email">電子郵件：</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div>
        <label for="password">新密碼：</label>
        <input type="password" id="password" v-model="password" />
      </div>
      <button type="submit">保存設置</button>
    </form>
  </div>
</template>

<script>
import store from '../store';

export default {
  name: 'Settings',
  data() {
    return {
      username: store.state.username,
      email: '',
      password: '',
    };
  },
  methods: {
    updateSettings() {
      store.mutations.login(this.username);
      alert('設置已保存！');
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
