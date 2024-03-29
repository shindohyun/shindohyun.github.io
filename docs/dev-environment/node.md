---
layout: default
title: Node
parent: Dev Environment
permalink: /docs/dev-environment/node
nav_order: 3
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
- default node 지정: `nvm alias default <version>` ex) nvm alias default v16.13.2

---

## Node 명령어
- node 버전 확인: `node -v`

---

## NPM 명령어
- `npm install <package>`: 패키지 설치 (기본적으로 현재 위치)
- `npm install -g <package>`: 시스템 전역에 패키지 설치
- `npm ls`: 설치된 패키지 목록 확인 (기본적으로 현재 위치)
- `npm -g ls`: 시스템 전역에 설치된 패키지 목록 확인
- `npm uninstall <package>`: 패키지 삭제
