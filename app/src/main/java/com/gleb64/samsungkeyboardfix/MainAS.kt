package com.gleb64.samsungkeyboardfix
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MainAS : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val tempInfo = serviceInfo
        tempInfo.flags = tempInfo.flags or AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
        serviceInfo = tempInfo
    }

    private fun checkBlackScreen(rootNode: AccessibilityNodeInfo?) {
        if (rootNode == null) return

        if(rootNode.className=="android.widget.TextView" && rootNode.packageName=="com.samsung.android.honeyboard" && rootNode.text == " ") {
            //Black screen appeared
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_DPAD_CENTER)
            return
        }

        for (i in 0 until rootNode.childCount) {
            checkBlackScreen(rootNode.getChild(i))
        }

    }

    override fun onAccessibilityEvent(e: AccessibilityEvent) {
        if (e.packageName?.equals("com.samsung.android.honeyboard")!!) {
            checkBlackScreen(rootInActiveWindow)
        }

    }


    override fun onInterrupt() {}




}