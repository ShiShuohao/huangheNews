<template>
  <div class="dF">
    <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="80px"
        class="demo-ruleForm">
      <el-form-item label="主题" prop="headline">
        <el-input v-model="ruleForm.headline"></el-input>
      </el-form-item>
      <el-form-item label="日期" required>
        <el-col :span="11"><el-form-item prop="date" ><el-date-picker v-model="ruleForm.date" type="date" placeholder="请选择日期" style="width: 100%;"></el-date-picker></el-form-item></el-col>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <!--富文本编辑器-->
        <div id="div1">
        </div>
      </el-form-item>
      <el-form-item class="el3">
        <el-button type="primary" @click="submitForm('ruleForm')" class="el4">发送</el-button>
        <el-button @click="resetForm('ruleForm')" class="el5">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "../../utils/request";
import {ElMessage} from "element-plus";
import E from 'wangeditor'

let editor;
export default {
  name: "BroadcastManage",
  data() {
    return {
      ruleForm: {
        userFrom: '',
        userTo: '',
        headline: '',
        content: '',
        date: '',
        state: 0
      },
      rules: {
        headline:[
          {
            required: true,
            message: '请输入主题',
            trigger: 'blur',
          },
        ],
        date: [
          {
            type: 'date',
            required: true,
            message: '请选择日期',
            trigger: 'change',
          },
        ],
      },
    }
  },
  methods: {
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
      editor.txt.html("")
    },
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {    //如果表单不空
          if (editor.txt.html()===''||editor.txt.html()==="") {
            ElMessage({
              type: 'warning',
              message: '请输入内容'
            })
          }else {
            this.ruleForm.content = editor.txt.html()
            request.post("/user/getAllUserEmail").then(res =>{
              let userEmailList = res.data
              for (let i=0;i<userEmailList.length;i++) {
                let reg = new RegExp('"', "g");
                let newStr = userEmailList[i].replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
                this.ruleForm.userTo = newStr
                request.post("/message/sendMessageToAllUser",this.ruleForm).then(res =>{
                })
              }
              ElMessage({
                type: 'success',
                message: '发送成功'
              })
              this.resetForm(formName)
            })
          }
        } else {        //如果表单为空
          return false
        }
      })
    },
    //加载数据
    load() {
      let userEmail = sessionStorage.getItem("userEmail")
      let reg = new RegExp('"', "g");
      let newStr = userEmail.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
      this.ruleForm.userFrom = newStr
      //加载富文本编辑器
      this.$nextTick(() => {
        editor = new E('#div1')
        editor.config.zIndex = 500
        editor.create()
      })
    },
  },
  created() {
    this.load()
  }
}
</script>

<style scoped>
.dF{
  width: 70%;position: absolute;left: 10%;right: 20%;margin-top: 20px;
}
.el-col-11[data-v-55fcbdfc]{
  max-width: 100%;width: 100%;
}
.el4{
  width: 40%;
}
.el5{
  width: 40%;margin-left: 20%;
}
</style>