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

## EC2
1. 인스턴스 생성  
  a. 키 페어 생성: RSA, .pem 선택 (*생성된 .pem 파일은 로컬에 보관*)  
  b. 네트워크 설정 > 보안 그룹 생성: SSH, HTTP, HTTPS 허용 (IP: 0.0.0.0/0)
2. 탄력적 IP  
  a. 탄력적 IP 주소 생성  
  b. 탄력적 IP 주소 인스턴스에 연결

### 카페24 도메인을 Route 53 에 호스팅
1. 호스팅 영역 생성  
  a. 도메인 이름에 카페24에서 등록한 도메인 입력  
2. A유형 레코드 생성  
  a. 레코드 이름: 비움  
  b. 값: EC2 인스턴스 퍼블릭 IPv4 주소  
3. CNAME유형 레코드 생성  
  a. 레코드 이름: www  
  b. 값: 도메인 이름
4. 카페24 도메인 정보변경 > 네임서버 변경  
  a. 다른 네임서버 선택  
  b. Route 53 호스팅 영역의 NS유형 레코드 값을 모두 입력

### 인스턴스에 SSH 연결
`ssh -i "<private key file>" <user account>@<public DNS>`  
- private key file: 키 페어 생성 시 만들어진 .pem 파일
- public DNS: 인스턴스에 할당되어 있는 퍼블릭 DNS 또는 별도로 연결한 DNS 주소
- user account: 기본 계정 `ec2-user`

### root 계정 활성화 (Amazon Linux 2 기준)
1. root 패스워드 설정
2. `sudo vi /etc/ssh/sshd_config` > 'PermitRootLogin yes' 주석 제거
3. `sudo mkdir /root/.ssh`
4. `sudo cp /home/ec2-user/.ssh/authorized_keys /root/.ssh`
5. `sudo systemctl restart sshd`

### 인스턴스에 SFTP 연결 (FileZilla 이용)
1. 사이트 관리자 > 새 사이트 > 일반
2. 설정  
  a. 프로토콜: SFTP  
  b. 호스트: EC2 인스턴스 퍼블릭 IPv4 주소  
  c. 로그온 유형: 키 파일  
  d. 사용자: 사용자 계정  
  e. 키 파일: 키 페어 생성 시 만들어진 .pem 파일

---

## Lightsail
1. 인스턴스 생성
2. 고정 IP  
  a. 고정 IP 생성  
  b. 인스턴스에 할당
3. DNS zone  
  a. Route 53 에 도메인 등록  
  b. DNS zone 생성  
  c. 도메인 입력  
  d. 레코드 추가: 서브 도메인 생성과 고정 IP 할당  
  e. Name server를 Route 53에 등록
4. 포트 추가  
  a. 인스턴스에 443 포트 추가

---

## Amazon Linux 2
- 버전 확인: `cat /etc/issue`
- root 패스워드 설정: `sudo passwd root`

### docker install
1. `sudo yum update`
2. `sudo yum install docker`
3. docker 그룹에 사용자 추가(sudo 없이 실행): `sudo usermod -aG docker <user account>`
4. docker 실행: `sudo service docker start`

### docker-compose install
1. `sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose`
2. `sudo chmod +x /usr/local/bin/docker-compose`
3. `docker-compose version`
