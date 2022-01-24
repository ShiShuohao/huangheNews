<template>
  <div class="dF">
    <el-input v-model="search" placeholder="请输入关键词" class="el1" />
    <el-button type="primary" @click="searchByInput()">搜索</el-button>
  </div>
  <div class="dS">
    <el-table :data="tableData" border class="el2">
      <el-table-column prop="id" label="编号" width="" align="center" v-if="false" />
      <el-table-column prop="headline" label="标题" width="" align="center" />
      <el-table-column prop="petname" label="作者" width="150" align="center" />
      <el-table-column prop="type" label="板块" width="100" align="center" />
      <el-table-column prop="content" label="内容" width="100" align="center"  v-if="false"/>
      <el-table-column prop="date" label="发布日期" width="100" align="center" :formatter="timeFilter"/>
      <el-table-column prop="click" label="点击量" width="70" align="center" />
      <el-table-column prop="comment" label="评论量" width="70" align="center" />
      <el-table-column label="操作" width="200" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
          <el-button type="text" size="big" @click="this.look(scope.row)">查看</el-button>
          <el-button type="text" size="big" @click="this.edit(scope.row)">修改</el-button>
          <el-button type="text" size="big" @click="this.first(scope.row)">取消置顶</el-button>
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
        class="el3">
    </el-pagination>
  </div>
  <!--修改新闻弹窗，点击修改时才显示-->
  <el-dialog v-model="dialogFormVisibleForEdit" title="修改新闻" width="60%">
    <el-form
        :model="formForEdit"
        label-width="80px">
      <el-form-item label="标题" prop="headline">
        <el-input v-model="formForEdit.headlineEdit"></el-input>
      </el-form-item>
      <el-form-item label="日期">
        <el-col :span="11"><el-form-item prop="date"><el-date-picker v-model="formForEdit.dateEdit" type="date" placeholder="请选择日期" style="width: 100%"></el-date-picker></el-form-item></el-col>
      </el-form-item>
      <el-form-item label="板块" prop="type">
        <el-select v-model="formForEdit.typeEdit" placeholder="请选择板块" class="el4">
          <el-option
              v-for="item in lists"
              :key="item.name"
              :label="item.name"
              :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <!--富文本编辑器-->
        <div id="div1">
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForEdit=false">取消</el-button>
        <el-button type="primary" @click="conformEdit()">确定</el-button>
      </span>
    </template>
  </el-dialog>

  <!--删除新闻弹窗，点击删除时才显示-->
  <el-dialog v-model="dialogFormVisibleForDelete" title="删除新闻">
    <span>是否确认删除该新闻？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForDelete = false">取消</el-button>
        <el-button type="primary" @click="conformDelete()">确定</el-button>
      </span>
    </template>
  </el-dialog>

  <!--取消置顶新闻弹窗，点击取消置顶时才显示-->
  <el-dialog v-model="dialogFormVisibleForFirst" title="取消置顶">
    <span>是否确认取消置顶该新闻？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForFirst = false">取消</el-button>
        <el-button type="primary" @click="conformFirst()">确定</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import request from "../../utils/request";
import E from "wangeditor";
import {ElMessage} from "element-plus";
import moment from 'moment';
let editor;
let rowForUpdate;

export default {
  name: "NewsListForFirst",
  data() {
    return {
      tableData: [

      ],
      total: 0,
      pageSize: 5,
      currentPage: 1,
      formForEdit: {
        idEdit: '',
        headlineEdit: '',
        contentEdit: '',
        dateEdit: '',
        typeEdit: '',
        clickEdit: 0,
        commentEdit: 0,
        firstEdit: 1
      },
      lists: [

      ],
      dialogFormVisibleForEdit: false,
      dialogFormVisibleForDelete: false,
      dialogFormVisibleForFirst: false,
      search: ''
    }
  },
  created() {
    this.loadData()

  },
  methods: {
    //关键词搜索
    searchByInput() {
      request.get("/news_User/searchNewsForFirst",{
        params: {
          search: this.search,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res =>{
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
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
      request.get("/news_User/getAllNewsForFirst",{
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
      this.$router.push({
        path: '/newsContent',
        params: {
          newsId: row.id
        }
      })
    },
    //修改新闻
    edit(row) {
      //显示修改窗口
      this.dialogFormVisibleForEdit = true
      //加载富文本编辑器
      this.$nextTick(() => {
        editor = new E('#div1')
        editor.config.zIndex = 500
        editor.create()
      })
      //回显富文本编辑器
      this.$nextTick(() => {
        let content = row.content
        editor.txt.html(content)
      })
      //加载下拉列表数据
      request.post("/type/getAllType").then(res =>{
        this.lists = res.data
      })
      //绑定数据
      this.formForEdit.idEdit = row.id
      this.formForEdit.headlineEdit = row.headline
      this.formForEdit.dateEdit = row.date
      this.formForEdit.typeEdit = row.type
      this.formForEdit.clickEdit = row.click
      this.formForEdit.commentEdit = row.comment
    },
    //确认修改
    conformEdit() {
      this.formForEdit.contentEdit = editor.txt.html()
      if (this.formForEdit.headlineEdit===null||this.formForEdit.headlineEdit==="") {
        ElMessage({
          type: 'warning',
          message: '请输入标题'
        })
      }else if (this.formForEdit.dateEdit===null||this.formForEdit.dateEdit==="") {
        ElMessage({
          type: 'warning',
          message: '请选择日期'
        })
      }else if (this.formForEdit.typeEdit===null||this.formForEdit.typeEdit==="") {
        ElMessage({
          type: 'warning',
          message: '请选择板块'
        })
      }else if (this.formForEdit.contentEdit===null||this.formForEdit.contentEdit===""){
        ElMessage({
          type: 'warning',
          message: '请输入内容'
        })
      }else {
        let formForEdit =  {
          id: this.formForEdit.idEdit,
          headline: this.formForEdit.headlineEdit,
          content: this.formForEdit.contentEdit,
          date: this.formForEdit.dateEdit,
          type: this.formForEdit.typeEdit,
          click: this.formForEdit.clickEdit,
          comment: this.formForEdit.commentEdit,
          first: this.formForEdit.firstEdit
        }
        request.post("/news/editNews",formForEdit).then(res =>{
          ElMessage({
            type: 'success',
            message: '修改成功'
          })
          this.dialogFormVisibleForEdit = false
          this.loadData()
        })
      }
    },
    //取消置顶新闻
    first(row) {
      rowForUpdate = row
      this.dialogFormVisibleForFirst = true
    },
    //确认取消置顶
    conformFirst() {
      request.post("/news/setNewsNotFirst",rowForUpdate.id).then(res =>{
        ElMessage({
          type: 'success',
          message: '取消置顶成功'
        })
        this.loadData()
        this.dialogFormVisibleForFirst = false
      })
    },
    //删除新闻
    delete(row) {
      rowForUpdate = row
      this.dialogFormVisibleForDelete = true
    },
    //确认删除
    conformDelete() {
      request.post("/news/deleteNews",rowForUpdate.id).then(res =>{
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        this.loadData()
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
  width: 300px;margin-right: 20px;
}
.dS{
  width: 90%;position: absolute;left: 5%;right: 5%;margin-top: 100px;
}
.el2{
  width: 100%;
}
.el3{
  margin-top: 15px;
}
.el-col-11[data-v-7ccc5e12]{
  max-width: 100%;width: 100%;
}
.el4{
  width: 100%;
}
</style>