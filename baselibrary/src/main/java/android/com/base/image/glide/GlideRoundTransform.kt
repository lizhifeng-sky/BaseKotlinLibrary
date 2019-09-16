package android.com.base.image.glide

import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * Created by lizhifeng on 2018/5/15.
 */

class GlideRoundTransform() : BitmapTransformation() {
    private var radius = 0f
    private var width = 0f
    private var color = Color.TRANSPARENT
    private var dp = 0

    constructor(dp: Int, width: Int, color: Int) : this() {
        this.radius = Resources.getSystem().displayMetrics.density * dp
        this.width = Resources.getSystem().displayMetrics.density * width
        this.color = color
    }

    constructor(dp: Int) : this() {
        this.dp = dp
    }

    init {
        this.radius = Resources.getSystem().displayMetrics.density * dp
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundCrop(pool, toTransform)
    }

    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null

        val result = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(result)
        val paint = Paint()
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())

        canvas.drawRoundRect(rectF, radius, radius, paint)
//        //画圆环
        if (width > 0f) {
            val rectF_2 = RectF(
                0f + width / 2f,
                0f + width / 2f,
                source.width.toFloat() - width / 2f,
                source.height.toFloat() - width / 2f
            )
            val paint_2 = Paint()
            paint_2.color = color
            paint_2.isAntiAlias = true
            paint_2.style = Paint.Style.STROKE;
            paint_2.strokeWidth = width
            canvas.drawRoundRect(rectF_2, radius, radius, paint_2)
        }
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }
}
