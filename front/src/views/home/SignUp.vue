<template>
  <v-app>
    <div>
      <v-sheet
        color="white"
        elevation="7"
        height="600"
        width="400"
        style="top: 100px;
        position: relative;
        left: 35%;"
      >
        <h1 style="text-align: center;">Sign Up</h1>
        <v-divider style="top: 2px;"></v-divider>
        <div class="sign-up-div">
          <p class="sign-up-p">Login ID</p>
          <input type="text" placeholder="Login ID" class="sign-up-input sign-up-input-login-id"/>
          <v-btn color="primary" style="width: 28%;cursor: pointer;left: 7px;">
            중복검사
          </v-btn>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">이름</p>
          <input type="text" placeholder="이름" class="sign-up-input"/>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">비밀번호</p>
          <input type="text" placeholder="비밀번호" class="sign-up-input"/>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">비밀번호 확인</p>
          <input type="text" placeholder="비밀번호 확인" class="sign-up-input"/>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">전화번호</p>
          <input type="text" placeholder="전화번호" class="sign-up-input"/>
        </div>
        <div class="sign-up-div">
          <v-btn color="primary" style="width: 100%; cursor: pointer;" v-on:click="singUpSave">
            회원가입
          </v-btn>
        </div>
      </v-sheet>
    </div>
  </v-app>
</template>

<script>
export default {
  name: 'SignUp',
  metaInfo: {
    title: '일정관리 회원가입'
  },
  data () {
    return {

    }
  },
  created () {

  },
  methods: {
    singUpSave () {
      this.$router.push({ path: `/`})
    }
  }
}
</script>

<style scoped>
.sign-up-div {
  padding-left: 30px;
  padding-right: 30px;
  height: 50px;
  margin-top: 40px;
  position: relative;
}
.sign-up-input {
  border-bottom: 1px solid;
  width: 100%;
  height: 100%;
}
.sign-up-p {
  margin-bottom: 0px;
  margin-top: 40px;
}
.sign-up-input-login-id {
  width: 70%;
}
.sign-up-input::placeholder {
  padding-left: 5px;
}
</style>
