

## 使用概述
`MotionLayout`是一个布局，是`ConstraintLayout`的子布局。专门用来实现运动过程中的控件动画，

`举例：`我们可以通过在布局文件`layoutA`中`MotionLayout`进行布局，在布局中设置两个子控件`TextView`和`ImageView`。比如我们想在布局中手指触碰向上滑动的时候让`ImageView`向上滑动，那么我们需要在xml文件夹下创建一个xml布局文件`scenceA`，在`scenceA`中处理滑动。我们会在`scenceA xml`文件中创建 两个`ConstraintSet/Constraint`节点，一个节点是动画开始节点表示动画开始前布局的状态`start`，另一个节点表示动画结束后布局的状态`end`。当动画完成的时候`ImageView`会从`start`状态转换为`end`状态。


`以下以结束状态进行举例：`
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

我们会为我们布局中空间id为`iv_0`的控件设置宽高和布局约束属性。
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

`Constraint`的属性完全等同于ConstraintLayout的属性，##### Constraint属性
`Constraint`的属性完全等同于ConstraintLayout的属性，

### Transition、onClick、onSwipe
Transition搬都会搭配onClick和onSwpie进行使用


**Transition所有属性**
![](https://files.mdnice.com/user/15648/206b38ce-84e9-449b-8acb-c315f377a4a2.png)


#### Transition、onSwipe
OnSwipe用于控制滑动中的动画，例如如下代码中，

`dragDirection`表示控件拖动的方法是向右拖动

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

## 单个属性使用和效果















