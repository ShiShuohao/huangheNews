<template>

  <!--管理员页面头部-->
  <HeadOfAdmin/>

  <!--管理员侧边栏-->
  <AsideOfAdmin/>

  <!--路由页面-->
  <div class="div1">
    <router-view/>
  </div>

</template>

<script>
import HeadOfAdmin from "../components/HeadOfAdmin";
import AsideOfAdmin from "../components/AsideOfAdmin";
export default {
  name: "AdminHome",
  components: {AsideOfAdmin, HeadOfAdmin},
  created() {
    document.body.style.overflow = 'hidden';
  }
}
</script>

<style scoped>
.div1{
  background: none;position: absolute;height: calc(100% - 50px);width: calc(100% - 160px);margin-top: 50px;margin-left: 160px;padding: 0;
}
</style>