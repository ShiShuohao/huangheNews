<template>

  <!--头部-->
  <HeadOfUser/>

  <!--侧边栏-->
  <AsideOfUser/>

  <!--路由页面-->
  <div class="dT">
    <router-view/>
  </div>

</template>

<script>
import HeadOfUser from "../components/HeadOfUser";
import AsideOfUser from "../components/AsideOfUser";
export default {
  name: "UserHome",
  components: {AsideOfUser, HeadOfUser},
  created() {
    document.body.style.overflow = 'hidden';
  }
}
</script>

<style scoped>
.dT{
  background: none;position: absolute;height: calc(100% - 50px);width: calc(100% - 160px);margin-top: 50px;margin-left: 160px;padding: 0;
}
</style>