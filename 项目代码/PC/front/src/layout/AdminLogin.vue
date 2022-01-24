<template>
  <!--头部-->
  <HeadOfWelcome/>
  <div class="divFirst">
    <p class="p1">管理员登录</p>
    <div class="div2">
      <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
          class="demo-ruleForm">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="verifyCode">
          <el-input v-model="ruleForm.verifyCode" class="el1"></el-input>
          <el-button type="primary" @click="sendVerifyCode(ruleForm.email)" class="el2">获取验证码</el-button>
        </el-form-item>
        <el-form-item class="el3">
          <el-button type="primary" @click="submitForm('ruleForm')" class="el4">登录</el-button>
          <el-button @click="resetForm('ruleForm')" class="el5">重置</el-button>
        </el-form-item>
      </el-form>
      <router-link to="/superAdminLogin" class="link1">超级管理员登录入口>></router-link>
    </div>
  </div>
</template>

<script>
import HeadOfWelcome from "../components/HeadOfWelcome";
import request from "../utils/request";
import { ElMessage } from 'element-plus'   //Element-plus消息提示

let verify;   //验证码
let role;     //用户角色
let inputEmail;  //输入邮箱

export default {
  name: "AdminLogin",
  components: {HeadOfWelcome},

  data() {
    return {
      ruleForm: {
        email: '',
        verifyCode: '',
      },
      rules: {
        email: [
          {
            required: true,
            message: '请输入邮箱',
            trigger: 'blur',
          },
        ],
        verifyCode: [
          {
            required: true,
            message: '请输入验证码',
            trigger: 'change',
          },
        ],
      },
    }
  },
  created() {

  },
  methods: {
    //发送验证码
    sendVerifyCode(email) {
      if (email===''||email===null){  //如果邮箱为空
        ElMessage({    //消息提示
          type: "warning",
          message: "请输入邮箱"
        })
      }else {
        inputEmail = email
        request.post("/user/sendEmailForAdmin",email).then(res =>{
          if (res.code==="0") {  //说明该用户存在，发送成功
            verify = res.data;
            request.post("/user/getRole",email).then(res =>{      //获取用户角色
              role = res.data;
            })
            ElMessage({    //消息提示
              type: "success",
              message: "获取成功"
            })
          }else {
            ElMessage({    //消息提示
              type: "warning",
              message: "该用户不存在"
            })
          }
        })
      }
    },
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {    //如果表单不空
          if (inputEmail!==this.ruleForm.email) {
            ElMessage({    //消息提示
              type: "warning",
              message: "请重新获取验证码"
            })
          }else if (verify!==this.ruleForm.verifyCode) {  //获取表单中的数据要通过 ”this.ruleForm.属性“ 来实现
            ElMessage({    //消息提示
              type: "warning",
              message: "验证码错误"
            })
          }else if (role===0||role===3) {
            ElMessage({    //消息提示
              type: "warning",
              message: "您不是管理员"
            })
          }else {
            ElMessage({    //消息提示
              type: "success",
              message: "登录成功"
            })
            sessionStorage.setItem("userEmail", JSON.stringify(this.ruleForm.email));  //将管理员的邮箱存储在session里
            this.$router.push('/adminHome');    //跳转到管理员页面
          }
        } else {            //如果表单为空
          return false
        }
      })
    },
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },
}
</script>

<style scoped>
.divFirst{
  background: white;position: absolute;width: 100%;left: 50%;top: calc(50% + 25px);transform: translate(-50%,-50%);height: calc(100% - 50px);
}
.p1{
  text-align: center;font-size: 22px;color: rgb(64, 158, 255);position: absolute;left: 30%;right: 30%;width: 40%;margin-top: 70px;padding: 10px;
}
.div2{
  position: absolute;left: 33%;width: 30%;right: 35%;margin-top: 160px;
}
.el1{
  width: 45%;margin-right: 5%;
}
.el2{
  width: 50%;
}
.el3{
  display:block;
}
.el4{
  width: 40%;
}
.el5{
  width: 40%;margin-left: 20%;
}
.link1{
  position: absolute;width: 100%;color: rgb(96, 98, 102);font-size: 13px;text-align: right;
}
</style>