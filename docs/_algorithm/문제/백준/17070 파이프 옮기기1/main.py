g_n = 0
g_map = None  # 2차원 배열
g_memo = None  # 3차원 배열


def is_able(r, c, state):
    return is_range(r, c) and is_wall(r, c, state)


def is_range(r, c):
    global g_map
    global g_n

    if r >= g_n or c >= g_n:
        return False
    else:
        return True


def is_wall(r, c, state):
    global g_map

    if state == 1:
        # 대각선 상태인 경우 위, 왼쪽 상태도 확인 (파이프가 차지하는 공간이기 때문에)
        if g_map[r][c] == 1 or g_map[r][c - 1] == 1 or g_map[r - 1][c] == 1:
            return False
        else:
            return True
    else:
        if g_map[r][c] == 1:
            return False
        else:
            return True


# state : 0 - 오른쪽 방향, 1 - 오른쪽-아래 대각선 방향, 2 - 아래 방향
def move_pipe(r, c, state):
    # if not(is_range(r, c)):
    #     return 0
    # elif not(is_wall(r, c, state)):
    #     return 0
    # elif r == g_n - 1 and c == g_n - 1:
    #     return 1
    global g_memo

    if g_memo[state][r][c] > 0:
        return g_memo[state][r][c]

    if r == g_n - 1 and c == g_n - 1:
        g_memo[state][r][c] = 1
        return g_memo[state][r][c]

    # 공통
    if is_able(r + 1, c + 1, 1):
        g_memo[state][r][c] += move_pipe(r + 1, c + 1, 1)

    if state == 0 or state == 1:
        if is_able(r, c + 1, 0):
            g_memo[state][r][c] += move_pipe(r, c + 1, 0)
    if state == 2 or state == 1:
        if is_able(r + 1, c, 2):
            g_memo[state][r][c] += move_pipe(r + 1, c, 2)

    return g_memo[state][r][c]


def main():
    global g_n
    global g_map
    global g_memo

    g_n = int(input())
    g_map = [[0] * g_n for _ in range(g_n)]
    for r in range(0, g_n):
        row = list(map(int, input().split()))
        for c in range(0, g_n):
            g_map[r][c] = row[c]

    g_memo = [[[0] * g_n for _ in range(g_n)] for _ in range(3)]
    print(move_pipe(0, 1, 0))


if __name__ == "__main__":
    main()