#include "node.h"

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