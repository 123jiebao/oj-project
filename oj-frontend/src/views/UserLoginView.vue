<template>
  <div id="userLoginView">
    <h2>用户登陆</h2>
    <a-form
      style="max-width: 480px; margin: 0 auto; margin-top: 48px"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="账号">
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item field="userPassword" tooltip="密码不少于8位" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          html-type="submit"
          style="width: 100px; margin-left: 148px"
          >登录</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import {
  UserControllerService,
  UserLoginRequest,
} from "../../generated-user-service";
import { reactive } from "vue";
import Message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const router = useRouter();
const store = useStore();

const form = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginRequest);

const handleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form);
  if (res.code === 0) {
    if (res.data?.token) {
      localStorage.setItem("token", res.data.token);
    }
    await store.dispatch("user/getLoginUser", res.data);
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    Message.error("登陆失败 ,  " + res.message);
  }
};
</script>
