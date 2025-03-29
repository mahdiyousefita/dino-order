package com.dino.order.mainpage.component

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class CustomSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    var swipeSensitivity = 1.0f // Default sensitivity

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // Adjust sensitivity by modifying the MotionEvent
        if (ev.action == MotionEvent.ACTION_MOVE) {
            // Scale the touch event based on the sensitivity
            val scaledY = ev.y * swipeSensitivity
            val modifiedEvent = MotionEvent.obtain(ev)
            modifiedEvent.setLocation(ev.x, scaledY)
            return super.onInterceptTouchEvent(modifiedEvent)
        }
        return super.onInterceptTouchEvent(ev)
    }
}