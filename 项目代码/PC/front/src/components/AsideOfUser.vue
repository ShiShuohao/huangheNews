<template>

  <div class="divF3">
    <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose">
      <el-menu-item index="1" @click="$router.push('/userHome')">
        <el-icon><user /></el-icon>
        <span>个人信息</span>
      </el-menu-item>
      <el-menu-item index="2" @click="$router.push('/myComment')">
        <el-icon><data-line /></el-icon>
        <span>我的评论</span>
      </el-menu-item>
      <el-menu-item index="3" @click="$router.push('/myCollect')">
        <el-icon><collection-tag /></el-icon>
        <span>我的收藏</span>
      </el-menu-item>
      <el-menu-item index="4" @click="$router.push('/myFocus')">
        <el-icon><finished /></el-icon>
        <span>我的关注</span>
      </el-menu-item>
      <el-sub-menu index="5">
        <template #title>
          <el-icon><message-box /></el-icon>
          <span>我的信箱</span>
        </template>
        <el-menu-item index="5-1" class="menu" @click="$router.push('/myMessageOfNotRead')">未读私信</el-menu-item>
        <el-menu-item index="5-2" class="menu" @click="$router.push('/myMessageOfReaded')">已读私信</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="6" @click="$router.push('/search')">
        <el-icon><reading /></el-icon>
        <span>搜索记录</span>
      </el-menu-item>
      <el-menu-item index="7" @click="$router.push('/requestToAdmin')">
        <el-icon><key /></el-icon>
        <span>权限申请</span>
      </el-menu-item>
    </el-menu>
  </div>

</template>

<script>
import User from "@element-plus/icons/lib/User";
import DataLine from "@element-plus/icons/lib/DataLine";
import CollectionTag from "@element-plus/icons/lib/CollectionTag";
import Finished from "@element-plus/icons/lib/Finished";
import MessageBox from "@element-plus/icons/lib/MessageBox";
import Reading from "@element-plus/icons/lib/Reading";
import Key from "@element-plus/icons/lib/Key";
export default {
  name: "AsideOfUser",
  components: {
    DataLine,
    User,
    CollectionTag,
    Finished,
    MessageBox,
    Reading,
    Key
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
.divF3{
  position: absolute;height: calc(100% - 50px);width: 160px;margin-top: 50px;border-top: 1.5px #f0f0f0 solid;border-right: 1px #f0f0f0 solid;
}
.el-menu-vertical-demo{
  padding-top: 30px;border-right: none;
}
.menu{
  min-width: 160px;
}
</style>