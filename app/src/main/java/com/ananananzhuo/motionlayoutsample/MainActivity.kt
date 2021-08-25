package com.ananananzhuo.motionlayoutsample

import android.content.Intent
import com.ananananzhuo.mvvm.activity.CustomAdapterActivity
import com.ananananzhuo.mvvm.bean.bean.ItemData
import com.ananananzhuo.mvvm.callback.CallData
import com.ananananzhuo.mvvm.callback.Callback

class MainActivity : CustomAdapterActivity() {

    override fun getAdapterDatas(): MutableList<ItemData> = mutableListOf(
        ItemData(title = "写三个布局实现简单滑动",callback = object :Callback{
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.activity_simple_anim)
            }
        }),
        ItemData(title = "写一个布局实现简单滑动",callback = object :Callback{
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_0)
            }
        }),
        ItemData(title = "实现底部向上滑动title，变旋转标题",callback = object :Callback{
            override fun callback(callData: CallData) {
                toAnimPage(R.layout.anim_1)
            }
        }),
    )

    private fun toAnimPage(layoutId: Int) {
        startActivity(Intent(this,ShowAnimActivity::class.java).apply {
            putExtra("layoutId",layoutId)
        })
    }

    override fun showFirstItem(): Boolean =true

}