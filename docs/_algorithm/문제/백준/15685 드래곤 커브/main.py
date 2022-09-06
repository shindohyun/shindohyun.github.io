# 0 : x + 1
# 1 : y - 1
# 2 : x - 1
# 3 : y + 1
def extend_end(direction, end):
    x = end['x']
    y = end['y']

    if direction == 0:
        x += 1
    elif direction == 1:
        y -= 1
    elif direction == 2:
        x -= 1
    elif direction == 3:
        y += 1

    return {'x': x, 'y': y}


def extend_dragon_curve(dragonSeed, direction, generation):
    dragonCurve = []
    directionList = []

    dragonCurve.append(dragonSeed)
    directionList.append(direction)

    # 끝 점 추가
    dragonCurve.append(extend_end(direction, dragonSeed))

    for _ in range(generation):
        addDirectionList = []
        for i in range(len(directionList) - 1, -1, -1):
            # 방향 변경
            # 0 -> 1 -> 2 -> 3 -> 0
            d = directionList[i]
            d = d + 1
            if d > 3:
                d = 0

            # 확장해서 끝점에 붙인다.
            dragonCurve.append(extend_end(d, dragonCurve[-1]))
            addDirectionList.append(d)

        directionList.extend(addDirectionList)  # 추가된 방향을 합친다.

    return dragonCurve


def main():
    n = int(input())
    dragonCurveList = []
    directionList = []
    generationList = []
    for _ in range(n):
        x, y, d, g = list(map(int, input().split()))
        curve = {'x': x, 'y': y}
        dragonCurveList.append(curve)
        directionList.append(d)
        generationList.append(g)

    total = [[False] * 101 for _ in range(101)]

    for i in range(0, n):
        extendedDragonCurve = extend_dragon_curve(dragonCurveList[i], directionList[i], generationList[i])
        for item in extendedDragonCurve:
            total[item['x']][item['y']] = True

    # 사각형 개수 찾기
    cnt = 0
    for i in range(101):
        for j in range(101):
            if total[i][j] == True:
                if (i + 1 <= 100) and (j + 1 <= 100):
                    if (total[i + 1][j] == True) and (total[i][j + 1] == True) and (total[i + 1][j + 1] == True):
                        cnt += 1

    print(cnt)


if __name__ == "__main__":
    main()