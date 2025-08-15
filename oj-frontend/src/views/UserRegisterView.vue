<template>
  <div id="userLoginView">
    <h2>用户注册</h2>
    <a-form
      style="max-width: 480px; margin: 0 auto; margin-top: 48px"
      ref="formRef"
      :rules="rules"
      :model="form"
      :style="{ width: '600px' }"
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="账号" validate-trigger="blur">
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item field="userPassword" label="密码" validate-trigger="blur">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <a-form-item
        field="checkPassword"
        label="确认密码"
        validate-trigger="blur"
      >
        <a-input-password
          v-model="form.checkPassword"
          placeholder="请确认密码"
        />
      </a-form-item>
      <a-form-item style="width: 100px; margin-left: 178px">
        <a-space>
          <a-button type="primary" html-type="submit">提交</a-button>
          <a-button ref="formRef" type="primary" @click="formRef.resetFields()"
            >重置</a-button
          >
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import {
  UserControllerService,
  UserRegisterRequest,
} from "../../generated-user-service";
import { reactive } from "vue";
import router from "@/router";
import { Message } from "@arco-design/web-vue";
import { ref, Ref } from "vue";

const formRef: Ref = ref(null);

const handleSubmit = async () => {
  const res = await UserControllerService.userRegisterUsingPost(form);
  if (res.code === 0) {
    Message.success("注册成功 , " + res.message);
    router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    Message.error("登陆失败 ,  " + res.message);
  }
};

const form = reactive({
  checkPassword: "",
  userAccount: "",
  userPassword: "",
} as UserRegisterRequest);

const rules = {
  userAccount: [
    {
      required: true,
      message: "userAccount is required",
    },
  ],
  userPassword: [
    {
      required: true,
      message: "userPassword is required",
    },
  ],
  checkPassword: [
    {
      required: true,
      message: "checkPassword is required",
    },
    {
      validator: (value: string, cb: (error?: string) => void) => {
        if (value !== form.userPassword) {
          cb("两次密码不一致");
        } else {
          cb();
        }
      },
    },
  ],
};
</script>
