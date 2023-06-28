// router.js 或 router/index.js
import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Login from '@/components/Login'
import Register from '@/components/Register'
import PostLostItem from '@/components/PostLostItem'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home
        },
        {
            path: '/users/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/users/showRegister',
            name: 'Register',
            component: Register
        },
        {
            path: '/lostitems/post',
            name: 'PostLostItem',
            component: PostLostItem
        }
    ]
})
