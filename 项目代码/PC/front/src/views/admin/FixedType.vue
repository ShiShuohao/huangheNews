<template>
  <span class="sp1">提示：0代表固定板块，1代表可变板块</span>
  <div class="dF">
    <el-table :data="tableData" border class="el1">
      <el-table-column prop="name" label="板块名" width="" align="center" />
      <el-table-column prop="newsNumber" label="新闻数量" width="350" align="center" />
      <el-table-column prop="state" label="板块状态" width="350" align="center" />
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
</template>

<script>
import request from "../../utils/request";

export default {
  name: "FixedType",
  data() {
    return {
      tableData: [

      ],
      total: 0,
      pageSize: 5,
      currentPage: 1,
    }
  },
  methods: {
    //当前页码改变
    handleCurrentChange() {
      this.loadData()
    },
    //加载数据
    loadData() {
      request.get("/type/getFixedType",{
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res =>{
        this.total = res.data.total
        this.tableData = res.data.records
      })
    }
  },
  created() {
    this.loadData()
  }
}
</script>

<style scoped>
.sp1{
  position: absolute;margin-top: 60px;left: 5%;font-size: 13px;color: red;
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