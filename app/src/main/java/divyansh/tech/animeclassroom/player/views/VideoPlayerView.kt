package divyansh.tech.animeclassroom.player.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : PlayerView(context, attrs, defStyleAttr), GestureDetector.OnGestureListener {

    init {
        controllerHideOnTouch = false
    }

    private val gestureDetector = GestureDetector(context, this)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (gestureDetector.onTouchEvent(event)) {
            performClick()
            if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return true
            }
        }
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        if (isControllerVisible) {
            hideController()
        } else {
            showController()
        }
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {

        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }
}