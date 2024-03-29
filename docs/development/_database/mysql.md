---
layout: default
title: MySQL
grand_parent: Development
parent: Database
permalink: /docs/development/database/mysql
nav_order: 2
---

# MySQL
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## 기본

### AS

```mysql
-- SELEC 에서 재정의된 컬럼명은 바로 사용이 가능하다.
SELECT `column` AS `as_column` FROM `table` GROUP BY `as_column` HAVING `as_column` BETWEEN 0 AND 9
```



### JOIN

```mysql
-- INNER JOIN. 두 테이블의 교집합, 교집합 범위 외의 레코드는 조회되지 않는다. ON 은 JOIN 조건문
-- NATURAL JOIN. 두 테이블에서 같은 컬럼명을 기준으로 자동 INNER JOIN. ON 조건문을 생략할 수 있다.
SELECT 
	A.column, 
	B.column 
FROM 
	`table1` AS `A` JOIN `table2` AS `B` 
	ON A.column = B.column

-- LEFT OUTER JOIN. 왼쪽 테이블을 기준으로 조회하되, 오른쪽 테이블과 조건문으로 겹치지 않는 부분에 대해서는 NULL로 출력한다.
SELECT 
	A.column, 
	B.column 
FROM 
	`table1` AS `A` LEFT JOIN `table2` AS `B` 
	ON A.column = B.column

-- RIGHT OUTER JOIN. 오른쪽 테이블을 기준으로 조회하되, 왼쪽 테이블과 조건문으로 겹치지 않는 부분에 대해서는 NULL로 출력한다.
SELECT
	A.column,
	B.column
FROM
	`table1` AS `A` RIGHT JOIN `table2` AS `B` 
	ON A.column = B.column

-- 'A LEFT JOIN B' 과 'B RIGHT JOIN A' 의 결과는 동일하다.

-- OUTER JOIN. MySQL에서 OUTER JOIN 의 키워드 자체는 제공하지 않지만 서로 겹치지 않는 데이터들을 조회한다는 의미이다. INNER JOIN의 조인 조건문을 다음과 같이 사용하여 표현할 수 있다.
SELECT ... ON A.column <> B.column
SELECT ... ON A.column != B.column

-- CROSS JOIN. 집합의 곱, 예를들어 A= {a, b, c, d} , B = {1, 2, 3} 의 집합 곱은 (a,1), (a,2), (a,3), (b,1), ... 이 된다.
SELECT A.column, B.column FROM `table1` AS `A` CROSS JOIN `table2` AS `B`

-- SELF JOIN. 하나의 테이블에서 다른 컬럼 끼리 조건을 갖고 조인하는 것, 어느 컬럼이 다른 레코드의 컬럼 아이디를 갖는 경우 활용할 수 있다.
SELECT
	A.name, 
	B.name AS `parent_name` 
FROM
	`table` AS `A` JOIN `table` AS `B`
	ON A.parent_idx = B.idx
```

```mysql
-- ON 의 조건으로 조인 테이블을 만들고, 생성된 테이블에서 WHERE 절로 조건 검색
... JOIN ... ON 조인 조건문 WHERE 조건문
```



### IS NULL, IS NOT NULL

```mysql
... WHERE `column` IS NOT NULL -- 해당 컬럼의 값이 null 이 아닌 레코드 조회
... WHERE `column` IS NULL -- 해당 컬럼의 값이 null 인 레코드 조회
```



### LIKE, NOT LIKE

MySQL은 기본적으로 문자의 대소문자를 구분하지 않는다.

[LIKE와 REGEXP 비교](#like-vs-regexp) 를 알아두는 것이 좋다.

```mysql
... WHERE `column` LIKE 'text' -- 'text'와 같은 문자열이라면
... WHERE `column` LIKE '%text%' -- 'text'가 포함되는 문자열이라면
... WHERE `column` NOT LIKE 'text%' -- 'text'로 시작하는 문자열이 아니라면

-- 대소문자를 구분해서 문자열을 찾으려는 경우 BINARY 키워드를 사용
... WHERE BINARY `column` LIKE 'text'
```



### BETWEEN AND

```mysql
... WHERE `column` BETWEEN 0 AND 9
```



### CASE

[IF, IFNULL](#논리-함수--if-ifnull) 과 비교하여 알아두면 좋다.

```mysql
SELECT
	CASE
		WHEN (`column` BETWEEN 1 AND 3) THEN 'A'
		WHEN (`column` BETWEEN 4 AND 7) THEN 'B'
		ELSE 'C'
	END AS `grade`
FROM `table`
```



### GROUP BY

[집계함수](#집계-함수---count-sum-avg-min-max) 와 함께 자주 사용된다. 이때 **그룹화에 의한 중복 제거 상태가 아닌 레코드들에 대해** 집계함수를 적용한다.

```mysql
-- 해당 컬럼에 대한 그룹을 만들고 중복된 레코드는 제거하여 보여준다.
GROUP BY `column`

-- 컬럼을 기준으로 묶인 각 그룹의 첫 번째 레코드만 조회된다.
SELECT * FROM `table` GROUP BY `column`

-- 컬럼을 기준으로 생성된 각 그룹 레코드 수를 조회한다.
SELECT `column`, COUNT(*) AS `count` FROM `table` GROUP BY `column`

-- WHERE 는 그룹화 전에 처리
SELECT * FROM `table` WHERE 조건문 GROUP BY `column`

-- HAVING 은 그룹화 후에 처리
SELECT * FROM `table` GROUP BY `column` HAVING 조건문

-- 그룹화 전 조건문1 처리, 그룹화 후 조건문2 처리
SELECT * FROM `table` WHERE 조건문1 GROUP BY `column` HAVING 조건문2

-- ORDER BY 는 마지막에
... GROUP BY `column` HAVING 조건문 ORDER BY `column`

```



### ORDER BY

```mysql
ORDER BY `column` -- 오름차순 정렬 (기본값)
ORDER BY `column` DESC 	-- 내림차순 정렬
```

```mysql
-- column01 기준으로 우선 정렬, column01 이 같을 경우 column02 기준으로 정렬
ORDER BY `column01`, `column02`

-- column02 기준으로 정렬할 경우에만 내림차순 정렬
ORDER BY `column01`, `column02` DESC

-- column01 기준으로 정렬할 경우에만 내림차순 정렬
ORDER BY `column01` DESC, `column02`
```



### LIMIT

```mysql
LIMIT n -- 상위 n개 반환
```



### SET

```mysql
SET @var := -1; -- 변수 설정

-- 변수를 활용하여 0~10 출력하기
SET @var := -1;
SELECT 
	(@var:=@var+1) AS `VAR`
FROM 
	`table`
WHERE 
	@var < 10
	
-- 변수를 활용하여 더미 데이터 출력하기
SET @var := -1;
SELECT
	(@var:=@var+1) AS `VAR`
	(SELECT COUNT(*) AS `count` FROM `table` WHERE `column` = @var) AS `COUNT`
FROM
	`table`
WHERE
	@var < 10
```



## 연산자

```mysql
-- 비교연산자
`column1` = `column2` -- 같다.
`column1` != `column2` -- 같지 않다.
`column1` <> `column2` -- 같지 않다.

-- 대입연산자
SET @var := 초기값 -- var 변수에 초기값 설정
```



## 함수

### 논리 함수 : IF, IFNULL

```mysql
IF(논리식, 참인 경우 값, 거짓인 경우 값)
SELECT IF(`column` > 5, 'O', 'X') AS `result` FROM `table`

IFNULL(`column`, 대체 값)
```



### 포함 관계 함수 : IN, NOT IN

집합에 포함되어 있는지 여부를 알아낸다. 집합에는 하나 이상의 값이나, 서브쿼리가 들어갈 수 있다.

```mysql
SELECT ... WHERE `column` IN (값1, 값2, ...) -- 포함되어 있는 레코드만 조회
SELECT ... WHERE `column` NOT IN (값, 값2, ...) -- 포함되어 있지 않는 레코드만 조회

-- 서브쿼리 결과에 포함되어 있는 레코드만 조회
SELECT ... WHERE `column` IN (SELECT `column` FROM `table`) 

-- 조건 컬럼 여러개 사용하는 방법, 괄호로 묶기
SELECT ... WHERE (`column1`, `column2`) NOT IN (SELECT `column1`, `column2` FROM `table`)
```



### 집계 함수 :  COUNT, SUM, AVG, MIN, MAX

NULL 은 제외하고 처리된다.

```mysql
COUNT(*) -- 레코드 수를 반환
COUNT(`column`) -- 해당 컬럼의 레코드 수를 반환, NULL 은 제외된다.
COUNT(DISTINCT `column`) -- 해당 컬럼의 중복 제거된 레코드 수를 반환

SUM(`column`)
AVG(`column`)
MIN(`column`)
MAX(`column`)
```



### 문자열 검색 함수 : REGEXP

정규식을 사용하여 문자열을 찾는다.

| 정규식 |                                |
| ------ | ------------------------------ |
| .      | 줄바꿈을 제외한 임의의 한 문자 |
| *      | 해당 문자 패턴 0번 이상 반복   |
| +      | 해당 문자 패턴 1번 이상 반복   |
| ^      | 문자열의 시작                  |
| $      | 문자열의 끝                    |
| \|     |                                |
| [...]  | 괄호안에 있는 어떠한 문자      |
| [^...] | 괄호안에 있지 않는 어떠한 문자 |
| {n}    | 반복횟수 지정                  |
| {m, n} | 반복횟수 최소, 최대            |

```mysql
... WHERE `column` REGEXP('text') -- 'text'가 포함되는 문자열이라면
... WHERE `column` REGEXP('^text') -- 'text'로 시작하는 문자열이라면
... WHERE `column` REGEXP('text$') -- 'text'로 끝나는 문자열이라면
... WHERE `column` REGEXP('^text$') -- 'text'와 같은 문자열이라면
... WHERE `column` REGEXP('^text1|text2$') -- 'text1'으로 시작하거나 'text2'로 끝나는 문자열이라면
```

#### LIKE vs REGEXP

```mysql
REGEXP('^text$') = LIKE 'text' -- 'text' 자체와 동일한 문자열을 찾는다.
REGEXP('text') = LIKE '%text%' -- 'text' 가 포함된 문자열을 찾는다.
```



### 날짜 추출 함수 : YEAR, MONTH, DAY, HOUR, MINUTE, SECOND

```mysql
YEAR('2020-05-21 01:23:00') -- 연도 추출
MONTH('2020-05-21 01:23:00') -- 달 추출
DAY('2020-05-21 01:23:00') -- 일 추출
HOUR('2020-05-21 01:23:00') -- 시간 추출
MINUTE('2020-05-21 01:23:00') -- 분 추출
SECOND('2020-05-21 01:23:00') -- 초 추출
```



### 날짜 형식 설정 함수 : DATE_FORMAT

| 포맷             |                            |
| ---------------- | -------------------------- |
| * %Y             | 연도 4자리 (2020)          |
| %y               | 연도 2자리 (20)            |
| %c               | 월 1~2자리 (1, 12)         |
| * %m             | 월 2자리 (01)              |
| %M               | 긴 영문 월 (January)       |
| %b               | 짧은 영문 월 (Jan)         |
| %D               | 서수 월 (1st)              |
| %e               | 일 1~2자리 (1, 12)         |
| * %d             | 일 2자리 (01)              |
| %r               | hh:mm:ss AM\|PM            |
| %T               | hh:mm:SS                   |
| %I (대문자 아이) | 시 12시간, 2자리 (01, 12)  |
| * %h             | 시 12시간, 2자리 (01, 12)  |
| %l (소문자 엘)   | 시 12시간, 1~2자리 (1, 12) |
| * %H             | 시 24시간, 2자리 (01, 24)  |
| * %i             | 분 2자리 (00, 59)          |
| * %S, %s         | 초 2자리                   |
| %p               | AM, PM                     |
| %W               | 긴 영문 요일 (Monday)      |
| %a               | 짧은 영문 요일 (Mon)       |
| %w               | 숫자 요일 (0:일요일)       |

```mysql
DATE_FORMAT(날짜, 포맷)
DATE_FORMAT('2020-05-21 17:18:00', '%Y-%m-%d')
```



### 날짜 연산 : DATE_ADD, DATE_SUB, DATEDIFF, TIMESTAMPDIFF

```mysql
-- 1초/분/시간/일/달/년 더하기
DATE_ADD('2020-05-21 01:23:00', INTERVAL 1 SECOND/MINUTE/HOUR/DAY/MONTH/YEAR)

-- 1초/분/시간/일/달/년 빼기
DATE_SUB('2020-05-21 01:23:00', INTERVAL 1 SECOND/MINUTE/HOUR/DAY/MONTH/YEAR)

DATEDIFF(날짜1, 날짜2) -- 날짜1-날짜2
SELECT DATEDIFF('2019-02-01 00:00:00', '2018-01-02 00:00:00')

-- 날짜1-날짜2 를 각 단위로 출력
TIMESTAMPDIFF(SECOND/MINUTE/HOUR/DAY/WEEK/MONTH/QUARTER/YEAR, 날짜1, 날짜2)
SELECT TIMESTAMPDIFF(MINUTE, '2019-02-01 00:00:00', '2018-01-02 00:00:00')
```

## 명령어
- Character Set 설정 보기: `show variables like 'c%';`