<template>
  <el-button type="primary" class="el1" @click="clear()">清空搜索记录</el-button>
  <div class="dF">
    <el-table :data="tableData" border class="el2">
      <el-table-column prop="content" label="搜索内容" width="" align="center" />
      <el-table-column label="操作" width="" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
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
        class="el3">
    </el-pagination>
  </div>
  <!--删除搜索记录弹窗-->
  <el-dialog v-model="dialogFormVisibleForDelete" title="删除搜索记录">
    <span>是否确认删除该搜索记录？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForDelete = false">取消</el-button>
        <el-button type="primary" @click="conformDelete()">确定</el-button>
      </span>
    </template>
  </el-dialog>
  <!--清空搜索记录弹窗-->
  <el-dialog v-model="dialogFormVisibleForClear" title="清空搜索记录">
    <span>是否确认清空搜索记录？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForClear = false">取消</el-button>
        <el-button type="primary" @click="conformClear()">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "../../utils/request";
import {ElMessage} from "element-plus";
let searchId;
let userEmail;

export default {
  name: "Search",
  data() {
    return {
      tableData: [

      ],
      total: 0,
      pageSize: 5,
      currentPage: 1,
      dialogFormVisibleForDelete: false,
      dialogFormVisibleForClear: false
    }
  },
  created() {
    //初始时加载表格数据
    this.loadData()
  },
  methods: {
    //当前页改变
    handleCurrentChange() {
      this.loadData()
    },
    //清空历史记录
    clear() {
      this.dialogFormVisibleForClear = true
    },
    //确认清空
    conformClear() {
      request.post("/search/clearUserSearch",userEmail).then(res =>{
        this.loadData()
        this.dialogFormVisibleForClear = false
        ElMessage({
          type: 'success',
          message: '清空成功'
        })
      })
    },
    //删除搜索记录
    delete(id) {
      searchId = id
      this.dialogFormVisibleForDelete = true
    },
    //确认删除
    conformDelete() {
      request.post("/search/deleteOneSearchById",searchId).then(res =>{
        this.loadData()
        this.dialogFormVisibleForDelete = false
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
      })
    },
    //加载表格数据
    loadData() {
      let email = sessionStorage.getItem("userEmail")
      let reg = new RegExp('"', "g");
      userEmail = email.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
      request.get("/search/getAllSearchByUserEmail",{
        params: {
          userEmail: userEmail,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res =>{
        this.total = res.data.total
        this.tableData = res.data.records
      })
    }
  },
}
</script>

<style scoped>
.el1{
  position: absolute;left: 5%;margin-top: 50px;
}
.dF{
  width: 90%;position: absolute;left: 5%;right: 5%;margin-top: 100px;
}
.el2{
  width: 100%;
}
.el3{
  margin-top: 15px;
}
</style>