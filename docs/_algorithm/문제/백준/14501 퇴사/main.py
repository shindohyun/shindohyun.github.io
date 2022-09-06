n = int(input()) # 남은 일 수
c = [] # 상담 일정

for i in range(0, n):
    t, p = list(map(int, input().split()))
    c.append({'t': t, 'p': p}) # t: 상담 완료 기간, p: 수입

# Dynamic Programing 
def get_max_pay(d):
    if d > n:
        return -1
    elif d == n:
        return 0
    else:
        no_select = get_max_pay(d + 1)
        select = get_max_pay(d + c[d]['t'])
        if select < 0:
            select -= c[d]['p']
        else:
            select += c[d]['p']

        return max(no_select, select)

print(get_max_pay(0))