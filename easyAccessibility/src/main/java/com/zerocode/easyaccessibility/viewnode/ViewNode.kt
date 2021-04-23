package com.zerocode.easyaccessibility.viewnode

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.os.Bundle
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import java.lang.RuntimeException

/**
 * 视图节点
 * @author ZeroCode
 * @date 2021/4/22:11:31
 */
class ViewNode(private val node: AccessibilityNodeInfo, path: Int = 0) : ViewNodeInterface {

    /**
     * 层级  默认为0
     */
    val path = path

    /**
     * 当前节点ID
     */
    val id: String? get() = node.viewIdResourceName


    /**
     * 当前文本信息
     */
    var text: CharSequence?
        get() {
            node.refresh()
            return node.text
        }
        set(v) {
            val arg = Bundle()
            arg.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, v)
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arg)
        }


    /**
     * 当前hint信息
     */
    var hintText: CharSequence?
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            node.hintText
        } else {
            null
        }
        @RequiresApi(Build.VERSION_CODES.O)
        set(value) {
            node.hintText = value
        }


    /**
     * 当前节点描述
     */
    val desc: String? get() = node.contentDescription?.toString()


    /**
     * 当前class的类名
     */
    val className: String get() = node.className.toString()


    /**
     * 父节点
     */
    val parent: ViewNode get() = ViewNode(node.parent, path - 1)


    /**
     * 子节点的数量
     */
    val childCount: Int get() = node.childCount


    /**
     * 所有的子节点
     */
    val child: ArrayList<ViewNode>
        get() {
            val list = arrayListOf<ViewNode>()
            for (i in 0 until childCount) {
                list.add(getChild(i))
            }
            return list
        }


    /**
     * 改节点是否可见
     */
    val isVisible: Boolean get() = node.isVisibleToUser


    /**
     * 通过当前节点获取子节点
     */
    fun getChild(childCount: Int): ViewNode {
        return ViewNode(node.getChild(childCount), path + 1)
    }


    /**
     * 此节点是否可单击。
     */
    fun isClickable() = node.isClickable


    /**
     * 节点是否可编辑。
     */
    fun isEditable() = node.isEditable

    /**
     * 获取此节点是否启用。
     */
    fun isEnabled() = node.isEnabled

    /**
     * 获取是否选择此节点。
     */
    fun isSelected() = node.isSelected


    /**
     * 触发单击
     */
    override fun click() = node.performAction(AccessibilityNodeInfo.ACTION_CLICK)


    /**
     * 触发长按
     */
    override fun longClick() = node.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK)


    /**
     * 连续2次点击
     * TODO 连续2次点击
     */
    override fun twiceClick(min: Long): Boolean {
        val longClick1 = longClick()
        Thread.sleep(min)
        val longClick2 = longClick()
        return longClick1 && longClick2
    }

    /**
     * 输入文字
     */
    override fun inputText(string: String) {
        text = string
    }




}