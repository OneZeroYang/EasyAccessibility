# EasyAccessibility

一个简单高效的无障碍服务工具

## 如何使用该库 

在项目根目录下  build.gradle 文件中 添加

maven { url 'https://jitpack.io' }
 
如下：
`
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
`

在 app 目录下build.gradle 文件中 添加

如下
`
dependencies {
	        implementation 'com.github.OneZeroYang:EasyAccessibility:1.1.0'
	}
`

## 如何初始化

1.在`AndroidManifest.xml`中声明无障碍服务


````xml

<service
            android:name="com.zerocode.easyaccessibility.EasyAccessibilityService"
            android:description="@string/accessibility_describe"
            android:label="Service Demo"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
            <intent-filter>
                <action android:name="android.accessibilityservice.AccessibilityService" />
            </intent-filter>
            <meta-data
                android:name="android.accessibilityservice"
                android:resource="@xml/accessibility_config" />
            >

        </service>

````


2.在`res`下创建xml文件夹，新建accessibility_config.xml ,内容如下：

````xml

<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeWindowStateChanged"
    android:accessibilityFeedbackType="feedbackAllMask"
    android:accessibilityFlags="flagIncludeNotImportantViews|flagReportViewIds|flagRetrieveInteractiveWindows|flagRequestEnhancedWebAccessibility"
    android:canRetrieveWindowContent="true"
    android:description="@string/accessibility_describe"
    android:notificationTimeout="10"
    android:canPerformGestures="true"
    android:canRequestEnhancedWebAccessibility="true"
    android:settingsActivity=".MainActivity"
    android:summary="基础导航/视图检索操作"/>

````

## 如何使用api

### 1.查找控件

通过文本查找
````kotlin
    EasyApi.withTxt("登录")
````

通过id查找
````kotlin
    EasyApi.withId("/login")
````

通过Desc查找
````kotlin
    EasyApi.withDesc("登录")
````

    
### 2.基于控件的触发事件

单击事件

````kotlin
    EasyApi.withDesc("登录").click()
````            

长按事件
````kotlin
    EasyApi.withDesc("登录").longClick()
````        
    
输入文字
````kotlin
    EasyApi.withId("/userName").inputText("123456")
````   

### 3.全局事件

返回
````kotlin
    EasyApi.back()
````

返回桌面
````kotlin
    EasyApi.backHome()
````


电源菜单
````kotlin
    EasyApi.powerDialog()
````


通知栏
````kotlin
    EasyApi.showNotificationBar()
````


最近任务
````kotlin
    EasyApi.workList()
````

### 4.全局手势


通过坐标点击 点击坐标0,0
````kotlin
    EasyApi.click(0,0)
````

通过坐标长按
````kotlin
    EasyApi.longClick(0,0)
````

两点之间的滑动
````kotlin
    EasyApi.scroll(0,0,10,10)
````

上滑
````kotlin
    EasyApi.scrollUp()
````

下滑
````kotlin
    EasyApi.scrollDown()
````

左滑
````kotlin
    EasyApi.scrollLeft()
````

右滑
````kotlin
    EasyApi.右滑()
````


其他api请下载源码自行查看
作者邮箱 102245912@qq.com 