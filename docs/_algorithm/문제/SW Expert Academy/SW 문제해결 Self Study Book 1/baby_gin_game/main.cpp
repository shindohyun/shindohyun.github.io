#pragma once
// 문제 : 
// 0~9 사이의 숫자 카드에서 임의의 카드 6장을 뽑았을 때,
// 3장의 카드가 연속적인 번호를 갖는 경우 run
// 3장의 카드가 동일한 번호를 갖는 경우 tripletes
// 6장의 카드가 run과 tripletes로만 구성된 경우 baby-gin
// 6자리의 숫자를 입력 받아 baby-gin 여부를 판단하는 프로그램 작성
// 예) 667767 : 두 개의 triplet 이므로 baby-gin (666,777)
// 064060 : 한 개의 run과 한 개의 triplet 이므로 baby-gin (456, 000)
// 101123 : 한 개의 triplet, 023이 run이 아니므로 baby-gin이 아님
//
// 입력 : 첫 번째 줄에 전체 테스트 케이스 수 T(1<= T <= 100)
// 각 테스트 케이스는 6자리 숫자
//
// 입력 예시 :
// 3
// 667767
// 054060
// 101123
//
// 출력 : 입력된 테스트 케이스가 baby-gin 이면 Baby Gin, 아니면 Lose 출력
//
// 출력 예시 : 
// Baby Gin
// Baby Gin
// Lose
//
// 관련 : 탐욕알고리즘

#include <iostream>

using namespace std;

int main() {
	int tripleCnt = 0;
	int runCnt = 0;
	int testCase = 0;
	int cases = 0;
	int cnt[10] = { 0, }; //0~9의 숫자카드의 개수 저장
	string* results;

	scanf("%d", &testCase);
	results = new string[testCase];

	for (int i = 0; i < testCase; i++) {
		scanf("%d", &cases);

		runCnt = 0;
		tripleCnt = 0;
		memset(cnt, 0, sizeof(int) * 10);

		//개수 저장
		for (int j = 0; j < 6; j++) {
			//맨 앞에 0이 오는 경우가 있으니까.
			//if (cases <= 0){
			//	break;
			//}

			cnt[cases % 10]++;
			cases /= 10;
		}

		for (int j = 0; j < 10; j++) {
			//triple 검사
			if (cnt[j] >= 3) {
				cnt[j] -= 3;
				tripleCnt++;
			}

			//run 검사
			if (j <= 7) {
				if (cnt[j] >= 1 && cnt[j + 1] >= 1 && cnt[j + 2] >= 1) {
					cnt[j] -= 1;
					cnt[j + 1] -= 1;
					cnt[j + 2] -= 1;
					runCnt++;
				}
			}
		}

		if (runCnt + tripleCnt >= 2) {
			results[i] = "Baby-Gin";

		}
		else {
			results[i] = "Lose";
		}
	}

	for (int i = 0; i < testCase; i++) {
		printf("%s\n", results[i].c_str());
	}

	delete[] results;

	return 0;
}