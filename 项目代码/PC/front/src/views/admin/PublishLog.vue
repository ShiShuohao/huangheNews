<template>
  <div class="dF">
    <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="80px"
        class="demo-ruleForm">
      <el-form-item label="版本" prop="version">
        <el-input v-model="ruleForm.version"></el-input>
      </el-form-item>
      <el-form-item label="日期" required>
        <el-col :span="11"><el-form-item prop="date"><el-date-picker v-model="ruleForm.date" type="date" placeholder="请选择日期" style="width: 100%"></el-date-picker></el-form-item></el-col>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <!--富文本编辑器-->
        <div id="div1">
        </div>
      </el-form-item>
      <el-form-item class="el1">
        <el-button type="primary" @click="submitForm('ruleForm')" class="el2">发布</el-button>
        <el-button @click="resetForm('ruleForm')" class="el3">重置</el-button>
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
  name: "PublishLog",
  data() {
    return {
      ruleForm: {
        version: '',
        date: '',
        content: ''
      },
      rules: {
        version:[
          {
            required: true,
            message: '请输入版本',
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
            request.post("/log/publishLog",this.ruleForm).then(res =>{
              ElMessage({
                type: 'success',
                message: '发布成功'
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
.el-col-11[data-v-05685c2f]{
  max-width: 100%;width: 100%;
}
.el1{
  display:block;
}
.el2{
  width: 40%;
}
.el3{
  width: 40%;margin-left: 20%;
}
</style>