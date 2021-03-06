package com.ananananzhuo.motionlayoutsample

import android.content.Intent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.ananananzhuo.mvvm.activity.CustomAdapterActivity
import com.ananananzhuo.mvvm.bean.bean.ItemData
import com.ananananzhuo.mvvm.callback.CallData
import com.ananananzhuo.mvvm.callback.Callback

class MainActivity : CustomAdapterActivity() {

    override fun getAdapterDatas(): MutableList<ItemData> = mutableListOf(
        ItemData(title = "写三个布局实现简单滑动", callback = object : Callback {
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.activity_simple_anim)
            }
        }),
        ItemData(title = "写一个布局实现简单滑动", callback = object : Callback {
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_0)
            }
        }),
        ItemData(title = "实现底部向上滑动title，变旋转标题", callback = object : Callback {
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_1)
            }
        }),
        ItemData(title = "（本例暂时未完成）滑动NestScrollView，topbar向上滑动", callback = object : Callback {
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_2)
            }
        }),
        ItemData(title = "实现keyframe动画", callback = object : Callback {
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_3)
            }
        }),
        ItemData(title = "OnSwip.touchRegionId", callback = object : Callback {
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_field0)
            }
        }),
        ItemData(title = "OnSwip.touchAnchorSide", callback = object : Callback {
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_field1)
            }
        }),
        ItemData(title = "实现嵌套滑动的效果", callback = object : Callback {
            override fun callback(callData: CallData) {
                startActivity(Intent(this@MainActivity, NestScrollActivity::class.java))
            }
        }),


        )

    private fun toAnimPage(layoutId: Int) {
        startActivity(Intent(this, ShowAnimActivity::class.java).apply {
            putExtra("layoutId", layoutId)
        })
    }

    override fun showFirstItem(): Boolean = true

}