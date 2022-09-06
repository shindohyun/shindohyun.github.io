#include <iostream>

using namespace std;

void custom_strcpy(char*, char*);
void custom_strrev(char*);
int custom_strcmp(char*, char*);
int custom_atoi(char*);
void custom_itoa(int, char*);

int main(void) {
	
	printf("<<<<< copy test >>>>>\n");
	char src[12] = "Hello World";
	char dest[12] = { 0, };
	printf("src: %s\ndest: %s\n", src, dest);

	
	custom_strcpy(dest, src);
	printf("dest: %s\n", dest);
	
	printf("\n<<<<< reverse test >>>>>\n");
	custom_strrev(dest);
	printf("dest: %s\n", dest);
	
	printf("\n<<<<< compare test >>>>>\n");
	char cmp_dest[3][12] = { "Hello World", "Hellp World", "Helln World" };
	int cmp[3] = { 0, };
	cmp[0] = custom_strcmp(cmp_dest[0], src);
	cmp[1] = custom_strcmp(cmp_dest[1], src);
	cmp[2] = custom_strcmp(cmp_dest[2], src);
		
	for (int i = 0; i < 3; i++) {
		switch(cmp[i]){
		case 0:
			printf("%s == %s\n", cmp_dest[i], src);
			break;
		case 1:
			printf("%s > %s\n", cmp_dest[i], src);
			break;
		case -1:
			printf("%s < %s\n", cmp_dest[i], src);
			break;
		}
	}
	
	printf("\n<<<<< string to integer test >>>>>\n");
	char strDigit[] = "1992";
	int digit = custom_atoi(strDigit);
	printf("\"%s\"->%d\n", strDigit, digit);

	printf("\n<<<<< integer to string test >>>>>\n");
	int digit2 = 12345;
	char strDigit2[1024] = { 0, };
	custom_itoa(digit2, strDigit2);
	printf("%d->\"%s\"\n", digit2, strDigit2);

	return 0;
}

void custom_strcpy(char* dest, char* src) {
	while (*src != '\0') {
		*dest = *src;

		dest++;
		src++;
	}
	*dest = '\0';
}

void custom_strrev(char* str) {
	int len = strlen(str);
	
	int temp = 0;

	for (int i = 0; i < len / 2; i++) {
		temp = str[i];
		str[i] = str[(len - 1) - i];
		str[(len - 1) - i] = temp;
	}
}

int custom_strcmp(char* str1, char* str2) {

	while (*str1 != '\0') {
		if (*str1 == *str2) {
			str1++;
			str2++;
		}
		else {
			break;
		}
	}

	int gap = *str1 - *str2;

	if (gap > 0) {
		return 1;
	}
	else if (gap < 0) {
		return -1;
	}
	else {
		return gap; //0
	}
}

int custom_atoi(char* str) {
	int value = 0, digit, c;
	while ((c = *str++) != '\0') {
		if ('0' <= c && c <= '9') {
			digit = c - '0';
		}
		else {
			break;
		}
		value = (value * 10) + digit;
	}

	return value;
}

void custom_itoa(int digit, char* str) {
	int q = 0, r = 0;
	int i = 0;
	
	do {
		q = digit / 10;
		r = digit % 10;

		char c = r + '0';
		str[i++] = c;

		digit = q;

	} while (q != 0);

	str[i] = '\0';
	custom_strrev(str);
}