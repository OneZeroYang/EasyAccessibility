package com.zerocode.easyaccessibility.tools

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.zerocode.easyaccessibility.EasyAccessibilityService
import com.zerocode.easyaccessibility.viewnode.ViewNode
import com.zerocode.easyaccessibility.viewnode.ViewRoot


val mViewRoot =ViewRoot.getInstance()

fun Intent.putComponent(pkg: String, cls: Class<*>) {
    val cs = ComponentName(pkg, cls.name).flattenToString()
    val bundle = Bundle()
    bundle.putString(":settings:fragment_args_key", cs)
    putExtra(":settings:fragment_args_key", cs)
    putExtra(":settings:show_fragment_args", bundle)
}

fun Context.jumpAccessibilityServiceSettingsParen(cls: Class<*> = EasyAccessibilityService::class.java){
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putComponent(packageName, cls)
    startActivity(intent)
}


fun findByTxt(txt:String): ViewNode? {
   return mViewRoot.findByTxt(txt)
}


fun findById(Id:String): ViewNode? {
    return mViewRoot.findById(Id)
}


fun findByDesc(Desc:String):ViewNode?{
    return mViewRoot.findByDesc(Desc)
}

