<template>
  <!--首页头部-->
  <HeadOfWelcome/>
  <div class="divFirst">
    <p class="p1">网站更新日志</p>
    <div class="divSecond">
      <el-table :data="tableData" border>
        <el-table-column prop="id" label="编号" width="" align="center" v-if="false" />
        <el-table-column prop="version" label="版本" width="300" align="center" />
        <el-table-column prop="date" label="发布日期" width="300" align="center" :formatter="timeFilter"/>
        <el-table-column prop="content" label="内容" width="100" align="center"  v-if="false"/>
        <el-table-column label="操作" width="" align="center" key="slot">
          <!--此处使用了插槽，来实现获得某行的对象-->
          <template #default="scope" >
            <el-button type="text" size="big" @click="this.look(scope.row)">查看</el-button>
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
  </div>
  <!--查看日志弹窗，点击查看时才显示-->
  <el-dialog v-model="dialogFormVisibleForLook" title="查看日志">
    <div id="content"></div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="dialogFormVisibleForLook=false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import moment from 'moment';
import $ from "../assets/js/jquery-3.6.0";
import HeadOfWelcome from "../components/HeadOfWelcome";
import request from "../utils/request";  //引入jQuery

export default {
  name: "About",
  components: {HeadOfWelcome},
  data() {
    return {
      tableData: [

      ],
      total: 0,
      pageSize: 5,
      currentPage: 1,
      dialogFormVisibleForLook: false
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
      request.get("/log/getAllLog",{
        params: {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    //查看详情
    look(row) {
      this.dialogFormVisibleForLook = true
      this.$nextTick(() =>{
        $('#content').html(row.content)
      })
    }
  }
}
</script>

<style scoped>
.divFirst{
  background: white;position: absolute;width: 100%;left: 50%;top: calc(50% + 25px);transform: translate(-50%,-50%);height: calc(100% - 50px);
}
.p1{
  text-align: center;padding: 10px;font-size: 22px;color: #409eff;
}
.divSecond{
  width: 60%;position: absolute;left: 20%;margin-top: 10px;
}
.el1{
  margin-top: 15px;
}
</style>