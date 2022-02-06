---
layout: default
title: Lazy Load
grand_parent: Development
parent: Vue
permalink: /docs/development/vue/lazy-load
nav_order: 1
---

# Lazy Load
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

Vue는 빌드 후 모든 소스 코드가 하나의 파일로 생성되고, 첫 접속 시 파일에 포함된 리소스 및 컴포넌트가 전부 로드된다.

특정 컴포넌트를 지연로드로 지정하는 경우 path로 접근했을 때 해당 컴포넌트(Async Component)를 로드시킨다.  
Async Component는 코드가 분리되어 Chunk 파일로 생성된다.

Chunk 파일의 이름을 지정할 수 있다. (같은 이름으로 설정한 모든 컴포넌트는 해당 파일로 함께 묶인다.)

```javascript
// router/index.js
component: () => import(/* webpackChunkName: "about" */ 'vue 파일명')
```

Vue CLI에서는 **prefetch** 기능을 제공한다. 이 기능은 Async Component와 같이 **미래에 사용될 리소스를 미리 캐시에 올려둠으로써 빠른 화면 전환을 가능하게 한다.** 기본 설정값은 `true`(사용)이다.

prefetch 기능에 의해 캐시에 리소스가 저장될 때 리소스 각각에 대해 request가 발생한다. 또한 미래에 사용될 모든 리소스를 내려 받은 후 첫 화면에 사용되는 리소스를 내려 받는다.

따라서 기본적으로 prefetch 기능을 꺼두고 prefetch 기능이 필요한 지연로드 컴포넌트에만 선택적으로 사용하는 것이 좋다.

```javascript
// vue.config.js
module.exports = {
  chainWebpack: config => {
    config.plugins.delete('prefetch')
  }
}
```

```javascript
// router/index.js
import(/* webpackPrefetch: true */ 'vue 파일명')
```

- 일반적: 사용자의 접속 빈도가 높은 컴포넌트
- 지연로드: 사용자의 접속 빈도가 낮은 컴포넌트
- 지연로드 prefetch: 지연로드 컴포넌트 중에서 사용하는 리소스가 큰 컴포넌트
