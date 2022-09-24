---
layout: default
title: Flutter
parent: Dev Environment
permalink: /docs/dev-environment/flutter
nav_order: 5
---

# Flutter
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Setup on Mac

### Flutter SDK Path 설정
1. 사용중인 쉘 확인: `echo $SHELL`
2. 쉘에 맞는 환경변수 파일 수정 (없는 경우 생성): `/bin/zsh`을 사용하는 경우 `~/.zshrc`
```shell
export PATH=$PATH:<Flutter SDK Path>/bin
```
3. 환경변수 적용: `source <환경변수 파일>`
4. PATH 확인: `echo $PATH`
5. flutter 명령어 유효성 확인: `which flutter`