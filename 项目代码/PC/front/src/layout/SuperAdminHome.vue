<template>

  <!--管理员页面头部-->
  <HeadOfAdmin/>

  <!--超级管理员的侧边栏-->
  <AsideOfSuperAdmin/>

  <!--路由页面-->
  <div class="dF">
    <router-view/>
  </div>

</template>

<script>
import HeadOfAdmin from "../components/HeadOfAdmin";
import AsideOfSuperAdmin from "../components/AsideOfSuperAdmin";
export default {
  name: "SuperAdminHome",
  components: {AsideOfSuperAdmin, HeadOfAdmin},
  created() {
    document.body.style.overflow = 'hidden';
  }
}
</script>

<style scoped>
.dF{
  background: none;position: absolute;height: calc(100% - 50px);width: calc(100% - 160px);margin-top: 50px;margin-left: 160px;padding: 0;
}
</style>