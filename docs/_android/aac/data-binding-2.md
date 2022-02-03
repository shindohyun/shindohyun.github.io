---
layout: default
title: Data Binding(2)
grand_parent: Android
parent: AAC
permalink: /docs/android/aac/data-binding-2
nav_order: 3
---

# Data Binding(2)
{: .no_toc }

---

# ViewStub 에 Data Binding 적용하기

Data Binding 라이브러리는 레이아웃에 ViewStub가 존재하는 경우 Binding Class 생성 시 이를 `ViewStubProxy` 로 대체한다. 

### ViewStubProxy

ViewStubProxy는 내부적으로 ViewStub 객체와 확장된 View 를 참조할 멤버변수를 모두 포함하고 있다. 따라서 전개 이전엔 ViewStub를, 전개 후엔 확장된 View를 접근할 수 있다. 또한 내부에서 `ViewStub.OnInflateListener` 를 생성하여 등록한다.

```java
// ViewStubProxy.java
...
private ViewStub mViewStub;
private ViewDataBinding mViewDataBinding; // 확장된 View에 대한 Binding Class
private View mRoot; // 확장된 View를 담는 멤버변수
...
// ViewStub의 전개가 성공한 후 호출되는 리스너
private OnInflateListener mProxyListener = new OnInflateListener() {
  @Override
  public void onInflate(ViewStub stub, View inflated) {
    // 확장된 View를 멤버변수로 저장
    mRoot = inflated;
    // 확장된 View에 Data Binding이 사용된 경우 해당 Binging Class에 결합하여 binding 객체 생성
    // 때문에 확장된 View에 대한 Binding Class에 접근할 수 있다.
    mViewDataBinding = DataBindingUtil.bind(mContainingBinding.mBindingComponent,
                                            inflated, stub.getLayoutResource());
    mViewStub = null;

    //아래 setOnInflateListener(ViewStub.OnInflateListener) 메서드로 등록된 리스너가 존재한다면 이벤트를 전달한다.
    if (mOnInflateListener != null) {
      mOnInflateListener.onInflate(stub, inflated);
      mOnInflateListener = null;
    }
    mContainingBinding.invalidateAll();
    mContainingBinding.forceExecuteBindings();
  }
};

public void setOnInflateListener(@Nullable OnInflateListener listener) {
  if (mViewStub != null) {
    mOnInflateListener = listener;
  }
}

public ViewDataBinding getBinding() {
  return mViewDataBinding;
}

public ViewStub getViewStub() {
  return mViewStub;
}
```

ViewStubProxy 사용 시 확장되는 View에 Data Binding이 사용된 경우 **전개 성공 후 데이터 결합이 진행되어야 한다.** 따라서 `ViewStubProxy.setOnInflateListener` 에 리스너를 등록하여 이벤트 발생 시점에 결합을 설정한다.

```xml
<!-- activity_main.xml -->
<?xml version="1.0" encoding="utf-8"?>
<layout ... >
	...
  <LinearLayout ... >
    <Button ... android:id="@+id/buttonInflateViewStub"/>
    <ViewStub
              android:id="@+id/viewStub"
              android:inflatedId="@+id/viewReplace"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:layout="@layout/view_replace"/>
  </LinearLayout>
</layout>
```

```xml
<!-- view_replace.xml -->
<layout ... >
  <data>
    <variable name="data" type="String" />
  </data>

  <LinearLayout ... >
    <TextView
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_gravity="center"
              android:text="@{data}"/>
  </LinearLayout>
</layout>
```

```java
// MainActivity.java

public class MainActivity extends AppCompatActivity {
  ActivityMainBinding binding;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ...
    binding.viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
      // ViewStub 전개 성공 후 호출
      @Override
      public void onInflate(ViewStub viewStub, View view) {
        // 확장된 View에 대한 데이터 결합 진행
        ViewReplaceBinding viewReplaceBinding = (ViewReplaceBinding) binding.viewStub.getBinding(); // 확장된 View에 대한 Binding Class 객체 참조
        viewReplaceBinding.setData("REPLACE VIEW");
      }
    });

    binding.buttonInflateViewStub.setOnClickListener(view->{
      binding.viewStub.getViewStub().inflate(); // ViewStub(ViewStubProxy) 전개
    });
  }
}
```



# RecyclerView Adapter 에 Data Binding 적용하기

```xml
<!-- activity_main.xml -->
<layout ... >
  <LinearLayout ... >
    <androidx.recyclerview.widget.RecyclerView ... android:id="@+id/recyclerView" />
  </LinearLayout>
</layout>
```

```xml
<!-- view_item -->
<?xml version="1.0" encoding="utf-8"?>
<layout ... >
  <data>
    <variable name="data" type="String" />
  </data>
  <LinearLayout ... >
    <TextView ... android:text="@{data}"/>
  </LinearLayout>
</layout>
```

```java
// MainActivity.java
public class MainActivity extends AppCompatActivity {
	...
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ... 
    ArrayList<String> items = new ArrayList<>();
    for(int i = 0; i < 100; i++){
      items.add(Integer.toString(i));
    }
    MyAdapter adapter = new MyAdapter(items);

    binding.recyclerView.setAdapter(adapter);
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }
}
```

```java
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
  ArrayList<String> items = new ArrayList<>();

  public MyAdapter(ArrayList<String> items){
    this.items = items;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    final String item = items.get(position);
    holder.getBinding().setData(item);
    holder.getBinding().executePendingBindings();
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder{
    private ViewItemBinding binding;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      binding = ViewItemBinding.bind(itemView);
    }

    public ViewItemBinding getBinding(){
      return binding;
    }
  }
}
```

결합된 데이터 변경 시 이에 대한 UI의 업데이트는 가까운 미래(프레임 변경 이전)에 예약된다. 때문에 데이터가 변경된다고 해서 관련된 View에 즉각적인 영향을 미치진 않는다.

이러한 메커니즘은 변경된 데이터에 대한 UI 업데이트를 한 번에 처리함으로써 성능상의 이점을 얻을 수 있다.

데이터 변경 시 즉각적인 View의 변화를 위해서는 `ViewDataBinding.executePendingBindings()` 메서드를 호출한다. `ViewDataBinding.executePendingBindings()` 메서드는 보류중인 모든 변경에 대한 UI를 업데이트한다. 단, 이는 UI 스레드에서 실행되기 때문에 자주 호출되지 않는것이 좋다.



# 백그라운드 스레드

Data Binding은 백그라운드 스레드에서 데이터 모델의 변경이 가능하다. 단, 컬렉션은 불가능하다.



# Binding Adapter

Data Binding 라이브러리는 결합된 값이 변경될 때마다 레이아웃의 **View 속성과 표현식을 통해** 적절한 setter 메서드를 호출한다. 이때 View의 속성 및 표현식과 setter 메서드를 연결해주고 호출하는 역할을 하는 것이 Binding Adapter이다.

>  **참고**: [androidx.databinding.adapters](https://android.googlesource.com/platform/frameworks/data-binding/+/refs/heads/studio-master-dev/extensions/baseAdapters/src/main/java/androidx/databinding/adapters) 패키지는 일반적으로 사용되는 Binding Adapter를 제공한다.



Data Binding 라이브러리는 레이아웃의 **View 속성명**과 **표현식 유형**에 따라 Binding Adapter에 정의된 setter 메서드를 찾는다.

- `android:text="@{user.name}"` : 속성명 `text`와 문자열을 반환하는 `user.name`을 통해 *문자열을 인수로 받는 setter 메서드* `setText(String)`을 찾는다.
- `android:scrimColor="@{@color/scrim}"` : 속성명 `scrimColor`와 정수값인 `@color/scrim`을 통해 *정수를 인수로 받는 setter 메서드* `setScrimColor(int)`를 찾는다.



### `@BindingMethods`와 `@BindingMethod`

setter 메서드와 View 속성명을 *명시적으로 연결*할 수 있다. 이때 *View 속성명과 setter 메서드 이름이 일치*(예를들어 속성명이 `text`인 경우 메서드 이름은  `setText`)*하지 않아도 된다.* 애너테이션은 클래스와 함께 사용된다.  

```java
@BindingMethods({
	@BindingMethod(type = TextView.class, attribute = "android:autoLink", method = "setAutoLinkMask"),
	@BindingMethod(type = TextView.class, attribute = "android:drawablePadding", method = "setCompoundDrawablePadding"),
	...
})
public class TextViewBindingAdapter { ...
```

> **Note**: Android 프레임워크 클래스에는 이미 이름 규칙을 사용하여 구현되어 있기 때문에 setter 메서드 이름을 변경할 필요가 없다.



### `@BindingAdapter`

*setter 메서드가 호출되는 로직을 설정*할 수 있다. 애너테이션은 메서드와 함께 사용된다. 이때 setter 메서드의 첫 번째 매개변수는 View의 타입을 받고 두 번째 매개변수는 표현식 유형을 받는다. 애너테이션에 명시된 속성명은 첫 번째 매개변수인 View 타입의 객체에서 사용된다.  

```java
@BindingAdapter("android:paddingLeft")
public static void setPaddingLeft(View view, int padding) {
  view.setPadding(padding,
                  view.getPaddingTop(),
                  view.getPaddingRight(),
                  view.getPaddingBottom());
}
```

> **Note**: Android 프레임워크 클래스에는 이미 생성되어 있다. 만약 개발자가 정의하는 Binding Adapter와 충돌이 발생할 경우 **개발자가 정의한 Binding Adapter가 우선 적용된다.**



아래와 같이 View의 여러 속성을 설정할 수 있다. 이때 모든 속성명은 첫 번째 매개변수인 View 타입의 객체에서 사용된다.   

```java
@BindingAdapter({"imageUrl", "error"})
public static void loadImage(ImageView view, String url, Drawable error) {
	...
}
```

이 경우 다음과 같이 사용된다. *setter 메서드의 두 번째 매개변수 부터 순서대로 속성명 순서에 맞게 매칭*된다. 이 매칭에 따라 속성명에 대한 표현식 유형이 정해진다. 이 경우 `imageUrl`속성에 대한 표현식 유형은 두 번째 매개변수의 타입인 `String`이 되고, `error`속성에 대한 표현식 유형은 `Drawable`이 된다.  

```xml
<ImageView app:imageUrl="@{venue.imageUrl}" app:error="@{@drawable/venueError}" />
```

setter 메서드에 *지정된 **모든** 속성을 설정*해야 해당 setter 메서드가 호출된다.

`requireAll`을 `false`로 설정하여 *하나의 속성만 설정되어도 setter 메서드가 호출*되게 할 수 있다.  

```java
@BindingAdapter(value={"imageUrl", "placeholder"}, requireAll=false)
public static void setImageUrl(ImageView imageView, String url, Drawable placeHolder) {
  ...
}
```



setter 메서드에 이전 값과 새 값을 선언할 수 있다. 이때 속성의 *모든 이전 값이 먼저 선언*되어야 한다.  

```java
@BindingAdapter("android:onLayoutChange")
public static void setOnLayoutChangeListener(View view, View.OnLayoutChangeListener oldValue,
                                             View.OnLayoutChangeListener newValue) {
  if (oldValue != null) {
    view.removeOnLayoutChangeListener(oldValue);
  }
  if (newValue != null) {
    view.addOnLayoutChangeListener(newValue);
  }
}
```

위 setter 메서드는 이벤트 리스너 `View.OnLayoutChangeListener`의 변경 시 호출된다. **이벤트 리스너에 대한 setter 메서드를 정의하기 위해서는 해당 리스너 클래스가 하나의 추상 메서드를 가져야한다.** (`View.OnLayoutChangeListener`는 추상 메서드 `onLayoutChange` 하나만 갖는다.)

하나 이상의 추상 메서드를 갖는 리스너의 경우, 아래 처럼 리스너를 메서드 개수 만큼 분할하여 구분한다.  

```java
// View.OnAttachStateChangeListener에는 
// onViewAttachedToWindow(View)와 onViewDetachedFromWindow(View)의 두 메서드가 있다.
// 리스너 당 하나의 메서드만 갖도록 리스너를 분할한다.

@TargetApi(VERSION_CODES.HONEYCOMB_MR1)
public interface OnViewDetachedFromWindow {
  void onViewDetachedFromWindow(View v);
}

@TargetApi(VERSION_CODES.HONEYCOMB_MR1)
public interface OnViewAttachedToWindow {
  void onViewAttachedToWindow(View v);
}
```

분할 된 각 리스너는 다음과 같이 사용한다. 원래 하나의 리스너를 분할한 것이기 때문에 어느 하나를 변경하면 다른 리스너에도 영향을 줄 수 있다. 때문에 보통 `requireAll`을 `false`로 설정하여 하나의 속성만 사용해도 되고 모든 속성을 사용해도 되게 setter 메서드를 구성한다.  

```java
    @BindingAdapter({"android:onViewDetachedFromWindow", "android:onViewAttachedToWindow"}, requireAll=false)
public static void setListener(View view, OnViewDetachedFromWindow detach, OnViewAttachedToWindow attach) {
  ...
}
```



어떤 속성에서는 표현식 유형을 변환하여 사용할 수 있다. 예를들어 `android:background` 속성은 원래 `Drawble` 유형을 받지만 색상의 정수값을 받을 수도 있다. 이는 `int` 값을  `ColorDrawable`(`Drawable`을 상속 받음)로 변환시키는 메서드가 있어 가능하다.  

```java
// androidx.databinding.adapters 패키지의 Converters.java
@BindingConversion
public static ColorStateList convertColorToColorStateList(int color) {
  return ColorStateList.valueOf(color);
}
```

표현식 유형 표현식 언어를 사용하여 선택할 수 있는데, 이때 선택 목록의 유형은 동일해야한다.  

```xml
<!-- 불가능한 표현 -->
<View ... android:background="@{isError ? @drawable/error : @color/white}"/>
```



# 양방향 데이터 결합

단방향 데이터 결합의 경우 (보통 ViewModel에서) 데이터를 레이아웃의 View에 전달한다. 이 경우 반대로 View에서 데이터가 변경(사용자가 조작하는 경우 발생될 수 있다.)되어도 ViewModel이 갖고 있는 데이터에는 영향을 주지 않는다. 이를 위해 메서드 참조나 리스너 결합을 사용하여 콜백 메서드 내에서 ViewModel의 데이터를 직접 변경해줘야 한다.

```java
public class MainViewModel extends ViewModel {
  private MutableLiveData<Boolean> isChecked;

  public MutableLiveData<Boolean> isChecked(){
    if(isChecked == null) {
      isChecked = new MutableLiveData<>();
      isChecked.setValue(false);
    }
    return isChecked;
  }

  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
    Log.d("test", isChecked ? "true" : "false");
    this.isChecked.setValue(isChecked); // 리스너 콜백 메서드에서 데이터를 직접 변경
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

    <CheckBox
              android:text="@{model.checked.toString()}"
              android:checked="@={model.checked}"
              android:onCheckedChanged="@{(compoundButton, isChecked)->model.onCheckedChanged(compoundButton, isChecked)}"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"/>
  </LinearLayout>
</layout>
```

양방향 데이터 결합(표기법: `@={}`)을 사용하게 되면 해당 속성과 관련된 데이터의 변경사항을 받을 수 있고, View에서 그 속성과 관련된 데이터 변경 시 연결된 ViewModel 데이터도 업데이트된다. 때문에 아래 처럼 코드가 간결해진다.

```java
public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> isChecked;

    public MutableLiveData<Boolean> isChecked(){
        if(isChecked == null) {
            isChecked = new MutableLiveData<>();
            isChecked.setValue(false);
        }
        return isChecked;
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

    <CheckBox
              android:text="@{model.checked.toString()}"
              android:checked="@={model.checked}"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"/>
  </LinearLayout>
</layout>
```

이렇게 양방향 데이터 결합을 기본적으로 지원하는 클래스가 있다. 아래 표의 클래스에서 해당 속성을 사용할 때 양방향 데이터 결합을 지원한다. (일부만 작성함)

| 클래스         | 속성               |
| -------------- | ------------------ |
| TextView       | android:text       |
| CompoundButton | android:checked    |
| CalendarView   | android:date       |
| TabHost        | android:currentTab |

> Custom View에서 양방향 데이터 결합을 지원하도록 설정하는 방법도 있다.

