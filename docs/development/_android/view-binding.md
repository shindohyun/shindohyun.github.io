---
layout: default
title: View Binding
grand_parent: Development
parent: Android
permalink: /docs/development/android/view-binding
nav_order: 12
---

# View Binding
{: .no_toc }

---

# build.gradle 설정

```groovy
android {
	viewBinding{
		enabled=true
	}
}
```

- 모듈에 포함된 모든 레이아웃 XML 파일에 대해 Binding Class 가 자동 생성
- 각 XML 파일 이름을 파스칼 표기법으로 변환하여 Binding Class 이름 지정 (접미사 '-Binding' 추가)
- 각 XML 파일에 구현되어 있는 Root View 와 ID 값을 갖는 모든 View 에 대한 참조 포함



# Layout XML File

```xml
<!-- activity_main.xml -->

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="match_parent"
	android:layout_height="match_parent" >
  
	<TextView
    android:id="@+id/textView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello" />

  <TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text=" World!" />
  
</LinearLayout>
```

레이아웃 리소스를 액티비티와 연결하기 위해서 기존에 Root View 에 설정해야했던 `tools:context="<Activity Class Name>"` 속성은 더이상 필요하지 않다.

ID 속성을 지정하지 않은 두번째 TextView 는 참조할 수 없다.



# Activity 에서 Binding Class 사용하기

```java
// MainActivity.java

public class MainActivity extends AppCompatActivity{
  private ActivityMainBinding binding;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    
    binding.textView.setText("Hi");
  }
}
```



# 생성된 Binding Class 분석

Binding Class 는 `모듈 패키지/databinding` 아래에 컴파일러에 의해 자동으로 생성된다.

위에서도 언급했듯이 각 XML 파일 이름에 접미사 '-Binding' 이 추가된 상태에서 *파스칼 표기법*으로 변환된 이름으로 Binding Class 가 자동 생성된다. 또한 레이아웃의 각 View 가 지닌 ID 값은 *카멜 표기법*으로 변환되어 해당 View 에 대한 인스턴스명으로 사용된다.

```java
// ActivityMainBinding.java

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView textView;

  private ActivityMainBinding(@NonNull LinearLayout rootView, @NonNull TextView textView) {
    this.rootView = rootView;
    this.textView = textView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    int id;
    missingId: {
      id = R.id.textView;
      TextView textView = rootView.findViewById(id);
      if (textView == null) {
        break missingId;
      }

      return new ActivityMainBinding((LinearLayout) rootView, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

```

1. `inflate(...)` 메소드를 호출하게 되면 내부에서는 `inflater` 를 통해 상응하는 XML 파일의 레이아웃 리소스를 `View` 객체로 전개하여 `bind(...)` 메소드에 전달한다. 이때 `View` 객체는 Root View 이다.
2. `bind(...)` 에서는 전달 받은 `View` 객체를 통해 하위의 모든 View 를 찾아 인스턴스화 한다. 이때 리소스 ID 를 사용하기 때문에 레이아웃 리소스 파일에서 ID 속성이 명시되어 있지 않으면 인스턴스화 할 수 없다. 또한 ID 값을 카멜 표기법으로 변환하여 해당 View 에 대한 인스턴스의 이름을 결정짓게 된다.
3. 마지막으로, 전개된 Root View 와 인스턴스화 된 모든 View 를 생성자에 전달하면서 Binding 객체를 생성한다.



# Fragment 에서 Binding Class 사용하기

```java
// SubFragment.java

public class SubFragment extends Fragment{
  private FragmentSubBinding binding;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentSubBinding.inflate(inflater, container, false);
    View view = binding.getRoot();

    binding.textView.setText("Hi");
    
    return view;
}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
  }
}
```

`onDestroyView()` 에서 Binding 객체를 정리해야한다.

> **참고**: 
>
> 프래그먼트를 특정 ViewGroup 에 바로 부착하지 않기 위해 `attachToRoot` 를 `false` 로 설정한다.
>
> `ViewBinding` 클래스에 정의된 `inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot)` 메소드에 전달된 파라미터 중 `root` 가 `null` 이 아니고 `attachToRoot` 는 `false` 값을 갖는 경우,  `root` 는 전개되는 `View` 에 설정되어 있는 `LayoutParams` 값을 올바르게 만들기 위해서만 사용된다.



# View Binding 무시하기

특정 레이아웃에서는 View Binding 를 피하고 싶은 경우, 아래와 같이 해당 레이아웃 파일의 Root View 에 `tools:viewBindingIgnore="true"`속성을 추가한다.

```xml
<!-- activity_main.xml -->

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
 	xmlns:tools="http://schemas.android.com/tools"
  android:layout_width="match_parent"
	android:layout_height="match_parent" 
	tools:viewBindingIgnore="true" >
  ...
```

컴파일러는 해당 레이아웃 XML 파일에 상응하는 Binding Class 파일을 생성하지 않는다.

