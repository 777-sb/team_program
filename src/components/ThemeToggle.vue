<template>
  <div class="theme-toggle">
    <button @click="toggleTheme">
      {{ isDarkMode ? '切換到淺色模式' : '切換到深色模式' }}
    </button>
  </div>
</template>

<script>
export default {
  name: 'ThemeToggle',
  data() {
    return {
      isDarkMode: false,
    };
  },
  methods: {
    toggleTheme() {
      this.isDarkMode = !this.isDarkMode;
      document.body.classList.toggle('dark-mode', this.isDarkMode);
    },
  },
};
</script>

<style scoped>
.theme-toggle {
  position: fixed;
  bottom: 20px;
  left: 20px;
}
button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
