## 概览

所谓的状态可以简单的理解为应用中的某个值的变化，比如可以是一个布尔值、数组

放在业务的场景中，可以是 TextField 中的文字、动画执行的状态、用户收藏的商品都是状态

我们知道 compose 是声明式的 ui，每次我们重组页面的时候都会把组件重组，此时就需要引入状态进行管理，例如：

我们在商品的 item 里面点击按钮收藏了商品，此时商品的收藏状态发生了改变，我们需要重组 ui 将商品变为已收藏状态，这个时候就需要用 remember 扩展方法保存重组状态，如果使用 boolean 这个基本类型保存那么就无法在重组 ui 后正常的设置组件的状态。

代码举例（抄官方代码）：

```kotlin
@Composable
fun HelloContent() {
   Column(modifier = Modifier.padding(16.dp)) {
       OutlinedTextField(
           value = "输入的值",
           onValueChange = { },
           label = { Text("Name") }
       )
   }
}
```

运行上面的代码，我们会发现无论我们如何在 TextField 中输入内容，TextFile 的内容都不会变，这就是因为无法保存状态导致的，以下代码示例可以正常的改变 TextField 中的内容

```kotlin

@Composable
fun textFieldStateHasTextShow(){
    var value by remember {//这里就是对TextField中展示的文字进行状态保存的操作
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize(1f),contentAlignment = Alignment.Center) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                  value=it//每次输入内容的时候，都回调这个更新状态，从而刷新重组ui
            },
            label = { Text("Name") }
        )
    }
}
```

## 状态管理的常用方法

### remember 重组中保存状态

组合函数可以通过`remember`记住单个对象，系统会在初始化期间将`remember`初始的值存储在组合中。重组的时候可以返回对象值，`remember`既可以用来存储可变对象又可以存储不可变的对象

当可组合项被移除后，会忘记 remember 存储的对象。

### mutableStateOf

`mutableStateOf` 会创建可观察的 `MutableState<T>`，例如如下代码：
data 就是一个`MutableState`对象

每当`data.value`值发生改变的时候，系统就会重组ui。

```kotlin
var data = remember {
        mutableStateOf("")
}
```

**注：mutableStateOf 必须使用 remember 嵌套才能在数据更改的时候重组界面**

### rememberSaveable 保存配置

`remember`可以帮助我们在界面重组的时候保存状态，而`rememberSaveable`可以帮助我们存储配置更改（`重新创建activity或进程`）时的状态。

### Livedata、Flow、RxJava 转换为状态

这三个框架是安卓常用的三个响应式开发框架，都支持转化为`State`对象，以 Flow 举例，如下代码可以转化为一个 State：

```kotlin
  val favorites = MutableStateFlow<Set<String>>(setOf())
    val state = favorites.collectAsState()
```

## 状态管理

### 有状态和无状态

使用 remember、rememberSaveState 方法保存状态的组合项是有状态组合

反之是无状态组合

### 状态提升

如下代码是官方关于状态提升的代码：

本例代码中 HelloContent 是无状态的，它的状态被提升到了 HelloScreen 中，HelloContent 有`name`和`onNameChange`两个参数，name 是状态，通过 HelloScreen 组合项传给 HelloContent

而 HelloContent 中发生的更改它也不能自己进行处理，必须将更改传给`HelloScreen`进行处理并重组界面。

以上的逻辑叫做：**状态下降，事件上升**

```
@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }

    HelloContent(name = name, onNameChange = { name = it })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}
```

## 存储状态的方式

前面的介绍中我们知道使用`rememberSaveable`方法我们可以通过 Bundle 的方式保存状态，那么如果我们要保存的状态不方便用 Bundle 的情况下该何如处理呢？

以下三种方式，可以实现对非 Bundle 的数据的保存（配置更改后的保存）

### Parcelize

代码示例：

```kotlin
@Parcelize
data class City(val name: String, val country: String) : Parcelable

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```

### MapSaver

```kotlin
data class City(val name: String, val country: String)

val CitySaver = run {
    val nameKey = "Name"
    val countryKey = "Country"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
        restore = { City(it[nameKey] as String, it[countryKey] as String) }
    )
}

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```

### ListSaver

```kotlin
data class City(val name: String, val country: String)

val CitySaver = listSaver<City, Any>(
    save = { listOf(it.name, it.country) },//数组中保存的值和City中的属性是顺序对应的
    restore = { City(it[0] as String, it[1] as String) }
)

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}
```

## 状态管理源码分析

### remember

初次阅读 remember 的源码，可能有理解不对的地方（但总得有人先去看不是），多多见谅，欢迎指正

- remember 方法调用的主流程

remember方法返回的是一个MutableState对象，MutableState可以在数据更新的时候通知系统重组ui

![](https://files.mdnice.com/user/15648/9c421908-d6c2-4985-a70e-73f66bbf171e.png)
rememberedValue 就是数据转换的逻辑

- rememberedValue 方法解析

![](https://files.mdnice.com/user/15648/80ea2662-56a8-4217-b974-782b3cd3a28c.png)

`inserting:`如果我们正在将新的节点插入到视图数中，那么 inserting=true




`reusing:`意为正在重用，我的理解是当前正在重新使用这个状态，所以避免多次获取

- reader.next 方法
  晒一段源码

```value
  fun next(): Any? {
        if (emptyCount > 0 || currentSlot >= currentSlotEnd) return Composer.Empty
        return slots[currentSlot++]
    }
```

`slots`是一个数组，`currentSlot`表示我们要获取到的状态在数组中的索引，compose 构建页面是单线程的，索引每次我们调用`remember`方法的时候如果状态已经存在就从`slots`中获取数据，然后把`currentSlot`索引加 1，这样当我们调用了最后一个`remember`方法的时候`currentSlot`索引刚好等于`slots`数组`.length-1`


