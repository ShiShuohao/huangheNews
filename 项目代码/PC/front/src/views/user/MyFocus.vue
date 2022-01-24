<template>
  <div class="dF">
    <el-table :data="tableData" border class="el1">
      <el-table-column prop="userTo" label="用户邮箱" width="" align="center" />
      <el-table-column prop="petname" label="用户昵称" width="" align="center" />
      <el-table-column label="操作" width="100" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
          <el-button type="text" size="big" @click="this.delete(scope.row.id)">取消关注</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
        background
        layout="total, prev, pager, next"
        :total="total"
        :page-size="pageSize"
        v-model:currentPage="currentPage"
        @current-change="handleCurrentChange"
        class="el2">
    </el-pagination>
  </div>
  <!--取消关注弹窗，点击取消时才显示-->
  <el-dialog v-model="dialogFormVisibleForDelete" title="取消关注">
    <span>是否确认取消关注该用户？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForDelete = false">取消</el-button>
        <el-button type="primary" @click="conformDelete()">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "../../utils/request";
import {ElMessage} from "element-plus";
let userEmail;
let focusId;

export default {
  name: "MyFocus",
  data() {
    return {
      tableData: [

      ],
      total: 0,
      currentPage: 1,
      pageSize: 5,
      dialogFormVisibleForDelete: false,
    }
  },
  methods: {
    //加载数据
    loadData() {
      let email = sessionStorage.getItem("userEmail")
      let reg = new RegExp('"', "g");
      userEmail = email.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
      request.get("/focus_User/get",{
        params: {
          userEmail: userEmail,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res =>{
        this.tableData = res.data.records;
        this.total = res.data.total
      })
    },
    //取消关注
    delete(id) {
      this.dialogFormVisibleForDelete = true
      focusId = id
    },
    //确认取消
    conformDelete() {
      request.post("/focus/deleteFocusById",focusId).then(res =>{
        this.loadData()
        this.dialogFormVisibleForDelete = false
        ElMessage({
          type: 'success',
          message: '取消成功'
        })
      })
    },
    //当页码改变时
    handleCurrentChange() {
      this.loadData()
    }
  },
  created() {
    this.loadData()
  }
}
</script>

<style scoped>
.dF{
  width: 90%;position: absolute;left: 5%;right: 5%;margin-top: 50px;
}
.el1{
  width: 100%;
}
.el2{
  margin-top: 15px;
}
</style>