---
layout: default
title: Docker
parent: DevOps·Infra
permalink: /docs/devops-infra/docker
nav_order: 2
---

# Docker
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## 명령어
- 컨테이너를 이미지로 저장: `docker commit -a <작성자> -m <코멘트> <컨테이너명> <레포지토리 이름<:태그>>`
- 목록 검색: `docker <container|image|network|volume> ls`
- stop 상태 컨테이너까지 모두 조회: `docker ps -a`
- 상세보기: `docker <container|image|network|volume> inspect <이름>`
- 태그 변경: `docker tag <from 레포지토리 이름<:태그>> <to 레포지토리 이름:태그>`
- 네트워크 연결: `docker network connect <네트워크 이름> <컨테이너명>`
- 네트워크 해제: `docker network disconnect <네트워크 이름> <컨테이너명>`
- 네트워크 삭제: `docker network rm <네트워크 이름>`
- 컨테니어 SSH 접속: `docker exec -it <container name> /bin/bash`
- 컨테이너 로그 보기: `docker logs <container name>`

---

## Nginx

### Command

```
docker container run -d \
--name nginx \
-p 80:80 \
-p 443:443 \
-v <volume path>/nginx/log:/var/log/nginx \
-v <volume path>/nginx/conf/default.conf:/etc/nginx/conf.d/default.conf \
-v <letsencrypt directory path>:<letsencrypt directory path> \
--net bridge \
nginx:latest
```

### docker-compose.yml

```yml
version: '3.8'

services:
  nginx:
    container_name: nginx
    image: nginx:latest
    ports:
      - 80:80
      - 443:443
    volumes:
      - <volume path>/nginx/log:/var/log/nginx/
      - <volume path>/nginx/conf/default.conf:/etc/nginx/conf.d/default.conf
      - <letsencrypt directory path>:<letsencrypt directory path>

networks:
  default:
    external: true
    name: bridge
```

---

## MariaDB

### Command

```
docker container run -d \
--name mariadb \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD='<password>' \
-v <volume path>/mariadb/conf.d:/etc/mysql/conf.d \
-v <volume path>/mariadb/data:/var/lib/mysql \
-v <volume path>/mariadb/initdb.d:/docker-entrypoint-initdb.d \
--net bridge \
mariadb \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci
```

### docker-compose.yml

```yml
version: '3.8'

services:
  mariadb:
    container_name: mariadb
    image: mariadb
    ports:
      - 3306:3306
    volumes:
      - <volume path>/mariadb/conf.d:/etc/mysql/conf.d
      - <volume path>/mariadb/data:/var/lib/mysql
      - <volume path>/mariadb/initdb.d:/docker-entrypoint-initdb.d
    environment:
      - MYSQL_ROOT_PASSWORD=<password>
    command:
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci

networks:
  default:
    external: true
    name: bridge
```