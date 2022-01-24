<template>
  <!--头部-->
  <HeadOfHome/>
  <!--左部列表-->
  <div id="left" class="dF">
    <el-table :data="tableDataForNewsList" class="el1" :cell-style="{background:'#ffffff'}" :show-header="false">
      <el-table-column label="" width="">
        <template #default="scope">
          <table class="t1">
            <tr class="tr1" id="row">
              <td colspan="6"><span @click="intoNewsContent(scope.row.id)" class="sp1">[{{scope.row.type}}]&nbsp;{{scope.row.headline}}</span></td>
            </tr>
            <tr class="tr2">
              <td class="td1"><img src="../assets/photo/uptime.png" alt="" class="im1"></td>
              <td class="td2">{{scope.row.date}}</td>
              <td class="td3"><img src="../assets/photo/click.png" alt="" class="im2"></td>
              <td class="td4">{{scope.row.click}}</td>
              <td class="td5"><img src="../assets/photo/comment.png" alt="" class="im3"></td>
              <td class="td6">{{scope.row.comment}}</td>
            </tr>
          </table>
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
  <!--右边-->
  <div class="dS">
    <table class="t2">
      <tr class="tr3">
        <td class="td7">兴趣推荐</td>
        <td class="td8"><img src="../assets/photo/change.png" alt="" class="im4"></td>
        <td><el-button @click="clearSearch()" type="text" class="el3">重置</el-button></td>
      </tr>
      <tr class="tr4">
        <td colspan="3" class="td9">基于用户搜索记录</td>
      </tr>
    </table>
    <el-table :data="tableDataForUserHobby" class="el4" :cell-style="{background:'#ffffff'}" :show-header="false">
      <el-table-column label="" width="">
        <template #default="scope">
          <table class="t3">
            <tr>
              <td colspan="3" class="td10"><span @click="intoNewsContent(scope.row.id)" class="sp2">●&nbsp;{{scope.row.headline}}</span></td>
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
import {ElMessage} from "element-plus";

let userEmail;

export default {
  name: "TypeContent",
  components: {HeadOfHome},
  data() {
    return {
      tableDataForNewsList: [

      ],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      tableDataForUserHobby: [

      ]
    }
  },
  created() {
    let str = sessionStorage.getItem("userEmail");
    if (str===null||str==="") {
      userEmail = str
    }else {
      let reg = new RegExp('"', "g");
      userEmail = str.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
    }
    this.loadData()
    this.loadHobbyNews()
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
    //加载板块数据
    loadData() {
      let typeName = this.$route.query.typeName  //板块名
      request.get("/news_User/getAllNewsByTypeName",{
        params: {
          typeName: typeName,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res =>{
        for (let i=0;i<res.data.records.length;i++) {
          res.data.records[i].date = moment(res.data.records[i].date).format("YYYY-MM-DD")
        }
        this.tableDataForNewsList = res.data.records
        this.total = res.data.total
      })
    },
    handleCurrentChange() {
      this.loadData()
    },
    //清空搜索记录
    clearSearch(){
      if (userEmail===null||userEmail==="") {
        ElMessage({
          type: 'warning',
          message: '请登录或注册后重试'
        })
      }else {
        request.post("/search/clearUserSearch",userEmail).then(res =>{
          ElMessage({
            type: 'success',
            message: '已重置'
          })
          this.loadHobbyNews()
        })
      }
    },
    //加载兴趣推荐数据
    loadHobbyNews() {
      if (userEmail===null||userEmail==="") {   //未登录

      }else {
        request.post("/news/getNewsByUserSearch",userEmail).then(res =>{
          this.tableDataForUserHobby = res.data
        })
      }
    }
  }
}
</script>

<style scoped>
.el-table tbody tr:hover>td {
  background-color:#ffffff!important
}
.dF{
  position: absolute;height: auto;width: 43%;background: white;left: 15%;margin-top: 70px;padding-right: 17px;padding-left: 17px;box-shadow: 0 0 3px 3px #f3f3f3;
}
.el1{
  width: 100%;
}
.t1{
  width: 100%;
}
.tr1{
  height: 70px;
}
.sp1{
  cursor:pointer;text-decoration: none;font-size: 18px;color: black;
}
.tr2{
  height: 40px;
}
.td1{
  text-align: right;
}
.td2{
  width: 25%;
}
.td3{
  width: 6%;
}
.td4{
  width: 8%;
}
.td5{
  width: 6%;
}
.td6{
  width: 8%;
}
.im1{
  height: 30px;width: 30px;
}
.im2{
  height: 30px;width: 30px;
}
.im3{
  height: 30px;width: 30px;
}
.el2{
  margin-top: 30px;margin-bottom: 30px
}
.dS{
  position: absolute;width: 18%;height: auto;right: 15%;background: white;margin-top: 70px;box-shadow: 0 0 3px 3px #f3f3f3;padding-right: 17px;padding-left: 17px;padding-bottom: 17px;
}
.t2{
  width: 100%;
}
.tr3{
  line-height: 50px;font-size: 18px;text-align: left;
}
.td7{
  width: 45%;
}
.td8{
  width: 7%;
}
.im4{
  height: 16px;width: 16px;position: relative;top: 3px;
}
.el3{
  text-align: left;text-decoration: none;color: red;font-size: 14px;
}
.tr4{
  line-height: 15px;font-size: 13px;text-align: left;
}
.td9{
  width: 45%;
}
.el4{
  width: 100%;
}
.t3{
  width: 100%;
}
.td10{
  padding-top: 10px;padding-bottom: 10px;
}
.sp2{
  cursor:pointer;font-size: 15px;color: black;text-decoration: none;
}
</style>