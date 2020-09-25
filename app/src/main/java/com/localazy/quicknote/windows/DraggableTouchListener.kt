package com.localazy.quicknote.windows

import android.content.Context
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.hypot


fun View.registerDraggableTouchListener(
    initialPosition: () -> Point,
    positionListener: (x: Int, y: Int) -> Unit
) {
    WindowHeaderTouchListener(context, this, initialPosition, positionListener)
}


class WindowHeaderTouchListener(
    context: Context,
    private val view: View,
    private val initialPosition: () -> Point,
    private val positionListener: (x: Int, y: Int) -> Unit
) : View.OnTouchListener {

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private val longClickInterval = ViewConfiguration.getLongPressTimeout()
    private var pointerStartX = 0
    private var pointerStartY = 0
    private var initialX = 0
    private var initialY = 0
    private var moving = false
    private var longClickPerformed = false
    private var timer: Timer? = null


    init {
        view.setOnTouchListener(this)
    }


    private fun scheduleLongClickTimer() {
        if (timer == null) {
            timer = Timer()
            timer?.schedule(timerTask {
                if (!moving && !longClickPerformed) {
                    view.post {
                        view.performLongClick()
                    }
                    longClickPerformed = true
                }
                cancelLongClickTimer()
            }, longClickInterval.toLong())
        }
    }


    private fun cancelLongClickTimer() {
        timer?.cancel()
        timer = null
    }


    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        when (motionEvent.action) {

            MotionEvent.ACTION_DOWN -> {
                pointerStartX = motionEvent.rawX.toInt()
                pointerStartY = motionEvent.rawY.toInt()
                with(initialPosition()) {
                    initialX = x
                    initialY = y
                }
                moving = false
                longClickPerformed = false
                scheduleLongClickTimer()
            }

            MotionEvent.ACTION_MOVE -> {
                if (!longClickPerformed) {
                    val deltaX = motionEvent.rawX - pointerStartX
                    val deltaY = motionEvent.rawY - pointerStartY
                    if (moving || hypot(deltaX, deltaY) > touchSlop) {
                        cancelLongClickTimer()
                        positionListener(initialX + deltaX.toInt(), initialY + deltaY.toInt())
                        moving = true
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                cancelLongClickTimer()
                if (!moving && !longClickPerformed) {
                    view.performClick()
                }
            }

        }

        return true
    }


}