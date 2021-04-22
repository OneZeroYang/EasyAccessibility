package com.zerocode.easyaccessibility

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings


/**
 * 打开无障碍 设置服务页面
 */
fun Context.jumpAccessibilityServiceSettings(cls: Class<*> = EasyAccessibilityService::class.java) {
    if (!isOpenAccessibilityService)
        jumpAccessibilityServiceSettingsParen(cls)
}

/**
 * 是否获取了无障碍服务
 */
val isOpenAccessibilityService: Boolean get() = EasyAccessibilityService.getAccessibilityServiceNnll() != null







