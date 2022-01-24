<template>
  <div class="dF">
    <el-table :data="tableData" border class="el1">
      <el-table-column prop="headline" label="主题" width="" align="center" />
      <el-table-column prop="petname" label="发送人" width="170" align="center" />
      <el-table-column prop="date" label="日期" width="100" align="center" :formatter="timeFilter"/>
      <el-table-column label="操作" width="160" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
          <el-button type="text" size="big" @click="this.look(scope.row.content)">查看</el-button>
          <el-button type="text" size="big" @click="this.delete(scope.row.id)">删除</el-button>
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
  <!--删除弹窗，点击删除时才显示-->
  <el-dialog v-model="dialogFormVisibleForDelete" title="删除私信">
    <span>是否确认删除该私信？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForDelete = false">取消</el-button>
        <el-button type="primary" @click="conformDelete()">确定</el-button>
      </span>
    </template>
  </el-dialog>
  <!--查看私信-->
  <el-dialog v-model="dialogFormVisibleForLook" title="查看私信">
    <div id="content"></div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="dialogFormVisibleForLook=false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "../../utils/request";
import {ElMessage} from "element-plus";
import moment from 'moment';
import $ from '../../assets/js/jquery-3.6.0';
let userEmail;
let messageId;

export default {
  name: "MyMessageOfReaded",
  data() {
    return {
      tableData: [

      ],
      total: 0,
      pageSize: 5,
      currentPage: 1,
      dialogFormVisibleForLook: false,
      dialogFormVisibleForDelete: false,
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    //格式化日期
    timeFilter(row, column, cellValue, index) {
      const date = row[column.property]
      if(date !=null){
        return moment(date).format("YYYY-MM-DD")
      }
    },
    //当前页码改变
    handleCurrentChange() {
      this.loadData()
    },
    //加载数据
    loadData() {
      let email = sessionStorage.getItem("userEmail")
      let reg = new RegExp('"', "g");
      userEmail = email.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
      request.get("/message_User/getAllReaded",{
        params: {
          userEmail: userEmail,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    //查看详情
    look(content) {
      this.dialogFormVisibleForLook = true
      this.$nextTick(() => {
        $('#content').html(content)
      })
    },
    //删除
    delete(id) {
      messageId = id
      this.dialogFormVisibleForDelete = true
    },
    //确认删除
    conformDelete() {
      request.post("/message/deleteMessage",messageId).then(res =>{
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        this.loadData()
        this.dialogFormVisibleForDelete = false
      })
    },
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