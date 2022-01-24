<template>
  <div class="dF">
    <el-table :data="tableData" border class="el1">
      <el-table-column prop="id" label="编号" width="" align="center" v-if="false" />
      <el-table-column prop="version" label="版本" width="300" align="center" />
      <el-table-column prop="date" label="发布日期" width="300" align="center" :formatter="timeFilter"/>
      <el-table-column prop="content" label="内容" width="100" align="center"  v-if="false"/>
      <el-table-column label="操作" width="" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
          <el-button type="text" size="big" @click="this.look(scope.row)">查看</el-button>
          <el-button type="text" size="big" @click="this.edit(scope.row)">修改</el-button>
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
        class="el2">
    </el-pagination>
  </div>
  <!--修改日志弹窗，点击修改时才显示-->
  <el-dialog v-model="dialogFormVisibleForEdit" title="修改日志" width="60%">
    <el-form
        :model="formForEdit"
        label-width="80px">
      <el-form-item label="版本" prop="version">
        <el-input v-model="formForEdit.versionEdit"></el-input>
      </el-form-item>
      <el-form-item label="日期">
        <el-col :span="11"><el-form-item prop="date"><el-date-picker v-model="formForEdit.dateEdit" type="date" placeholder="请选择日期" style="width: 100%"></el-date-picker></el-form-item></el-col>
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
  <!--删除日志弹窗，点击删除时才显示-->
  <el-dialog v-model="dialogFormVisibleForDelete" title="删除日志">
    <span>是否确认删除该日志？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForDelete = false">取消</el-button>
        <el-button type="primary" @click="conformDelete()">确定</el-button>
      </span>
    </template>
  </el-dialog>
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
import request from "../../utils/request";
import E from "wangeditor";
import {ElMessage} from "element-plus";
import moment from 'moment';
import $ from '../../assets/js/jquery-3.6.0';  //引入jQuery

let editor;
let rowForUpdate;

export default {
  name: "LogList",
  data() {
    return {
      tableData: [

      ],
      total: 0,
      pageSize: 5,
      currentPage: 1,
      formForEdit: {
        idEdit: '',
        versionEdit: '',
        dateEdit: '',
        contentEdit: ''
      },
      dialogFormVisibleForEdit: false,
      dialogFormVisibleForDelete: false,
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
    },
    //修改日志
    edit(row) {
      //显示修改窗口
      this.dialogFormVisibleForEdit = true
      //加载富文本编辑器
      this.$nextTick(() =>{
        editor = new E('#div1')
        editor.config.zIndex = 500
        editor.create()
      })
      //回显富文本编辑器
      this.$nextTick(() => {
        let content = row.content
        editor.txt.html(content)
      })
      //绑定数据
      this.formForEdit.idEdit = row.id
      this.formForEdit.versionEdit = row.version
      this.formForEdit.dateEdit = row.date
    },
    //确认修改
    conformEdit() {
      this.formForEdit.contentEdit = editor.txt.html()
      if (this.formForEdit.versionEdit===null||this.formForEdit.versionEdit==="") {
        ElMessage({
          type: 'warning',
          message: '请输入版本'
        })
      }else if (this.formForEdit.dateEdit===null||this.formForEdit.dateEdit==="") {
        ElMessage({
          type: 'warning',
          message: '请选择日期'
        })
      }else if (this.formForEdit.contentEdit===null||this.formForEdit.contentEdit===""){
        ElMessage({
          type: 'warning',
          message: '请输入内容'
        })
      }else {
        let formForEdit =  {
          id: this.formForEdit.idEdit,
          version: this.formForEdit.versionEdit,
          content: this.formForEdit.contentEdit,
          date: this.formForEdit.dateEdit
        }
        request.post("/log/editLog",formForEdit).then(res =>{
          ElMessage({
            type: 'success',
            message: '修改成功'
          })
          this.dialogFormVisibleForEdit = false
          this.loadData()
        })
      }
    },
    //删除日志
    delete(row) {
      rowForUpdate = row
      this.dialogFormVisibleForDelete = true
    },
    //确认删除
    conformDelete() {
      request.post("/log/deleteLog",rowForUpdate.id).then(res =>{
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
  width: 100%;
}
.el2{
  margin-top: 15px;
}
.el-col-11[data-v-55fcbdfc]{
  max-width: 100%;width: 100%;
}
</style>