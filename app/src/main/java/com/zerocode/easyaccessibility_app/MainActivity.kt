package com.zerocode.easyaccessibility_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zerocode.easyaccessibility.EasyApi
import com.zerocode.easyaccessibility.tools.isOpenAccessibilityService
import com.zerocode.easyaccessibility.tools.jumpAccessibilityServiceSettings

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread(){
            while (true){
                Thread.sleep(1000)
                Log.e("ssssssssssssssssssss", "是否获取了无障碍服务: $isOpenAccessibilityService")
                if (isOpenAccessibilityService){
                    break
                }
            }
            Thread.sleep(4000)
            val click = EasyApi.withTxt("设置")
            click?.click()


        }.start()
        jumpAccessibilityServiceSettings()






    }
}