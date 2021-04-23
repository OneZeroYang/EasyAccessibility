package com.zerocode.easyaccessibility.api

import android.os.Build
import androidx.annotation.RequiresApi

interface BaseEasyApi {
    //返回 等同于 按下 返回键
    fun back():Boolean

    // 返回 桌面
    fun backHome():Boolean


    //电源菜单
    fun powerDialog(): Boolean

    //通知栏
    fun showNotificationBar(): Boolean

    //展开通知栏 > 快捷设置
    fun quickSettings(): Boolean


    //锁屏
    @RequiresApi(Build.VERSION_CODES.P)
    fun lockScreen(): Boolean


    //截屏
    @RequiresApi(Build.VERSION_CODES.P)
    fun screenShot(): Boolean


    //最近任务
    fun workList(): Boolean

    //分屏
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun splitScreen(): Boolean
}