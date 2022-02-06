---
layout: default
title: v-if vs v-show
grand_parent: Development
parent: Vue
permalink: /docs/development/vue/vif-vshow
nav_order: 2
---

# v-if vs v-show
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## v-if
조건에 따라 element를 생성, 삭제한다. 화면에 보이지 않을 때 element 자체가 존재하지 않는다.  
toggle 발생 시 많은 자원을 사용한다. toggle 빈도가 높은 경우 `v-show`를 사용하는 것이 좋다.

---

## v-show
조건에 상관 없이 element가 생성되고 조건에 따라 출력, 숨김 처리된다. 화면에 보이지 않을 때에도 element는 `display: none;` 형태로 존재한다.