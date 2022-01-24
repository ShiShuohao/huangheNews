<template>
  <!--头部-->
  <HeadOfWelcome/>
  <div class="dF">
    <p class="p1">用户登录</p>
    <p class="p2">首次登录即自动注册</p>
    <div class="dS">
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
          <el-button type="primary" @click="sendVerifyCode()" class="el2">获取验证码</el-button>
        </el-form-item>
        <el-form-item class="el3">
          <el-button type="primary" @click="submitForm('ruleForm')" class="el4">登录</el-button>
          <el-button @click="resetForm('ruleForm')" class="el5">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import HeadOfWelcome from "../components/HeadOfWelcome";
import request from "../utils/request";
import { ElMessage } from 'element-plus'   //Element-plus消息提示

let verify;   //验证码
let inputEmail;   //用户输入的邮箱
let state;  //用户是登录还是注册

export default {
  name: "UserLogin",
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
    sendVerifyCode() {
      if (this.ruleForm.email===''||this.ruleForm.email===null){  //如果邮箱为空
        ElMessage({    //消息提示
          type: "warning",
          message: "请输入邮箱"
        })
      }else {
        inputEmail = this.ruleForm.email
        request.post("/user/sendEmail",this.ruleForm.email).then(res =>{
          ElMessage({    //消息提示
            type: "success",
            message: "获取成功，请查收"
          })
          verify = res.data
          state = res.msg
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
          }else {
            sessionStorage.setItem("userEmail", JSON.stringify(this.ruleForm.email));  //将管理员的邮箱存储在session里
            if (state==="登录") {
              ElMessage({    //消息提示
                type: "success",
                message: "登录成功"
              })
              this.$router.push('/')  //回到首页
            }else {
              request.post("/user/logon",this.ruleForm).then(res =>{
                ElMessage({    //消息提示
                  type: "success",
                  message: "注册成功，请完善个人信息"
                })
                this.$router.push('/finishUserInfo')
              })
            }
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
.dF{
  background: white;position: absolute;width: 100%;left: 50%;top: calc(50% + 25px);transform: translate(-50%,-50%);height: calc(100% - 50px);
}
.p1{
  text-align: center;font-size: 22px;color: rgb(64, 158, 255);position: absolute;left: 30%;right: 30%;width: 40%;margin-top: 70px;padding: 10px;
}
.p2{
  text-align: center;font-size: 14px;color: rgb(64, 158, 255);position: absolute;left: 30%;right: 30%;width: 40%;margin-top: 110px;padding: 10px;
}
.dS{
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
</style>