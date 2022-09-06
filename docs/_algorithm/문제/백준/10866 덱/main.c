#include <stdio.h>
#include <malloc.h>

typedef enum bool{
	false = 0, 
	true = 1
}bool;

typedef struct Node{
	struct Node *pNext;
	struct Node *pPre;
	int nData;
}NODE, *PNODE;

PNODE NodeCreate(int _nData)
{
	PNODE pNode = (PNODE)malloc(sizeof(NODE));
	if(pNode != NULL)
	{
		pNode->pNext = NULL;
		pNode->pPre = NULL;
		pNode->nData = _nData;
	}
	return pNode;
}

void NodeFree(PNODE _pNode)
{
	if(_pNode != NULL)
	{
		if(_pNode->pNext != NULL)
		{
			_pNode->pNext = NULL;
		}
		if(_pNode->pPre != NULL)
		{
			_pNode->pPre = NULL;
		}
		free(_pNode);
		_pNode = NULL;
	}
}

typedef struct Deque
{
	int nSize;
	PNODE pHead;
	PNODE pTail;
}DEQUE, *PDEQUE;


PDEQUE DequeCreate()
{
	PDEQUE pDeque = (PDEQUE)malloc(sizeof(DEQUE));
	if(pDeque != NULL)
	{
		pDeque->pHead = NULL;
		pDeque->pTail = NULL;
		pDeque->nSize = 0;
	}
	return pDeque;
}

void DequeFree(PDEQUE _pDeque)
{
	//한쪽 방향으로만 정리
	PNODE pCurNode = _pDeque->pHead;
	PNODE pNextNode = NULL;
	while(_pDeque->nSize--)
	{
		pNextNode = pCurNode->pNext;
		NodeFree(pCurNode);
		pCurNode = pNextNode;
	}
	free(_pDeque);
	_pDeque = NULL;
}

void DequePushFront(PDEQUE _pDeque, int _nData)
{
	PNODE pNewNode = NodeCreate(_nData);
	PNODE pHeadNode = NULL;

	if(_pDeque->nSize == 0)
	{
		_pDeque->pHead = pNewNode;
		_pDeque->pTail = pNewNode;
	}
	else
	{	pHeadNode = _pDeque->pHead;
		_pDeque->pHead = pNewNode;
		//양방향 연결
		pNewNode->pNext = pHeadNode;
		pHeadNode->pPre = pNewNode;
	}
	_pDeque->nSize++;
}

void DequePushBack(PDEQUE _pDeque, int _nData)
{
	PNODE pNewNode = NodeCreate(_nData);
	PNODE pTailNode = NULL;

	if(_pDeque->nSize == 0)
	{
		_pDeque->pHead = pNewNode;
		_pDeque->pTail = pNewNode;
	}
	else
	{
		pTailNode = _pDeque->pTail;
		_pDeque->pTail = pNewNode;
		//양방향 연결
		pTailNode->pNext = pNewNode;
		pNewNode->pPre = pTailNode;
	}
	_pDeque->nSize++;
}

int DequePopFront(PDEQUE _pDeque)
{
	PNODE pHeadNode;
	PNODE pNextNode;
	int nPopData;

	if(_pDeque->nSize == 0)
	{
		return -1;
	}

	pHeadNode = _pDeque->pHead;
	pNextNode = pHeadNode->pNext;
	nPopData = pHeadNode->nData;

	NodeFree(pHeadNode);
	_pDeque->pHead  = pNextNode;
	_pDeque->nSize--;

	return nPopData;
}

int DequePopBack(PDEQUE _pDeque)
{
	PNODE pTailNode;
	PNODE pPreNode;
	int nPopData;

	if(_pDeque->nSize == 0)
	{
		return -1;
	}

	pTailNode = _pDeque->pTail;
	pPreNode = pTailNode->pPre;
	nPopData = pTailNode->nData;

	NodeFree(pTailNode);
	_pDeque->pTail = pPreNode;
	_pDeque->nSize--;

	return nPopData;
}

int DequeFront(PDEQUE _pDeque)
{
	if(_pDeque->nSize == 0)
	{
		return -1;
	}
	
	return _pDeque->pHead->nData;
}

int DequeBack(PDEQUE _pDeque)
{
	if(_pDeque->nSize == 0)
	{
		return -1;
	}

	return _pDeque->pTail->nData;
}

int DequeGetSize(PDEQUE _pDeque)
{
	return _pDeque->nSize;
}

bool DequeIsEmpty(PDEQUE _pDeque)
{
	if(DequeGetSize(_pDeque) > 0)
	{
		return false;
	}
	else
	{
		return true;
	}
}

int main()
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

	return 0;
}