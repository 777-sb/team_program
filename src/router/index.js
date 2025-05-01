import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import Vocabulary from '../views/Vocabulary.vue';
import Listening from '../views/Listening.vue';
import Reading from '../views/Reading.vue';
import Settings from '../views/Settings.vue';
import Statistics from '../views/Statistics.vue';
import store from '../store';

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/vocabulary', component: Vocabulary, meta: { requiresAuth: true } },
  { path: '/listening', component: Listening, meta: { requiresAuth: true } },
  { path: '/reading', component: Reading, meta: { requiresAuth: true } },
  { path: '/settings', component: Settings, meta: { requiresAuth: true } },
  { path: '/statistics', component: Statistics, meta: { requiresAuth: true } },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !store.state.isAuthenticated) {
    next('/login');
  } else {
    next();
  }
});

export default router;
