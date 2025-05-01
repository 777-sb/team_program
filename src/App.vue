<template>
  <div id="app">
    <NavBar />
    <transition name="fade" mode="out-in">
      <router-view />
    </transition>
    <ThemeToggle />
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'
import ThemeToggle from './components/ThemeToggle.vue'

export default {
  name: 'App',
  components: {
    NavBar,
    ThemeToggle
  }
}
</script>

<style>
/* 淺色模式 */
body {
  background-color: white;
  color: #2c3e50;
}

/* 深色模式 */
body.dark-mode {
  background-color: #1a1a1a;
  color: #f0f0f0;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  margin-top: 60px;
}

h1 {
  margin-bottom: 20px;
}

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

nav {
  padding: 10px;
  background-color: #f0f0f0;
  text-align: center;
}

router-link {
  margin: 0 10px;
  text-decoration: none;
  color: #333;
}

router-link:hover {
  color: #007bff;
}

/* 添加過渡動畫 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
