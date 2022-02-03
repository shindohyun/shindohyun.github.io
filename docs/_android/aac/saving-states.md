---
layout: default
title: 상태 저장
grand_parent: Android
parent: AAC
permalink: /docs/android/aac/saving-states
nav_order: 7
---

# 상태 저장
{: .no_toc }

---


# onSaveInstanceState()

- **디스크에 직렬화되어 저장**되기 때문에 *읽기, 쓰기 속도가 느리다.*
- 직렬화/역직렬화 과정이 *메인 스레드에서 진행* 된다.
- 때문에 **Primitive Type 또는 문자열 등 단순하고 작은 데이터를 저장해야 한다.**  
  UI 상태를 저장 및 복원하기 위해 사용되는 경우 필수적인 최소한의 데이터를 저장한다.
- **구성 변경 및 시스템에 의한 프로세스 종료 시에도 유지된다.**  
  Activity가 **정지**되기 시작하면 시스템은 `onSaveInstanceState()` 를 호출한다. 이틀 통해 Instance State Bundle에 UI상태 정보를 저장할 수 있다.
- **`onSaveInstanceState()` 를 통해 저장된 데이터는 구성 변경 또는 시스템에 의한 프로세스 종료 후 Recreate 될 때에만 복원된다.**  
  구성 변경 또는 시스템에 의한 프로세스 종료**와** 사용자에 의한 명시적 종료(사용자가 Activity 및 Fragment 를 종료하는 경우) 또는 개발자에 의한  `finish()` 메서드 호출을 구분할 수 있다. (ViewModel에서도 구성 변경에 의한 종료인지 구분하고 구성 변경시 새로운 소유자가 기존 ViewModel에 연결된다.)
- View의 상태 정보를 저장하려면 `onSaveInstanceState()` 를 *재정의*하여 Instance State Bundle에 값을 저장한다.  

> **시스템에 의한 프로세스 종료**:  
> RAM 여유 공간이 없는 경우 시스템은 특정 Activity를 실행하는 **프로세스를 종료**시킨다.(시스템은 Activity를 직접 종료하지 않는다.) 활발히 활동 중인 Activity의 프로세스 일수록 종료될 가능성이 낮아진다. 반면, 정지 상태와 같이 활동이 적은 Activity는 종료될 가능성이 크다. **때문에 시스템은 Activity 정지 시 UI 상태 정보를 저장하도록 `onSaveInstanceState()` 메서드를 호출한다.**
>
> ```
> // 에c뮬레이터 사용 시 시스템에 의한 종료 테스트 (홈 버튼 누른 후 아래 명령어 실행)
> adb shell am kill [package name]
> ```

> **`onSaveInstanceState()` 의 기본 구현**:  
> `onSaveInstanceState()` 메서드는 기본적으로 구성 변경 및 프로세스 중단 시 View의 상태 정보를 저장하도록 구현되어 있다. (**주의: 이때, `android:id` 속성으로 ID 값이 부여된 View에 대해서만 상태 정보를 저장한다.**) 예를들어 `EditText` 는 텍스트를 저장하고 `ListView` 는 스크롤 위치를 저장한다. *때문에 상태 정보가 유지되지 않는 View 에 대해서만 추가적으로 작업해주면 된다.*



### Instance State

**UI의 이전 상태를 복원하기 위해 저장된 데이터**를 뜻한다. 구성 변경이나 시스템에 의한 프로세스 종료 후 Activity를 복원할 때 시스템은 이 Instance State를 사용하여 새로운 Activity의 인스턴스를 생성한다.

Instance State는 `Bundle` 객체에 저장되는 키-값의 쌍이다. 시스템은 레이아웃의 각 View 객체와 관련된 정보를 Instance State Bundle에 저장함으로써 UI 상태를 저장한다.



### UI 상태 저장하기

```java
public class MainActivity extends AppCompatActivity {
  private static final String STATE_CURRENT_SCORE = "CURRENT SCORE";
  private static int score;
	...
  
  // 구성 변경 또는 프로세스 중단 시 호출된다.
  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    // 추가적인 상태 정보를 Instance State Bundle 에 저장
    outState.putInt(STATE_CURRENT_SCORE, score);

    // super 클래스를 호출해야만 View 상태를 저장할 수 있다.
    // 기본 구현을 호출하여 기본적인 View 계층 구조의 상태를 저장한다. (EditText, ListView 등)
    super.onSaveInstanceState(outState);
  }
}
```



### UI 상태 복원하기

```java
public class MainActivity extends AppCompatActivity {
  private static final String STATE_CURRENT_SCORE = "CURRENT SCORE";

  private static int score;
  private TextView textView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main); // 항상 먼저 호출되어야한다.

    textView = findViewById(R.id.textView);
   	...
      
    // 구성 변경 또는 시스템에 의한 프로세스 종료로 인하여
    // 파괴 된 인스턴스를 다시 만들고 있는지(Recreate) 확인
    if (savedInstanceState != null) {
      // 저장된 상태 정보로 멤버 초기화 (복원)
      score = savedInstanceState.getInt(STATE_CURRENT_SCORE);
    } else {
      // 기본값으로 멤버 초기화
      score = 0;
    }
    textView.setText(Integer.toString(score));
    ...
  }
  
  ...
}
```

```java
// onRestoreInstanceState() 메서드를 사용하여 복원하는 경우
public class MainActivity extends AppCompatActivity {
  private static final String STATE_CURRENT_SCORE = "CURRENT SCORE";

  private static int score;
  private TextView textView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView = findViewById(R.id.textView);
    ...

    score = 0;
    textView.setText(Integer.toString(score));
    ...
  }
  
  // Recreate 시에만 onStart()와 onPostCreate(Bundle) 사이에서 호출된다.
  // Bundle이 null인지 확인할 필요가 없다.
  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    // 항상 super 클래스를 먼저 호출하여 기본 구현에서 View 계층 구조의 상태를 복원할 수 있도록 한다.
    super.onRestoreInstanceState(savedInstanceState);

    //저장된 상태 정보로 멤버 초기화 (복원)
    score = savedInstanceState.getInt(STATE_CURRENT_SCORE);
    textView.setText(Integer.toString(score));
  }
}
```



# SavedStateRegistry

**UI 컨트롤러의 저장된 상태에 구성요소를 결합시켜 구성요소가 상태 저장에 기여할 수 있도록 해준다.**



### SavedStateRegistryOwner

- UI 컨트롤러(Activity, Fragment)가 구현하고 있다.
- `getSavedStateRegistry()` 메서드를 통해 `SavedStateRegistry` 를 가져온다.



### SavedStateRegistry

- **키와 함께 소유자(UI 컨트롤러)와 제공자(구성요소)를 연결하고 상태 복원 시 그 키를 통해 해당 제공자가 기여한 저장된 상태를 가져올 수 있다.**



### SavedStateRegistry.SavedStateProvider

- UI 컨트롤러(`SavedStateRegistryOwner`)의 저장된 상태에 결합될 **구성요소가 구현**해야한다.
- `saveState()` 메서드를 통해 저장되어야 하는 상태가 포함된 Bundle을 반환한다.  
  `SavedStateRegistry` 는 UI 컨트롤러 수명 주기 중 상태 저장 단계(구성 변경 또는 시스템에 의한 프로세스 중단)에서 이 메서드를 호출한다.  
  위 `onSaveInstanceState()` 와 동일한 Bundle 데이터를 저장한다. 때문에 동인한 데이터 제한을 갖는다. (단순하고 작아야함)



### 사용법

```java
public class SavedStateProviderImpl implements SavedStateRegistry.SavedStateProvider {
  private static final String TAG = "SavedStateProviderImpl";
  private static final String PROVIDER_KEY = "SavedStateProviderImpl";
  private static final String STATE_CURRENT_SCORE = "CURRENT SCORE";
  private int score;

  public SavedStateProviderImpl(SavedStateRegistryOwner savedStateRegistryOwner){
    score = 0;
    savedStateRegistryOwner.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
      if (event == Lifecycle.Event.ON_CREATE) {
        // 소유자(UI 컨트롤러)의 SavedStateRegistry 객체를 가져온다.
        SavedStateRegistry savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();

        // 소유자와 제공자(구성요소)를 키로 연결한다.
        savedStateRegistry.registerSavedStateProvider(PROVIDER_KEY, this);

        // 키를 이용해 제공자가 기여한 상태 값을 가져온다.
        Bundle state = savedStateRegistry.consumeRestoredStateForKey(PROVIDER_KEY);

        if (state != null) {
          score = state.getInt(STATE_CURRENT_SCORE);
          Log.d(TAG, "owner life cycle ON_CREATE: " + Integer.toString(score));
        }
      }
    });
  }

  @NonNull
  @Override
  public Bundle saveState() {
    // 상태 저장 단계(구성 변경 또는 시스템에 의한 프로세스 중단)에서 호출된다.
    Bundle bundle = new Bundle();
    bundle.putInt(STATE_CURRENT_SCORE, score);

    Log.d(TAG, "saveState: " + Integer.toString(score));
    return bundle;
  }

  public void addScore(int add){
    this.score += add;
    Log.d(TAG, "add score: " + Integer.toString(score));
  }
}
```

```java
public class MainActivity extends AppCompatActivity {
  private SavedStateProviderImpl savedStateProvider;
  ...
   
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    ...
    savedStateProvider = new SavedStateProviderImpl(this);
    button.setOnClickListener(view -> {
      savedStateProvider.addScore(1);
    });
  }
}
```



# UI 상태 관리 조합

- ViewModel: UI 컨트롤러에 표시되는 모든 데이터를 메모리에 저장
- `onSaveInstanceState()`: UI 컨트롤러 중단 후 복원 시 필요한 간단한 데이터를 저장
- 로컬 지속성: UI 컨트롤러를 열고 닫을 때 유지하기 위한 모든 데이터를 로컬 저장소에 저장, 검색 비용이 많이 듬

예) ViewModel에는 실시간으로 UI 컨트롤러에 출력되는 데이터를 저장하고 있다. 앱을 조작하면서 유지되어야 하는 데이터는 바로 바로 로컬 저장소에 저장 시킨다. 앱이 중단되는 경우 재시작 시 복원되어야 하는 *데이터의 ID 값*을 `onSaveInstanceState()` 에 저장한다. 실제 데이터는 로컬 저장소에 저장되어 있어 복원 시 ID 값으로 검색하여 가져온다.

- UI 컨트롤러가 처음으로 생성되는 경우: `onSaveInstanceState()` 의 Bundle과 ViewModel 객체는 비어있다.
- 시스템에 의해 종료 된 후 재시작하는 경우: `onSaveInstanceState()` 의 Bundle에 데이터가 저장되어 있고 ViewModel은 비어있다.
- 구성 변경 후: `onSaveInstanceState()` 의 Bundle과 ViewModel 객체에 데이터가 저장되어 있다.

*`onSaveInstanceState()` 의 Bundle은 ViewModel로 전달되어야 한다.*



# ViewModel의 저장된 상태 모듈

ViewModel은 시스템에 의한 프로세스 중단 시 폐기된다. 때문에 `onSaveInstanceState()` 와 결합하여 사용해야 한다. `onSaveInstanceState()` 는 UI 컨트롤러 관련 클래스에 종속된 메소드이다. 때문에 ViewModel이 `onSaveInstanceState()` 를 사용하기 위해서는 저장된 상태 모듈이 필요하다.



### `SavedStateHandle`

- 저장된 상태에 객체를 저장 및 검색(`set()`, `get()`)할 수 있게 하는 Key-Value Map
- `getListData()` 메소드를 사용하면 *LiveData로 래핑된 상태 값*을 가져올 수 있다.
- 시스템에 의한 프로세스 중단 시에도 유지된다.
- UI 컨트롤러의 다른 상태 저장 값과 함께 Bundle에 저장 및 복원 된다.

> **상태 저장이 지원하는 데이터**:  
> 상태 저장을 위한 Bundle과 SavedStateHandle에서 저장 가능한 데이터는 동일한 유형을 갖고, 단순하고 가벼운 값이여야 한다. 유형에는 Primitive type 또는 Bundle, Serializable, Parcelable 등이 있다.  
> 지원하지 않는 유형을 저장하려는 경우 Parcelable 또는 Serializable을 구현해야 한다.



### 사용법

```groovy
// build.gradle
dependencies {
  ...
  implementation 'androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.1'
  implementation 'androidx.fragment:fragment:1.2.0'
}
```

Fragment 1.2.0 부터 ViewModel의 생성자 인수로 SavedStateHandle을 사용할 수 있다.

```java
public class SavedStateViewModel extends ViewModel {
  private final String STATE_KEY_AGE = "AGE";
  private SavedStateHandle savedStateHandle;
  private LiveData<String> intro;
	
  // ViewModel의 생성자를 통해 SavedStateHandle 객체를 수신 받는다.
  public SavedStateViewModel(SavedStateHandle savedStateHandle) {
    this.savedStateHandle = savedStateHandle;

    // 동일한 키 값으로 저장된 상태값을 LiveData로 래핑하여 가져온다.
    // 키의 값이 변경되면 LiveData가 새 값을 수신한다.
    LiveData<Integer> age = savedStateHandle.getLiveData(STATE_KEY_AGE);
    intro = Transformations.switchMap(age, i ->{
      return makeIntro(i);
    });
  }

  public LiveData<String> getIntro(){
    return intro;
  }

  public LiveData<String> makeIntro(Integer age){
    MutableLiveData<String> info = new MutableLiveData<>();
    info.setValue("I am " + age.toString() + " y.o");
    return info;
  }

  public Integer getAge(){
    // 키의 값이 있는지 확인
    if(!savedStateHandle.contains(STATE_KEY_AGE)){
      setAge(0);
    }

    Integer age = savedStateHandle.get(STATE_KEY_AGE);
    if(age == null) age = 0;
    return age;
  }
  
  public void setAge(Integer age) {
    savedStateHandle.set(STATE_KEY_AGE, age);
  }
}
```

```java
public class MainActivity extends AppCompatActivity {
  ...
  SavedStateViewModel model;
  
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    ...
    model = new ViewModelProvider(this).get(SavedStateViewModel.class);

    final Observer<String> observer = new Observer<String>() {
      @Override
      public void onChanged(String intro) {
        textView.setText(intro);
      }
    };

    model.getIntro().observe(this, observer);

    button.setOnClickListener(view -> {
      Integer age = model.getAge();
      model.setAge(age + 1);
    });
  }
}
```

ViewModelProvider는 ViewModel에 적절한 SavedStateHandle을 제공한다.



# ViewModel의 저장된 상태 모듈 + SavedStateRegistry

위 *ViewModel의 저장된 상태 모듈*에서 `SavedStateHandle` 은 상태 데이터를 저장, 검색할 수 있지만(단순히 시스템에 의한 중단 시에도 상태를 유지하기 위한 용도이다.) *상태 저장 시점에서 자동으로 데이터를 처리하지 못한다.* 

이를 위해서는 UI 컨트롤러 단의 `onSaveInstanceState()` 에서 ViewModel에 작성된 상태 저장 메소드를 호출해야 한다. (위 예제의 경우 `setAge()` 메서드)

다른 방법으로는 구성요소가 상태 저장에 기여할 수 있도록 해주는 `SavedStateRegistry.SavedStateProvider` 를 결합시키는 것이다. UI 컨트롤러 수명 주기 중 상태 저장 단계(구성 변경 또는 시스템에 의한 프로세스 중단)에서 호출되는 `SavedStateProvider.saveState()` 메서드를 활용한다.`SavedStateHandle` 은 이 제공자를 설정할 수 있는 `setSavedStateProvider()` 메서드를 제공한다.



### 사용법

```java
public class SavedStateViewModel extends ViewModel {
  private final static String STATE_KEY_SCORE = "SCORE";
  private final static String STATE_KEY_PROVIDER= "PROVIDER";
  private SavedStateHandle savedStateHandle;
  private static MutableLiveData<Integer> score = new MutableLiveData<>();

  public SavedStateViewModel(SavedStateHandle savedStateHandle) {
    this.savedStateHandle = savedStateHandle;

    Bundle bundle = savedStateHandle.get(STATE_KEY_PROVIDER);
    if (bundle != null) {
      score.setValue(ScoreSavedStateProvider.restoreScore(bundle));
    }
    savedStateHandle.setSavedStateProvider(STATE_KEY_PROVIDER, new ScoreSavedStateProvider());
    if(score.getValue() == null) score.setValue(0);
  }

  public MutableLiveData<Integer> getScore(){
    return score;
  }

  public void setScore(MutableLiveData<Integer> score){
    this.score = score;
  }
  
  private static class ScoreSavedStateProvider implements SavedStateRegistry.SavedStateProvider {
    // 상태 저장 단계(구성 변경 또는 시스템에 의한 프로세스 중단)에서 호출된다.
    @NonNull
    @Override
    public Bundle saveState() {
      Bundle bundle = new Bundle();
      if (score != null) {
        bundle.putInt(STATE_KEY_SCORE, score.getValue());
      }
      return bundle;
    }

    @Nullable
    private static Integer restoreScore(Bundle bundle) {
      if (bundle.containsKey(STATE_KEY_SCORE)) {
        return bundle.getInt(STATE_KEY_SCORE);
      }
      return null;
    }
  }
}
```

```java
public class MainActivity extends AppCompatActivity {
  SavedStateViewModel model;
  ...
    
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    ...
    model = new ViewModelProvider(this).get(SavedStateViewModel.class);

    final Observer<Integer> observer = new Observer<Integer>() {
      @Override
      public void onChanged(Integer score) {
        textView.setText(Integer.toString(score));
      }
    };

    model.getScore().observe(this, observer);

    button.setOnClickListener(view -> {
      MutableLiveData<Integer> score = model.getScore();
      Integer s = score.getValue();
      score.setValue(score.getValue()+1);
      model.setScore(score);
    });
  }
}
```

