<template>
  <div class="divF5">
    <img src="../assets/photo/logo.png" alt="" class="img1">
    <el-input v-model="searchContent" placeholder="搜索网站内容"  class="el1"/>
    <el-button type="primary" class="el2" @click="search()">搜索</el-button>
    <!--登录状态显示-->
    <div v-if="loginVisible" class="div2">
      <img v-if="boyHead" src="../assets/photo/boy.png" alt="" class="img2">
      <img v-if="girlHead" src="../assets/photo/girl.png" alt="" class="img2">
      <!--下拉菜单-->
      <el-dropdown class="el3">
        <span class="el-dropdown-link">
          <span id="petname" class="span1"></span>
          <el-icon class="el-icon--right">
            <arrow-down class="arrow1" />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/userHome')">个人中心</el-dropdown-item>
            <el-dropdown-item @click="logout()">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <!--未登录状态显示-->
    <div v-if="notLoginVisible" class="div3">
      <router-link to="/userLogin" class="link1">登录/注册</router-link>
    </div>
  </div>
</template>

<script>
import {ArrowDown} from "@element-plus/icons";
import request from "../utils/request";
import $ from "../assets/js/jquery-3.6.0";
import {ElMessage} from "element-plus";

let email;

export default {
  name: "HeadOfHome",
  components: {
    ArrowDown,
  },
  data() {
    return {
      searchContent: '',
      loginVisible: false,
      notLoginVisible: false,
      boyHead: false,
      girlHead: false
    }
  },
  methods: {
    //退出登录
    logout() {
      sessionStorage.removeItem("userEmail")
      this.$router.push('/')
      this.loginVisible = false
      this.notLoginVisible = true
    },
    //加载时判断是否登录
    loadLoginStateAndPetname() {
      let userEmail = sessionStorage.getItem("userEmail")
      if (userEmail===null||userEmail==='') {   //说明未登录
        this.notLoginVisible = true
        this.loginVisible = false
        email = userEmail
      }else {
        this.notLoginVisible = false
        this.loginVisible = true
        let reg = new RegExp('"', "g");
        let newStr = userEmail.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
        email = newStr
        request.post("/user/get",newStr).then(res =>{
          $('#petname').text(res.data.petname)
          if (res.data.sex==="女") {
            this.girlHead = true
            this.boyHead = false
          }else {
            this.girlHead = false
            this.boyHead = true
          }
        })
      }
    },
    //搜索
    search() {
      if (this.searchContent===null||this.searchContent==='') {
        ElMessage({
          type: 'warning',
          message: '搜索内容不能为空'
        })
      }else if (email===null||email==="") {
        this.$router.push({
          path: '/searchResult',
          query: {
            searchContent: this.searchContent
          }
        })
      }else {
        request.get("/search/saveUserSearchContent",{
          params: {
            userEmail: email,
            searchContent: this.searchContent
          }
        }).then(res =>{
          this.$router.push({
            path: '/searchResult',
            query: {
              searchContent: this.searchContent
            }
          })
        })
      }
    }
  },
  created() {
    document.body.style.backgroundColor="#f6f6f6";  //设置背景色
    this.loadLoginStateAndPetname()
  }
}
</script>

<style scoped>
.divF5{
  background: white;height: 50px;width: 100%;border-bottom: 1.5px #f0f0f0 solid;position: absolute;margin-top: 0;
}
.img1{
  position: absolute;height: 65%;top: 50%;transform: translate(-50%,-50%);left: 20%;
}
.el1{
  position: absolute;width: 23%;height: 40px;transform: translate(-50%,-50%);left: 48%;top: 50%;
}
.el2{
  position: absolute;width: 70px;height: 40px;transform: translate(-50%, -50%);left: 65%;top: 50%;
}
.div2{
  position: absolute;width: 20%;height: 40px;transform: translate(-50%,-50%);top: 50%;left: 80%;text-align: center;
}
.img2{
  height: 40px;width: 40px;margin-left: -10px;
}
.el3{
  position: absolute;transform: translate(-50%,-50%);left: 80%;top: 50%;font-size: 16px;width: 150px;text-align: center;
}
.span1{
  font-size: 15px;
}
.arrow1{
  position: relative;top: 3px;
}
.div3{
  position: absolute;transform: translate(-50%, -50%);left: 80%;top: 50%;font-size: 16px;width: 100px;text-align: center;
}
.link1{
  color:black;
}
</style>