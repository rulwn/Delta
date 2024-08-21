package RecycleViewHelper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class Redondeador(private val radius: Float) : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("rounded_corners".toByteArray(Charsets.UTF_8))
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val bitmap = pool.get(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val rect = RectF(0f, 0f, toTransform.width.toFloat(), toTransform.height.toFloat())
        paint.isAntiAlias = true
        canvas.drawRoundRect(rect, radius, radius, paint)
        paint.xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(toTransform, 0f, 0f, paint)
        return bitmap
    }
}