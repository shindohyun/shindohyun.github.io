---
layout: default
title: Java
parent: Dev Environment
permalink: /docs/dev-environment/java
nav_order: 1
---

# Java
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Mac

### [Temurin (Adoptium OpenJDK)](https://adoptium.net/) 설치
- 설치된 JDK 목록 확인: `/usr/libexec/java_home -V`


### 시스템 환경변수 설정 (Java 버전 변경)
1. 사용중인 쉘 확인: `echo $SHELL`
2. 쉘에 맞는 환경변수 파일 수정 (없는 경우 생성): `/bin/zsh`을 사용하는 경우 `~/.zshrc`

```shell
JAVA_HOME=<JDK home path>
PATH=$PATH:$JAVA_HOME/bin

export JAVA_HOME
export PATH
```

3. 환경변수 적용: `source <환경변수 파일>`
4. Java 버전 확인: `java -version`
5. JAVA_HOME 확인: `echo $JAVA_HOME`