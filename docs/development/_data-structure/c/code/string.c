#include "string.h"

int strcmp(char *_str1, char *_str2)
{
	for(; *_str1 &&(*_str1 == *_str2); _str1++, _str2++);
	return *_str1 - *_str2;
}