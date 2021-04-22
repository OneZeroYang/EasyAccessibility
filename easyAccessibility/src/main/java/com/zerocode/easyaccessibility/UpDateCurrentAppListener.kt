package com.zerocode.easyaccessibility
/**
 * 页面更新监听
 * @author ZeroCode
 * @date 2021/4/22:10:57
 */
interface UpDateCurrentAppListener {
    fun onUpDateCurrentApp(pkg: String, classNameStr: String)
}