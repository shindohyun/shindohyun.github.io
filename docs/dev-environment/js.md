---
layout: default
title: JavaScript
parent: Dev Environment
permalink: /docs/dev-environment/js
nav_order: 2
---

# JavaScript
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Utils

### ESLint
- 설치: 프레임워크에서 프로젝트 생성 시 옵션으로 제공
- 설정 파일명: `.eslintrc.*`

### Prettier
- 설치: 프레임워크에서 프로젝트 생성 시 옵션으로 제공
- 설정 파일명: `.prettierrc`
- VSCode에 Prettier - Code formatter 확장 프로그램 설치 후 VSCode Settings(`settings.json`)에도 Prettier 규칙을 설정할 수 있지만 로컬에 다른 설정 파일이 존재하는 경우 무시된다.
- 설정 파일 우선순위: `.prettierrc` > `.editorconfig` > `settings.json`

### eslint-config-prettier
- ESLint와 Prettier 규칙이 충돌하는 경우에 필요
- 설치: `npm install --save-dev eslint-config-prettier`
- `.eslintrc.*` 파일 수정

```json
{
  "extends": [
    "some-other-config-you-use",
    "prettier" // *extends 배열 마지막에 추가, Prettier와 충돌하는 규칙을 꺼버린다.
  ]
}
```

---

## VSCode

### 확장 프로그램 설치
- ESLint: *ESLint 확장 프로그램은 ESLint 라이브러리를 사용하기 때문에 로컬 또는 시스템 전역에 [ESLint 라이브러리 설치](#eslint) 필수*
- Prettier - Code formatter

### Editor 설정
- `settings.json` 파일에 아래 내용 추가

```json
{
  // 에디터의 기본 포맷으로 Prettier 포맷을 설정
  "editor.defaultFormatter": "esbenp.prettier-vscode",
  // 파일 저장 시 포맷에 맞게 수정하도록 설정
  "editor.formatOnSave": true
}
```
