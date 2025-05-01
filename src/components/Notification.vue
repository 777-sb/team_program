/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="notification" v-if="message">
    <p>{{ message }}</p>
    <button @click="closeNotification">關閉</button>
  </div>
</template>

<script>
export default {
  name: 'Notification',
  data() {
    return {
      message: '你有新的學習提醒！',
    };
  },
  methods: {
    closeNotification() {
      this.message = '';
    },
  },
};
</script>

<style scoped>
.notification {
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 15px;
  background-color: #007bff;
  color: white;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: space-between;
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
</style>
