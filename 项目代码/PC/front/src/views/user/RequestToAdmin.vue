<template>
  <!--详细描述-->
  <div class="dF">
    <el-descriptions
        class="margin-top"
        title="权限信息"
        direction="vertical"
        :column="1"
        border>
      <template #extra>
        <el-button @click="requestToAdmin()" type="primary">申请成为管理员</el-button>
      </template>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <user />
            </el-icon>
            当前角色
          </div>
        </template>
        <el-tag size="big"><span id="role"></span></el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </div>
  <!--申请弹窗-->
  <el-dialog v-model="dialogFormVisibleForRequest" title="管理员申请">
    <span>是否确认申请成为管理员？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForRequest = false">取消</el-button>
        <el-button type="primary" @click="conformRequest()">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import User from "@element-plus/icons/lib/User";
import request from "../../utils/request";
import $ from '../../assets/js/jquery-3.6.0';
import {ElMessage} from "element-plus";
let userEmail;
let role;

export default {
  name: "RequestToAdmin",
  components: {
    User,
  },
  data() {
    return {
      dialogFormVisibleForRequest: false
    }
  },
  methods: {
    //申请成为管理员
    requestToAdmin() {
      if (role===0) {
        this.dialogFormVisibleForRequest = true
      }else if (role===1) {
        ElMessage({
          type: 'warning',
          message: '您已是管理员'
        })
      }else if (role===2) {
        ElMessage({
          type: 'warning',
          message: '您已是管理员'
        })
      }else {
        ElMessage({
          type: 'warning',
          message: '审核中，请耐心等待'
        })
      }
    },
    //确认申请
    conformRequest() {
      request.post("/user/changeUserToAdminForRequest",userEmail).then(res =>{
        ElMessage({
          type: 'success',
          message: '已提交申请，请耐心等待'
        })
        this.loadData()
        this.dialogFormVisibleForRequest = false
      })
    },
    //加载数据
    loadData() {
      let email = sessionStorage.getItem("userEmail")
      let reg = new RegExp('"', "g");
      userEmail = email.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
      request.post("/user/getRole",userEmail).then(res =>{
        role = res.data
        if (role===0) {
          $('#role').html("用户");
        }else if (role===1) {
          $('#role').html("管理员");
        }else if (role===2) {
          $('#role').html("超级管理员");
        }else {
          $('#role').html("待审核的管理员");
        }
      })
    }
  },
  created() {
    this.loadData()
  }
}
</script>

<style scoped>
.dF{
  width: 60%;position: absolute;left: 15%;right: 5%;margin-top: 120px;
}
</style>