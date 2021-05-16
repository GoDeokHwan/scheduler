import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/',
    name: 'Login',
    component: () => import( '../views/home/Login.vue')
  },
  {
    path: '/signUp',
    name: 'SignUp',
    component: () => import('../views/home/SignUp.vue')
  },
  {
    path: '/scheduler',
    name: 'Scheduler',
    component: () => import('../views/scheduler/Scheduler.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
