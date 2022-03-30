---
layout: default
title: Nginx
parent: DevOps·Infra
permalink: /docs/devops-infra/nginx
nav_order: 4
---

# Nginx
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## default.conf
- on Docker Container
- Reverse Proxy
- Force Redirect to SSL

```conf
server {
  listen 80;
  server_name <도메인>;
  location / {
    return 301 https://$host$request_uri;
  }
}

server {
  listen 443 ssl;
  server_name <도메인>;
  location / {
    proxy_pass http://<docker container name>:<port>; 
    proxy_redirect off;
  }

  ssl_certificate <fullchain.pem file path>;
  ssl_certificate_key <privkey.pem file path>;
}
```

---

## default.conf (for Troubleshooting)

```conf
server {
  location / {
      root   /usr/share/nginx/html;
      index  index.html index.htm;
      try_files $uri $uri/ /index.html; # subpath error solution
  }
}
```