---
layout: default
title: JQuery
parent: JavaScript
permalink: /docs/javascript/jquery
nav_order: 1
---

# JQuery
{: .no_toc }

---

### 화면 출력 후 호출되는 함수
1. `$(documnet).ready()` 과 `$(function())`
   
   - DOM Tree 생성 직후 호출 (`$(window).load()` 보다 빨리 호출됨)
   - 중복 사용 가능 (선언 순서대로 실행)
1. `$(window).load()`

   - Display 이후 호출

   - 참고: JavaScript `window.onload`와 동일하다는 블로그 글을 보고 테스트를 했지만 다른 결과를 보여 정리해봤다. 함께 사용될 일이 없겠지만...그냥 궁금해서...

     |                              | JQuery `$(window).load()` | JavaScript `window.onload`             |
     | ---------------------------- | ------------------------- | -------------------------------------- |
     | 중복사용                     | 가능                      | 불가능 (마지막으로 선언된 함수만 실행) |
     | `<body onload=>`와 함께 사용 | 가능                      | 불가능 (실행되지 않음)                 |
     | 함께 사용 시 호출 순서       | 2                         | 1                                      |

     

