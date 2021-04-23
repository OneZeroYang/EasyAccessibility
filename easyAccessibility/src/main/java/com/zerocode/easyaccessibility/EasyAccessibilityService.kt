package com.zerocode.easyaccessibility

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.zerocode.easyaccessibility.api.BaseEasyApi
import com.zerocode.easyaccessibility.callback.UpDateCurrentAppListener
import java.lang.RuntimeException

/**
 * 无障碍服务
 * @author ZeroCode
 * @date 2021/4/22:9:52
 */
class EasyAccessibilityService : AccessibilityService(),
    BaseEasyApi {


    companion object {
        private var easyAccessibilityService: EasyAccessibilityService? = null

        fun getAccessibilityService(): EasyAccessibilityService =
            if (easyAccessibilityService != null) easyAccessibilityService!! else throw RuntimeException(
                "无障碍服务未开启！"
            )

        fun getAccessibilityServiceNnll() = easyAccessibilityService
    }


    /**
     * 根节点
     */
    private val rootInWindow: AccessibilityNodeInfo?
        get() {
            return try {
                rootInActiveWindow //will occur exception
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }


    /**
     * 页面更新监听 === 切换activity 或者弹出 dialog 时
     */
    private val updateCurrentAppList: ArrayList<UpDateCurrentAppListener> = arrayListOf()


    override fun onCreate() {
        super.onCreate()
        easyAccessibilityService = this
    }


    override fun onDestroy() {
        super.onDestroy()
        if (easyAccessibilityService != null) {
            easyAccessibilityService = null
        }
    }

    override fun onInterrupt() {

    }

    /**
     * 事件类型    描述
    TYPE_VIEW_CLICKED    View被点击
    TYPE_VIEW_LONG_CLICKED    View被长按
    TYPE_VIEW_SELECTED    View被选中
    TYPE_VIEW_FOCUSED    View获得焦点
    TYPE_VIEW_TEXT_CHANGED    View文本变化
    TYPE_WINDOW_STATE_CHANGED    打开了一个PopupWindow，Menu或Dialog
    TYPE_NOTIFICATION_STATE_CHANGED    Notification变化
    TYPE_VIEW_HOVER_ENTER    一个View进入悬停
    TYPE_VIEW_HOVER_EXIT    一个View退出悬停
    TYPE_TOUCH_EXPLORATION_GESTURE_START    触摸浏览事件开始
    TYPE_TOUCH_EXPLORATION_GESTURE_END    触摸浏览事件完成
    TYPE_WINDOW_CONTENT_CHANGED    窗口的内容发生变化，或子树根布局发生变化
    TYPE_VIEW_SCROLLED    View滚动
    TYPE_VIEW_TEXT_SELECTION_CHANGED    Edittext文字选中发生改变事件
    TYPE_ANNOUNCEMENT    应用产生一个通知事件
    TYPE_VIEW_ACCESSIBILITY_FOCUSED    获得无障碍焦点事件
    TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED    无障碍焦点事件清除
    TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY    在给定的移动粒度下遍历视图文本的事件
    TYPE_GESTURE_DETECTION_START    开始手势监测
    TYPE_GESTURE_DETECTION_END    结束手势监测
    TYPE_TOUCH_INTERACTION_START    触摸屏幕事件开始
    TYPE_TOUCH_INTERACTION_END    触摸屏幕事件结束
    TYPE_WINDOWS_CHANGED    屏幕上的窗口变化事件，需要API 21+
    TYPE_VIEW_CONTEXT_CLICKED    View中的上下文点击事件
    TYPE_ASSIST_READING_CONTEXT    辅助用户读取当前屏幕事件
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.e("sgfgre", event?.action.toString())
        event ?: return
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                //界面切换
                val classNameStr = event.className
                val pkg = event.packageName as String?
                if (!classNameStr.isNullOrBlank() && pkg != null) {
                    updateCurrentApp(pkg, classNameStr.toString())
                }
            }
        }
    }


    /**
     * 当前页面更新通知
     */
    private fun updateCurrentApp(pkg: String, classNameStr: String) {
        updateCurrentAppList.forEach {
            it.onUpDateCurrentApp(pkg, classNameStr)
        }
    }


    /**
     * 添加监听
     */
    fun addUpdateCurrentAppListener(listener: UpDateCurrentAppListener) {
        updateCurrentAppList.add(listener)
    }

    /**
     * 移除监听
     */
    fun removeUpdateCurrentAppListener(listener: UpDateCurrentAppListener) {
        if (updateCurrentAppList.isNullOrEmpty())
            updateCurrentAppList.remove(listener)
    }


    /**
     * 获取根节点
     */
    fun getRootView()  = rootInWindow ?: throw RuntimeException("获取根节点失败！")



    /**
     * 返回操作
     */
    override fun back(): Boolean =  performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)

    /**
     * 返回桌面
     */
    override fun backHome(): Boolean =   performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)

    /**
     * 电源菜单
     */
    override fun powerDialog(): Boolean =
          performGlobalAction(AccessibilityService.GLOBAL_ACTION_POWER_DIALOG)

    /**
     * 通知栏
     */
    override fun showNotificationBar(): Boolean =
          performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS)

    /**
     * 展开通知栏 > 快捷设置
     */
    override fun quickSettings(): Boolean =
          performGlobalAction(AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS)


    /**
     * 锁屏
     */
    override fun lockScreen(): Boolean =
          performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN)


    /**
     * 截屏
     */
    override fun screenShot(): Boolean =
          performGlobalAction(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT)

    /**
     * 最近任务
     */
    override fun workList(): Boolean =
          performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS)

    /**
     * 分屏
     */
    override fun splitScreen(): Boolean =
          performGlobalAction(AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN)

}