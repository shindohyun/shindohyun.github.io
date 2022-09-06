#include "deque.h"

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