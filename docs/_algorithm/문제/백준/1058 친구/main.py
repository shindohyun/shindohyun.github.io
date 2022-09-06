FLOYDWARSHALL = True

if FLOYDWARSHALL:
    # 친구의 관계는 가중치가 1인 무향 그래프로 표현할 수 있다.
    # 2-친구에 포함되는 경우를 그래프 상으로 생각했을 때
    # 1) A와 B가 친구인 경우: 경유지 없이 정점1에서 정점2로 직접 이동
    # 2) A와 C가 친구이고 C와 B가 친구인 경우: 정점1에서 하나의 경유지만 거쳐 정점2로 이동
    # 모든 가중치는 1이므로 경로를 이동할 때 마다 가중치는 1씩 증가된다.
    #
    # 플로이드 워셜은 이동하는 경로의 가중치를 합하고 비교하여 최소값을 선택해 결과값을 도출한다.
    # 가중치가 모두 같을 경우 단순히 몇 개의 경로를 이동했는지 알 수 있다.
    # 예를들어, 가중치가 모두 1인 경우 플로이드 워셜의 결과 값으로 가중치가 2라면 하나의 경유지만 거쳐간 것이다.
    #
    # 2-친구를 구하기 위해서는 플로이드 워셜의 결과 값으로 가중치가 2 이하인 개수를 모두 합한다.

    INF = 9999

    n = int(input()) # 사람의 수
    ad = [] # 인접 행렬

    for i in range(0, n):
        w = list(input()) # 입력값를 가중치로 대체 (N->INF, Y->1)
        for j in range(0, len(w)):
            if w[j] == 'N': w[j] = INF
            elif w[j] == 'Y': w[j] = 1

        ad.append(w)

    # i== j->0
    for i in range(0, n):
        ad[i][i] = 0

    # 플로이드 워셜 알고리즘
    # t: 경유지, s: 출발지, e: 도착지
    for t in range(0, n):
        for s in range(0, n):
            for e in range(0, n):
                t_route = ad[s][t] + ad[t][e]
                if t_route > INF:
                    t_route = INF

                ad[s][e] = min(ad[s][e], t_route)

    print(ad)

    # 2-친구가 가장 많은 사랑의 2-친구 개수를 출력
    max_cnt = 0
    for i in range(0, n):
        cnt = 0
        for j in range(0, n):
            if ad[i][j] == 1 or ad[i][j] == 2:
                cnt += 1

        if max_cnt< cnt:
            max_cnt = cnt

    print(max_cnt)
else:
    # 플로이드 워셜 보다 빠르다.
    # 2-친구의 경우는 직접 친구이든지 한 다리 건너 친구인 경우이다.
    # 직접 친구인 경우 그래프렝 표시된 대로 찾을 수 있고,
    # 한 다리 건너 친구인 경우 직접 친구의 친구 목록을 탐색하면 된다.
    # 이때 직접 친구의 친구 목록에서 자신과의 관계는 이미 계산되었으므로 제외한다.
    # 예를들어, A와 B가 친구이고 B는 A(A와 B가 친구이기 때문에 당연히), C, D 와 친구일 때
    # A의 2-친구는 직접 친구 B,
    # 이 B의 친구 목록을 봤을 때 (A는 자기 자신이니까 제외하고) C, D가 한 다리 건너 친구 이다.
    # 결국 A-B, A-B-C, A-B-D 이렇게 세 개가 나온다.

    n = int(input())  # 사람의 수
    ad = []  # 인접 행렬

    for i in range(0, n):
        ad.append(list(input()))

    max_cnt = 0  # 2-친구 수가 가장 많은 사람의 2-친구 수

    for i in range(0, len(ad)):
        l = ad[i]  # 타겟의 친구 목록
        check = [0] * n  # 타겟과 2-친구이면 1로 체크

        # 직접 친구 탐색
        for j in range(0, len(l)):
            if l[j] == 'Y':
                check[j] = 1
                dl = ad[j]  # 직접 친구의 친구 목록(한 다리 건너 친구 탐색의 대상)

                # 한 다리 건너 친구 탐색
                # 직접 친구의 친구 목록 탐색
                for k in range(0, len(dl)):
                    if k == i:
                        # 현재 타겟과의 친구 관계는 이미 계산되었으므로 제외
                        continue
                    else:
                        if dl[k] == 'Y':
                            check[k] = 1

        cnt = 0
        for c in range(0, len(check)):
            if check[c] == 1:
                cnt += 1

        if max_cnt < cnt:
            max_cnt = cnt

    print(max_cnt)