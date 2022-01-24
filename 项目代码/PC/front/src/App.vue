<template>

  <!-- vue项目启动时默认入口是App.vue,为了实现不同的页面跳转，-->
  <!-- 需要将每个页面写成一个layout文件，-->
  <!-- 通过路由来改变App.vue中的router-view的显示内容来实现-->

  <!-- 如果一个layout中需要实现大部分内容不变，只有部分内容改变，-->
  <!-- 则需要在这个layout中加上一个router-view，通过嵌套路由的方式，使该router-view显示不同的views-->

  <router-view/>

</template>
