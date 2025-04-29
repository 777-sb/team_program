<template>
  <div>
    <h1>註冊頁面</h1>
    <form @submit.prevent="handleRegister">
      <div>
        <label for="username">用戶名：</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">密碼：</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div>
        <label for="confirmPassword">確認密碼：</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
      </div>
      <button type="submit">註冊</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: '',
    };
  },
  methods: {
    handleRegister() {
      if (this.password !== this.confirmPassword) {
        alert('密碼不一致，請重新輸入');
        return;
      }
      // 這裡可以添加註冊邏輯
      alert(`用戶名：${this.username}，密碼：${this.password}`);
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
