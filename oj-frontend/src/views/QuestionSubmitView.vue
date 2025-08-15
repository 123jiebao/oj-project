<template>
  <div id="questionSubmitView">
    <a-form :model="searchParams" layout="inline">
      <a-form-item field="questionTittle" label="题目" style="min-width: 240px">
        <a-input
          v-model="searchParams.questionTittle"
          placeholder="请输入题目"
        />
      </a-form-item>
      <a-form-item field="language" label="编程语言" style="min-width: 240px">
        <a-select
          v-model="searchParams.language"
          :style="{ width: '320px' }"
          placeholder="选择编程语言"
        >
          <!-- <a-option>不限</a-option> -->
          <a-option>java</a-option>
          <a-option>c++</a-option>
          <a-option>go</a-option>
          <a-option>html</a-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSubmit">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-divider size="0" />
    <a-table
      :ref="tableRef"
      :columns="columns"
      :data="dataList"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      @page-change="onPageChange"
    >
      <template #judgeInfo="{ record }">
        <div v-if="record.judgeInfo" style="line-height: 1.6">
          <!-- 消息 -->
          <div><strong>状态:</strong> {{ record.judgeInfo.message }}</div>
          <!-- 内存（转换为 MB） -->
          <div>
            <strong>内存:</strong>
            {{
              record.judgeInfo.memory
                ? (Number(record.judgeInfo.memory) / (1024 * 1024)).toFixed(2) +
                  " MB"
                : "-"
            }}
          </div>
          <!-- 时间 -->
          <div>
            <strong>时间:</strong>
            {{ record.judgeInfo.time ? record.judgeInfo.time + " ms" : "-" }}
          </div>
        </div>
        <span v-else>无</span>
      </template>
      <template #createTime="{ record }">
        {{ moment(record.createTime).format("YYYY-MM-DD") }}
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";
import {
  QuestionControllerService,
  QuestionSubmitQueryRequest,
} from "../../generated-question-service";
import message from "@arco-design/web-vue/es/message";
import moment from "moment";

const tableRef = ref();

const dataList = ref([]);
const total = ref(0);
const searchParams = ref<QuestionSubmitQueryRequest>({
  questionTittle: undefined,
  language: undefined,
  current: 1,
  pageSize: 4,
});

const loading = ref(false); // 新增 loading 状态

const loadData = async () => {
  loading.value = true;
  try {
    const res =
      await QuestionControllerService.listQuestionSubmitVoByPageUsingPost({
        ...searchParams.value,
        sortField: "createTime",
        sortOrder: "desc",
      });
    if (res.code === 0) {
      dataList.value = res.data.records;
      total.value = res.data.total;
    } else {
      message.error("加载失败，" + res.message);
    }
  } catch (err) {
    // 可选：静默失败或简单提示
  } finally {
    loading.value = false;
  }
};

// --- 新增：自动刷新逻辑 ---
// eslint-disable-next-line no-undef
let refreshTimer: number | null = null;

// 启动定时刷新
const startAutoRefresh = () => {
  refreshTimer = window.setInterval(() => {
    loadData();
  }, 10000);
};

// 组件挂载后启动自动刷新
onMounted(() => {
  loadData();
  if (!refreshTimer) {
    startAutoRefresh();
  }
});

// 组件卸载时清除定时器
onUnmounted(() => {
  if (refreshTimer) {
    clearInterval(refreshTimer);
  }
});
// --- 新增结束 ---

const columns = [
  {
    title: "提交号",
    dataIndex: "id",
  },
  {
    title: "编程语言",
    dataIndex: "language",
  },
  {
    title: "判题信息",
    slotName: "judgeInfo",
  },
  {
    title: "判题状态",
    dataIndex: "status",
  },
  {
    title: "题目",
    dataIndex: "questionTittle",
  },
  {
    title: "用户 id",
    dataIndex: "userId",
  },
  {
    title: "创建时间",
    slotName: "createTime",
  },
];

const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
  loadData();
};

/**
 * 确认搜索，重新加载数据
 */
const doSubmit = () => {
  searchParams.value = {
    ...searchParams.value,
    current: 1,
  };
  // 显式调用加载
  loadData();
};
</script>

<style scoped>
#questionSubmitView {
  max-width: 1280px;
  margin: 0 auto;
}
</style>
