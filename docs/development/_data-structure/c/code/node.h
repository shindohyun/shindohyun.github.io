#pragma once

#include <stdio.h>
#include <malloc.h>
#include "type.h"

typedef struct Node{
	struct Node *pNext;
	struct Node *pPre;
	int nData;
}NODE, *PNODE;

PNODE NodeCreate(int _nData);
void NodeFree(PNODE _pNode);