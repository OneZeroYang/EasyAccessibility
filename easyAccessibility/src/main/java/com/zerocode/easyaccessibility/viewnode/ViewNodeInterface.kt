package com.zerocode.easyaccessibility.viewnode


interface ViewNodeInterface {


    fun click() :Boolean

    fun longClick():Boolean

    fun twiceClick():Boolean

    fun inputText(string: String)
}