#pragma once
// 문제 : 
// 유한 개의 정수로 이루어진 집합이 있을 때, 이 집합의 부분 집합 중에서 
// 그 집합의 원소를 모두 더한 값이 0이 되는 경우가 있는지를 알아내보자.
// 예를 들어, {-7, -3, -2, 5, 8}
// 이라는 집합이 있을 때, {-3, -2, 5} 는 이 집합의 부분 집합이면서 총 합이 0 이므로 이 경우의 답은 참이다.
//
// 입력 : 
// 첫 줄에는 테스트 케이스의 개수 N이 주어진다. 이후 N줄에 걸처 테스트케이스가 주어진다.
// 각 테스트 케이스는 처음에는 집합에 포함된 원소의 개수가 주어지고 
// 이후 원소가 입력으로 주어진다.
//
// 입력 예시 :
// 1
// 5 -7 -3 -2 5 8
//
// 출력 : 
// 각 테스트 케이스에 대하여 집합의 원소를 모두 더한 값이 0이 되는 경우가 있으면
// True를 출력, 없다면 False를 출력
//
// 출력 예시 : True
//
// 관련 : 비트연산

#include <iostream>

using namespace std;

int main() {
	int testCase = 0;
	int arr[128] = { 0, };
	int arr_size = 0;
	string input = "";
	string* results = NULL;
	char* tok = NULL;
	const char* split = " ";

	int subsetsCnt = 0;		//부분집합 개수
	int sum = 0;			//부분집합의 합
	bool isFound = false;	//부분집합의 합이 0이 되는 경우를 찾았는지.

	bool result = false;

	
	scanf("%d", &testCase);
	results = new string[testCase];

	for (int i = 0; i < testCase; i++) {
		//초기화
		memset(arr, 0, sizeof(int) * 128);
		arr_size = 0;
		subsetsCnt = 0;

		getchar();//입력버퍼 비우기

		//숫자 입력 받아서 집합 만들기
		scanf("%[^\n]", input.c_str());

		
		tok = strtok((char*)input.c_str(), split);
		while (tok != NULL) {
			arr[arr_size++] = atoi(tok);
			tok = strtok(NULL, split);
		}

		//부분집합 개수 구하기
		subsetsCnt = (1 << arr_size);

		result = false;

		//모든 부분집합을 조회하면서 합을 구한다.
		for (int j = 0; j < subsetsCnt; j++) {
			sum = 0;
			isFound = false;

			//부분집합에 포함되어 있는 원소를 찾아서 합한다.
			for (int z = 0; z < arr_size; z++) {

				//z번째 원소가 j부분집합에 속해있는지 확인하고 합한다.
				if (j & (1 << z)) {
					isFound = true;
					sum += arr[z];
				}
			}

			//부분집합의 합이 0이 되는지 확인
			if (isFound && sum == 0) {
				result = true;
				break;
			}
		}

		if (result) {
			results[i] = "True";
		}
		else {
			results[i] = "False";
		}
	}

	for (int i = 0; i < testCase; i++) {
		printf("%s\n", results[i].c_str());
	}

	delete[] results;

	return 0;
}