<template>
  <!--头部-->
  <div class="dF">
    <img src="../assets/photo/logo.png" alt="" class="im1">
    <el-input v-model="searchContent" placeholder="搜索网站内容"  class="el1"/>
    <el-button type="primary" class="el2" @click="search()">搜索</el-button>
    <!--登录状态显示-->
    <div v-if="loginVisible" class="dS">
      <img v-if="boyHead" src="../assets/photo/boy.png" alt="" class="im2">
      <img v-if="girlHead" src="../assets/photo/girl.png" alt="" class="im3">
      <!--下拉菜单-->
      <el-dropdown class="el3">
        <span class="el-dropdown-link">
          <span id="petname" class="sp1"></span>
          <el-icon class="el-icon--right">
            <arrow-down class="a1" />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/userHome')">个人中心</el-dropdown-item>
            <el-dropdown-item @click="logout()">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <!--未登录状态显示-->
    <div v-if="notLoginVisible" class="dT">
      <router-link to="/userLogin" class="r1">登录/注册</router-link>
    </div>
  </div>
  <!--左部列表-->
  <div id="left" class="d4">
    <el-table :data="tableDataForNewsList" class="el4" :cell-style="{background:'#ffffff'}" :show-header="false">
      <el-table-column label="" width="">
        <template #default="scope">
          <table class="t1">
            <tr class="tr1" id="row">
              <td colspan="6"><span @click="intoNewsContent(scope.row.id)" class="sp2">[{{scope.row.type}}]&nbsp;{{scope.row.headline}}</span></td>
            </tr>
            <tr class="tr2">
              <td class="td1"><img src="../assets/photo/uptime.png" alt="" class="im4"></td>
              <td class="td2">{{scope.row.date}}</td>
              <td class="td3"><img src="../assets/photo/click.png" alt="" class="im5"></td>
              <td class="td4">{{scope.row.click}}</td>
              <td class="td5"><img src="../assets/photo/comment.png" alt="" class="im6"></td>
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
        class="el5">
    </el-pagination>
  </div>
  <!--右边-->
  <div class="d5">
    <table class="t2">
      <tr class="tr3">
        <td class="td7">兴趣推荐</td>
        <td class="td8"><img src="../assets/photo/change.png" alt="" class="im7"></td>
        <td><el-button @click="clearSearch()" type="text" href="refresh_hobby" class="el6">重置</el-button></td>
      </tr>
      <tr class="tr4">
        <td colspan="3" class="td9">基于用户搜索记录</td>
      </tr>
    </table>
    <el-table :data="tableDataForUserHobby" class="el7" :cell-style="{background:'#ffffff'}" :show-header="false">
      <el-table-column label="" width="">
        <template #default="scope">
          <table class="t3">
            <tr>
              <td colspan="3" class="td10"><span @click="intoNewsContent(scope.row.id)" class="sp3">●&nbsp;{{scope.row.headline}}</span></td>
            </tr>
          </table>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
import request from "../utils/request";
import moment from "moment";
import {ElMessage} from "element-plus";
import ArrowDown from "@element-plus/icons/lib/ArrowDown";
import $ from "../assets/js/jquery-3.6.0";

let userEmail;
export default {
  name: "SearchResult",
  components: {
    ArrowDown,
  },
  data() {
    return {
      tableDataForNewsList: [

      ],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      tableDataForUserHobby: [

      ],
      searchContent: '',
      loginVisible: false,
      notLoginVisible: false,
      boyHead: false,
      girlHead: false
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
    this.loadLoginStateAndPetname()
    this.loadData()
    this.loadHobbyNews()
  },
  methods: {
    //退出登录
    logout() {
      sessionStorage.removeItem("userEmail")
      this.$router.push('/')
      this.loginVisible = false
      this.notLoginVisible = true
    },
    //加载时判断是否登录
    loadLoginStateAndPetname() {
      let userEmail = sessionStorage.getItem("userEmail")
      if (userEmail===null||userEmail==='') {   //说明未登录
        this.notLoginVisible = true
        this.loginVisible = false

      }else {
        this.notLoginVisible = false
        this.loginVisible = true
        let reg = new RegExp('"', "g");
        let newStr = userEmail.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
        request.post("/user/get",newStr).then(res =>{
          $('#petname').text(res.data.petname)
          if (res.data.sex==="女") {
            this.girlHead = true
            this.boyHead = false
          }else {
            this.girlHead = false
            this.boyHead = true
          }
        })
      }
    },
    //搜索
    search() {
      if (this.searchContent===null||this.searchContent==='') {
        ElMessage({
          type: 'warning',
          message: '搜索内容不能为空'
        })
      }else if (userEmail===null||userEmail==="") {
        request.get("/news/searchNews",{
          params: {
            searchContent: this.searchContent,
            currentPage: this.currentPage,
            pageSize: this.pageSize
          }
        }).then(res =>{
          for (let i=0;i<res.data.records.length;i++) {
            res.data.records[i].date = moment(res.data.records[i].date).format("YYYY-MM-DD")
          }
          this.tableDataForNewsList = res.data.records
          this.total = res.data.total
          this.loadHobbyNews()
        })
      }else {
        request.get("/search/saveUserSearchContent",{
          params: {
            userEmail: userEmail,
            searchContent: this.searchContent
          }
        }).then(res =>{
          request.get("/news/searchNews",{
            params: {
              searchContent: this.searchContent,
              currentPage: this.currentPage,
              pageSize: this.pageSize
            }
          }).then(res =>{
            for (let i=0;i<res.data.records.length;i++) {
              res.data.records[i].date = moment(res.data.records[i].date).format("YYYY-MM-DD")
            }
            this.tableDataForNewsList = res.data.records
            this.total = res.data.total
            this.loadHobbyNews()
          })
        })
      }
    },
    //进入新闻详情页
    intoNewsContent(id) {
      this.$router.push({
        path: '/newsContent',
        query: {
          newsId: id
        }
      })
    },
    //加载搜索数据
    loadData() {
      let searchContent = this.$route.query.searchContent            //搜索内容
      request.get("/news/searchNews",{
        params: {
          searchContent: searchContent,
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
  background: white;height: 50px;width: 100%;border-bottom: 1.5px #f0f0f0 solid;position: absolute;margin-top: 0;
}
.im1{
  position: absolute;height: 65%;top: 50%;transform: translate(-50%,-50%);left: 20%;
}
.el1{
  position: absolute;width: 23%;height: 40px;transform: translate(-50%,-50%);left: 48%;top: 50%;
}
.el2{
  position: absolute;width: 70px;height: 40px;transform: translate(-50%, -50%);left: 65%;top: 50%;
}
.dS{
  position: absolute;width: 20%;height: 40px;transform: translate(-50%,-50%);top: 50%;left: 80%;text-align: center;
}
.im2{
  height: 40px;width: 40px;margin-left: -10px;
}
.im3{
  height: 40px;width: 40px;margin-left: -10px;
}
.el3{
  position: absolute;transform: translate(-50%,-50%);left: 80%;top: 50%;font-size: 16px;width: 150px;text-align: center;
}
.sp1{
  font-size: 15px;
}
.a1{
  position: relative;top: 3px;
}
.dT{
  position: absolute;transform: translate(-50%, -50%);left: 80%;top: 50%;font-size: 16px;width: 100px;text-align: center;
}
.r1{
  color:black;
}
.d4{
  position: absolute;height: auto;width: 43%;background: white;left: 15%;margin-top: 70px;padding-right: 17px;padding-left: 17px;box-shadow: 0 0 3px 3px #f3f3f3;
}
.el4{
  width: 100%;
}
.t1{
  width: 100%;
}
.tr1{
  height: 70px;
}
.sp2{
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
.im4{
  height: 30px;width: 30px;
}
.im5{
  height: 30px;width: 30px;
}
.im6{
  height: 30px;width: 30px;
}
.el5{
  margin-top: 30px;margin-bottom: 30px
}
.d5{
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
.im7{
  height: 16px;width: 16px;position: relative;top: 3px;
}
.el6{
  text-align: left;text-decoration: none;color: red;font-size: 14px;
}
.tr4{
  line-height: 15px;font-size: 13px;text-align: left;
}
.td9{
  width: 45%;
}
.el7{
  width: 100%;
}
.t3{
  width: 100%;
}
.td10{
  padding-top: 10px;padding-bottom: 10px;
}
.sp3{
  cursor:pointer;font-size: 15px;color: black;text-decoration: none;
}
</style>