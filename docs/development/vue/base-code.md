---
layout: default
title: Base Code
grand_parent: Development
parent: Vue
permalink: /docs/development/vue/base-code
nav_order: 999
---

# Base Code
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Vue3

```
<template>
  <div></div>
</template>
<script>
import { onMounted, onUnmounted } from 'vue'

export default {
  name: '',
  components: {},
  setup() {
    onMounted(() => {
      // 컴포넌트 인스턴트가 마운트된 후 호출
      // 화면 내용이 랜더링된 후에 호출
      // tip. 화면 로딩 이후에 출력되어도 되는 데이터 또는 HTML 객체 부분을 획득하는 구간으로 사용
    })
    onUnmounted(() => {
      // 컴포넌트 인스턴트가 마운트 해제된 후 호출
    })
  },
  created() {
    // 컴포넌트 인스턴스가 생성된 후 호출
    // tip. 해당 컴포넌트에서 가장 먼저 보여줘야 하는 데이터를 획득하는 구간으로 사용
    // Composition API 에서 beforeCreate, created hook을 지원하지 않음
  }
}
</script>

```

---

## Vue2

```
<template>
  <div></div>
</template>
<script>
export default {
  name: '',
  components: {},
  created() {
    // 컴포넌트 인스턴스가 생성된 후 호출
    // tip. 해당 컴포넌트에서 가장 먼저 보여줘야 하는 데이터를 획득하는 구간으로 사용
  },
  mounted() {
    // 컴포넌트 인스턴트가 마운트된 후 호출
    // 화면 내용이 랜더링된 후에 호출
    // tip. 화면 로딩 이후에 출력되어도 되는 데이터 또는 HTML 객체 부분을 획득하는 구간으로 사용
  },
  unmounted() {
    // 컴포넌트 인스턴트가 마운트 해제된 후 호출
  }
}
</script>

```

---

## VSCode Vue Base Code Snippets 설정 방법
`Code > Preference > User Snippets > vue.json`

```
{
  "Generate Vue Base Code": {
    "prefix": "vue-base",
    "body": [
      "<base code>"
    ],
    "description": "Generate Vue Base Code"
  }
}

```
