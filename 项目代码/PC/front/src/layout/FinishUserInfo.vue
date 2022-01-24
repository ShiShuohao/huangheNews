<template>
  <!--头部-->
  <HeadOfWelcome/>
  <div class="divFirst">
    <p class="p1">完善个人信息</p>
    <div class="divSecond">
      <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
          class="demo-ruleForm">
        <el-form-item label="昵称" prop="petname">
          <el-input v-model="ruleForm.petname"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="ruleForm.sex" class="el1">
            <el-radio label="男" class="man"></el-radio>
            <el-radio label="女" class="woman"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日" required>
          <el-col :span="11" class="el2">
            <el-form-item prop="birthday">
              <el-date-picker
                  v-model="ruleForm.birthday"
                  type="date"
                  placeholder="选择您的生日"
                  class="el3">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="地区" prop="area">
          <el-select v-model="ruleForm.area" placeholder="请选择省份" class="el3">
            <el-option label="河南省" value="河南省"></el-option>
            <el-option label="北京市" value="北京市"></el-option>
            <el-option label="上海市" value="上海市"></el-option>
            <el-option label="天津市" value="天津市"></el-option>
            <el-option label="重庆市" value="重庆市"></el-option>
            <el-option label="黑龙江省" value="黑龙江省"></el-option>
            <el-option label="辽宁省" value="辽宁省"></el-option>
            <el-option label="吉林省" value="吉林省"></el-option>
            <el-option label="河北省" value="河北省"></el-option>
            <el-option label="湖北省" value="湖北省"></el-option>
            <el-option label="湖南省" value="湖南省"></el-option>
            <el-option label="山东省" value="山东省"></el-option>
            <el-option label="山西省" value="山西省"></el-option>
            <el-option label="陕西省" value="陕西省"></el-option>
            <el-option label="安徽省" value="安徽省"></el-option>
            <el-option label="浙江省" value="浙江省"></el-option>
            <el-option label="江苏省" value="江苏省"></el-option>
            <el-option label="福建省" value="福建省"></el-option>
            <el-option label="广东省" value="广东省"></el-option>
            <el-option label="海南省" value="海南省"></el-option>
            <el-option label="四川省" value="四川省"></el-option>
            <el-option label="云南省" value="云南省"></el-option>
            <el-option label="贵州省" value="贵州省"></el-option>
            <el-option label="青海省" value="青海省"></el-option>
            <el-option label="甘肃省" value="甘肃省"></el-option>
            <el-option label="江西省" value="江西省"></el-option>
            <el-option label="台湾省" value="台湾省"></el-option>
            <el-option label="内蒙古自治区" value="内蒙古自治区"></el-option>
            <el-option label="宁夏回族自治区" value="宁夏回族自治区"></el-option>
            <el-option label="新疆维吾尔自治区" value="新疆维吾尔自治区"></el-option>
            <el-option label="西藏自治区" value="西藏自治区"></el-option>
            <el-option label="广西壮族自治区" value="广西壮族自治区"></el-option>
            <el-option label="香港特别行政区" value="香港特别行政区"></el-option>
            <el-option label="澳门特别行政区" value="澳门特别行政区"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="el4">
          <el-button type="primary" @click="submitForm('ruleForm')" class="el5">确定</el-button>
          <el-button @click="resetForm('ruleForm')" class="el6">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import HeadOfWelcome from "../components/HeadOfWelcome";
import {ElMessage} from "element-plus";
import request from "../utils/request";
export default {
  name: "FinishUserInfo",
  components: {HeadOfWelcome},
  data() {
    return {
      ruleForm: {
        email: '',
        petname: '',
        sex: '',
        birthday: '',
        area: ''
      },
      rules: {
        petname:[
          {
            required: true,
            message: '请输入昵称',
            trigger: 'blur',
          },
        ],
        sex: [
          {
            required: true,
            message: '请输入性别',
            trigger: 'change',
          },
        ],
        birthday: [
          {
            type: 'date',
            required: true,
            message: '请选择生日',
            trigger: 'change',
          },
        ],
        area: [
          {
            required: true,
            message: '请选择省份',
            trigger: 'change',
          },
        ],
      }
    }
  },
  methods: {
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {    //如果表单不空
          request.post("/user/editUserInfo", this.ruleForm).then(res => {
            ElMessage({
              type: 'success',
              message: '修改成功'
            })
            this.$router.push('/')   //返回主页
          })
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
      this.ruleForm.email = newStr
    },
  },
  created() {
    this.load()
  }
}
</script>

<style scoped>
.divFirst{
  background: white;position: absolute;width: 100%;left: 50%;top: calc(50% + 25px);transform: translate(-50%,-50%);height: calc(100% - 50px);
}
.p1{
  text-align: center;font-size: 22px;color: rgb(64, 158, 255);position: absolute;left: 30%;right: 30%;width: 40%;margin-top: 70px;padding: 10px;
}
.divSecond{
  position: absolute;left: 32%;width: 30%;right: 35%;margin-top: 160px;
}
.el1{
  width: 100%;
}
.man{
  position: absolute;transform: translate(-50%,-50%);left: 20%;top: 50%;
}
.woman{
  position: absolute;transform: translate(-50%,-50%);right: 20%;top: 50%;
}
.el2{
  max-width: 100%;width: 100%;
}
.el3{
  width: 100%
}
.el4{
  display:block;
}
.el5{
  width: 40%;
}
.el6{
  width: 40%;margin-left: 20%;
}
</style>