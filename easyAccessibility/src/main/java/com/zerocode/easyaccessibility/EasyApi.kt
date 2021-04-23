package com.zerocode.easyaccessibility

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.zerocode.easyaccessibility.tools.*
import com.zerocode.easyaccessibility.viewnode.ViewNode

object EasyApi {


    /**
     * 打开无障碍服务
     */
    fun Context.jumpAccessibilityServiceSettings() {
        jumpAccessibilityServiceSettings(EasyAccessibilityService::class.java)
    }

    /**
     * 是否获取了无障碍服务
     */
    fun isOpenAccessibilityService(): Boolean =
        com.zerocode.easyaccessibility.tools.isOpenAccessibilityService


    /**
     * 通过 txt 来查找节点
     */
    fun withTxt(txt: String): ViewNode? {
        return findByTxt(txt)
    }

    /**
     * 通过 id 来查找 节点
     */
    fun withId(id: String): ViewNode? {
        return findById(id)
    }

    /**
     * 通过 desc 来查找节点
     */
    fun withDesc(desc: String): ViewNode? {
        return findByDesc(desc)
    }


    ////////////////////////////////  全局操作/////////

    //返回 等同于 按下 返回键
    fun back(): Boolean = EasyAccessibilityService.getAccessibilityService().back()

    // 返回 桌面
    fun backHome(): Boolean = EasyAccessibilityService.getAccessibilityService().backHome()


    //电源菜单
    fun powerDialog(): Boolean = EasyAccessibilityService.getAccessibilityService().powerDialog()

    //通知栏
    fun showNotificationBar(): Boolean =
        EasyAccessibilityService.getAccessibilityService().showNotificationBar()

    //展开通知栏 > 快捷设置
    fun quickSettings(): Boolean =
        EasyAccessibilityService.getAccessibilityService().quickSettings()


    //锁屏
    @RequiresApi(Build.VERSION_CODES.P)
    fun lockScreen(): Boolean = EasyAccessibilityService.getAccessibilityService().lockScreen()


    //截屏
    @RequiresApi(Build.VERSION_CODES.P)
    fun screenShot(): Boolean = EasyAccessibilityService.getAccessibilityService().screenShot()


    //最近任务
    fun workList(): Boolean = EasyAccessibilityService.getAccessibilityService().workList()

    //分屏
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun splitScreen(): Boolean = EasyAccessibilityService.getAccessibilityService().splitScreen()


    /**
     * 通过坐标点击
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun click(x: Int, y: Int) = clickByCoordinate(x, y)

    /**
     * 通过坐标长按
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun longClick(x: Int, y: Int) = longClickByCoordinate(x, y)

    /**
     * 两点之间的滑动
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun scroll(x1: Int, y1: Int, x2: Int, y2: Int, dur: Int = 500) =
        scrollByCoordinate(x1, y1, x2, y2, dur)

    /**
     * 上滑
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun scrollUp() = scrollUpByCoordinate()


    /**
     * 下滑
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun scrollDown() = scrollDownByCoordinate()


    /**
     * 左滑
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun scrollLeft() = scrollLeftByCoordinate()


    /**
     * 右滑
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun scrollRight() = scrollRightByCoordinate()


}