# 项目文档

## 项目概述

这是一个基于 Vue 3 的前端项目，用于构建在线评判系统（OJ）的前端界面。项目使用了 Vue CLI 进行初始化，
并集成了多种 Vue 生态中的插件和工具，如 Vue Router、Vuex、TypeScript、ESLint 等，以提供高效的开发体验和代码质量保证。

## 目录结构

项目的目录结构如下：

```
├── .eslintrc.js
├── babel.config.js
├── package.json
├── public
├── README.md
├── src
│   ├── App.vue
│   ├── assets
│   ├── components
│   │   ├── GlobalHeader.vue
│   │   └── globalHeader.vue
│   ├── layouts
│   │   └── BasicLayout.vue
│   ├── main.ts
│   ├── router
│   │   ├── index.ts
│   │   └── routes.ts
│   ├── shims-vue.d.ts
│   ├── store
│   │   ├── index.ts
│   │   └── user.ts
│   ├── views
│   │   ├── AboutView.vue
│   │   ├── AdminView.vue
│   │   ├── BanView.vue
│   │   └── HomeView.vue
│   └── access
│       ├── accessEnum.ts
│       └── checkAccess.ts
├── tsconfig.json
└── vue.config.js
```

## 核心模块和功能

### Vue CLI

项目使用 Vue CLI 进行初始化，提供了项目脚手架和开发服务。

### Vue Router

Vue Router 用于管理页面路由和导航。路由配置文件位于 `src/router/routes.ts`。

### Vuex

Vuex 用于状态管理。状态管理相关的代码位于 `src/store` 目录。

### TypeScript

项目使用 TypeScript 提供静态类型检查，增强代码的可维护性。

### ESLint + Prettier

ESLint 和 Prettier 用于代码质量和格式化，配置文件位于项目根目录。

### Arco Design Vue

Arco Design Vue 是项目使用的 UI 组件库，位于 `node_modules/@arco-design/web-vue`。

## 组件说明

### GlobalHeader

`GlobalHeader.vue` 是全局头部组件，包含导航菜单和用户信息。

### BasicLayout

`BasicLayout.vue` 是基本布局组件，包含头部、内容和底部。

### HomeView

`HomeView.vue` 是首页组件。

### AboutView

`AboutView.vue` 是关于页面组件。

### AdminView

`AdminView.vue` 是管理员页面组件。

### BanView

`BanView.vue` 是禁止访问页面组件。

## 路由配置

路由配置文件位于 `src/router/routes.ts`，定义了项目的路由信息。

## 状态管理

状态管理相关的代码位于 `src/store` 目录，包括 `index.ts` 和 `user.ts`。

## 权限控制

权限控制相关的枚举和检查函数位于 `src/access` 目录。
