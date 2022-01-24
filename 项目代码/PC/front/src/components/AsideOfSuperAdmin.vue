<template>

  <div class="divF2">
    <el-menu
        default-active="1-1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose">
      <el-sub-menu index="1">
        <template #title>
          <el-icon><user /></el-icon>
          <span>权限管理</span>
        </template>
        <el-menu-item index="1-1" class="menu" @click="$router.push('/superAdminHome')">管理员列表</el-menu-item>
        <el-menu-item index="1-2" class="menu" @click="$router.push('/adminRequest')">管理员申请</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>

</template>

<script>
import User from "@element-plus/icons/lib/User";

export default {
  name: "AsideOfSuperAdmin",
  components: {
    User,
  },
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
.divF2{
  position: absolute;height: calc(100% - 50px);width: 160px;margin-top: 50px;border-top: 1.5px #f0f0f0 solid;border-right: 1px #f0f0f0 solid;
}
.el-menu-vertical-demo{
  padding-top: 30px;border-right: none;
}
.menu{
  min-width: 160px;
}
</style>