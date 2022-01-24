<template>
  <!--头部-->
  <HeadOfHome/>
  <!--导航栏-->
  <div class="dF">
    <router-link to="/" class="l1">首页</router-link>
    <router-link to="/latestNews" class="l2">最新</router-link>
    <router-link to="/hotNews" class="l3">热门</router-link>
    <router-link to="/firstNews" class="l4">置顶</router-link>
    <router-link to="/allType" class="l5">全部板块</router-link>
  </div>
  <!--中间部分-->
  <div class="dS">
    <el-table :data="tableData" class="el1" :show-header="false" :cell-style="{background:'#ffffff'}">
      <el-table-column align="center">
        <template #default="scope" class="el1">
          <table class="el1">
            <tr class="tr1">
              <td class="td1"><span @click="intoNewsContent(scope.row.id)" class="sp1">[{{scope.row.type}}]&nbsp;{{scope.row.headline}}</span></td>
              <td class="td2">{{scope.row.date}}</td>
              <td class="td2">{{scope.row.click}}&nbsp;&nbsp;浏览</td>
            </tr>
          </table>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import HeadOfHome from "../components/HeadOfHome";
import request from "../utils/request";
import moment from "moment";
export default {
  name: "LatestNews",
  components: {HeadOfHome},
  data() {
    return {
      tableData: [

      ],
    }
  },
  methods: {
    //进入新闻详情页
    intoNewsContent(id) {
      this.$router.push({
        path: '/newsContent',
        query: {
          newsId: id
        }
      })
    },
    //加载数据
    loadData() {
      request.post("/news/getLatestNews").then(res =>{
        for (let i=0;i<res.data.length;i++) {
          res.data[i].date = moment(res.data[i].date).format("YYYY-MM-DD")
        }
        this.tableData = res.data
      })
    }
  },
  created() {
    document.body.style.overflow = 'auto'
    this.loadData()
  }
}
</script>

<style scoped>
.el-table tbody tr:hover>td {
  background-color:#ffffff!important
}
.dF{
  position: absolute;width: 100%;height: 45px;margin-top: 50.5px;background: white;box-shadow: 0 0 3px 0 #f3f3f3;
}
.l1{
  position: absolute;transform: translate(-50%,-50%);left: 20%;top: 50%;font-size: 17px;color:black;
}
.l2{
  position: absolute;transform: translate(-50%,-50%);left: 27%;top: 50%;color: #409eff;font-size: 17px;
}
.l3{
  position: absolute;transform: translate(-50%,-50%);left: 34%;top: 50%;color: black;font-size: 17px;
}
.l4{
  position: absolute;transform: translate(-50%,-50%);left: 41%;top: 50%;color: black;font-size: 17px;
}
.l5{
  position: absolute;transform: translate(-50%,-50%);left: 50%;top: 50%;color:black;font-size: 17px;
}
.dS{
  background: white;width: 60%;height: auto;position: absolute;left: 20%;margin-top: 120px;box-shadow: 0 0 3px 3px #f3f3f3;
}
.el1{
  width: 100%
}
.tr1{
  height: 25px
}
.td1{
  text-align: left;padding-left: 10px;
}
.sp1{
  cursor:pointer;text-decoration: none;font-size: 14px;color: black;
}
.td2{
  width: 100px
}
</style>