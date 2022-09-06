---
layout: default
title: LiveData
grand_parent: Development
parent: Android
permalink: /docs/development/android/livedata
nav_order: 17
---

# LiveData
{: .no_toc }

---

- **수명 주기를 인식하는 Observable Data Holder Class**  
  *수명 주기 인식의 특징과 Observable Data의 특징을 모두 갖는다.*

- `LiveData` 는 **`LifecycelOwner` 인터페이스를 구현한 객체** (예: `AppCompatActivity,Fragment`)와 **관찰자인 `Observer` 객체**를 *한 쌍으로* 등록한다.(`LiveData.observe` 메소드를 사용) 이때 관찰자는 `LifecycleOwner`가 소유하는 `Lifecycle` 객체에 결합되고 `LiveData` 를 구독한다.
- `LiveData` 는 *데이터가 변경될 때 `LifecycleOwner` 객체의 수명 주기를 인식*하고, 객체가 **활성 상태(`STARTED`, `RESUMED`)라면 이에 연결된 모든 관찰자들을 트리거** 한다.(*최신 데이터 유지*)  
  추가적으로 비활성 상태에서 활성 상태로 변경될 때에도 트리거한다. (여러 번 활성 상태로 변경되는 경우 마지막 변경에 대해서만)

- 수명 주기가 끝나면(`DESTROYED`) 관찰자는 자동 삭제된다. (*메모리 누수 없음*)
- `LiveData.observeForever` 메소드를 사용하면 관찰자 객체만 등록하게 된다. 해당 관찰자는 수명 주기 모니터링 없이 항상 데이터 변경에 대한 알림을 받는다.
- `LiveData.removeObserver` 메소드를 사용하면 관찰자를 삭제할 수 있다. 특정 `Observer` 객체만 제거할 수도 있고, `LifecycleOwner` 객체가 갖는 모든 관찰자를 제거할 수도 있다.
- 일반적으로 `ViewModel` 클래스 내에서 `LiveData` 객체를 사용한다. (*구성 변경에도 데이터 유지*)
- 다음 두 가지 이유로 보통 `onCreate()` 에서 `LiveData` *객체 관찰을 시작*한다.
  - `onResume()` 메서드에서 중복 호출을 피하기 위해
  - 활성 상태가 되는 즉시(`STARTED`) `LiveData` 객체에서 최신 값을 수신하기 위해, 이는 구성 변경으로 인해 Activity 또는 Fragment가 다시 생성되면 최신 데이터를 즉시 얻게한다.



# Observer Class

- 데이터가 변경되었을 때, `onChanged(T t)` 메소드를 통해 LiveData로 부터 콜백을 받을 수 있는 클래스.
- 일반적으로 Activity나 Fragment 같은 UI 컨트롤러에 `Observer` 객체를 만들고 데이터가 변경될 때마다 관찰자가 대신 UI를 업데이트 한다.(*UI와 데이터 일치 보장*)



# 사용법

```java
public class MainViewModel extends ViewModel {
  private MutableLiveData<User> user;

  public MutableLiveData<User> getUser() {
    if (user == null) {
      user = new MutableLiveData<User>();
      loadUser();
    }
    return user;
  }

  public void setUser(MutableLiveData<User> user) {
    this.user = user;
  }

  public void addUserAge(int add) {
    // 비즈니스 로직 수행 및 데이터 업데이트를 처리한다.
    User value = user.getValue();
    value.setAge(value.getAge()+add);
    user.setValue(value);
  }

  private void loadUser() {
    // 실제 개발 시 데이터베이스 혹은 네트워크 통신의 비동기 처리로 필요한 데이터를 가져온다.
    User value = new User("Dohyun", 30);
    user.setValue(value);
  }
}
```

`MutableLiveData` 는 값을 수정하기 위한 메소드로 `setValue(T)` 와 `postValue(T)` 를 제공한다. 이 메소드들이 호출되면 관찰자를 트리거한다. (`Observer.onChanged(T)` 메서드 호출)

`setValue(T)` 는 기본 스레드에서 호출하는 반면, `postValue(T)` 는 작업자 스레드에서 값을 수정해야 하는 경우 호출한다.

```java
public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ...
    MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);

    final Observer<User> userObserver = new Observer<User>() {
      @Override
      public void onChanged(User user) {
        // UI 업데이트
        textView.setText(user.getName() + ", " + Integer.toString(user.getAge()));
      }
    };

    model.getUser().observe(this, userObserver);

    button.setOnClickListener(view -> {
      model.addUserAge(1);
    });
  }
}
```

`LiveData` 객체에 값이 설정되어 있지 않다면 데이터 변경을 인지하지 못하므로 관찰자의 `onChange()` 메서드가 호출되지 않는다.



# LiveData 확장

```java
public class CustomLiveDataUser extends LiveData<User> {
  ...
    
  @Override
  protected void onActive() {
    // 활성 상태의 관찰자 수가 1이 될 때 호출
  }

  @Override
  protected void onInactive() {
    // 활성 상태의 관찰자 수가 0이 될 때 호출
  }
}
```

일반적인 `LiveData` 객체와 같은 방법으로 사용한다.

`onActive()`, `onInactive()` 메소드는 활성 상태인 관찰자 개수에 따라 호출되기 때문에 *무거운 리소스를 관리할 때 유용하게 사용된다.*

`LiveData` 클래스를 **싱글톤 패턴**으로 구현하면 여러 관찰자가 `LiveData` **객체를 공유**할 수 있다. 예를들어, 특정 시스템 서비스에 연결되는 싱글톤 `LiveData` 클래스를 만들면 이를 구독하는 모든 관찰자들은 해당 리소스에 접근 가능하다. (*리소스 공유*)

```java
public class CustomLiveDataUser extends LiveData<User> {
  private static CustomLiveDataUser instance;
  ...
    
  @MainThread
  public static CustomLiveDataUser get() {
    if (instance == null) {
      instance = new CustomLiveDataUser();
    }
    return instance;
  }
  
  private CustomLiveDataUser(){}
}
```

```java
public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ...
    CustomLiveDataUser.get().observe(this, user -> {
      textView.setText(user.getName() + ", " + Integer.toString(user.getAge()));
    }); 
  }
}
```



# LiveData 변환

`Transformations` 클래스를 사용해 `LiveData` 를 변환할 수 있다.

```java
public class MainViewModel extends ViewModel {
  private MutableLiveData<User> user;
  private LiveData<String> intro;
  ...
    
  // map
  public LiveData<String> getIntro1(){
    if (intro == null) {
      intro = Transformations.map(user, paramUser -> {
        return "My name is " + paramUser.getName() + " and I'm " + Integer.toString(paramUser.getAge()) + " y.o.";
      });
    }
    return intro;
  }
  
  // switchMap
  public LiveData<String> getIntro2(){
    if (intro == null) {
      intro = Transformations.switchMap(user, paramUser -> {
        return makeIntro(paramUser);
      });
    }
    return intro;
  }

  public LiveData<String> makeIntro(User user){
    MutableLiveData<String> intro = new MutableLiveData<String>();
    intro.setValue("My name is " + user.getName() + " and I'm " + Integer.toString(user.getAge()) + " y.o.");
    return intro;
  }
}
```

```java
public class MainActivity extends AppCompatActivity {
  private MainViewModel model;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ...
    model.getIntro1().observe(this, intro -> {
      textView3.setText(intro);
    });

    button.setOnClickListener(view -> {
      model.addUserAge(1); // user가 변경될 때마다 intro도 변경된다.
    });
  }
}
```

- `LiveData<Y> map(LiveData<X> source, Function<X, Y> mapFunction)`  
  `source` 의 각 값에 `mapFunction` 을 적용하고, `source` 에 매핑된 `LiveData` 를 반환한다. **`source` 의 값이 변경되면 반환되는 `LiveData` 의 값도 변경된다.**
- `LiveData<Y> switchMap(LiveData<X> source, Function<X, LiveData<Y> switchMapFunction)`  
  `Transformations.map` 과 동일하지만 함수 결과값 자체가 `LiveData` 를 반환해야한다.

변환은 느리게 계산되며 반환된 `LiveData` 가 **관찰 될 때만 실행**된다. 수명주기 동작은 `source` 에서 반환 되는 `LiveData` 항목으로 전파된다.

두 번째 매개변수로 받는 함수는 메인 스레드에서 실행된다.



# LiveData 병합

`MutableLiveData` 의 서브 클래스인`MediatorLiveData` 클래스를 사용하면 여러 `LiveData` 를 병합할 수 있다. (데이터의 결합을 의미하진 않는다.)

`MediatorLiveData` 는 여러 개의 `LiveData` 객체를 관찰하고 `Observer.onChanged` 이벤트에 반응할 수 있다. 때문에 `MediatorLiveData` 객체의 관찰자는 `MediatorLiveData` 에 추가된 `LiveData` 객체 중 하나라도 변경될 때마다 트리거된다. (단, `MediatorLiveData` 가 활성 상태인 경우에만)

```java
public class MainViewModel extends ViewModel {
  private MutableLiveData<String> odd = new MutableLiveData<>();
  private MutableLiveData<String> even = new MutableLiveData<>();
  private MediatorLiveData<String> num = new MediatorLiveData<>();

  public MutableLiveData<String> getOdd(){return odd;}
  public MutableLiveData<String> getEven(){return even;}
  public MediatorLiveData<String> getNum(){
    num.addSource(odd, value->{
      num.setValue(value);
    });

    num.addSource(even, value->{
      num.setValue(value);
    });
    return num;
  }
}
```

```java
public class MainActivity extends AppCompatActivity {
  private MainViewModel model;
  static int i;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ...
    model.getEven().setValue("0");
    model.getOdd().setValue("1");
    i = 2;

    model.getNum().observe(this, value->{
      textView.setText(value);
    });

    button.setOnClickListener(view -> {
      MutableLiveData<String> liveData = (i % 2 == 0) ? model.getEven() : model.getOdd();
      String value = liveData.getValue();
      value += " ";
      value += Integer.toString(i);
      liveData.setValue(value);
      i++;
    });
  }
}
```



# DataBinding 과 함께 사용하기

DataBinding의 Observable Field 대체하기

```java
public class MainViewModel extends ViewModel {
  private MutableLiveData<Integer> odd = new MutableLiveData<>();
  private MutableLiveData<Integer> even = new MutableLiveData<>();

  public MutableLiveData<Integer> getOdd(){
    if(odd.getValue() == null) odd.setValue(1);
    return odd;
  }

  public MutableLiveData<Integer> getEven(){
    if(even.getValue() == null) even.setValue(2);
    return even;
  }

  public void nextOdd(){
    odd.setValue(odd.getValue() + 2);
  }

  public void nextEven(){
    even.setValue(even.getValue() + 2);
  }

  public void onClickNextOdd(TextView textView) {
    nextOdd();
  }

  public void onClickNextEven(TextView textView) {
    nextEven();
  }
}
```

```java
public class MainActivity extends AppCompatActivity {
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);
    binding.setModel(model);
    binding.setLifecycleOwner(this);
  }
}
```

> **`ViewDataBinding.setLifecycleOwner(LifecycleOwner)`**:  
> ViewDataBinding 에서 LiveData의 변경을 관찰하는데 사용해야하는 LifecycleOwner를 설정해야 한다. 이를 설정하지 않으면 DataBinding의 표현식에서 사용된 LiveData가 관찰되지 않는다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
  <data>
    <import type="com.dohyun.myapplication.MainViewModel"/>
    <variable name="model" type="MainViewModel" />
  </data>

  <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

    <LinearLayout
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:orientation="horizontal">
      <TextView
                android:id="@+id/odd"
                android:text="@{model.odd.toString()}"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
      <Button
              android:onClick="@{()->model.onClickNextOdd(odd)}"
              android:text="next odd"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"/>
    </LinearLayout>
    <LinearLayout
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:orientation="horizontal">
      <TextView
                android:id="@+id/even"
                android:text="@{model.even.toString()}"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
      <Button
              android:onClick="@{()->model.onClickNextEven(even)}"
              android:text="next even"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"/>
    </LinearLayout>
  </LinearLayout>
</layout>
```

