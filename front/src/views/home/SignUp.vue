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
          <input type="text" placeholder="Login ID" class="sign-up-input sign-up-input-login-id" v-model="loginId"/>
          <v-btn color="primary" style="width: 28%;cursor: pointer;left: 7px;" v-on:click="loginIdValidationCheck">
            중복검사
          </v-btn>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">이름</p>
          <input type="text" placeholder="이름" class="sign-up-input" v-model="name"/>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">비밀번호</p>
          <input type="password" placeholder="비밀번호" class="sign-up-input" v-model="password"/>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">비밀번호 확인</p>
          <input type="password" placeholder="비밀번호 확인" class="sign-up-input" v-on:focusout="passwordCheck" v-model="checkPassword"/>
          <p v-if="isPassword">Password가 다릅니다.</p>
        </div>
        <div class="sign-up-div">
          <p class="sign-up-p">전화번호</p>
          <input type="text" placeholder="전화번호" class="sign-up-input" v-model="phoneNumber"/>
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
import axios from 'axios'
import helpes from '../../util/Helpers'

export default {
  name: 'SignUp',
  metaInfo: {
    title: '일정관리 회원가입'
  },
  data () {
    return {
      loginId: null,
      password: null,
      name: null,
      phoneNumber: null,
      checkPassword: null,
      isPassword: false,
      isLoginId: false
    }
  },
  created () {

  },
  methods: {
    singUpSave () {
      if (!this.validation()) {
        return
      }
      axios.post('http://localhost:8180/api/v1/users', {
        loginId : this.loginId,
        password : this.password,
        name : this.name,
        phoneNumber : this.phoneNumber
      })
      .then(res => {
        if (res.data.code === 0) {
          this.$router.push({ path: `/`})
        } else {
          alert(res.data.message)
        }
      }).catch(err => alert(err))
    },
    passwordCheck () {
      if (this.checkPassword != this.password) {
        this.isPassword = true
      } else {
        this.isPassword = false
      }
    },
    validation () {
      if (helpes.isNull(this.loginId)) {
        alert('아이디는 필수값입니다.')
        return false
      }
      if (!this.isLoginId) {
        alert('아이디 중복체크를 해주세요.')
        return false
      }

      if (helpes.isNull(this.password) && this.checkPassword === this.password) {
        alert('비밀번호는 필수값입니다.')
        return false
      }
      if (helpes.isNull(this.name)) {
        alert('이름은 필수값입니다.')
        return false
      }
      if (helpes.isNull(this.phoneNumber)) {
        alert('전화번호는 필수값입니다.')
        return false
      }
      return true
    },
    loginIdValidationCheck () {
      axios.get('http://localhost:8180/api/v1/user/id-check', {
        params: {
          loginId : this.loginId
        }
      })
        .then(res => {
          console.log(res)
          if (res.data.code === 0) {
            if (res.data.data) {
              alert("이미 존재하는 아이디 입니다.")
              this.isLoginId = false
            } else {
              alert("사용가능한 아이디 입니다.")
              this.isLoginId = true
            }
          } else {
            alert(res.data.message)
          }
        }).catch(err => alert(err))
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
