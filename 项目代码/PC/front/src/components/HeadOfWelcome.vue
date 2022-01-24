<template>

  <div class="divF7">
    <img src="../assets/photo/logo.png" alt="" class="img1">
    <router-link to="/" class="link1">首页</router-link>
    <router-link to="/about" class="link2">关于本站</router-link>
    <router-link to="/adminLogin" class="link3">控制台</router-link>
  </div>

</template>

<script>
export default {
  name: "HeadOfWelcome"
}
</script>

<style scoped>
.divF7{
  background: white;height: 50px;width: 100%;border-bottom: 1.5px #f0f0f0 solid;position: absolute;margin-top: 0;
}
.img1{
  position: absolute;height: 65%;top: 50%;transform: translate(-50%,-50%);left: 50%;
}
.link1{
  text-decoration: none;position: absolute;left: 75%;top: 50%;transform: translate(-50%,-50%);color: black;font-size: 15px;
}
.link2{
  text-decoration: none;position: absolute;left: 82%;top: 50%;transform: translate(-50%,-50%);color: black;font-size: 15px;
}
.link3{
  text-decoration: none;position: absolute;left: 89%;top: 50%;transform: translate(-50%,-50%);color: black;font-size: 15px;
}
</style>