#pragma once

#include <stdio.h>
#include "..\code\string.h"
#include "..\code\deque.h"

void deque_test()
{
int nCnt;
	int i, j;
	char szCmd[32];
	char szTempCmd[32];
	int nData;
	bool bSplit;
	int *arrResult;
	int nResultCnt;
	DEQUE *dq;

	dq = DequeCreate();
	
	scanf("%d", &nCnt);
	arrResult = (int*)malloc(sizeof(int) * nCnt);
	nResultCnt = 0;
	for(i = 0; i < nCnt; i++)
	{

//문자열 초기화
#if 1
		for(j = 0; j < 32; j++)
		{
			szCmd[j] = 0;
			szTempCmd[j] = 0;
		}
#else
		szCmd[0] = '\0';
		szTempCmd[0] = '\0';
#endif
		
		scanf(" %[^\n]s", szCmd);
		
		if(strcmp(szCmd, "pop_front") == 0)
		{

			arrResult[nResultCnt++] = DequePopFront(dq);
		}
		else if(strcmp(szCmd, "pop_back") == 0)
		{
			arrResult[nResultCnt++] = DequePopBack(dq);
		}
		else if(strcmp(szCmd, "size") == 0)
		{
			arrResult[nResultCnt++] = DequeGetSize(dq);
		}
		else if(strcmp(szCmd, "empty") == 0)
		{
			arrResult[nResultCnt++] = DequeIsEmpty(dq);
		}
		else if(strcmp(szCmd, "front") == 0)
		{
			arrResult[nResultCnt++] = DequeFront(dq);
		}
		else if(strcmp(szCmd, "back") == 0)
		{
			arrResult[nResultCnt++] = DequeBack(dq);
		}
		else
		{
			bSplit = false;
			nData = 0;
			for(j = 0; j < 32; j++)
			{
				if(szCmd[j] == '\0')
				{
					break;
				}

				if(bSplit == true)
				{
					nData *= 10;
					nData += szCmd[j] - 48; //'0'(48);
				}
				else
				{
					szTempCmd[j] = szCmd[j];
				}

				if(szCmd[j] == ' ')
				{
					bSplit = true;
					
					szTempCmd[j] = '\0';
				}
			}

			if(strcmp(szTempCmd, "push_front") == 0)
			{
				DequePushFront(dq, nData);

			}
			else if(strcmp(szTempCmd, "push_back") == 0)
			{
				DequePushBack(dq, nData);
			}
		}
	}
	
	for(i = 0; i < nResultCnt; i++)
	{
		printf("%d\n", arrResult[i]);
	}

	free(arrResult);
	arrResult = NULL;
	DequeFree(dq);

	return;
}