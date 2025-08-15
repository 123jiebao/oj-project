import ACCESS_ENUM from "@/access/accessEnum";
import { RouteRecordRaw } from "vue-router";
export const routes: Array<RouteRecordRaw> = [
  {
    path: "/user",
    name: "用户",
    component: () => import("@/layouts/UserLayout.vue"),
    children: [
      {
        path: "/user/login",
        name: "用户登录",
        component: () => import("@/views/UserLoginView.vue"),
      },
      {
        path: "/user/register",
        name: "用户注册",
        component: () => import("@/views/UserRegisterView.vue"),
      },
    ],
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/",
    name: "主页",
    meta: {
      hideInMenu: true,
    },
    redirect: "/questions",
  },
  {
    path: "/questions",
    name: "浏览题目",
    component: () => import("@/views/QuestionsView.vue"),
  },
  {
    path: "/question_submit",
    name: "浏览题目提交",
    component: () => import("@/views/QuestionSubmitView.vue"),
  },
  {
    path: "/view/question/:id",
    name: "在线做题",
    component: () => import("@/views/ViewQuestionView.vue"),
    props: true,
    meta: {
      access: ACCESS_ENUM.USER,
      hideInMenu: true,
    },
  },
  {
    path: "/add/question",
    name: "创建题目",
    component: () => import("@/views/AddQuestionView.vue"),
    meta: {
      access: ACCESS_ENUM.USER,
    },
  },
  {
    path: "/update/question",
    name: "更新题目",
    component: () => import("@/views/AddQuestionView.vue"),
    meta: {
      access: ACCESS_ENUM.USER,
      hideInMenu: true,
    },
  },
  {
    path: "/manage/question",
    name: "管理题目",
    component: () => import("@/views/ManageQuestionView.vue"),
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/ban",
    name: "Ban",
    component: () => import("@/views/BanView.vue"),
    meta: {
      hideInMenu: true,
    },
  },
];
