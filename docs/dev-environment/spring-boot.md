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