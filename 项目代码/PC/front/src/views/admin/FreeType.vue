<template>
  <el-button type="primary" class="el1" @click="add()">新增板块</el-button>
  <span class="sp1">提示：0代表固定板块，1代表可变板块</span>
  <div class="dF">
    <el-table :data="tableData" border class="el2">
      <el-table-column prop="name" label="板块名" width="300" align="center" />
      <el-table-column prop="newsNumber" label="新闻数量" width="300" align="center" />
      <el-table-column prop="state" label="板块状态" width="300" align="center" />
      <el-table-column label="操作" width="" align="center" key="slot">
        <!--此处使用了插槽，来实现获得某行的对象-->
        <template #default="scope" >
          <el-button type="text" size="big" @click="this.delete(scope.row)">删除</el-button>
          <el-button type="text" size="big" @click="this.edit(scope.row)">修改</el-button>
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
  <!--新增板块弹窗，点击新增时才显示-->
  <el-dialog v-model="dialogFormVisible" title="新增板块">
    <el-form :model="form">
      <el-form-item label="板块名" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="conformAdd()">确定</el-button>
      </span>
    </template>
  </el-dialog>
  <!--修改板块弹窗，点击修改时才显示-->
  <el-dialog v-model="dialogFormVisibleForEdit" title="修改板块">
    <el-form :model="formForEdit">
      <el-form-item label="原板块名" :label-width="formLabelWidth">
        <el-input v-model="formForEdit.oldName" autocomplete="off" disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="新板块名" :label-width="formLabelWidth">
        <el-input v-model="formForEdit.newName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="贴子数" :label-width="formLabelWidth">
        <el-input v-model="formForEdit.newsNumber" autocomplete="off" disabled="true"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForEdit = false">取消</el-button>
        <el-button type="primary" @click="conformEdit()">确定</el-button>
      </span>
    </template>
  </el-dialog>
  <!--删除板块弹窗，点击删除时才显示-->
  <el-dialog v-model="dialogFormVisibleForDelete" title="删除板块">
    <span>是否确认删除该板块？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleForDelete = false">取消</el-button>
        <el-button type="primary" @click="conformDelete()">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import request from "../../utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "FreeType",
  data() {
    let row;
    return {
      tableData: [

      ],
      form: {
        name: '',
        newsNumber: 0,
        state: 1
      },
      formForEdit: {
        oldName: '',
        newName: '',
        newsNumber: 0
      },
      formLabelWidth: 100,
      dialogFormVisible: false,
      dialogFormVisibleForEdit: false,
      total: 0,
      pageSize: 5,
      currentPage: 1,
      dialogFormVisibleForDelete: false,
      row,
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
    //删除板块
    delete(row) {
      this.row = row
      this.dialogFormVisibleForDelete = true
    },
    //确认删除
    conformDelete() {
      request.post("/type/delete",this.row).then(res =>{
        if (res.code==="1") {
          ElMessage({
            type: 'error',
            message: '该板块存在帖子，无法删除'
          })
        }else {
          ElMessage({
            type: 'success',
            message: '删除成功'
          })
        }
        this.loadData()
        this.dialogFormVisibleForDelete = false
      })
    },
    //修改板块名
    edit(row) {
      this.formForEdit.oldName = row.name
      this.formForEdit.newName = ''
      this.formForEdit.newsNumber = row.newsNumber
      this.dialogFormVisibleForEdit = true
    },
    //确定修改板块
    conformEdit() {
      if (this.formForEdit.newName===''||this.formForEdit.newName===null) {
        ElMessage({
          type: 'warning',
          message: '板块名不能为空'
        })
      }else {
        request.get("/type/edit",{
          params: {
            oldName: this.formForEdit.oldName,
            newName: this.formForEdit.newName,
            newsNumber: this.formForEdit.newsNumber
          }
        }).then(res => {
          if (res.code==="1") {   //更新失败
            ElMessage({
              type: 'error',
              message: '已存在该板块'
            })
          }else {
            ElMessage({
              type: 'success',
              message: '修改成功'
            })
          }
          this.loadData()  //重新加载数据
          this.dialogFormVisibleForEdit = false
        })
      }
    },
    //点击新增按钮
    add() {
      //首先将表单数据置为空值，防止上次的数据残留造成影响
      this.form = {
        name: '',
        newsNumber: 0,
        state: 1
      }
      //让新增对话框显示
      this.dialogFormVisible = true
    },
    //确认新增板块
    conformAdd() {
      //先判断输入是否为空
      if (this.form.name===null||this.form.name==='') {
        ElMessage({
          type: 'warning',
          message: '板块名不能为空'
        })
      }else {
        //将表单对象通过调用接口传递到后端，通过后端写入数据库
        request.post("/type/add",this.form).then(res =>{
          if (res.code==="0") {
            ElMessage({
              type: 'success',
              message: '新增成功'
            })
            //重新加载表格数据，使新插入的数据显示
            this.loadData()
            this.dialogFormVisible = false
          }else {
            ElMessage({
              type: 'error',
              message: '已存在该板块'
            })
            this.dialogFormVisible = false
          }
        })
      }
    },
    //加载表格数据，调用接口从后端获取可变板块的板块名和新闻数
    loadData() {
      request.get("/type/getFreeType",{
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
}
</script>

<style scoped>
.el1{
  position: absolute;left: 5%;margin-top: 50px;
}
.sp1{
  position: absolute;margin-top: 60px;left: 15%;font-size: 13px;color: red;
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