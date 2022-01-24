<template>
  <!--详细描述-->
  <div class="dF">
    <el-descriptions
        class="margin-top"
        title="个人信息"
        direction="vertical"
        :column="3"
        border>
      <template #extra>
        <el-button @click="edit()" type="primary">修改</el-button>
      </template>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <user />
            </el-icon>
            昵称
          </div>
        </template>
        <el-tag size="big"><span id="name"></span></el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <male />
            </el-icon>
            性别
          </div>
        </template>
        <el-tag size="big"><span id="sex"></span></el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <clock />
            </el-icon>
            生日
          </div>
        </template>
        <el-tag size="big"><span id="birthday"></span></el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <location />
            </el-icon>
            地区
          </div>
        </template>
        <el-tag size="big"><span id="area"></span></el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <message />
            </el-icon>
            邮箱
          </div>
        </template>
        <el-tag size="big"><span id="email"></span></el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </div>
  <!--修改弹窗，点击修改时才显示-->
  <el-dialog v-model="dialogFormVisibleForEdit" title="修改个人信息" width="40%">
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
                class="el1">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item label="地区" prop="area">
        <el-select v-model="ruleForm.area" placeholder="请选择省份" class="el1">
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
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForEdit=false">取消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import User from "@element-plus/icons/lib/User";
import Message from "@element-plus/icons/lib/Message";
import Clock from "@element-plus/icons/lib/Clock";
import Male from "@element-plus/icons/lib/Male";
import Location from "@element-plus/icons/lib/Location";
import $ from '../../assets/js/jquery-3.6.0';
import request from "../../utils/request";
import moment from "moment";
import {ElMessage} from "element-plus";

let petname;
let sex;
let birthday;
let area;
let mail;
let role;

export default {
  name: "SelfInfo",
  components: {
    User,
    Location,
    Message,
    Clock,
    Male
  },
  created() {
    this.loadData()
  },
  data() {
    return {
      dialogFormVisibleForEdit: false,
      ruleForm: {
        email: '',
        petname: '',
        sex: '',
        birthday: '',
        area: '',
        role: 0
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
    //加载数据
    loadData() {
      let userEmail = sessionStorage.getItem("userEmail");   //从session中取出
      let reg = new RegExp('"', "g");
      let email = userEmail.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
      request.post("/user/get",email).then(res =>{
        petname = res.data.petname
        sex = res.data.sex
        birthday = moment(res.data.birthday).format("YYYY-MM-DD")
        area = res.data.area
        mail = res.data.email
        role = res.data.role
        $('#sex').html(res.data.sex)
        $('#name').html(res.data.petname)
        $('#birthday').html(birthday)
        $('#area').html(res.data.area)
        $('#email').html(res.data.email)
      })
    },
    //修改
    edit() {
      this.ruleForm.email = mail
      this.ruleForm.petname = petname
      this.ruleForm.sex = sex
      this.ruleForm.area = area
      this.ruleForm.birthday = birthday
      this.ruleForm.role = role
      this.dialogFormVisibleForEdit = true
    },
    //提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {    //如果表单不空
          request.post("/user/editUser", this.ruleForm).then(res => {
            ElMessage({
              type: 'success',
              message: '修改成功'
            })
            this.loadData()
            this.dialogFormVisibleForEdit = false
          })
        } else {        //如果表单为空
          return false
        }
      })
    },
  }
}
</script>

<style scoped>
.dF{
  width: 80%;position: absolute;left: 7%;right: 5%;margin-top: 120px;
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
</style>