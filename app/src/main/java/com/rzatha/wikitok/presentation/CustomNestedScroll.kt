package com.rzatha.wikitok.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class CustomNestedScrollView : NestedScrollView {

    private var isScrollable = true // По умолчанию прокрутка включена

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        // Разрешаем дочерним элементам первыми обрабатывать события
        parent.requestDisallowInterceptTouchEvent(true)
        return super.onInterceptTouchEvent(e)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (clampedY) {
            // Если достигнут конец скролла, разрешаем родителю обрабатывать события
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }
}