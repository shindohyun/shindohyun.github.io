---
layout: default
title: JNI (Java Native Interface)
grand_parent: Development
parent: Java
permalink: /docs/development/java/jni
nav_order: 10
---

# JNI (Java Native Interface)
{: .no_toc }

---

JVM 위에서 실행되는 자바코드가 운영체제의 네이티브 응용프로그램이나 C, C++과 같은 라이브러리들을 호출하거나 반대로 호출되는 것을 가능하게 하는 프로그래밍 프레임워크이다.

자바 단에서는 `native`키워드를 사용한 인터페이스 선언부만 존재하고 이는 다른 언어(주로 C, C++)로 구현된 라이브러리 함수를 호출하게 된다. 한 가지 예로, `Object` 클래스의 `hashCode()`메소드는 다음과 같이 정의되어있다.

```java
public native int hashCode(); //메모리 주소값으로 해쉬 값을 생성하는 네이티브 코드를 호출한다.
```

참고) 위와 같이 정의된 함수를 native 메소드라고 하고 native 메소드에서 참조되는 상수 앞에 `@Native` 애너테이션을 붙인다.



TODO: native 메소드 구현해보기