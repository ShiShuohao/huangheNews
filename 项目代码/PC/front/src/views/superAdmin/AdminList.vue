<template>
  <span class="sp1">提示：管理员降级后将恢复为用户</span>
  <span class="sp2">提示：0代表用户，1代表管理员，3代表待审核管理员</span>
  <div class="dF">
    <el-table :data="tableData" border class="el1">
      <el-table-column prop="email" label="邮箱" width="350" align="center" />
      <el-table-column prop="petname" label="昵称" width="" align="center" />
      <el-table-column prop="role" label="当前角色" width="150" align="center" />
      <el-table-column label="操作" width="150" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
          <el-button type="text" size="big" @click="this.downRole(scope.row)">降级</el-button>
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
  <!--管理员降级弹窗，点击降级时才显示-->
  <el-dialog v-model="dialogFormVisibleForDownRole" title="管理员降级">
    <span>是否确认将该管理员降为用户？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForDownRole = false">取消</el-button>
        <el-button type="primary" @click="conform()">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "../../utils/request";
let rowForEdit;

export default {
  name: "AdminList",
  data() {
    return {
      tableData:[

      ],
      /*
          分页插件的三个基本参数为total，pageSize，currentPage，
          其中加载数据时需要将pageSize和currentPage传到后端，
          结合Mybatis-plus中的分页插件便可以实现分页查询
       */
      total: 0,        //总条数，从后端获取
      pageSize: 5,     //每页显示的条数
      currentPage: 1,  //当前所在页
      dialogFormVisibleForDownRole: false
    }
  },
  methods: {
    //加载表格数据（结合分页操作，因此加载数据时需要将pageSize和currentPage传到后端）
    loadData() {
      request.get("/user/getAllUserAdmin",{
        params: {
          pageSize: this.pageSize,
          currentPage: this.currentPage
        }
      }).then(res =>{
        this.total = res.data.total  //返回总条数
        this.tableData = res.data.records
      })
    },
    //管理员降级
    downRole(row) {
      rowForEdit = row
      this.dialogFormVisibleForDownRole = true
    },
    //确认降级
    conform() {
      request.post("/user/downRole",rowForEdit.email).then(res =>{
        this.loadData()
        this.dialogFormVisibleForDownRole = false
      })
    },
    //当前页码改变（例如点击下一页、上一页等操作）
    handleCurrentChange(){
      //重新加载数据
      this.loadData()
    }
  },
  created() {
    this.loadData()
  }
}
</script>

<style scoped>
.sp1{
  position: absolute;margin-top: 50px;left: 5%;font-size: 13px;color: red;
}
.sp2{
  position: absolute;margin-top: 70px;left: 5%;font-size: 13px;color: red;
}
.dF{
  width: 90%;position: absolute;left: 5%;right: 5%;margin-top: 100px;
}
.el1{
  width: 100%;
}
.el2{
  margin-top: 15px;
}
</style>