package com.zerocode.easyaccessibility.viewnode

import android.os.Build
import androidx.annotation.RequiresApi

/**
 *  一些常用的api
 * @author ZeroCode
 * @date 2021/4/23:10:47
 */
interface ViewNodeInterface {


    //点击
    fun click() :Boolean

    //长按
    fun longClick():Boolean

    //连续2次点击
    fun twiceClick(min:Long=500):Boolean

    //输入
    fun inputText(string: String)



}