package com.zerocode.easyaccessibility

import android.content.Context
import com.zerocode.easyaccessibility.viewnode.ViewNode

object EasyAbyService {


    /**
     * 打开无障碍服务
     */
    fun Context.jumpAccessibilityServiceSettings() {
        jumpAccessibilityServiceSettings(EasyAccessibilityService::class.java)
    }

    /**
     * 是否获取了无障碍服务
     */
    fun isOpenAccessibilityService(): Boolean = isOpenAccessibilityService


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






}