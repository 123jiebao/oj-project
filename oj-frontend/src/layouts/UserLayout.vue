<template>
  <div id="userLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img class="logo" src="../assets/logo.png" />
          <div>OJ</div>
        </a-space></a-layout-header
      >
      <a-layout-content class="content"><router-view /></a-layout-content>
      <a-layout-footer class="footer"
        ><a href="" target="_blank">OJ平台 by Lzy</a></a-layout-footer
      >
    </a-layout>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
#userLayout {
  text-align: center;
}
#userLayout .header {
  margin-top: 55px;
  margin-bottom: 16px;
}
#userLayout .logo {
  width: 64px;
  height: 64px;
}

#userLayout .content {
  margin-bottom: 16px;
  padding: 20px;
}

#userLayout .footer {
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
