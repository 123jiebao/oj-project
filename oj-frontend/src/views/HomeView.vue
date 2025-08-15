<template>
  <div id="basicLayout">
    <CodeEditor :value="codevalue" :handleChange="onCodeChange" />
    <MdEditor :value="mdvalue" :handleChange="onMdChange" />
  </div>
</template>

<script setup lang="ts">
import CodeEditor from "@/components/CodeEditor.vue";
import MdEditor from "@/components/MdEditor.vue";
import { ref } from "vue";

const mdvalue = ref();
const codevalue = ref();
const onMdChange = (v: string) => {
  mdvalue.value = v;
};
const onCodeChange = (v: string) => {
  codevalue.value = v;
  console.log(v);
};
</script>

<style scoped></style>
