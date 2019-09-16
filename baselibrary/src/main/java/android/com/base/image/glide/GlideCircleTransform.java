package android.com.base.image.glide;

import android.content.Context;
import android.graphics.*;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by lizhifeng on 2018/7/6.
 */

public class GlideCircleTransform extends BitmapTransformation {

    private float width = 0f;
    private int color = Color.TRANSPARENT;

    public GlideCircleTransform() {
    }

    public GlideCircleTransform(Context context) {
        super(context);
    }

    public GlideCircleTransform(float width, int color) {
        this.width = width;
        this.color = color;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        //画圆环
        if (width != 0f) {
            Paint paint_2 = new Paint();
            paint_2.setColor(color);
            paint_2.setAntiAlias(true);
            paint_2.setStyle(Paint.Style.STROKE);
            paint_2.setStrokeWidth(width);
            canvas.drawCircle(r, r, r-width/2, paint_2);
        }
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
