---
layout: default
title: 정렬
grand_parent: Development
parent: Java
permalink: /docs/development/java/sort
nav_order: 2
---

# 정렬
{: .no_toc }

배열과 객체를 정렬하는 다양한 방법 정리
{: .fs-6 .fw-300 }

---

### 배열 정렬

`Arrays.sort(...)` 를 사용하여 배열을 정렬한다. 기본적으로 오름차순으로 정렬된다.  
내림차순으로 정렬하는 경우  `Collections.reverseOrder()` 또는 `Comparator.reverseOrder()` 와 함께 사용한다. 이때, Primitive Type (boolean, short, int, long, float, double, char) 배열은 사용할 수 없기 때문에 Reference Type의 배열로 대체한다.

```java
int[] intArr = {...};
Integer[] integerArr = {...};
String[] strArr = {...};

//오름차순 정렬
Arrays.sort(intArr);

//내림차순 정렬
//Arrays.sort(intArr, Collections.reverseOrder()); 불가
Arrays.sort(integerArr, Collections.reverseOrder()); 
Arrays.sort(integerArr, Comparator.reverseOrder());
Arrays.sort(strArr, Collections.reverseOrder()); //String은 Reference Type
```



### List Collection 정렬

List Collection에 속하는 `ArrayList`, `LinkedList`, `Vector`, `Stack` 의 경우 `Collections.sort()` 를 사용하여 정렬한다. 이 역시 오름차순을 기본으로 한다. 내림차순 정렬 시 `Collections.reverseOrder()` 또는 `Comparator.reverseOrder()` 를 사용한다.  

Collection 은 Primitive Type 을 사용할 수 없기 때문에 역시 Reference Type에 대해서만 적용 가능하다.

```java
ArrayList<Integer> arrList = new ArrayList<Integer>();

Collections.sort(arrList); //오름차순 정렬
Collections.sort(arrList, Collections.reverseOrder()); //내림차순 정렬
```



### PriorityQueue 의 내림차순

`PriorityQueue` 은 List Collection에 속하진 않지만 우선순위를 내림차순으로 결정 짓기 위해 `Collections.reverseOrder()` 와 `Comparator.reverseOrder()` 를 사용할 수 있다. `PriorityQueue` 는 **기본적으로 오름차순**으로 우선순위를 결정한다.

```java
PriorityQueue<Integer> pq = null;
pq = new PriorityQueue<Integer>(); //오름차순 정렬
pq = new PriorityQueue<Integer>(Collections.reverseOrder()); //내림차순 정렬
```



### 객체 정렬

클래스에 Comparable 인터페이스 또는 Comparator 인터페이스를 상속 받고 특정 메소드를 오버라이드함으로써 해당 객체 정렬 시 순서를 결정할 수 있다.

- Comparable Interface  
  `compareTo(T)` 메소드를 오버라이드하여 **해당 클래스에 대한 기본 정렬 기준**을 구현한다.  
  `compareTo(T)` 의 *매개변수로 받는 객체*는 현재 객체의 비교 대상으로, 리스트 순서상 *현재 객체 바로 앞에 위치하는 객체*이다.  
  `compareTo(T)` 는 **음수를 반환하면 자리를 교체하고 0 이상의 수를 반환하면 자리를 유지한다.**  
  규칙 : <u>현재 객체(`this`) 가 앞에 있으면 오름차순 정렬, 비교 대상 객체(`o`) 가 앞에 있으면 내림차순 정렬</u>   

  ```java
  class MyClass implements Comparable<MyClass>{
    int i;
    String str;
    //...
    
    @Override
    public int compareTo(MyClass o) {
      // 현재 객체 - 바로 앞 객체 = a 인 경우
      // a >= 0 : 현재 객체가 더 크거나 같은 상황에서 자리를 유지한다.
      // a < 0 : 현재 객체가 더 작은 상황에서 자리를 바꾼다.
      // => 오름차순 정렬
  		return this.i - o.i;
      
      // 바로 앞 객체 - 현재 객체 = b 인 경우
      // b >= 0 : 바로 앞 객체가 더 크거나 같은 상황에서 자리를 유지한다.
      // b < 0 : 바로 앞 객체가 더 작은 상황에서 자리를 바꾼다.
      // => 내림차순 정렬
      return o.i - this.i;
      
      // ====== 문자열 비교 ======
      // 현재 객체 보다 바로 앞 객체의 문자열이 더 길면 음수 : 자리 교체
      // 바로 앞 객체 보다 현재 객체의 문자열 길이가 더 길거나 같으면 0이상 : 자리 유지
      // => 오름차순 정렬
      return this.str.compareTo(o.str);
      
      // 바로 앞 객체 보다 현재 객체의 문자열이 더 길면 음수 : 자리 교체
      // 현재 객체 보다 바로 앞의 문자열 길이가 더 길거나 같으면 0이상 : 자리 유지
      // => 내림차순 정렬
      return o.str.compareTo(this.str);
      
      //현재 리스트 순서의 역순으로 정렬한다.
      return -1;
  	}
  }
  ```

  

- Comparator Interface  
  `compare(T o1, T o2)` 메소드를 오버라이드 하여 **특정 클래스의 객체에 대한 정렬 기준**을 구현한다.  
  `compare(T o1, T o2)` 역시 **음수를 반환하면 자리를 교체하고 0 이상의 수를 반환하면 자리를 유지한다.**  
  규칙 : <u>`o1` 가 앞에 있으면 오름차순 정렬, `o2` 가 앞에 있으면 내림차순 정렬</u>  
  Comparator 인터페이스를 상속 받은 클래스를 재사용할 필요가 없는 경우 익명 클래스 형태로 많이 사용된다.
  
  ```java
  class MyClass{
    public int i;
    public String str;
   	//...
  }
  
  class MyClassComparator implements Comparator<MyClass>{
    //...
    @Override
    public int compare(MyClass o1, MyClass o2) {
      //오름차순 정렬
      return o1.i - o2.i;
      return o1.str.compareTo(o2.str);
      
      //내림차순 정렬
      return o2.i - o1.i;
      return o2.str.compareTo(o1.str);
      
      //현재 리스트 순서의 역순으로 정렬
      return -1;
    }    	
  }
  
  //...
  
  //익명 클래스로 사용하기
  Integer[] arr = new Integer[3];
  ArrayList<Integer> list = new ArrayList<Integer>();
  //...
  Arrays.sort(arr, new Comparator<Integer>(){
    @Override
    public int compare(Integer o1, Integer o2){
      return o1 - o2;
    }
  });
  Collections.sort(arr, new Comparator<Integer>(){
    @Override
    public int compare(Integer o1, Integer o2){
      return o2 - o1;
    }
  });
  ```
  
  

### 객체 정렬의 사용

```java
class MyClass implements Comparable<MyClass>{
  int i;
  //...
  
  @Override
  public int compareTo(MyClass o) {
    return this.i - o.i; //오름차순
	}
}
class MyClassComparator implements Comparator<MyClass>{
  //...
  
  @Override
  public int compare(MyClass o1, MyClass o2){
    return o2.i - o1.i; //내림차순
  }
}
//...
MyClass[] objArr = new MyClass[3];
objArr[0] = new MyClass(1);
objArr[0] = new MyClass(10);
objArr[0] = new MyClass(100);

ArrayList<MyClass> objList = new ArrayList<MyClass>();
objList.add(new MyClass(1));
objList.add(new MyClass(10));
objList.add(new MyClass(100));

Arrays.sort(objArr); //오름차순 정렬
Arrays.sort(objArr, new MyClassComparator()); //내림차순 정렬, MyClass의 경우 멤버 변수는 모두 Primitive Type 이지만, 클래스 자체는 Reference Type 이기 때문에 가능하다.

Collections.sort(objList); //오름차순 정렬
Collections.sort(objList, new MyClassComparator()); //내림차순 정렬

PriorityQeueu<MyClass> pq = null;
pq = new PriorityQueue<MyClass>(); //오른차순으로 우선순위 결정
pq = new PriorityQueue<MyClass>(new MyClassComparator()); //내림차순으로 우선순위 결정
```

