<template>

  <div class="divF4">
    <img src="../assets/photo/logo.png" alt="" style="position: absolute;height: 65%;top: 50%;transform: translate(-50%,-50%);left: 10%;">
    <p class="p1">后台管理系统</p>
    <!--下拉菜单-->
    <el-dropdown class="el1">
    <span class="el-dropdown-link">
      <span id="petname"></span>
      <el-icon class="el-icon--right">
        <arrow-down class="arrow1" />
      </el-icon>
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="logout()">退出</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script>
import { ArrowDown } from '@element-plus/icons'
import request from "../utils/request";
import $ from '../assets/js/jquery-3.6.0';

export default {
  name: "HeadOfAdmin",
  components: {
    ArrowDown
  },
  data() {
    return {
      ArrowDown,
    }
  },
  created() {   //项目加载时直接调用
    this.loadPetName()
    document.body.style.backgroundColor="#ffffff";  //设置背景色
  },
  methods: {

    //加载用户昵称
    loadPetName() {

      /*
        此处有一个易错点，post请求中的参数是不能带双引号的，否则请求失败
        而sessionStorage中取出的变量是带双引号的，因此需要使用正则表达式去掉引号
       */

      let userEmail = sessionStorage.getItem("userEmail");   //从session中取出
      let reg = new RegExp('"', "g");
      let newStr = userEmail.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
      request.post("/user/get",newStr).then(res =>{
        $('#petname').text(res.data.petname)
      })
    },

    //绑定退出登录按键功能
    logout() {
      sessionStorage.removeItem("userEmail");   //移除session
      this.$router.push('/');  //跳转到欢迎页
    },
  },
}
</script>

<style scoped>
.divF4{
  height: 50px;width: 100%;border-bottom: 1.5px #f0f0f0 solid;position: absolute;margin-top: 0;
}
.p1{
  text-decoration: none;position: absolute;left: 26%;top: 50%;transform: translate(-50%, -50%);color: #409eff;font-size: 23px;
}
.el1{
  position: absolute;transform: translate(-50%,-50%);left: 80%;top: 50%;font-size: 16px;
}
.arrow1{
  position: relative;top: 3px;
}
</style>