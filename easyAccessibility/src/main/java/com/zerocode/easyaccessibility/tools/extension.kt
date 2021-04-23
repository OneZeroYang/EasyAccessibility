package com.zerocode.easyaccessibility.tools

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Pair
import android.view.ViewConfiguration
import android.view.WindowManager
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import com.zerocode.easyaccessibility.EasyAccessibilityService


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


/**
 * 同步手势
 * @param description GestureDescription
 * @return Boolean
 */
@RequiresApi(api = Build.VERSION_CODES.N)
fun doGestures(
    description: GestureDescription,
    onCancel: Function0<Unit>?
): Boolean {
    // 主线程不指定Handler
    val handler = if (Looper.myLooper() == Looper.getMainLooper()) null
    else HandlerThread("ges").let {
        it.start()
        Handler(it.looper)
    }
    val result = ResultBox(false)
    EasyAccessibilityService.getAccessibilityService().dispatchGesture(
        description,
        object : AccessibilityService.GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription) {
                result.setAndNotify(true)
            }

            override fun onCancelled(gestureDescription: GestureDescription) {
                onCancel?.invoke()
                result.setAndNotify(false)
            }
        }, handler
    ).also {
        if (!it) {
            return false
        }
    }
    return (result.blockedGet() ?: false).also {
        //结束 HanderThread
        handler?.looper?.quitSafely()
    }
}


/**
 * 异步手势
 * @param strokeList List<out StrokeDescription>
 */
@RequiresApi(api = Build.VERSION_CODES.N)
fun doGesturesAsync(strokeList: List<GestureDescription.StrokeDescription>) {
    val builder = GestureDescription.Builder()
    for (stroke in strokeList) {
        builder.addStroke(stroke)
    }
    EasyAccessibilityService.getAccessibilityService()?.dispatchGesture(builder.build(), null, null)
}


/**
 * 点转路径
 * @param points Array<Pair<Int, Int>>
 * @return Path
 */
private fun pointsToPath(points: Array<Pair<Int, Int>>): Path {
    val path = Path()
    if (points.isEmpty()) return path
    path.moveTo(points[0].first.toFloat(), points[0].second.toFloat())

    for (i in 1 until points.size) {
        path.lineTo(points[i].first.toFloat(), points[i].second.toFloat())
    }
    return path
}


/**
 * 根据点坐标生成路径 执行手势
 * @param duration Long
 * @param points Array<Pair<Int, Int>>
 * @param onCancel  操作被打断
 * @return Boolean
 */
@RequiresApi(Build.VERSION_CODES.N)
fun gesture(
    duration: Long,
    points: Array<Pair<Int, Int>>,
    onCancel: Function0<Unit>? = null
): Boolean {
    val path = pointsToPath(points)
    return playGestures(listOf(GestureDescription.StrokeDescription(path, 0, duration)), onCancel)
}


/**
 * 同步
 * @param strokes Array<out StrokeDescription>
 * @return Boolean
 */
@RequiresApi(api = Build.VERSION_CODES.N)
fun playGestures(
    strokeList: List<GestureDescription.StrokeDescription>,
    onCancel: Function0<Unit>? = null
): Boolean {
    val builder = GestureDescription.Builder()
    for (stroke in strokeList) {
        builder.addStroke(stroke)
    }
    return doGestures(builder.build(), onCancel)
}

/**
 *
 * @param x Int
 * @param y Int
 * @param delay Int
 * @return Boolean
 */
@RequiresApi(Build.VERSION_CODES.N)
fun pressWithTime(x: Int, y: Int, delay: Int): Boolean {
    return gesture(delay.toLong(), arrayOf(Pair(x, y)))
}


/**
 * 通过坐标点击
 */
@RequiresApi(Build.VERSION_CODES.N)
fun clickByCoordinate(x: Int, y: Int): Boolean =
    pressWithTime(x, y, ViewConfiguration.getTapTimeout() + 50)

/**
 * 长按 相对坐标
 * @param x Int
 * @param y Int
 * @return Boolean
 */
@RequiresApi(Build.VERSION_CODES.N)
fun longClickByCoordinate(x: Int, y: Int) = pressWithTime(
    x, y, (ViewConfiguration.getLongPressTimeout() + 200)
)

/**
 * 两点间滑动
 * @param x1 Int
 * @param y1 Int
 * @param x2 Int
 * @param y2 Int
 * @param dur Int
 * @return Boolean
 */
@RequiresApi(Build.VERSION_CODES.N)
fun scrollByCoordinate(x1: Int, y1: Int, x2: Int, y2: Int, dur: Int): Boolean =
    gesture(
        dur.toLong(), arrayOf(
            Pair(x1, y1),
            Pair(x2, y2)
        )
    )


val deviceHeight: Int
    get() {
        val m = DisplayMetrics()
        EasyAccessibilityService.getAccessibilityService().display?.getRealMetrics(m)
        return m.heightPixels
    }


val deviceWidth: Int
    get() {
        val m = DisplayMetrics()
        EasyAccessibilityService.getAccessibilityService().display?.getRealMetrics(m)
        return m.widthPixels
    }


/**
 * 上滑
 */
@RequiresApi(Build.VERSION_CODES.N)
fun scrollUpByCoordinate(): Boolean {
    val mtop = (deviceHeight * 0.1).toInt()
    val mBottom = (deviceHeight * 0.85).toInt()
    val xCenter = (deviceWidth * 0.5).toInt()
    return scrollByCoordinate(xCenter, mBottom, xCenter, mtop, 400)
}


/**
 * 下滑
 */
@RequiresApi(Build.VERSION_CODES.N)
fun scrollDownByCoordinate(): Boolean {
    val mtop = (deviceHeight * 0.15).toInt()
    val mBottom = (deviceHeight * 0.9).toInt()
    val xCenter = (deviceWidth * 0.5).toInt()
    return scrollByCoordinate(xCenter, mtop, xCenter, mBottom, 400)
}

/**
 * 左滑
 */
@RequiresApi(Build.VERSION_CODES.N)
fun scrollLeftByCoordinate(): Boolean {
    return scrollByCoordinate(
        (deviceWidth * 0.9).toInt(),
        (deviceHeight * 0.5).toInt(),
        (deviceWidth * 0.15).toInt(),
        (deviceHeight * 0.5).toInt(),
        400
    )
}


/**
 * 右滑
 */
@RequiresApi(Build.VERSION_CODES.N)
fun scrollRightByCoordinate(): Boolean {
    return scrollByCoordinate(
        (deviceWidth * 0.15).toInt(),
        (deviceHeight * 0.5).toInt(),
        (deviceWidth * 0.9).toInt(),
        (deviceHeight * 0.5).toInt(),
        400
    )
}



