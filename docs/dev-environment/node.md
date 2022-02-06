---
layout: default
title: Node
parent: Dev Environment
permalink: /docs/dev-environment/node
nav_order: 2
---

# Node
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Setup on Mac

### [NVM (Node Version Manager)](https://github.com/nvm-sh/nvm) 설치
1. 환경변수 파일에 nvm 경로가 추가되었는지 확인: `/bin/zsh`을 사용하는 경우 `~/.zshrc`
2. 환경변수 적용: `source <환경변수 파일>`

### NVM 명령어
- nvm 버전 확인: `nvm -v`
- 설치 가능한 node 버전 확인: `nvm ls-remote`
- 설치된 node 버전 확인: `nvm ls`
- 최신 버전 node 설치: `nvm install node`
- 특정 버전 node 설치: `nvm install <version>` ex) nvm install v16.13.2
- 특정 버전 node 사용: `nvm use <version>`
- 특정 버전 node 제거: `nvm uninstall <version>`

---

## Node 명령어
- node 버전 확인: `node -v`

---

## NPM 명령어
- install 옵션 `-g`: 시스템 전역 설치
- install 옵션 `--save`: 현재 위치 설치
- 설치 모듈 목록 보기: `npm -g ls`
- 설치된 모듈 삭제: `npm uninstall <module name>`
- nvm 사용시 설치된 모듈 삭제: `~/.nvm/versions/node/<version directory>/lib/node_modules` 아래에 module directory 삭제
