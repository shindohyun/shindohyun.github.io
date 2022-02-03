---
layout: default
title: AWS
#parent: DevOps·Infra
#permalink: /docs/devops-infra/aws
#nav_order: 1
nav_exclude: true
search_exclude: true
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

## Lightsail
1. 인스턴스 생성
2. 고정 IP  
  a. 고정 IP 생성  
  b. 인스턴스에 할당
4. DNS zone  
[Route 53](#route-53)에서 도메인 등록이 선행되어야한다.  
  a. DNS zone 생성  
  b. 도메인 입력  
  c. 레코드 추가: 서브 도메인 생성과 고정 IP 할당  
  d. Name server를 Route 53에 등록
5. 포트 추가  
  a. 인스턴스에 443 포트 추가
