---
layout: default
title: AWS
parent: DevOps·Infra
permalink: /docs/devops-infra/aws
nav_order: 1
#nav_exclude: true
#search_exclude: true
---

# AWS
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Route 53
1. 도메인 등록
2. Name server 등록

---

## Lightsail
1. 인스턴스 생성
2. 고정 IP  
  a. 고정 IP 생성  
  b. 인스턴스에 할당
3. DNS zone  
  [Route 53](#route-53)에서 도메인 등록이 선행되어야한다.  
  a. DNS zone 생성  
  b. 도메인 입력  
  c. 레코드 추가: 서브 도메인 생성과 고정 IP 할당  
  d. Name server를 Route 53에 등록
4. 포트 추가  
  a. 인스턴스에 443 포트 추가
5. 인스턴스 실행 및 리눅스 설정  
  a. 리눅스 버전 확인: `cat /etc/issue`  
  b. root 패스워드 설정: `sudo passwd root`

---

## Amazon Linux 2

### docker-compose install
1. `sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose`
2. `sudo chmod +x /usr/local/bin/docker-compose`
3. `docker-compose version`
