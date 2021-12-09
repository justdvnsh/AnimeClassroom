package divyansh.tech.animeclassroom.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import divyansh.tech.animeclassroom.R
import kotlin.math.abs

class SingleViewTouchableMotionLayout(context: Context, attributeSet: AttributeSet? = null) :
    MotionLayout(context, attributeSet) {

    private val touchableArea by lazy {
        findViewById<View>(R.id.videoPlayerFragment)
    }

    private val clickableArea by lazy {
        findViewById<View>(R.id.videoPlayerFragment)
    }
    private var startX: Float = 0f
    private var startY: Float = 0f
    private val viewRect = Rect()
    private var touchStarted = false

    private fun touchEventInsideTargetView(v: View, ev: MotionEvent): Boolean {
        if (ev.x > v.left && ev.x < v.right) {
            if (ev.y > v.top && ev.y < v.bottom) {
                return true
            }
        }
        return false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchEventInsideTargetView(clickableArea, ev)) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x
                    startY = ev.y
                }

                MotionEvent.ACTION_UP -> {
                    val endX = ev.x
                    val endY = ev.y
                    if (isAClick(startX, endX, startY, endY)) {
                        if (doClickTransition()) {
                            return true
                        }
                    }
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun doClickTransition(): Boolean {
        var isClickHandled = false
        if (this.progress > 0.95F) {
            this.transitionToStart()
            isClickHandled = true
        }
        return isClickHandled
    }

    private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
        val differenceX = abs(startX - endX)
        val differenceY = abs(startY - endY)
        return !/* =5 */(differenceX > 200 || differenceY > 200)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchStarted = false
                return super.onTouchEvent(event)
            }
        }
        if (!touchStarted) {
            touchableArea.getHitRect(viewRect)
            touchStarted = viewRect.contains(event.x.toInt(), event.y.toInt())
        }
        return touchStarted && super.onTouchEvent(event)
    }
}