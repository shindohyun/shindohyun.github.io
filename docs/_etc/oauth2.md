---
layout: default
title: OAuth2
parent: etc.
permalink: /docs/etc/oauth2
nav_order: 2
---

# OAuth2
{: .no_toc }

---

# 과정

1. `Client`가 `Resource Server`에 특정 서비스 사용을 위한 등록을 요청한다.
2. `Resource Server`는 `Client`에게 **Client ID**와 **Client Secret** 값을 전달한다. `Client`는 이 두 개의 정보를 노출되지 않도록 저장해야한다. (특히 Client Secret 데이터는 노출되서는 안된다.)
3. `Client`는 `Resource Owner`에게 특정 서비스 사용을 위해서 `Resource Server`에게 허가 받기를 요구한다.
4. `Resource Owner`는 `Resource Server`에 접근한다.
5. 로그인 되어있지 않다면 `Resource Server`는 `Resource Owner`에게 로그인을 요구하고 `Client`에서 사용하려는 특정 서비스 및 `Client`가 `Resource Owner`의 정보를 사용하는 것을 허가할 것인지 여부를 묻는다.
6. `Resource Owner`가 5번 과정에서 허용했다면 `Resource Server`는 `Client`에게 **Code**를 전달한다.
7. `Client`는 `Resource Server`에게 Client ID, Client Secret, Code 를 전달한다.
8. `Resource Server`는 전달 받은 Client ID, Client Secret, Code를 모두 검증한 뒤 `Client`에게 **Access Token**을 발급한다.
9. `Client`는 발급 받은 Access Token을 노출되지 않게 저장하고 있다가 서비스나 `Resource Owner`의 정보가 필요할 때 `Resource Server`에게 제출한다.
10. `Resource Server`는 Access Token을 확인하고 서비스나 `Resource Owner`의 정보를 `Client`에게 제공한다.