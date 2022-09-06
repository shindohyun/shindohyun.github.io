#pragma once
// 문제 : 
// 5 x 5의 2차 배열에 무작위로 25개의 숫자로 초기화를 한 후 25개의 각 요소에
// 대해서 그 요소와 이웃한 요소와의 차의 절대값을 구한 뒤,
// 구한 모든 값의 총 합을 구한다.

#include <iostream>

using namespace std;

int main() {
	int sum = 0, tempSum = 0;
	int tempR, tempC;
	int arr[5][5] = { 0, };
	int dx[4] = { 0, 0, -1, 1 };
	int dy[4] = { -1, 1, 0, 0 };

	//배열 생성
	for (int i = 0; i < 5; i++) {
		for (int j = 0; j < 5; j++){
			arr[i][j] = rand() % 10;

			printf("%d ", arr[i][j]);
		}

		printf("\n");
	}

	printf("\n");

	//델타를 이용한 탐색
	for (int r = 0; r < 5; r++) {
		for (int c = 0; c < 5; c++) {
			tempSum = 0;
			for (int d = 0; d < 4; d++) {
				tempR = r + dy[d];
				tempC = c + dx[d];
				if (tempR >= 0 && tempR < 5 && tempC >= 0 && tempC < 5) {
					tempSum += abs(arr[r][c] - arr[tempR][tempC]);
				}
			}
			printf("%d ", tempSum);
			sum += tempSum;
		}

		printf("\n");
	}

	//총 합
	printf("\n%d", sum);

	return 0;
}