---
layout: default
title: Spring Boot
parent: Dev Environment
permalink: /docs/dev-environment/spring-boot
nav_order: 3
---

# Spring Boot
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## VSCode

### 확장 프로그램 설치
- Extension Pack for Java
- Spring Boot Extension Pack
- Gradle Extension Pack
- Lombok Annotations Support for VS Code

### JDK 경로 설정
`settings.json` 파일 수정  
'Extension Pack for Java' 확장 프로그램이 설치되어 있어야 아래 옵션을 사용할 수 있다.

```json
{
    // 자바 언어 서버 실행 환경
    // * Java11 이상 필요
    "java.jdt.ls.java.home": "<JDK home path>",

    // 프로젝트 실행 환경
    // build.gradle 설정에 따라 런타임 시 적절한 구성을 선택
    "java.configuration.runtimes": [
        {
          "name": "JavaSE-1.8",
          "path": "<JDK home path>",
          "default": true
        },
        {
          "name": "JavaSE-11",
          "path": "<JDK home path>"
        }
    ]
}
```

---

## Dependencies
```gradle
// Spring Web에 내장되어 있는 로깅 모듈(logBack)을 사용하지 않기 위해 의존성을 제거
configurations {
  all {
    exclude group: 'org.springframework.boot', module: 'spring-boot-starter-logging'
  }
}

dependencies {
  // Spring Web: Spring MVC, REST 및 내장되어 있는 Tomcat 서버의 사용 등 웹 개발과 관련된 모든 종속성을 가져온다.
  implementation 'org.springframework.boot:spring-boot-starter-web'
  // Spring Boot DevTools: 변경 사항에 따른 재시작 및 업데이트를 등 개발 시간 개선을 위한 개발자 도구
  developmentOnly 'org.springframework.boot:spring-boot-devtools'
  // Validation
  implementation 'org.springframework.boot:spring-boot-starter-validation'
  // MySQL Driver: Java 어플리케이션과 MySQL을 연결
  runtimeOnly 'mysql:mysql-connector-java'
  // MyBatis Framework
  implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2'
  // Spring Security
  implementation 'org.springframework.boot:spring-boot-starter-security'
  testImplementation 'org.springframework.security:spring-security-test'
  // JWT: JWT 사용을 위함
  implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
  // Log4j2
  implementation group: 'org.springframework.boot', name: 'spring-boot-starter-log4j2', version: '2.6.3'
  // Log4jdbc-Log4j2: SQL 로그를 보기 위해서 필요
  implementation group: 'org.bgee.log4jdbc-log4j2', name: 'log4jdbc-log4j2-jdbc4.1', version: '1.16'

  testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```