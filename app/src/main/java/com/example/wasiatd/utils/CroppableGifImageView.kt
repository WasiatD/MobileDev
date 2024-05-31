import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import pl.droidsonroids.gif.GifImageView

class CroppableGifImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GifImageView(context, attrs, defStyleAttr) {

    private var cropRect: RectF? = null

    fun setCropRect(rect: RectF) {
        cropRect = rect
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        cropRect?.let {
            canvas.save()
            canvas.clipRect(it)
            super.onDraw(canvas)
            canvas.restore()
        } ?: super.onDraw(canvas)
    }
}
