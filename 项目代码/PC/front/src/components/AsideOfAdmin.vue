<template>
  <div class="divF1">
    <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose">
      <el-menu-item index="1" @click="$router.push('/adminHome')">
        <el-icon><user /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><document /></el-icon>
          <span>新闻管理</span>
        </template>
        <el-menu-item index="2-1" class="menu" @click="$router.push('/newsListForNotFirst')">新闻列表</el-menu-item>
        <el-menu-item index="2-2" class="menu" @click="$router.push('/newsListForFirst')">置顶列表</el-menu-item>
        <el-menu-item index="2-3" class="menu" @click="$router.push('/publishNews')">新闻发布</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="3">
        <template #title>
          <el-icon><collection-tag /></el-icon>
          <span>板块管理</span>
        </template>
        <el-menu-item index="3-1" class="menu" @click="$router.push('/fixedType')">固定板块</el-menu-item>
        <el-menu-item index="3-2" class="menu" @click="$router.push('/freeType')">可变板块</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="4" @click="$router.push('/broadcastManage')">
        <el-icon><bell /></el-icon>
        <span>系统通知</span>
      </el-menu-item>
      <el-sub-menu index="5">
        <template #title>
          <el-icon><notebook /></el-icon>
          <span>日志管理</span>
        </template>
        <el-menu-item index="5-1" class="menu" @click="$router.push('/logList')">日志列表</el-menu-item>
        <el-menu-item index="5-2" class="menu" @click="$router.push('/publishLog')">日志发布</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>

</template>

<script>
import User from "@element-plus/icons/lib/User";
import CollectionTag from "@element-plus/icons/lib/CollectionTag";
import Notebook from "@element-plus/icons/lib/Notebook";
import Bell from "@element-plus/icons/lib/Bell";
import Document from "@element-plus/icons/lib/Document";

export default {
  name: "AsideOfAdmin",
  //引入element-plus图标
  components: {
    CollectionTag,
    Notebook,
    User,
    Document,
    Bell,
  },
  //element-plus已经写好的
  setup() {
    const handleOpen = (key, keyPath) => {
      console.log(key, keyPath)
    }
    const handleClose = (key, keyPath) => {
      console.log(key, keyPath)
    }
    return {
      handleOpen,
      handleClose,
    }
  },
}
</script>

<style scoped>
.divF1{
  position: absolute;height: calc(100% - 50px);width: 160px;margin-top: 50px;border-top: 1.5px #f0f0f0 solid;border-right: 1px #f0f0f0 solid;
}
.el-menu-vertical-demo{
  padding-top: 30px;border-right: none;
}
.menu{
  min-width: 160px;
}
</style>