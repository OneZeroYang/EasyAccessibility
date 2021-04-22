package com.zerocode.easyaccessibility.viewnode

import android.util.Log
import com.zerocode.easyaccessibility.EasyAccessibilityService

/**
 * 根节点
 * @author ZeroCode
 * @date 2021/4/22:13:56
 */
class ViewRoot private constructor() {


    /**
     * 节点
     */
    val mViewNode: ViewNode
        get() = ViewNode(
            EasyAccessibilityService.getAccessibilityService().getRootView()
        )

    companion object {
        var mViewRoot: ViewRoot? = null
        fun getInstance(): ViewRoot {
            if (mViewRoot == null) {
                synchronized(this) {
                    if (mViewRoot == null) {
                        mViewRoot = ViewRoot()
                    }
                }
            }
            return mViewRoot!!
        }
    }


    private fun findRecursion(
        type: Int,
        txt: String,
        viewNode: ViewNode,
        depth: Int = 0,
        list: ArrayList<ViewNode>
    ): ArrayList<ViewNode> {
        //这个是防止栈溢出
        if (depth > 50) {
            return list
        }


        if (viewNode.childCount == 0) {
            //已经没有子控件了
            if (judgeView(type, txt, viewNode))
                list.add(viewNode)
        } else {
            //这里可能导致栈溢出
            viewNode.child.forEach {
                findRecursion(type, txt, it, depth + 1,list)
            }
        }
        return list
    }


    /**
     * 判断条件
     */
    private fun judgeView(type: Int, txt: String, viewNode: ViewNode): Boolean {

        return when (type) {
            1 -> txt == viewNode.text  //通过 txt 比对
            2 -> txt == viewNode.desc  //通过 desc 描述 对比
            3 -> txt == viewNode.id    //通过 控件 的 id 对比
            else -> false
        }
    }


    /**
     *  txt 匹配
     */
    fun findByTxt(txt: String): ViewNode? {
        return findRecursion(1, txt, mViewNode, list = arrayListOf()).let {
            if (it.isNullOrEmpty()) null else
                it[0]
        }
    }

    /**
     *  desc 匹配
     */
    fun findByDesc(desc: String): ViewNode? {
        return findRecursion(2, desc, mViewNode, list = arrayListOf()).let {
            if (it.isNullOrEmpty()) null else
                it[0]
        }
    }

    /**
     *  id 匹配
     */
    fun findById(id: String): ViewNode? {
        return findRecursion(3, id, mViewNode, list = arrayListOf()).let {
            if (it.isNullOrEmpty()) null else
                it[0]
        }
    }


}