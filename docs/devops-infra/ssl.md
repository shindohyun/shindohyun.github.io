---
layout: default
title: SSL
parent: DevOps·Infra
permalink: /docs/devops-infra/ssl
nav_order: 1
---

# SSL
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Let's Encrypt
SSL 인증서를 무료로 발급해주는 CA

### Setup (Amazon Linux2 기준)
1. EPEL 설치  
  a. `sudo yum update`  
  b. `sudo amazon-linux-extras install epel -y`

2. Certbot 설치  
  a. `sudo yum install certbot -y`

3. 인증서 생성 (Standalone)  
  a. `certbot certonly --standalone -d 도메인` ex) certbot certonly --standalone -d domain.com -d www.domain.com  
  b. 생성 위치: `/etc/letsencrypt/live`

### Standalone 특징
- 인증서 생성 시 도메인 와일드카드 지정 불가능
- 인증서 생성 시 웹 서버를 중단시키고 진행해야함

---

## Certbot
Let's Encrypt 인증서를 자동으로 발급 및 갱신해주는 프로그램

### 명령어
- 인증서 목록 조회: `certbot certificates`
- 인증서 삭제: `certbot delete`

---

## 도커 컨테이너에 SSL 인증서 볼륨 마운트하기

### 주의  
- /etc/letsencrypt/live 에는 실제 인증서 파일의 심볼릭 링크가 존재한다.
- 실제 인증서 파일은 /etc/letsencrypt/archive 에 위치한다.
- 위 두 디렉토리는 관리자 접근권한을 갖는다.
- 도커 볼륨은 심볼릭 링크 파일을 제대로 마운트할 수 없다. (빈 파일이 만들어진다.)

### 설정
1. 인증서 파일에 접근하기 위해 도커를 관리자 권한으로 실행시킨다.
2. /etc/letsencrypt 디렉토리 전체를 마운트시켜 심볼릭 링크(live에 위치)와 실제 인증서 파일(archive에 위치)에 모두 접근 가능하도록 한다.
3. 443 포트를 연다.
