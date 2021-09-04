关注公众号学习更多知识

## 简单实现效果
模拟器录制的，比较卡顿，建议耐心看完


![](https://files.mdnice.com/user/15648/dc9a6718-0fd1-459a-bab4-8875f482f06f.gif)


## 使用概述
`MotionLayout`是一个布局，`ConstraintLayout`的子布局。专门用来实现运动过程中的控件动画，

`举例：`我们可以通过在布局文件`layoutA`中`MotionLayout`进行布局，在布局中设置两个子控件`TextView`和`ImageView`。比如我们想在布局中手指触碰向上滑动的时候让`ImageView`向上滑动，那么我们需要在xml文件夹下创建一个xml布局文件`scenceA`，在`scenceA`中处理滑动。我们会在`scenceA xml`文件中创建 两个`ConstraintSet/Constraint`节点，一个节点是动画开始节点表示动画开始前布局的状态`start`，另一个节点表示动画结束后布局的状态`end`。当动画完成的时候`ImageView`会从`start`状态转换为`end`状态。


`以结束状态进行举例：`
```kotlin
<ConstraintSet android:id="@+id/end">
        <Constraint
            android:id="@id/iv_0"
            android:layout_width="100dp"
            android:layout_height="100dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            android:rotation="720"
            app:layout_constraintTop_toTopOf="parent" >
            <CustomAttribute
                app:customDimension="100dp"
                app:attributeName="end"
                />
        </Constraint>
    </ConstraintSet>
```



## 常用api介绍
>可以通过看我的解释入门，更详细细致的文档参加官方文档：
>
>`https://developer.android.google.cn/training/constraint-layout/motionlayout/ref?hl=zh_cn`
### MotionScence
作为动画布局中的根节点

### ConstraintSet
可以存放Constraint节点，一般一个动画xml文件中可以有两个ConstraintSet节点A和B，A节点作为动画开始前的状态，B节点作为动画结束时的状态。

例如A节点中控件的宽高是100dp，B节点中动画的宽高是300dp。当动画开始执行后会是一个放大动画，控件的宽高会以动画的形式从100dp放大到300dp。

代码举例：


```xml
  <ConstraintSet android:id="@+id/start">
        //节点A
        <Constraint
            android:id="@+id/iv_0"
            android:layout_width="10dp"
            android:layout_height="10dp"/>

    </ConstraintSet>

    <ConstraintSet android:id="@+id/end">
        //节点B
        <Constraint
            android:id="@id/iv_0"
            android:layout_width="100dp"
            android:layout_height="100dp"/>
    </ConstraintSet>
```
#### 属性一览


### Constraint
每一个Constraint可以为一个控件设置动画效果，例如如下代码：

我们会为我们布局中控件id为`iv_0`的控件设置宽高和布局约束属性。
##### Constraint属性
`Constraint`的属性完全等同于ConstraintLayout的属性，
`Constraint`的属性完全等同于ConstraintLayout的属性，


```xml
<Constraint
            android:id="@+id/iv_0"
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintTop_toTopOf="parent" >
```

**Constraint属性**

`Constraint`的属性完全等同于ConstraintLayout的属性，
##### Constraint属性
`Constraint`的属性完全等同于ConstraintLayout的属性，

### Transition、onClick、onSwipe
Transition可以搭配多个onClick和onSwpie进行使用


**Transition所有属性**
![](https://files.mdnice.com/user/15648/206b38ce-84e9-449b-8acb-c315f377a4a2.png)


#### Transition、onSwipe
OnSwipe用于控制滑动中的动画，例如如下代码中，

`dragDirection`表示控件拖动的方法

`touchAnchorId：`表示所触碰的控件id是iv_0

`touchAnchorSide：`表示触碰的方向是从左向右
```xml
 <Transition
        app:constraintSetEnd="@id/end"
        app:constraintSetStart="@id/start"
        app:duration="2000">
        <OnSwipe
            app:dragDirection="dragRight"
            app:touchAnchorId="@id/iv_0"
            app:touchAnchorSide="right" />
    </Transition>
```
**onSwipe所有属性**

![](https://files.mdnice.com/user/15648/e8117ca4-bffb-4ad5-910c-5dcb898913b1.png)


#### Transition、onClick
onClick与onSwipe类似，用法也相仿，表示当我们点击控件的时候动画开始。

不再赘述这里

### KeyFrameSet、KeyPosition、KeyAttribute
`KeyFrameSet`必须位于`Transition`节点内

`KeyFrameSet`可以搭配`KeyPosition`和`KeyAttribute`实现更精确的动画控制

例如我们可以再KeyFrameSet中配置多个KeyPosition来控制动画执行某个百分比对应的运动状态


这里的代码控制实在不能用文字的描述的很好，所以写一些代码和展示一些效果吧



#### scence代码

布局文件代码：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.motion.widget.MotionLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:showPaths="true"
    app:layoutDescription="@xml/anim_3_scene">

    <ImageView
        android:id="@+id/iv_03"
        android:layout_width="50dp"
        android:src="@drawable/apple"
        android:layout_height="50dp"/>
</androidx.constraintlayout.motion.widget.MotionLayout>
```

scence代码：

```xml
<MotionScene xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <Transition
        app:constraintSetEnd="@id/end"
        app:duration="500"
        app:constraintSetStart="@+id/start">
        <OnSwipe
            app:dragDirection="dragRight"
            app:touchAnchorId="@id/iv_03"
            app:touchAnchorSide="right" />
        <KeyFrameSet>
            <KeyPosition
                app:framePosition="20"
                app:motionTarget="@id/iv_03"
                app:keyPositionType="pathRelative"
                app:percentY="-0.4"
                />
            <KeyPosition
                app:framePosition="50"
                app:motionTarget="@id/iv_03"
                app:keyPositionType="pathRelative"
                app:percentY="-0.2"
                />
            <KeyPosition
                app:framePosition="80"
                app:motionTarget="@id/iv_03"
                app:percentY="-0.4"
                app:keyPositionType="pathRelative"
                />

        </KeyFrameSet>
    </Transition>

    <ConstraintSet android:id="@+id/start">
        <Constraint
            android:id="@+id/iv_03"
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </ConstraintSet>

    <ConstraintSet android:id="@+id/end">
        <Constraint
            android:id="@id/iv_03"
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </ConstraintSet>

</MotionScene>
```
#### 实现效果

动画会按照如下的规矩运动
![](https://files.mdnice.com/user/15648/9db8b323-4cae-41bc-ab03-645eb1533729.png)

## 比较有用的单个属性使用

### OnSwipe.touchRegionId
可以通过touchRegionId设置触碰被触碰的控件，比如本例中，我们设置`app:touchRegionId="@id/iv"`那么只有在我们手指触碰再ImaveView时才会有动画效果，否则如果不设置`touchRegionId`我们触碰全局都可以实现滑动效果。

`touchRegionId`有个缺点，当我们给某个`MotionLayout`设置`touchRegionId`时，它内部的所有子view都无法执行点击事件（**down事件直接就在onInterceptTouchEvent中被拦截了，没有可处理的空间**）。这个情况可以通过自定义`MotionLayout`解决，我目前并没有尝试过，项目中使用的时候再去处理。

示例：
```xml
 <Transition
        app:constraintSetEnd="@id/end"
        app:constraintSetStart="@+id/start">
        <OnSwipe
            app:dragDirection="dragLeft"
            app:touchRegionId="@id/iv"
            app:touchAnchorId="@id/iv"
            app:touchAnchorSide="left" />
    </Transition>
```


[布局文件](https://gitee.com/ymeddmn/MotionLayoutSample/blob/main/app/src/main/res/layout/anim_field0.xml)

[scence文件](https://gitee.com/ymeddmn/MotionLayoutSample/blob/main/app/src/main/res/xml/anim_field0_scene.xml)


### OnSwipe.touchAnchorSide
滑动所固定到的目标视图的一侧。MotionLayout 将尝试在该固定点与用户手指之间保持恒定的距离。可接受的值包括 "left"、"right"、"top" 和 "bottom"。

不过也不能太夸张，比如如果你滑动的方向是向右，但是touchAnchorSide设置top这样仍然会是水平滑动固定到视图的右侧

### OnSwip.dragDirection
用户滑动的方向，有四个可选值：`"dragLeft"、"dragRight"、"dragUp" 和 "dragDown"`

### OnClick的属性
比较好理解，所以我直接截图官网内容了

![](https://files.mdnice.com/user/15648/6d5877a3-cb56-4b7b-898a-686e45357be3.png)

### KeyPosition
大部分内容是copy官网的
#### motion:motionTarget
其运动由此 <KeyPosition> 控制的视图。

#### motion:framePosition
1 到 99 之间的整数，用于指定运动序列中视图何时到达此 <KeyPosition> 指定的点。例如，如果 `framePosition` 为 25，则视图在整个运动路径的四分之一处到达指定点。

#### motion:percentX、motion:percentY
指定视图应到达的位置。`keyPositionType` 属性指定如何解释这些值。

#### motion:keyPositionType
指定如何解释 percentX 和 percentY 值。可能的设置包括：
##### parentRelative
`percentX` 和 `percentY` 是相对于父视图指定的。X 为横轴，范围从 0（左端）到 1（右端）。Y 为纵轴，其中 0 为顶部，1 为底部。

例如，如果您希望目标视图到达父视图右端中间的某个点，可以将 `percentX` 设置为 1，将 `percentY` 设置为 0.5。

##### deltaRelative
`percentX` 和 `percentY` 是相对于视图在整个运动序列过程中移动的距离指定的。X 为横轴，Y 为纵轴；在这两种情况下，0 为视图在该轴上的起始位置，1 为最终位置。

例如，假设目标视图向上移动 100 dp，然后再向右移动 100 dp，但是您希望视图按以下方式移动：首先在运动的前四分之一部分向上移动 40 dp，然后向上呈弧形移动。为此，请将 `framePosition` 设置为 25，将 keyPositionType 设置为 `deltaRelative`，并将 `percentY` 设置为 -0.4。

##### pathRelative
X 轴是目标视图在路径范围内移动的方向，其中 0 为起始位置，1 为最终位置。Y 轴垂直于 X 轴，正值位于路径左侧，负值位于右侧；设置一个非零的 `percentY` 可使视图向一个方向或另一个方向呈弧形运动。因此，视图的初始位置为 (0,0)，最终位置为 (1,0)。

例如，假设您希望视图按如下方式移动：在运动序列前半部分的移动距离占总距离的 10％，然后加速移动以覆盖剩余 90％ 的距离。为此，请将 `framePosition` 设置为 50，将 `keyPositionType` 设置为 `pathRelative`，并将 `percentX` 设置为 0.1。

## 使用MotionLayout实现嵌套滑动的效果
### 示例代码
[代码示例](https://github.com/ananananzhuo-blog/MotionLayoutSample/blob/main/app/src/main/java/com/ananananzhuo/motionlayoutsample/NestScrollActivity.kt)

### 设计图

本例要实现的效果如下：

![](https://files.mdnice.com/user/15648/dee83489-4b49-46d4-8c50-0a8d6791b17b.png)


### 实现思路
`View`的结构如上图，实现这个效果需要两个`View`，绿色的`View`是一个`MotionLayout` `viewA`，红色的`View`是一个`NestScrollView` `viewB`，`viewA和viewB`同时存在于同一个`MotionLayout` viewP中。

实现滑动的效果我们需要两个`scence`，第一个`scence`  `activity_nest_scroll_scene` 挂载在`viewP`中，用来处理我们滑动中`viewB`时`viewA`向上缩放，`viewB`向上滑动的实现。


我们的实现效果中还需要在向上滑动的时候将绿色部分的文字由垂直方向排列转换为水平排列。这需要我们同时再给`viewA`节点中的`MotionLayout`添加一个`scence`，这个`scence`用于处理文字的展示变化
### 实现代码的关键点

1. 如果想让`header`内部嵌套的`scence`跟随外部的`scence`联动，则需要设置

`app:motionProgress="1"`

`motionProgress`这个属性是关键，

我们需要在外部`scence`的动画结束状态中将`header`的`motion`动画进度设置为1，这样在外部`scence`动画进行的过程中，内部`scence`会保持相同的动画进度

示例中的代码如图：

![](https://files.mdnice.com/user/15648/6f195425-7fc2-4aa5-b947-51d2a43e6381.png)

### 实现效果
![滑动效果](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/859aab2797d64963a22a7ab35b8a9596~tplv-k3u1fbpfcp-watermark.image)

### 实现代码(可跳过)

1. 最外层布局viewP的代码

`app:layoutDescription="@xml/activity_nest_scroll_scene"`用来给设置header和NestScrollView之间的动画


`文件名：activity_nest_scroll.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.motion.widget.MotionLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".NestScrollActivity"
    app:layoutDescription="@xml/activity_nest_scroll_scene">

    <include layout="@layout/motion_header"/>
    <androidx.core.widget.NestedScrollView
        android:id="@+id/scrollable"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
      >

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/large_text" />

    </androidx.core.widget.NestedScrollView>

</androidx.constraintlayout.motion.widget.MotionLayout>
```
2. viewP的scence代码（activity_nest_scroll_scene）
   `文件名：activity_nest_scroll_scene.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<MotionScene xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:motion="http://schemas.android.com/apk/res-auto"
    >



    <Transition
        motion:constraintSetEnd="@+id/end"
        motion:constraintSetStart="@+id/start"
        motion:duration="250"
        motion:motionInterpolator="linear">

        <OnSwipe
            motion:dragDirection="dragUp"
            motion:touchAnchorId="@+id/motionLayout"
            motion:touchAnchorSide="bottom" />
    </Transition>


    <ConstraintSet android:id="@+id/start">
        <Constraint
            android:id="@+id/motionLayout"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            app:layout_constraintTop_toTopOf="parent" />
        <Constraint
            android:id="@+id/scrollable"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/motionLayout" />

    </ConstraintSet>

    <ConstraintSet android:id="@+id/end">
        <Constraint
            android:id="@+id/motionLayout"
            android:layout_width="match_parent"
            android:layout_height="56dp"
            app:motionProgress="1"
            app:layout_constraintTop_toTopOf="parent" />
        <Constraint
            android:id="@+id/scrollable"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/motionLayout" />
    </ConstraintSet>

</MotionScene>

```

3. viewA的代码
   这部分代码在第一部分中使用include标签添加到布局中

`文件名：motion_header.xml`
```xml

<androidx.constraintlayout.motion.widget.MotionLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/motionLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#1e376b"
    app:layoutDescription="@xml/scene_17_header"
    app:showPaths="true">

    <TextView
        android:id="@+id/background"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:background="#0a3"
        android:scaleType="centerCrop"
        />

    <TextView
        android:id="@+id/label"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="安安安安卓"
        android:textColor="#FFF"
        android:textSize="32dp"
        android:transformPivotX="0dp"
        android:transformPivotY="0dp" />



    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_begin="200dp" />

</androidx.constraintlayout.motion.widget.MotionLayout>
```
4. viewA的scence代码
   `文件名：scene_17_header.xml`
```xml
<?xml version="1.0" encoding="utf-8"?><!--
  Copyright (C) 2018 The Android Open Source Project
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  -->
<MotionScene xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:motion="http://schemas.android.com/apk/res-auto">

    <Transition
        motion:constraintSetEnd="@+id/end"
        motion:constraintSetStart="@+id/start"
        motion:duration="1000"
        motion:motionInterpolator="linear">
        <OnSwipe
            motion:dragDirection="dragUp"
            motion:touchAnchorId="@+id/background"
            motion:touchAnchorSide="bottom" />
    </Transition>

    <ConstraintSet android:id="@+id/start">
        <Constraint
            android:id="@id/background"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:alpha="1.0"
            android:scaleX="1.1"
            android:scaleY="1.1"
            motion:layout_constraintTop_toTopOf="parent" />
        <Constraint
            android:id="@id/label"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:alpha="0.6"
            android:rotation="-90.0"
            android:translationY="8dp"
            motion:layout_constraintBottom_toBottomOf="@+id/guideline"
            motion:layout_constraintStart_toStartOf="parent" />

        <Constraint
            android:id="@id/guideline"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            motion:layout_constraintGuide_begin="200dp" />
    </ConstraintSet>

    <ConstraintSet android:id="@+id/end">
        <Constraint
            android:id="@id/background"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:alpha="0"
            android:translationX="0dp"
            android:translationY="0dp"
            motion:layout_constraintTop_toTopOf="parent" />
        <Constraint
            android:id="@id/label"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginBottom="8dp"
            android:rotation="0.0"
            motion:layout_constraintBottom_toBottomOf="@+id/guideline"
            motion:layout_constraintStart_toStartOf="parent" />

        <Constraint
            android:id="@id/guideline"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            motion:layout_constraintGuide_begin="56dp" />
    </ConstraintSet>
</MotionScene>
```

  
  
  
  
  
  
  
  
  