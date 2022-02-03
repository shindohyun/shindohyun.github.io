---
layout: default
title: ViewModel
grand_parent: Android
parent: AAC
permalink: /docs/android/aac/viewmodel
nav_order: 5
---

# ViewModel
{: .no_toc }

---

- *Activity나 Fragment 같은 UI 컨트롤러*의 **수명 주기를 고려**하여 관련  **데이터를 획득, 저장, 관리**하도록 설계된 클래스
- **UI 컨트롤러와 앱의 다른 요소(예: 비즈니스 로직 클래스) 사이의 통신을 처리**
- **UI 컨트롤러의 수명주기와 함께한다.**
- **메모리 내에 저장**하기 때문에 *읽기, 쓰기 속도가 빠르다.*
- **구성 변경 발생에도 유지된다.**  
  구성 변경이 발생하면 기존 소유자가 파괴되지만 새로운 소유자가 기존 ViewModel에 연결되기 때문에 ViewModel이 갖는 데이터는 유지된다.
- **시스템에 의한 프로세스 종료에는 유지되지 않는다.**  
  때문에 `onSaveInstanceState()` 와 결합하여 사용해야 한다.
- LiveData 또는 Data Binding 을 통해 변경사항 정보를 노출한다.  
  UI 컨트롤러가 ViewModel의 변경사항을 관찰할 수 있어야하기 때문에
- View 계층 구조에 접근하거나 View, Lifecycle, Context, UI 컨트롤러(Activity, Fragment) UI 컨트롤러에 대한 참조를 보유해서는 안된다.

ViewModel의 목적은 UI 컨트롤러의 데이터를 캡슐화하여 구성이 변경되어도 데이터를 유지하는 것.



> **UI 컨트롤러의 역할**:  
> 데이터 출력, 사용자 작업에 반응, 권한 요청, 운영체제 커뮤니케이션 처리 정도의 역할만을 할당해야한다. 데이터를 다루는(획득, 저장, 관리) 로직은 분리되는 것이 좋다.

> **구성 변경**:  
> 구성 변경이되면 UI 컨트롤러는 다시 생성(`onDestroy()` -> `onCreate()`)된다. 이 과정에서 UI 컨트롤러는 필요한 데이터를 얻고 필요한 메소드를 호출하는 과정을 반복하게 되어 리소스가 낭비된다.
>
> **구성 변경 발생 요인**:  
> 화면 회전, 키보드 가용성, 다중창 모드 활성화, Locale 변경



# ViewModelStore Class

ViewModel 인스턴스를 저장하는 `HashMap` 을 멤버 변수로 갖는다.



# ViewModelStoreOwner Interface

이 인터페이스를 구현하는 클래스의 책임은 다음과 같다.

- ViewModelStore 소유자로서 현재 갖고 있는 ViewModelStore 인스턴스를 제공해야 한다.
- 구성 변경에 의한 파괴인지 확인해야 한다.  
  - 완전한 파괴인 경우  
    위에서 언급한 것 처럼 ViewModel은 UI 컨트롤러의 수명주기를 따르기 때문에 `onDestroy()` (사용자가 Activity 또는 Fragment 를 종료하는 경우 또는 개발자가 `finish()`를 호출하는 경우 호출됨) 에서 `ViewModelStore.clear()` 를 호출하여 모든 ViewModel 을 정리해야 한다. 이때 `ViewModel.onCleared` 메서드가 호출된다.
  - 구성 변경에 의한 파괴인 경우  
    Recreate 되어야 하기때문에 현재 ViewModelStore 인스턴스를 유지해야 한다. 메모리에 남아있는 ViewModel을 새로운 소유자와 연결시킨다.

`AppCompatActivity` 와 `Fragment` 에는 이미 ViewModelStoreOwner 인터페이스가 구현되어 있다.



# ViewModelProvider Class

 `ViewModelProvider.get` 메소드를 통해 ViewModel 을 제공한다.

ViewModelStoreOwner가 갖는 ViewModelStore에서 특정 ViewModel 인스턴스를 가져온다.(`HashMap` key로 조회) 해당 인스턴스가 존재하지 않는 경우 ViewModelProvider.Factory 객체로 ViewModel을 인스턴스화 하고, ViewModelStore에 추가한다.



# 사용법

```groovy
// build.gradle
dependencies {
    ...
    implementation 'androidx.lifecycle:lifecycle-viewmodel:2.3.1'
}
```

```java
public class MainModel extends ViewModel {
  private User user;

  public MainModel(){

  }

  public void setUser(User user){
    this. user = user;
  }

  public User getUser() {
    if (user == null) {
      user = new User();
      loadUser();
    }
    return user;
  }

  public void addUserAge(int add) {
    // 비즈니스 로직 수행 및 데이터 업데이트를 처리한다.
    user.setAge(user.getAge()+add);
  }

  private void loadUser() {
    // 실제 개발 시 데이터베이스 혹은 네트워크 통신의 비동기 처리로 필요한 데이터를 가져온다.
    user.setName("Dohyun");
    user.setAge(30);
  }
}
```

```java
public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ...
    MainModel model = new ViewModelProvider(this).get(MainModel.class);

    textViewAge.setText(Integer.toString(model.getUser().getAge()));

    button.setOnClickListener(view->{
      model.addUserAge(1);
      textViewAge.setText(Integer.toString(model.getUser().getAge()));
    });
  }
}
```



# Fragment 에서 Activity ViewModel 사용하기

현재 Fragment가 속해있는 Activity를 ViewModelProvider의 생성자 매개변수(ViewModelStoreOwner)로 넘겨주면 해당 Activity가 소유한 ViewModel 인스턴스를 가져올 수 있다.

```java
 MainModel model = new ViewModelProvider(requireActivity()).get(MainModel.class);
```

이를 이용해 **같은 Activity에 속한 여러 Fragment에서 ViewModel을 공유**할 수 있다.



# ViewModel과 LifecycleOwner

ViewModel은 LifecycleOwner 보다 오래 지속되도록 설계되었다. 따라서 ViewModel에는 LifecycleObserver가 포함될 수는 있지만 *Observable의 변경사항을 관찰해서는 안된다.*



# ViewModel과 Application Context

ViewModel에서 Application의 Context가 필요한 경우, `AndroidViewModel` 클래스를 상속 받고 생성자의 매개변수로 넘어온 `Application` 객체를 통해 Context를 얻는다.

다음과 같이 Application 객체를 전달하여 생성한다.

```java
MainViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
```



# ViewModel로 Loader 대체하기

> **Loader**: 효율적으로 데이터를 로드하기 위해 사용한다. 비동기 방식의 데이터 로드, 데이터 변경 사항 모니터링 등의 특징을 갖는다. (API 28 부터 사용 중단됨) 

- ViewModel: 구성 변경에도 데이터 유지
- LiveData: 수명 주기 인식, 데이터 로드 방식 제공
- Room: 데이터 변경 사항 모니터링

데이터베이스가 변경되면 Room에서 LiveData에 변경을 알리고, LiveData는 수정된 데이터로 UI를 업데이트한다.



# `Observable` 을 구현한 ViewModel

- Data Binding 라이브러리와 함께 ViewModel에서 LiveData를 사용하는 것 처럼 데이터 변경을 다른 구성요소에 알릴 수 있다. 단, 수명 주기 인식은 할 수 없다.
- LiveData를 사용할 때 보다 Data Binding을 더 세밀하게 제어할 수 있다.  
  - 데이터 변경 시 알림 제어
  - 맞춤 메서드로 양방향 데이터 결합의 속성 값 설정

`Observable` 인터페이스를 구현하는 경우 `addOnPropertyChangedCallback()` 과 `removeOnPropertyChangedCallback()` 메서드 안에서 속성 변경 알림을 받을 리스너를 직접 추가, 삭제해야 한다. 또한 속성 변경 알림을 위한 메서드를 정의하고 알림 전송 시점에 해당 메소드를 직접 호출해줘야 한다.

`BaseObservable` 클래스는 `Observable` 인터페이스를 구현하고 이런 리스너 추가, 삭제와 속성 변경 알림 메서드를 제공하는 편의 클래스이다. 

ViewModel을 상속 받아야하기 때문에 이 클래스를 상속 받을 순 없고, `Observable` 을 직접 구현해야 한다. 속성 변경 알림 메서드는 `BaseObservable` 에서 제공하는 메서드(`notifyChange()`, `notifyPropertyChanged(int fieldId)`)를 따라 작성하면 된다.

```java
public class MainViewModel extends ViewModel implements Observable {
  private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
  private Integer odd;
  private Integer even;

  // 속성 변경사항 구독 시 콜백 메서드, 속성 변경 알림을 받을 리스너를 추가
  @Override
  public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    callbacks.add(callback);
  }

  // 속성 변경사항 구독 취소 시 콜백 메서드, 속성 변경 알림을 받을 리스너를 삭제
  @Override
  public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    callbacks.remove(callback);
  }

  // 이 인스턴스의 모든 속성이 변경되었음을 리스너에게 알림, 알림이 전송되는 시점을 개발자가 직접 결정
  public void notifyChange() {
    callbacks.notifyCallbacks(this, 0, null);
  }

  // 특정 속성이 변경되었음을 리스너에게 알림, 알림이 전송되는 시점을 개발자가 직접 결정
  // 변경되는 속성에 대한 getter는 @Bindable 주석으로 표시되어
  // fieldId 매개 변수로 사용할 BR 클래스의 필드를 생성해야합니다.
  public void notifyPropertyChanged(int fieldId) {
    callbacks.notifyCallbacks(this, fieldId, null);
  }

  @Bindable
  public Integer getOdd(){
    if(odd == null) odd = 1;
    return odd;
  }

  @Bindable
  public Integer getEven(){
    if(even == null) even = 2;
    return even;
  }

  public void nextOdd(){
    odd += 2;
    notifyPropertyChanged(BR.odd);
  }

  public void nextEven(){
    even += 2;
    notifyPropertyChanged(BR.even);
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

