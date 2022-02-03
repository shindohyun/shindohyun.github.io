---
layout: default
title: Integer.parseInt(String s, int radix)
parent: Java
permalink: /docs/java/integer-parseint
nav_order: 15
---

# Integer.parseInt(String s, int radix)
{: .no_toc }

---

두 번째 인수로 지정된 기수로 문자열 인수를 인식하여 부호 있는 10진수 정수로 변환한다.

```java
String value = ;
System.out.println(Integer.parseInt("1000", 2)); // 2진수 문자열을 10진수로, 8
System.out.println(Integer.parseInt("1000", 8)); // 8진수 문자열을 10진수로, 512
System.out.println(Integer.parseInt("1000", 16)); // 16진수 문자열을 10진수로, 4096
System.out.println(Integer.parseInt("1000", 32)); // 32진수 문자열을 10진수로, 32768
```

