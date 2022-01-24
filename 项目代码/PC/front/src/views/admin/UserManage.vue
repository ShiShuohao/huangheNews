<template>
  <div class="dF">
    <el-table :data="tableData" border class="el2">
      <el-table-column prop="email" label="邮箱" width="" align="center" />
      <el-table-column prop="petname" label="昵称" width="" align="center" />
      <el-table-column prop="sex" label="性别" width="50" align="center" />
      <el-table-column prop="birthday" label="生日" width="100" align="center" :formatter="timeFilter" />
      <el-table-column prop="area" label="地区" width="140" align="center" />
      <el-table-column label="操作" width="60" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
          <el-button type="text" size="big" @click="this.delete(scope.row)">删除</el-button>
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
        class="el1">
    </el-pagination>
  </div>
  <!--删除用户弹窗，点击删除时才显示-->
  <el-dialog v-model="dialogFormVisibleForDelete" title="删除用户">
    <span>是否确认删除该用户？</span>
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
import moment from 'moment';

let editor;
let rowForUpdate;
export default {
  name: "UserManage",
  data() {
    return {
      tableData: [

      ],
      total: 0,
      pageSize: 5,
      currentPage: 1,
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
      request.get("/user/getAllUserNotAdmin",{
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    //删除用户
    delete(row) {
      rowForUpdate = row
      this.dialogFormVisibleForDelete = true
    },
    //确认删除
    conformDelete() {
      request.post("/user/deleteUserByEmail",rowForUpdate.email).then(res =>{
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        this.loadData()       //请求后的操作要放到请求的大括号里面，否则异步操作会造成加载时数据没有更新
        this.dialogFormVisibleForDelete = false
      })
    }
  }
}
</script>

<style scoped>
.dF{
  width: 90%;position: absolute;left: 5%;right: 5%;margin-top: 50px;
}
.el1{
  margin-top: 15px;
}
.el2{
  width: 100%;
}
</style>