<template>
  <!--头部-->
  <HeadOfHome/>
  <!--导航栏-->
  <div class="divFirst">
    <router-link to="/" class="link1">首页</router-link>
    <router-link to="/latestNews" class="link2">最新</router-link>
    <router-link to="/hotNews" class="link3">热门</router-link>
    <router-link to="/firstNews" class="link4">置顶</router-link>
    <router-link to="/allType" class="link5">全部板块</router-link>
  </div>
  <!--中间部分-->
  <div class="divSecond">
    <el-table :data="tableData" class="el1" :show-header="false">
      <el-table-column align="center">
        <template #default="scope" class="tem1">
            <el-button type="text" class="el2" @click="intoTypeContent(scope.row.name)">{{scope.row.name}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import HeadOfHome from "../components/HeadOfHome";
import request from "../utils/request";
export default {
  name: "AllType",
  components: {HeadOfHome},
  data() {
    return {
      tableData: [

      ],

    }
  },
  methods: {
    //加载板块数据
    loadData() {
      request.post("/type/getAllType").then(res =>{
        this.tableData = res.data
      })
    },
    //跳转到具体板块
    intoTypeContent(name) {
      this.$router.push({
        path: '/typeContent',
        query: {
          typeName: name
        }
      })
    }
  },
  created() {
    this.loadData()
  }
}
</script>

<style scoped>
.divFirst{
  position: absolute;width: 100%;height: 45px;margin-top: 50.5px;background: white;box-shadow: 0 0 3px 0 #f3f3f3;
}
.link1{
  position: absolute;transform: translate(-50%,-50%);left: 20%;top: 50%;font-size: 17px;color:black;
}
.link2{
  position: absolute;transform: translate(-50%,-50%);left: 27%;top: 50%;color: black;font-size: 17px;
}
.link3{
  position: absolute;transform: translate(-50%,-50%);left: 34%;top: 50%;color: black;font-size: 17px;
}
.link4{
  position: absolute;transform: translate(-50%,-50%);left: 41%;top: 50%;color: black;font-size: 17px;
}
.link5{
  position: absolute;transform: translate(-50%,-50%);left: 50%;top: 50%;color:#409eff;font-size: 17px;
}
.divSecond{
  background: white;width: 60%;height: 400px;position: absolute;left: 20%;margin-top: 120px;box-shadow: 0 0 3px 3px #f3f3f3;
}
.el1{
  width: 100%;
}
.tem1{
  width: 100%;
}
.el2{
  color:black;font-size: 17px
}
</style>