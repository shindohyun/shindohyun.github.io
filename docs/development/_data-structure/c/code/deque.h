#pragma once

#include "node.h"

typedef struct Deque
{
	int nSize;
	PNODE pHead;
	PNODE pTail;
}DEQUE, *PDEQUE;

PDEQUE DequeCreate();
void DequeFree(PDEQUE _pDeque);
void DequePushFront(PDEQUE _pDeque, int _nData);
void DequePushBack(PDEQUE _pDeque, int _nData);
int DequePopFront(PDEQUE _pDeque);
int DequePopBack(PDEQUE _pDeque);
int DequeFront(PDEQUE _pDeque);
int DequeBack(PDEQUE _pDeque);
int DequeGetSize(PDEQUE _pDeque);
bool DequeIsEmpty(PDEQUE _pDeque);