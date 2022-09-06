---
layout: default
title: 화면 출력 후 호출되는 함수
grand_parent: Development
parent: jQuery
permalink: /docs/development/jquery/load-function
nav_order: 1
---

# 화면 출력 후 호출되는 함수
{: .no_toc }

---

1. `$(documnet).ready()` 과 `$(function())`
   
   - DOM Tree 생성 직후 호출 (`$(window).load()` 보다 빨리 호출됨)
   - 중복 사용 가능 (선언 순서대로 실행)
1. `$(window).load()`

   - Display 이후 호출

   - 참고: JavaScript `window.onload`와 비교

     |                              | JQuery `$(window).load()` | JavaScript `window.onload`             |
     | ---------------------------- | ------------------------- | -------------------------------------- |
     | 중복사용                     | 가능                      | 불가능 (마지막으로 선언된 함수만 실행) |
     | `<body onload=>`와 함께 사용 | 가능                      | 불가능 (실행되지 않음)                 |
     | 함께 사용 시 호출 순서       | 2                         | 1                                      |

     

