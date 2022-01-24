<template>
  <!--头部-->
  <HeadOfHome/>
  <div class="dF">
  <!--新闻内容-->
  <table class="t1">
    <tr class="tr1">
      <td colspan="2" class="td1">[<span id="type"></span>]&nbsp;&nbsp;<span id="headline"></span></td>
    </tr>
    <tr class="tr2">
      <td class="td2">
        <img src="../assets/photo/boy.png" alt="" class="boy" id="boy" v-if="boyHeadAuthor">
        <img src="../assets/photo/girl.png" alt="" class="girl" id="girl" v-if="girlHeadAuthor">
      </td>
      <td>
        <span id="author"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span id="date"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="text" @click="focusNewsAuthor()" class="el1">关注TA</el-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="text" @click="messageNewsAuthor()" class="el2">私信TA</el-button>
      </td>
    </tr>
    <tr>
      <td colspan="2" id="content"></td>
    </tr>
  </table>
  <table class="t2">
    <tr>
      <td v-if="notCollect" class="td3"><img @click="insertCollect()" src="../assets/photo/collect.png" alt="" style="width: 30px;height: 30px;"><span style="position: relative;top: -8px;left: 10px;">点击收藏</span></td>
      <td v-if="collected"  class="td4"><img @click="deleteCollect()" src="../assets/photo/collected.png" alt="" style="width: 30px;height: 30px;"><span style="position: relative;top: -8px;left: 10px;">取消收藏</span></td>
    </tr>
  </table>
  <el-table :data="tableDataForComment" class="el3" v-if="commentArea" :cell-style="{background:'#ffffff'}">
    <el-table-column label="" width="">
      <template #default="scope">
        <table class="t3" v-if="scope.row.sex==='男'">
            <tr class="tr3">
              <td class="td5">
                <div class="dS">
                  <img src="../assets/photo/boy.png" alt="" class="im1"><br>
                  <span class="sp1">{{scope.row.petname}}</span><br>
                  <el-button type="text" @click="focusCommentAuthor(scope.row.author)" class="el4">关注TA</el-button><br>
                  <el-button type="text" @click="messageCommentAuthor(scope.row.author)" class="el5">私信TA</el-button>
                </div>
              </td>
              <td class="td6">
                <span>{{scope.row.content}}</span>
              </td>
            </tr>
            <tr class="tr4">
              <td colspan="2" class="td7"><span>{{scope.row.date}}</span></td>
            </tr>
        </table>
        <table class="t4" v-if="scope.row.sex==='女'">
          <tr class="tr5">
            <td class="td8">
              <div class="dT">
                <img src="../assets/photo/girl.png" alt="" class="im2"><br>
                <span class="sp2">{{scope.row.petname}}</span><br>
                <el-button type="text" @click="focusCommentAuthor(scope.row.author)" class="el6">关注TA</el-button><br>
                <el-button type="text" @click="messageCommentAuthor(scope.row.author)" class="el7">私信TA</el-button>
              </div>
            </td>
            <td class="td9">
              <span>{{scope.row.content}}</span>
            </td>
          </tr>
          <tr class="tr6">
            <td colspan="2" class="td10"><span>{{scope.row.date}}</span></td>
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
        class="el8">
    </el-pagination>
  <div class="d5">
    <el-input
        class="el9"
        v-model="textarea"
        :rows="10"
        type="textarea"
        placeholder="请输入您的评论"
        id="comment"/>
    <el-button class="el10" type="primary" @click="publishComment()">发表评论</el-button>
  </div>
  </div>
  <!--私信弹窗，点击私信时才显示-->
  <el-dialog v-model="dialogFormVisible" title="发送私信">
    <el-form model="messageForm">
      <el-form-item label="主题" prop="headline">
        <el-input v-model="messageForm.headline"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <!--富文本编辑器-->
        <div id="div1">
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm()">发送</el-button>
      </span>
    </template>
  </el-dialog>
  <el-backtop />
</template>

<script>
import request from "../utils/request";
import $ from "../assets/js/jquery-3.6.0";
import {ElMessage} from "element-plus";
import moment from "moment";
import HeadOfHome from "../components/HeadOfHome";
import Star from "@element-plus/icons/lib/Star";
import StarFilled from "@element-plus/icons/lib/StarFilled";
import E from 'wangeditor'

let newsId;
let newsAuthor;
let userEmail;
let editor;
export default {
  name: "NewsContent",
  components: {
    HeadOfHome,
    Star,
    StarFilled,
  },
  data() {
    return {
      boyHeadAuthor: false,
      girlHeadAuthor: false,
      notCollect: false,
      collected: false,
      total: 0,
      pageSize: 5,
      currentPage: 1,
      tableDataForComment: [

      ],
      textarea: '',
      commentArea: false,
      newsAuthor: newsAuthor,
      messageForm: {
        userFrom: '',
        userTo: '',
        headline: '',
        content: '',
        date: '',
        state: 0
      },
      dialogFormVisible: false
    }
  },
  methods: {
    handleCurrentChange() {
      this.loadCommentData()
    },
    //加载新闻内容数据
    loadNewsData() {
      newsId = this.$route.query.newsId
      console.log(newsId)
      //使该新闻的点击量加1
      request.post("/news/increaseNewsClick",newsId).then(res =>{
        request.post("/news_User/getNewsById",newsId).then(res =>{
          $('#type').text(res.data.type)
          $('#headline').text(res.data.headline)
          if (res.data.sex==="男") {
            this.boyHeadAuthor = true
            this.girlHeadAuthor = false
          }else {
            this.boyHeadAuthor = false
            this.girlHeadAuthor = true
          }
          $('#author').text(res.data.petname)
          $('#date').text(moment(res.data.date).format("YYYY-MM-DD"))
          $('#content').html(res.data.content)
          newsAuthor = res.data.author
        })
      })
    },
    //加载评论数据
    loadCommentData() {
      request.get("/comment_User/getAllCommentByNewsId",{
        params: {
          newsId: newsId,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res =>{
        if (res.data.records.length===0) {

        }else {
          for (let i=0;i<res.data.records.length;i++) {
            res.data.records[i].date = moment(res.data.records[i].date).format("YYYY-MM-DD")
          }
          this.tableDataForComment = res.data.records
          this.total = res.data.total
          this.commentArea = true
        }
      })
    },
    //发表评论
    publishComment() {
      if (this.textarea===null||this.textarea==="") {
        ElMessage({
          type: 'warning',
          message: '请输入评论'
        })
      }else if (userEmail===null||userEmail==="") {
        ElMessage({
          type: 'warning',
          message: '请登录或注册后重试'
        })
      }else {
        let comment = {
          newsId: newsId,
          author: userEmail,
          content: this.textarea
        }
        request.post("/comment/insertComment",comment).then(res =>{
          ElMessage({
            type: 'success',
            message: '评论成功'
          })
          request.post("/news/increaseNewsComment",newsId).then(res =>{
            $('#comment').val("")   //清空评论区
            this.loadCommentData()
          })
        })
      }
    },
    //关注新闻作者
    focusNewsAuthor() {
      if (userEmail===null||userEmail==="") {
        ElMessage({
          type: 'warning',
          message: '请登录或注册后重试'
        })
      }else {
        request.get("/focus/focusUser",{
          params: {
            userFrom: userEmail,
            userTo: newsAuthor
          }
        }).then(res =>{
          if (res.code==="0") {
            ElMessage({
              type: 'success',
              message: '关注成功，可在个人中心查看'
            })
          }else {
            ElMessage({
              type: 'warning',
              message: '您已经关注过了，可在个人中心查看'
            })
          }
        })
      }
    },
    //关注评论作者
    focusCommentAuthor(author) {
      if (userEmail===null||userEmail==="") {
        ElMessage({
          type: 'warning',
          message: '请登录或注册后重试'
        })
      }else {
        request.get("/focus/focusUser",{
          params: {
            userFrom: userEmail,
            userTo: author
          }
        }).then(res =>{
          if (res.code==="0") {
            ElMessage({
              type: 'success',
              message: '关注成功，可在个人中心查看'
            })
          }else {
            ElMessage({
              type: 'warning',
              message: '您已经关注过了，可在个人中心查看'
            })
          }
        })
      }
    },
    //私信新闻作者
    messageNewsAuthor() {
      if (userEmail===null||userEmail==="") {
        ElMessage({
          type: 'warning',
          message: '请登录或注册后重试'
        })
      }else {
        this.dialogFormVisible = true
        //加载富文本编辑器
        this.$nextTick(() => {
          editor = new E('#div1')
          editor.config.zIndex = 500
          editor.create()
        })
        this.messageForm.userFrom = userEmail
        this.messageForm.userTo = newsAuthor
        this.messageForm.headline = ''
        this.messageForm.content = ''
        editor.txt.html("")
      }
    },
    //私信评论作者
    messageCommentAuthor(author) {
      if (userEmail===null||userEmail==="") {
        ElMessage({
          type: 'warning',
          message: '请登录或注册后重试'
        })
      }else {
        this.dialogFormVisible = true
        //加载富文本编辑器
        this.$nextTick(() => {
          editor = new E('#div1')
          editor.config.zIndex = 500
          editor.create()
        })
        this.messageForm.userFrom = userEmail
        this.messageForm.userTo = author
        this.messageForm.headline = ''
        this.messageForm.content = ''
        editor.txt.html("")
      }
    },
    //确认发送私信
    submitForm() {
      this.messageForm.content = editor.txt.html()
      if (this.messageForm.headline===null||this.messageForm.headline==="") {
        ElMessage({
          type: 'warning',
          message: '请输入主题'
        })
      }else if (this.messageForm.content===null||this.messageForm.content==="") {
        ElMessage({
          type: 'warning',
          message: '请输入内容'
        })
      }else {
        request.post("/message/sendMessage",this.messageForm).then(res =>{
          ElMessage({
            type: 'success',
            message: '已送达'
          })
          this.dialogFormVisible = false
        })
      }
    },
    //加载收藏信息
    loadCollectData() {
      if (userEmail===null||userEmail==="") {
        this.notCollect = true
        this.collected = false
      }else {
        request.get("/collect/getCollectInfo",{
          params: {
            newsId: newsId,
            userEmail: userEmail
          }
        }).then(res =>{
          if (res.data==="未收藏") {
            this.notCollect = true
            this.collected = false
          }else {
            this.collected = true
            this.notCollect = false
          }
        })
      }
    },
    //添加收藏
    insertCollect() {
      if (userEmail===null||userEmail==="") {
        ElMessage({
          type: 'warning',
          message: '请登录或注册后重试'
        })
      }else {
        request.get("/collect/insertCollect",{
          params: {
            newsId: newsId,
            userEmail: userEmail
          }
        }).then(res =>{
          this.collected = true
          this.notCollect = false
          ElMessage({
            type: 'success',
            message: '收藏成功'
          })
        })
      }
    },
    //取消收藏
    deleteCollect() {
      request.get("/collect/deleteCollect",{
        params: {
          newsId: newsId,
          userEmail: userEmail
        }
      }).then(res =>{
        this.notCollect = true
        this.collected = false
        ElMessage({
          type: 'success',
          message: '已取消'
        })
      })
    },
  },
  created() {
    document.body.style.overflow = 'scroll'
    document.body.style.overflowX = 'hidden'
    this.loadNewsData()
    this.loadCommentData()
    let str = sessionStorage.getItem("userEmail")
    if (str===null||str==="") {
      userEmail = str
    }else {
      let reg = new RegExp('"', "g");
      userEmail = str.replace(reg, "");    //正则表达式，将字符串两端的双引号去掉
    }
    this.loadCollectData()
  }
}
</script>

<style scoped>
.el-table tbody tr:hover>td {
  background-color:#ffffff!important
}
.dF{
  position: absolute;height: auto;width: 60%;background: white;left: 18.8%;margin-top: 70px;padding-right: 1.2%;padding-left: 1.2%;box-shadow: 0 0 3px 3px #f3f3f3;
}
.t1{
  width: 100%;
}
.tr1{
  height: 80px;
}
.td1{
  font-size: 22px
}
.tr2{
  height: 60px;position: relative;top: -10px;left: 10px;
}
.td2{
  width: 6%;
}
.boy{
  height: 40px;width: 40px;border-radius: 20px;
}
.girl{
  height: 40px;width: 40px;border-radius: 20px;
}
.el1{
  text-decoration: none;color: red;font-size: 15px;
}
.el2{
  text-decoration: none;color: red;font-size: 15px;
}
.t2{
  width: 100%;height: 250px;
}
.td3{
  text-align: center;padding-top: 30px;
}
.td4{
  text-align: center;padding-top: 30px;
}
.el3{
  width: 100%;
}
.t3{
  width: 100%;
}
.tr3{
  background: #fafafa;
}
.td5{
  width: 15%;text-align: center;
}
.dS{
  padding: 20px;
}
.im1{
  height: 50px;width: 50px;padding: 15px;padding-bottom: 20px;
}
.sp1{
  font-size: 15px;
}
.el4{
  text-decoration: none;color: red;font-size: 13px;position: relative;top: 5px;
}
.el5{
  text-decoration: none;color: red;font-size: 13px;position: relative;top: 8px;
}
.td6{
  padding: 10px;
}
.tr4{
  font-size: 12px;text-align: right;
}
.td7{
  padding-bottom: 20px;
}
.t4{
  width: 100%;
}
.tr5{
  background: #fafafa;
}
.td8{
  width: 15%;text-align: center;
}
.dT{
  padding: 20px;
}
.im2{
  height: 50px;width: 50px;padding: 15px;padding-bottom: 20px;
}
.sp2{
  font-size: 15px;
}
.el6{
  text-decoration: none;color: red;font-size: 13px;position: relative;top: 5px;
}
.el7{
  text-decoration: none;color: red;font-size: 13px;position: relative;top: 8px;
}
.td9{
  padding: 10px;
}
.tr6{
  font-size: 12px;text-align: right;
}
.td10{
  padding-bottom: 20px;
}
.el8{
  margin-top: 15px;
}
.d5{
  text-align: center
}
.el9{
  padding-top: 30px;padding-bottom: 30px;
}
.el10{
  margin-bottom: 60px;
}
</style>