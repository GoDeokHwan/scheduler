<template>
  <v-app>
    <div>
      <v-sheet
        color="white"
        elevation="7"
        height="300"
        width="400"
        style="top: 200px;
        position: relative;
        left: 35%;"
      >
        <h1 style="text-align: center;">LOGIN</h1>
        <v-divider style="top: 2px;"></v-divider>
        <div style="padding-left: 30px;padding-right: 30px;height: 50px;margin-top: 20px;">
          <input type="text" placeholder="Login ID" style="border-bottom: 1px solid; width: 100%; height: 100%;"/>
        </div>
        <div style="padding-left: 30px;padding-right: 30px;height: 50px;margin-top: 20px;">
          <input type="password" placeholder="Password" style="border-bottom: 1px solid; width: 100%; height: 100%;"/>
        </div>
        <div style="padding-right: 30px;padding-left: 30px; padding-top: 30px;">
          <v-btn color="primary" style="width: 100%; cursor: pointer;" v-on:click="login">
            로그인
          </v-btn>
        </div>
      </v-sheet>
      <v-sheet
        color="white"
        elevation="7"
        height="40"
        width="400"
        style="top: 220px;
          left: 35%;
          position: relative;
          text-align: center;
          padding-top: 12px;"
      >
        <p style="display: inline-block;">일정관리</p>&nbsp;<p style="display: inline-block; text-decoration: underline; cursor: pointer;" v-on:click="singUpPageSend">회원가입</p>
      </v-sheet>
    </div>
  </v-app>
</template>

<script>
export default {
  name: 'Login',
  metaInfo: {
    title: '일정관리 로그인'
  },
  data () {
    return {

    }
  },
  created () {

  },
  methods: {
    singUpPageSend () {
      this.$router.push({ path: `/signUp`})
    },
    login () {
      this.$router.push({ path: `/scheduler`})
    }
  }
}
</script>

<style scoped>
input[type='text']::placeholder {
  padding-left: 5px;
}
</style>
