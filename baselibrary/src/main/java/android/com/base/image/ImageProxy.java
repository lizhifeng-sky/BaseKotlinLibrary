package android.com.base.image;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.fragment.app.Fragment;

public class ImageProxy implements IImage {
    private IImage image = null;

    public ImageProxy(IImage image) {
        this.image = image;
    }


    @Override
    public IImage load(Context context, String url) {
        return image.load(context,url);
    }

    @Override
    public IImage load(Activity activity, String url) {
        return image.load(activity,url);
    }

    @Override
    public IImage load(Fragment context, String url) {
        return image.load(context,url);
    }

    @Override
    public IImage placeHolder(int placeHolderId) {
        image.placeHolder(placeHolderId);
        return image;
    }

    @Override
    public IImage errorHolder(int errorHolderId) {
        image.errorHolder(errorHolderId);
        return image;
    }

    @Override
    public IImage scaleType(ImageView.ScaleType scaleType) {
        image.scaleType(scaleType);
        return image;
    }

    @Override
    public IImage resize(int width, int height) {
        image.resize(width, height);
        return image;
    }

    @Override
    public IImage asRound(int radii, int radiiWidth, int radiiColor) {
        image.asRound(radii, radiiWidth, radiiColor);
        return image;
    }

    @Override
    public IImage asCircle(int radiiWidth, @ColorInt int radiiColor) {
        image.asCircle(radiiWidth, radiiColor);
        return image;
    }

    @Override
    public void into(ImageView imageView) {
        image.into(imageView);
    }
}
